package model.quiz;

import model.exceptions.AnswerIncorrectException;
import model.exceptions.OutOfTriesException;
import model.question.QuestionList;

public class UnlimitedTriesQuiz extends Quiz {

    static int numAttempts;

    public UnlimitedTriesQuiz(QuestionList questions) {
        super(questions);
        numAttempts = 0;
    }

    // MODIFIES: this
    // EFFECTS: submit an answer to the current question and return feedback string;
    // does not modify max mark of current question;
    // throws AnswerIncorrectException if the user should re-try the question;
    @Override
    public String submitAnswer(String answer) throws AnswerIncorrectException, OutOfTriesException {
        numAttempts++;
        if (!checkAnswer(answer)) {
            throw new AnswerIncorrectException("Incorrect!");
        }
        return "Correct!";
    }

    // EFFECTS: returns number of attempts taken to answer questions so far
    public int getNumAttempts() {
        return numAttempts;
    }

    @Override
    public String endQuiz() {
        return String.format("It took you %d attempts to answer %d questions correctly.",
                numAttempts, super.questions.length());
    }
}
