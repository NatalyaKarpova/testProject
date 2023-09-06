
import org.testng.Assert;
import org.testng.annotations.*;

public class TestJob {
    private Calculator calculator;


    @BeforeClass(alwaysRun = true)
    void beforeClass() {
        calculator = new Calculator();
        System.out.println("Выполняется до всех тестов!");
    }

    @BeforeMethod(alwaysRun = true)
    void beforeMethod() {
        System.out.println("Выполняется перед каждым тестом!");
    }

    @AfterClass(alwaysRun = true)
    void afterClass() {
        System.out.println("Выполняется после всех тестов!");
    }

    @Test (description = "Проверка сложения")
    void testAddition() {
        int result = calculator.add(5, 2);
        Assert.assertEquals(7, result, "Успех");
    }

    @Test(description = "Проверка вычитания")
    void testSubtraction() {

        int result = calculator.subtract(5, 2);
        Assert.assertEquals(3, result, "Успех");
    }

    @Test(description = "Проверка умножения")
    void testMultiplication() {
        int result = calculator.multiply(5, 2);
        Assert.assertEquals(10, result,"Успех");
    }

    @Test(description = "Проверка деления")
    void testDivision() {
        double result = calculator.divide(5, 2);
        Assert.assertEquals(2.5, result, "Успех");
    }

    @Test(description = "Проверка деления на 0")
    void testDivisionByZero() {

        Assert.assertThrows(ArithmeticException.class, () -> {
            calculator.divide(5, 0);
        });
    }
}

