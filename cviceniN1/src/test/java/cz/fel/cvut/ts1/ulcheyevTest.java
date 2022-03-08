package cz.fel.cvut.ts1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ulcheyevTest {
    @Test
    public void factorialTest() {
        ulcheyev facrtorialTest = new ulcheyev();
        long excepted = 4;
        long actual = facrtorialTest.factorial(3);
        assertEquals(excepted, actual);

    }
}
