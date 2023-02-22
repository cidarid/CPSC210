package model.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArithmeticQuestionTest extends QuestionTest {
    private ArithmeticQuestion additionQuestion, subtractionQuestion, multiplicationQuestion, invalidQuestion;

    @BeforeEach
    void runBefore() {
        additionQuestion = new ArithmeticQuestion(5, ArithmeticQuestion.Operation.ADDITION, 5, 3);
        subtractionQuestion = new ArithmeticQuestion(5, ArithmeticQuestion.Operation.SUBTRACTION, 5, 3);
        multiplicationQuestion = new ArithmeticQuestion(5, ArithmeticQuestion.Operation.MULTIPLICATION, 5, 3);
        invalidQuestion = new ArithmeticQuestion(5, ArithmeticQuestion.Operation.invalid, 5, 3);
        question = additionQuestion;
    }

    @Test
    void testConstructor() {
        assertEquals(5, additionQuestion.getMaxMark());
    }

    @Test
    void testQuestionString() {
        assertEquals("What is 5 + 3 ? [5 points]", additionQuestion.getQuestionString());
    }

    @Test
    void testCheckAnswerCorrect() {
        assertTrue(additionQuestion.isCorrect("8"));
    }

    @Test
    void testCheckAnswerIncorrect() {
        assertFalse(additionQuestion.isCorrect("9"));
    }

    @Test
    void testSubtraction() {
        assertEquals("What is 5 - 3 ? [5 points]", subtractionQuestion.getQuestionString());
        assertTrue(subtractionQuestion.isCorrect("2"));
    }

    @Test
    void testMultiplication() {
        assertEquals("What is 5 * 3 ? [5 points]", multiplicationQuestion.getQuestionString());
        assertTrue(multiplicationQuestion.isCorrect("15"));
    }

    @Test
    void testInvalidEnum() {
        assertEquals(invalidQuestion.getQuestionString(), "null [5 points]");
        try {
            invalidQuestion.isCorrect("3");
            fail("Answer checker should be null.");
        } catch (NullPointerException e) {
            // pass
        }
    }
}
