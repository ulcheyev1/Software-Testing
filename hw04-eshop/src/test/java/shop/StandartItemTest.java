package shop;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import shop.StandardItem;
public class StandartItemTest {

    StandardItem standartItem;
    StandardItem standartItemCopy;

    int id;
    String name;
    float price;
    String category;
    int loyaltyPoints;

    @BeforeEach
    public void setStandardItem() {
        id = 12345;
        name = "Ball";
        price = 15;
        category = "Sport";
        loyaltyPoints = 6;
        standartItem = new StandardItem(id, name, price, category, loyaltyPoints);
    }

    @Test
    public void constructorStandartItem_createStandartItem_parametersAreEqual() {
        Assertions.assertEquals(id, standartItem.getID());
        Assertions.assertEquals(name, standartItem.getName());
        Assertions.assertEquals(price, standartItem.getPrice());
        Assertions.assertEquals(category, standartItem.getCategory());
        Assertions.assertEquals(loyaltyPoints, standartItem.getLoyaltyPoints());
    }

    @Test
    public void standartItemCopy_ComparisonOfTheStandartItemAndItsCopy_ParametersAreEqual() {
        standartItemCopy = standartItem.copy();
        Assertions.assertEquals(standartItem.getID(), standartItemCopy.getID());
        Assertions.assertEquals(standartItem.getName(), standartItemCopy.getName());
        Assertions.assertEquals(standartItem.getPrice(), standartItemCopy.getPrice());
        Assertions.assertEquals(standartItem.getCategory(), standartItemCopy.getCategory());
        Assertions.assertEquals(standartItem.getLoyaltyPoints(), standartItemCopy.getLoyaltyPoints());
    }

    // Parameterized test with Arguments Provider
    @ParameterizedTest(name = "Object 1 is equal to Object 2 expected result - {2}")
    @ArgumentsSource(StandartItemTestArgumentsProvider.class)
    public void standardItemEquals_comparisonWithAnotherObject_boolean(StandardItem standardItem1, StandardItem standardItem2, boolean expected) {
        Assertions.assertEquals(expected, standardItem1.equals(standardItem2));
    }

    // The second version of the parameterized test with file source
    @ParameterizedTest(name = "Object with id = {0}, name = {1}, price = {2}, category = {3}, loyaltyPoints = {4} is equal to object with " +
            "the same parameters")
    @CsvFileSource(resources = "/StandardItemTestDataForParameterizedTest.csv" , numLinesToSkip = 1)
    public void standardItemEquals_TestWithFileSource(int id, String name, float price, String category, int loyaltyPoints) {
        StandardItem item1 = new StandardItem(id, name,price, category,loyaltyPoints);
        StandardItem item2 = new StandardItem(id, name,price, category,loyaltyPoints);
        Assertions.assertTrue(item1.equals(item2));
    }

}

