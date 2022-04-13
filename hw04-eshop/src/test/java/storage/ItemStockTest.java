package storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import shop.StandardItem;

public class ItemStockTest {
    ItemStock itemStock;

    @BeforeEach
    public void setItemStock(){
        itemStock = new ItemStock(new StandardItem(5, "Drink", 15, "Food", 5));
    }

    @Test
    public void testTtemStockConstructor_createItemStock(){
        int id = 32;
        String name = "Drink";
        int price = 15;
        int loyaltyPoints = 3;
        String category = "Food";
        StandardItem sItem1 = new StandardItem(id,name,price,category,loyaltyPoints);
        ItemStock itemStock = new ItemStock(sItem1);
        Assertions.assertTrue(sItem1.equals(itemStock.getItem()));
        Assertions.assertEquals(0, itemStock.getCount());
    }


    @ParameterizedTest(name = "Increase in count by {0}")
    @ValueSource(ints = {5, 10, 15, 20})
    public void increaseItemCount_increasingCount_returnNewCount(int number){
        int actualCount = itemStock.getCount();
        itemStock.IncreaseItemCount(number);
        Assertions.assertEquals(actualCount + number, itemStock.getCount());
    }

    @ParameterizedTest(name = "Decrease in count by {0}")
    @ValueSource(ints = {5, 10, 15, 20})
    public void decreaseItemCount_decreasingCount_returnNewCount(int number){
        int actualCount = itemStock.getCount();
        itemStock.decreaseItemCount(number);
        Assertions.assertEquals(actualCount - number, itemStock.getCount());
    }

    @ParameterizedTest(name = "The number of items after increasing in count by {0} than decreasing in count by {1} should be {2}")
    @CsvSource({"5, 3, 2", "5, 2, 3"})
    public void increaseAndDecreaseItemCount_returnNewCount(int increase, int decrease, int result){
        itemStock.IncreaseItemCount(increase);
        itemStock.decreaseItemCount(decrease);
        Assertions.assertEquals(result, itemStock.getCount());
    }
}
