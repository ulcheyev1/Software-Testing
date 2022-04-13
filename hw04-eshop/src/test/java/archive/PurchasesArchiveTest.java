package archive;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import shop.Item;
import shop.Order;
import shop.ShoppingCart;
import shop.StandardItem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PurchasesArchiveTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private PurchasesArchive purchasesArchive;
    private HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchive;
    private ArrayList<Order> mockedOrderArchive;

    private StandardItem mockedItemN1;
    private StandardItem mockedItemN2;

    private ItemPurchaseArchiveEntry mockedItemPurchaseArchiveEntryN1;
    private ItemPurchaseArchiveEntry mockedItemPurchaseArchiveEntryN2;


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        mockedItemN1 = mock(StandardItem.class);
        mockedItemN2 = mock(StandardItem.class);

        itemPurchaseArchive = new HashMap<>();
        mockedOrderArchive = mock(ArrayList.class);

        mockedItemPurchaseArchiveEntryN1 = mock(ItemPurchaseArchiveEntry.class);
        mockedItemPurchaseArchiveEntryN2 = mock(ItemPurchaseArchiveEntry.class);

        when(mockedItemN1.getID()).thenReturn(1);
        when(mockedItemN2.getID()).thenReturn(2);
        when(mockedItemN1.toString()).thenReturn("Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5");
        when(mockedItemN2.toString()).thenReturn("Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10");

        when(mockedItemPurchaseArchiveEntryN1.toString()).thenReturn("ITEM  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5   HAS BEEN SOLD 1 TIMES");
        when(mockedItemPurchaseArchiveEntryN2.toString()).thenReturn("ITEM  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10   HAS BEEN SOLD 1 TIMES");
        when(mockedItemPurchaseArchiveEntryN1.getRefItem()).thenReturn(mockedItemN1);
        when(mockedItemPurchaseArchiveEntryN2.getRefItem()).thenReturn(mockedItemN2);


    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printOutput_checkOutput_correctOutput() {
        itemPurchaseArchive.put(1, mockedItemPurchaseArchiveEntryN1);
        itemPurchaseArchive.put(2, mockedItemPurchaseArchiveEntryN2);
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);
        String expectedString1 = "ITEM PURCHASE STATISTICS:\r\n" +
                "ITEM  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5   HAS BEEN SOLD 1 TIMES\r\n" +
                "ITEM  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10   HAS BEEN SOLD 1 TIMES";
        purchasesArchive.printItemPurchaseStatistics();
        Assertions.assertEquals(expectedString1, outContent.toString().trim());
        outContent.reset();
    }

    @Test
    public void  getHowManyTimesHasBeenItemSold_CheckHowManyTimesHasAnItemBeenSold_correctNumber(){
        itemPurchaseArchive.put(1, mockedItemPurchaseArchiveEntryN1);
        itemPurchaseArchive.put(2, mockedItemPurchaseArchiveEntryN2);
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);

        when(mockedItemPurchaseArchiveEntryN1.getCountHowManyTimesHasBeenSold()).thenReturn(2);
        when(mockedItemPurchaseArchiveEntryN2.getCountHowManyTimesHasBeenSold()).thenReturn(8);

        Assertions.assertEquals(2, purchasesArchive.getHowManyTimesHasBeenItemSold(mockedItemN1));
        Assertions.assertEquals(8, purchasesArchive.getHowManyTimesHasBeenItemSold(mockedItemN2));

        Mockito.verify(mockedItemPurchaseArchiveEntryN1).getCountHowManyTimesHasBeenSold();
        Mockito.verify(mockedItemPurchaseArchiveEntryN2).getCountHowManyTimesHasBeenSold();

    }
    @Test
    public void checkHowManyTimesHasBeenItemSoldWhichNotSoldYet_returns0(){
        Item mockedItedWhichNotSoldYet = mock(StandardItem.class);

        itemPurchaseArchive.put(1, mockedItemPurchaseArchiveEntryN1);
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);

        when(mockedItemPurchaseArchiveEntryN1.getCountHowManyTimesHasBeenSold()).thenReturn(2);
        when(mockedItemPurchaseArchiveEntryN2.getCountHowManyTimesHasBeenSold()).thenReturn(8);

        Assertions.assertEquals(0, purchasesArchive.getHowManyTimesHasBeenItemSold(mockedItedWhichNotSoldYet));

        verify(mockedItemPurchaseArchiveEntryN1, times(0)).getCountHowManyTimesHasBeenSold();
        verify(mockedItemPurchaseArchiveEntryN2, times(0)).getCountHowManyTimesHasBeenSold();
    }

    @Test
    public void putOrderToPurchasesArchive_putNewOrder_incrementSize(){

        ArrayList<Item> items = new ArrayList<>();
        items.add(mockedItemN2);

        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Yevgen", "CVUT");
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);

        // Size before put item in order archive. Put 1 item.
        itemPurchaseArchive.put(1, mockedItemPurchaseArchiveEntryN1);
        int sizeBefore = itemPurchaseArchive.size();

        // Size after put item in order archive
        purchasesArchive.putOrderToPurchasesArchive(order);
        int sizeAfter = itemPurchaseArchive.size();

        // Statistics Validation
        String expectedOutput = "ITEM PURCHASE STATISTICS:\r\n" +
                "ITEM  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5   HAS BEEN SOLD 1 TIMES\r\n" +
                "ITEM  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10   HAS BEEN SOLD 1 TIMES";
        purchasesArchive.printItemPurchaseStatistics();

        Assertions.assertEquals(sizeBefore, sizeAfter-1);
        Assertions.assertEquals(expectedOutput, outContent.toString().trim());
        verify(mockedOrderArchive).add(order);
        outContent.reset();
    }

    @Test
    public void putExistingItemsToPurchasesArchive_putOrderToArchive_increaseSoldCountAndSizeDoesnotChange() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(mockedItemN1);

        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Yevgen", "CVUT");
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);

        //Put itemN1
        itemPurchaseArchive.put(1, mockedItemPurchaseArchiveEntryN1);

        // Size before put item in order archive
        int sizeBefore = itemPurchaseArchive.size();

        // Put itemN1 in order archive
        purchasesArchive.putOrderToPurchasesArchive(order);

        // Size after put item in order archive
        int sizeAfter = itemPurchaseArchive.size();

        //Check increase count
        verify(mockedItemPurchaseArchiveEntryN1).increaseCountHowManyTimesHasBeenSold(1);
        verify(mockedOrderArchive).add(order);
        Assertions.assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void TestConstructorPurchasesArchive_checkStatisticsAndSameIds(){
        itemPurchaseArchive.put(1, mockedItemPurchaseArchiveEntryN1);
        itemPurchaseArchive.put(2, mockedItemPurchaseArchiveEntryN2);
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);

        // Statistics check
        String expectedString1 = "ITEM PURCHASE STATISTICS:\r\n" +
                "ITEM  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5   HAS BEEN SOLD 1 TIMES\r\n" +
                "ITEM  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10   HAS BEEN SOLD 1 TIMES";
        purchasesArchive.printItemPurchaseStatistics();

        Assertions.assertEquals(1, itemPurchaseArchive.get(1).getRefItem().getID());
        Assertions.assertEquals(2, itemPurchaseArchive.get(2).getRefItem().getID());
        Assertions.assertEquals(expectedString1, outContent.toString().trim());
    }


}





//

