package shop;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

public class OrderTest {
    Order order;
    Order order1;
    ArrayList<Item> list = new ArrayList();

    @BeforeEach
    public void setOrder() {
        Item item = new Item(2, "Cup", 250, "Kitchen") {
        };
        this.list.add(item);
    }

    @Test
    public void constructorOrder_createOrder_parametersAreEqual() {
        this.order = new Order(new ShoppingCart(this.list), "Yevgen", "Ololo", 1);
        ArrayList<Item> expectedItems = this.list;
        String expectedName = "Yevgen";
        String expectedAddress = "Ololo";
        int expectedState = 1;
        Assertions.assertEquals(expectedItems, this.order.getItems());
        Assertions.assertEquals(expectedName, this.order.getCustomerName());
        Assertions.assertEquals(expectedAddress, this.order.getCustomerAddress());
        Assertions.assertEquals(expectedState, this.order.getState());
    }

    @Test
    public void constructorOrder_createOrderWithoutState_parametersAreEqual() {
        this.order1 = new Order(new ShoppingCart(this.list), "Yevgen", "Ololo");
        ArrayList<Item> expectedItems = this.list;
        String expectedName = "Yevgen";
        String expectedAddress = "Ololo";
        int expectedState = 0;
        Assertions.assertEquals(expectedItems, this.order1.getItems());
        Assertions.assertEquals(expectedName, this.order1.getCustomerName());
        Assertions.assertEquals(expectedAddress, this.order1.getCustomerAddress());
        Assertions.assertEquals(expectedState, this.order1.getState());
    }

    @Test
    public void constructorOrder_shoppingCartIsNull_NullPointerException(){
        Assertions.assertThrows(NullPointerException.class, ()-> new Order(null, "Yevgen", "Ololo"));
    }
}
