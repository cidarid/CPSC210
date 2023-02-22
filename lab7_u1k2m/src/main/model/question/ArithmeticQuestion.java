package model.question;

import model.question.checker.ArithmeticAnswerChecker;

public class ArithmeticQuestion extends Question {
    public enum Operation { ADDITION, SUBTRACTION, MULTIPLICATION, invalid }

    public ArithmeticQuestion(int maxMark, Operation operation, int firstOp, int secondOp) {
        super(maxMark, getQuestionString(operation, firstOp,secondOp), getAnswerChecker(operation, firstOp, secondOp));
    }

    private static String getQuestionString(Operation operation, int firstOp, int secondOp) {
        String questionString = "What is " + firstOp;
        switch (operation) {
            case ADDITION:
                questionString += " + ";
                break;
            case SUBTRACTION:
                questionString += " - ";
                break;
            case MULTIPLICATION:
                questionString += " * ";
                break;
            default:
                return null;
        }
        questionString += secondOp + " ?";
        return questionString;
    }

    private static ArithmeticAnswerChecker getAnswerChecker(Operation operation, int firstOp, int secondOp) {
        int answer;
        switch (operation) {
            case ADDITION:
                answer = firstOp + secondOp;
                break;
            case SUBTRACTION:
                answer = firstOp - secondOp;
                break;
            case MULTIPLICATION:
                answer = firstOp * secondOp;
                break;
            default:
                return null;
        }
        return new ArithmeticAnswerChecker(answer);
    }
}
