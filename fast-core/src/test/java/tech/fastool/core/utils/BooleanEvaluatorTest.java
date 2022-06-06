package tech.fastool.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tester for {@linkplain BooleanEvaluator}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BooleanEvaluatorTest {

    @Test
    public void isNullValue() {
        BooleanEvaluator be = BooleanEvaluator.DEFAULT_TRUE_EVALUATOR;
        assertFalse(be.isNullValue());
        assertTrue(be.isStrIgnoreCase());
    }

    @Test
    public void setNullValue() {
    }

    @Test
    public void isStringIgnoreCase() {
    }

    @Test
    public void setStringIgnoreCase() {
    }

    @Test
    public void addTrueFactors() {
    }

    @Test
    public void addFalseFactors() {
    }

    @Test
    public void addTrueFactor() {
    }

    @Test
    public void addFalseFactor() {
    }

    @Test
    public void addFactor() {
        BooleanEvaluator be = BooleanEvaluator.createFalseEvaluator(false, true);
        be.addFactor("Y", "N");
        assertTrue(be.evalTrue("Y"));
        assertTrue(be.evalTrue("y"));
        assertTrue(be.evalFalse("N"));
        assertTrue(be.evalFalse("n"));
        assertFalse(be.evalFalse("A"));
        assertFalse(be.evalTrue("A"));
    }

    @Test
    public void evalTrue() {
    }

    @Test
    public void evalFalse() {
    }

    @Test
    public void createFalseEvaluator() {
        BooleanEvaluator be = BooleanEvaluator.createFalseEvaluator(false, false, "N", "不", "N", "否", "不是");
        assertTrue(be.evalFalse("N"));
        assertFalse(be.evalFalse("n"));
    }

    @Test
    public void createTrueEvaluator() {
        BooleanEvaluator be = BooleanEvaluator.createTrueEvaluator(false, false, "Y", "是");
        assertFalse(be.isStrIgnoreCase());
        assertFalse(be.isNullValue());
        assertFalse(be.evalTrue("y"));
    }

    @Test
    public void testCreateTrueEvaluator() {
    }
}