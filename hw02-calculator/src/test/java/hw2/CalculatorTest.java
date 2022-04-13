package hw2;
import org.junit.jupiter.api.*;
public class CalculatorTest {

    Calculator calculator;
    @BeforeEach
    public void setCalculator() {
        calculator = new Calculator();
    }

    @Test
    public void add_returnsNumber_SumOfTwoNumbers(){
        int expected = 5;
        int actual = calculator.add(2, 3);
        Assertions.assertEquals(expected, actual);
    }
}
