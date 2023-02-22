package model.quiz;

import model.exceptions.AnswerIncorrectException;
import model.exceptions.OutOfTriesException;
import model.question.QuestionList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimitedTriesQuizTest extends QuizTest {
    LimitedTriesQuiz quiz;

    @BeforeEach
    void runBefore() {
        QuestionList questionList = generateQuestionList();
        quiz = new LimitedTriesQuiz(questionList);
        super.quiz = new LimitedTriesQuiz(questionList);
    }

    @Test
    void testConstructor() {
        super.testConstructor();
        assertEquals(6, quiz.getMaxMark());
    }

    @Test
    void testSubmitAnswerAllCorrect() {
        try {
            quiz.getNextQuestion();
            String feedback = quiz.submitAnswer("Earth");
            assertEquals("Correct!", feedback);
            assertEquals(4, quiz.getMarkSoFar());
            quiz.getNextQuestion();
            feedback = quiz.submitAnswer("Canada");
            assertEquals("Correct!", feedback);
            assertEquals(6, quiz.getMarkSoFar());
            assertFalse(quiz.hasMoreQuestions());
            assertEquals("Your final mark is: 6 out of 6", quiz.endQuiz());
        } catch (Exception e) {
            fail("Should not have thrown exception.");
        }
    }

    @Test
    void testSubmitAnswerOneIncorrectOneCorrect() {
        quiz.getNextQuestion();
        assertEquals(4, quiz.getNumAttemptsForQuestion());
        try {
            quiz.submitAnswer("erth");
        } catch (Exception e) {
            assertEquals("Incorrect!", e.getMessage());
        }
        assertEquals(0, quiz.getMarkSoFar());
        assertEquals(3, quiz.getNumAttemptsForQuestion());
        try {
            String response = quiz.submitAnswer("Earth");
            assertEquals("Correct!", response);
        } catch (Exception e) {
            fail("Shouldn't be an exception here.");
        }
        assertEquals(3, quiz.getMarkSoFar());
        quiz.getNextQuestion();
        try {
            quiz.submitAnswer("Cambodia");
        } catch (Exception e) {
            assertEquals("Incorrect!", e.getMessage());
        }
        assertEquals(3, quiz.getMarkSoFar());
        assertEquals(1, quiz.getNumAttemptsForQuestion());
        try {
            String response = quiz.submitAnswer("Canada");
            assertEquals("Correct!", response);
        } catch (Exception e) {
            fail("Exception " + e.getMessage());
        }
        assertEquals(4, quiz.getMarkSoFar());
        assertFalse(quiz.hasMoreQuestions());
        assertEquals("Your final mark is: 4 out of 6", quiz.endQuiz());
    }

    @Test
    void testSubmitAnswerMaxIncorrect() {
        quiz.getNextQuestion();
        assertEquals(4, quiz.getNumAttemptsForQuestion());
        for (int i = 3; i > 0; i--) {
            try {
                quiz.submitAnswer("erth");
            } catch (Exception e) {
                assertEquals("Incorrect!", e.getMessage());
                assertEquals(i, quiz.getNumAttemptsForQuestion());
            }
        }
        assertEquals(1, quiz.getNumAttemptsForQuestion());
        try {
            quiz.submitAnswer("erth");
            fail("Should have thrown exception.");
        } catch (OutOfTriesException e) {
            // pass
        } catch (AnswerIncorrectException e) {
            fail("Should have thrown out of tries exception.");
        }
        assertEquals(0, quiz.getMarkSoFar());
        quiz.getNextQuestion();
        try {
            quiz.submitAnswer("Canada");
        } catch (Exception e) {
            fail("Shouldn't throw exception here.");
        }
        assertEquals(2, quiz.getMarkSoFar());
        assertFalse(quiz.hasMoreQuestions());
        assertEquals("Your final mark is: 2 out of 6", quiz.endQuiz());
    }
}
