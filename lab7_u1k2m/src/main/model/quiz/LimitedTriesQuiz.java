package model.quiz;

import model.exceptions.AnswerIncorrectException;
import model.exceptions.OutOfTriesException;
import model.question.QuestionList;

public class LimitedTriesQuiz extends Quiz {

    static int numAttempts;

    public LimitedTriesQuiz(QuestionList questions) {
        super(questions);
        numAttempts = 0;
    }

    // MODIFIES: this
    // EFFECTS: submit an answer to the current question and return feedback string;
    // if the answer is incorrect, decrements the max mark of the current question by one;
    // throws AnswerIncorrectException if the user should re-try the question
    // throws an OutOfTriesException if the answer is incorrect and no more
    // attempts are allowed
    @Override
    public String submitAnswer(String answer) throws AnswerIncorrectException, OutOfTriesException {
        numAttempts++;
        if (checkAnswer(answer)) {
            return "Correct!";
        }
        if (curQuestion.getMaxMark() > 1) {
            curQuestion.setMaxMark(curQuestion.getMaxMark() - 1);
            throw new AnswerIncorrectException("Incorrect!");
        } else {
            curQuestion.setMaxMark(curQuestion.getMaxMark() - 1);
            throw new OutOfTriesException("Incorrect! Out of tries.");
        }
    }

    public int getNumAttemptsForQuestion() {
        return curQuestion.getMaxMark();
    }
/*
    // EFFECTS: returns number of attempts taken to answer questions so far
    public int getNumAttempts() {
        return numAttempts;
    }

    @Override
    public String endQuiz() {
        return String.format("It took you %d attempts to answer %d questions correctly.",
                numAttempts, super.questions.length());
    }*/
}
