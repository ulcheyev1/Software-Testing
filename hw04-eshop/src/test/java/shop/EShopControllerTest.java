package shop;

import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static shop.EShopController.*;

public class EShopControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    Item[] storageItems = {
            new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
            new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
            new StandardItem(3, "Screwdriver", 200, "TOOLS", 5)
    };
    int[] itemCount = {10, 6, 4};


    @BeforeEach
    public void setUpStreams() {
        EShopController.startEShop();
        for (int i = 0; i < storageItems.length; i++) {
            EShopController.getStorage().insertItems(storageItems[i], itemCount[i]);
        }
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }


    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void eShopControllerProcessTest_validShoppingProcess_everythingOk() throws NoItemInStorage {

        // 1. ADDING GOODS
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(storageItems[0]);
        cart.addItem(storageItems[1]);
        cart.addItem(storageItems[1]);
        cart.addItem(storageItems[2]);

        // Check total cart price and number of items
        Assertions.assertEquals(17200.0, cart.getTotalPrice());
        Assertions.assertEquals(4, cart.getItemsCount());

        // Output check
        String expectedStringAdding =
                "Item with ID 1 added to the shopping cart.\r\n" +
                        "Item with ID 2 added to the shopping cart.\r\n" + "Item with ID 2 added to the shopping cart.\r\n" +
                        "Item with ID 3 added to the shopping cart.";
        Assertions.assertEquals(expectedStringAdding, outContent.toString().trim());
        outContent.reset();

        // 2. REMOVE GOODS FROM THE SHOPPING CART
        cart.removeItem(1);

        // Check total cart price and number of items
        Assertions.assertEquals(17200.0 - 5000, cart.getTotalPrice());
        Assertions.assertEquals(3, cart.getItemsCount());

        // Output check
        String expectedStringRemove = "Item with ID 1 removed from the shopping cart.";
        Assertions.assertEquals(expectedStringRemove, outContent.toString().trim());

        // 3. PURCHASE TEST. TEST OF CHANGING THE QUANTITY OF THE PRODUCT IN THE ARCHIVE AND IN THE STORAGE.
        // Purchase
        purchaseShoppingCart(cart, "Yevgen", "CVUT");


        //Check storage quantity. The product with id 1 has been removed from the shopping cart.
        Assertions.assertEquals(10, EShopController.getStorage().getItemCount(storageItems[0]));
        Assertions.assertEquals(6 - 2, EShopController.getStorage().getItemCount(storageItems[1]));
        Assertions.assertEquals(4 - 1, EShopController.getStorage().getItemCount(storageItems[2]));

        //Check archive quantity. The product with id 1 has been removed from the shopping cart.
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[0]));
        Assertions.assertEquals(2, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[1]));
        Assertions.assertEquals(1, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[2]));
    }

    @Test
    public void eShopControllerProcessTest_invalidShoppingProcessWithEmptyCart_NotIncreaseCountOfSold() throws NoItemInStorage {
        // 1. CREATE CART AND ADD 1 ITEM
        ShoppingCart cart1 = new ShoppingCart();
        cart1.addItem(storageItems[0]);

        // 2. REMOVE GOODS FROM THE SHOPPING CART
        cart1.removeItem(1);
        outContent.reset();

        // Check total cart price and number of items
        Assertions.assertEquals(0, cart1.getTotalPrice());
        Assertions.assertEquals(0, cart1.getItemsCount());

        // Purchase
        purchaseShoppingCart(cart1, "Yevgen", "CVUT");
        String expectedStringEmptyCart = "Error: shopping cart is empty";
        Assertions.assertEquals(expectedStringEmptyCart, outContent.toString().trim());


        //Check storage quantity. Nothing should change
        Assertions.assertEquals(10, EShopController.getStorage().getItemCount(storageItems[0]));
        Assertions.assertEquals(6, EShopController.getStorage().getItemCount(storageItems[1]));
        Assertions.assertEquals(4, EShopController.getStorage().getItemCount(storageItems[2]));

        //Check archive quantity. Nothing should change
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[0]));
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[1]));
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[2]));
    }

    @Test
    public void eShopControllerProcessTest_invalidShoppingProcessItemOutOfStock_thowsException() throws NoItemInStorage {
        // 1. ADDING GOODS
        ShoppingCart cart1 = new ShoppingCart();
        cart1.addItem(storageItems[0]);
        // Item storageItems[2] has quantiny 4. To test we will put more in the shopping cart
        cart1.addItem(storageItems[2]);
        cart1.addItem(storageItems[2]);
        cart1.addItem(storageItems[2]);
        cart1.addItem(storageItems[2]);
        cart1.addItem(storageItems[2]);
        cart1.addItem(storageItems[2]);

        // Check total cart price and number of items
        Assertions.assertEquals(5000 + 6 * 200, cart1.getTotalPrice());
        Assertions.assertEquals(7, cart1.getItemsCount());

        // 2. Trying to remove a non-existent item from shopping cart will not change anything
        cart1.removeItem(9999);
        Assertions.assertEquals(5000 + 6 * 200, cart1.getTotalPrice());
        Assertions.assertEquals(7, cart1.getItemsCount());

        // Purchase throws exception
        Assertions.assertThrows(NoItemInStorage.class, () -> purchaseShoppingCart(cart1, "Yevgen", "CVUT"));

        //Check archive quantity. Nothing should change
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[0]));
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[1]));
        Assertions.assertEquals(0, EShopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[2]));


    }

}


