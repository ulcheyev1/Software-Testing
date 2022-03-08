package hw2;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void subtract_returnsInt_subtractOfTwoNumbers(){
        int expected = 3;
        int actual = calculator.subtract(6, 3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void multiply_returnsInt_multiplyOfTwoNumbers(){
        int expected = 12;
        int actual = calculator.multiply(4, 3);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void divide_returnsInt_divideOfTwoNumbers(){
        int expected = 5;
        int actual = calculator.divide(25, 5);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void divide_returnsException_divideByZeroException(){
       Assertions.assertThrows(ArithmeticException.class, () -> calculator.divide(25, 0));
    }


}
