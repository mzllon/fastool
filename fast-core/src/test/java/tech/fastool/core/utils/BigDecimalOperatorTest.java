package tech.fastool.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tester for {@linkplain  BigDecimalOperator}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-02
 */
public class BigDecimalOperatorTest {

    @Test
   public void of() {
        String result = BigDecimalOperator.of(1).add(1, 2).toString();
        assertEquals("4", result);
    }

    @Test
    public void testOf() {
        float f1 = 1.2F;
        String result = BigDecimalOperator.of(f1).add(1.2F, 1.2F).toString();
        assertEquals("3.6", result);
    }

    @Test
    public void testOf1() {
        String result = BigDecimalOperator.of(2.2).add(2.2).toString();
        assertEquals("4.4", result);
    }

    @Test
    public void testOf2() {

    }

    @Test
    void testOf3() {
    }

    @Test
    void testOf4() {
    }

    @Test
    void testOf5() {
    }

    @Test
    void testOf6() {
    }

    @Test
    void add() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testAdd1() {
    }

    @Test
    void testAdd2() {
    }

    @Test
    void testAdd3() {
    }

    @Test
    void testAdd4() {
    }

    @Test
    void testAdd5() {
    }

    @Test
    void testAdd6() {
    }

    @Test
    void addInts() {
    }

    @Test
    void testAdd7() {
    }

    @Test
    void testAdd8() {
    }

    @Test
    void testAdd9() {
    }

    @Test
    void testAdd10() {
    }

    @Test
    void addFloats() {
    }

    @Test
    void testAdd11() {
    }

    @Test
    void testAdd12() {
    }

    @Test
    void testAdd13() {
    }

    @Test
    void testAdd14() {
    }

    @Test
    void addLongs() {
    }

    @Test
    void testAdd15() {
    }

    @Test
    void testAdd16() {
    }

    @Test
    void testAdd17() {
    }

    @Test
    void testAdd18() {
    }

    @Test
    void addDoubles() {
    }

    @Test
    void testAdd19() {
    }

    @Test
    void testAdd20() {
    }

    @Test
    void addStrs() {
    }

    @Test
    void testAdd21() {
    }

    @Test
    void testAdd22() {
    }

    @Test
    void testAdd23() {
    }

    @Test
    void subtract() {
    }

    @Test
    void testSubtract() {
    }

    @Test
    void testSubtract1() {
    }

    @Test
    void testSubtract2() {
    }

    @Test
    void testSubtract3() {
    }

    @Test
    void testSubtract4() {
    }

    @Test
    void subtractInts() {
    }

    @Test
    void testSubtract5() {
    }

    @Test
    void testSubtract6() {
    }

    @Test
    void testSubtract7() {
    }

    @Test
    void testSubtract8() {
    }

    @Test
    void subtractFloats() {
    }

    @Test
    void testSubtract9() {
    }

    @Test
    void testSubtract10() {
    }

    @Test
    void testSubtract11() {
    }

    @Test
    void testSubtract12() {
    }

    @Test
    void subtractLongs() {
    }

    @Test
    void testSubtract13() {
    }

    @Test
    void testSubtract14() {
    }

    @Test
    void testSubtract15() {
    }

    @Test
    void testSubtract16() {
    }

    @Test
    void subtractDoubles() {
    }

    @Test
    void testSubtract17() {
    }

    @Test
    void testSubtract18() {
    }

    @Test
    void subtractStrs() {
    }

    @Test
    void testSubtract19() {
    }

    @Test
    void testSubtract20() {
    }

    @Test
    void testSubtract21() {
    }

    @Test
    void multiply() {
    }

    @Test
    void testMultiply() {
    }

    @Test
    void testMultiply1() {
    }

    @Test
    void testMultiply2() {
    }

    @Test
    void multiplyInts() {
    }

    @Test
    void testMultiply3() {
    }

    @Test
    void testMultiply4() {
    }

    @Test
    void testMultiply5() {
    }

    @Test
    void testMultiply6() {
    }

    @Test
    void multiplyFloats() {
    }

    @Test
    void testMultiply7() {
    }

    @Test
    void testMultiply8() {
    }

    @Test
    void testMultiply9() {
    }

    @Test
    void testMultiply10() {
    }

    @Test
    void multiplyLongs() {
    }

    @Test
    void testMultiply11() {
    }

    @Test
    void testMultiply12() {
    }

    @Test
    void testMultiply13() {
    }

    @Test
    void testMultiply14() {
    }

    @Test
    void multiplyDoubles() {
    }

    @Test
    void testMultiply15() {
    }

    @Test
    void testMultiply16() {
    }

    @Test
    void multiplyStrs() {
    }

    @Test
    void testMultiply17() {
    }

    @Test
    void testMultiply18() {
    }

    @Test
    void testMultiply19() {
    }

    @Test
    void divide() {
    }

    @Test
    void testDivide() {
    }

    @Test
    void testDivide1() {
    }

    @Test
    void testDivide2() {
    }

    @Test
    void testDivide3() {
    }

    @Test
    void testDivide4() {
    }

    @Test
    void testDivide5() {
    }

    @Test
    void testDivide6() {
    }

    @Test
    void testDivide7() {
    }

    @Test
    void testDivide8() {
    }

    @Test
    void testDivide9() {
    }

    @Test
    void testDivide10() {
    }

    @Test
    void testDivide11() {
    }

    @Test
    void testDivide12() {
    }

    @Test
    void testDivide13() {
    }

    @Test
    void testDivide14() {
    }

    @Test
    void testDivide15() {
    }

    @Test
    void testDivide16() {
    }

    @Test
    void testDivide17() {
    }

    @Test
    void testDivide18() {
    }

    @Test
    void testDivide19() {
    }

    @Test
    void testDivide20() {
    }

    @Test
    void testAdd24() {
    }

    @Test
    void testMultiply20() {
    }

    @Test
    void testDivide21() {
    }

    @Test
    void getResult() {
    }

    @Test
    void getResultToInt() {
    }

    @Test
    void testGetResultToInt() {
    }

    @Test
    void getResultToLong() {
    }

    @Test
    void testGetResultToLong() {
    }

    @Test
    void getResultToDouble() {
    }

    @Test
    void testGetResultToDouble() {
    }

    @Test
    void getResultStr() {
    }

    @Test
    void testGetResultStr() {
    }
}