package model.quiz;

import model.exceptions.AnswerIncorrectException;
import model.question.QuestionList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnlimitedTriesQuizTest extends QuizTest {
    UnlimitedTriesQuiz quiz;

    @BeforeEach
    void runBefore() {
        QuestionList questionList = generateQuestionList();
        quiz = new UnlimitedTriesQuiz(questionList);
        super.quiz = new UnlimitedTriesQuiz(questionList);
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
            assertEquals("It took you 2 attempts to answer 2 questions correctly.", quiz.endQuiz());
        } catch (Exception e) {
            fail("Should not have thrown exception.");
        }
    }

    @Test
    void testSubmitAnswerOneIncorrectOneCorrect() {
        quiz.getNextQuestion();
        try {
            quiz.submitAnswer("erth");
        } catch (Exception e) {
            assertEquals("Incorrect!", e.getMessage());
        }
        assertEquals(0, quiz.getMarkSoFar());
        try {
            String response = quiz.submitAnswer("Earth");
            assertEquals("Correct!", response);
        } catch (Exception e) {
            fail("Shouldn't be an exception here.");
        }
        assertEquals(2, quiz.getNumAttempts());
        assertEquals(4, quiz.getMarkSoFar());
        quiz.getNextQuestion();
        try {
            quiz.submitAnswer("Cambodia");
        } catch (Exception e) {
            assertEquals("Incorrect!", e.getMessage());
        }
        assertEquals(4, quiz.getMarkSoFar());
        try {
            String response = quiz.submitAnswer("Canada");
            assertEquals("Correct!", response);
        } catch (Exception e) {
            fail("Shouldn't be an exception here.");
        }
        assertEquals(6, quiz.getMarkSoFar());
        assertFalse(quiz.hasMoreQuestions());
        assertEquals(4, quiz.getNumAttempts());
        assertEquals("It took you 4 attempts to answer 2 questions correctly.", quiz.endQuiz());
    }
}
