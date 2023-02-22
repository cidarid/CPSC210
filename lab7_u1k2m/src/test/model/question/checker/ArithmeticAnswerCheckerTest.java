package model.question.checker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArithmeticAnswerCheckerTest {

    private ArithmeticAnswerChecker arithmeticAnswerChecker;

    @BeforeEach
    void runBefore() {
        arithmeticAnswerChecker = new ArithmeticAnswerChecker(3);
    }

    @Test
    void testCaseMatch() {
        assertTrue(arithmeticAnswerChecker.checkAnswer("3"));
    }

    @Test
    void testStringInput() {
        assertFalse(arithmeticAnswerChecker.checkAnswer("three"));
    }

    @Test
    void testOutOfBoundsInput() {
        assertFalse(arithmeticAnswerChecker.checkAnswer("21474836470"));
    }

    @Test
    void testNonIntegerInput() {
        assertFalse(arithmeticAnswerChecker.checkAnswer("3.0"));
    }
}
