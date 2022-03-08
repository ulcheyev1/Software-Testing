package lab03;
import org.junit.jupiter.api.*;

public class FooTest {
    Foo foo;
    @BeforeEach
    public void setFoo() {
        foo = new Foo();
    }

    @Test
    public void returnNumber_returnsFive_passed(){

        Assertions.assertEquals(5, foo.returnNumber());
    }

    @Test
    public void returnNumber_returnsFive_failure(){
        Assertions.assertEquals(7, foo.returnNumber());
    }

    @Test
    public void getNum_returnsNum_num(){
        Assertions.assertEquals(0, foo.getNum());
    }

    @Test
    public void increment_returnsIncrement_1(){
        foo.increment();
        Assertions.assertEquals(1, foo.getNum());
    }

    @Test
    public void exceptionThrow_returnsNadpis_exception(){
        Assertions.assertThrows(Exception.class, foo::exceptionThrown);
    }
}