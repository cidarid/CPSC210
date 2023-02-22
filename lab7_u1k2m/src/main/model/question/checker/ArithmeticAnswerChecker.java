package model.question.checker;

public class ArithmeticAnswerChecker extends AnswerChecker {

    private int answer;

    public ArithmeticAnswerChecker(int answer) {
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(String userResponse) {
        int userResponseAsInt;
        try {
            userResponseAsInt =  Integer.parseInt(userResponse);
        } catch (NumberFormatException e) {
            return false;
        }
        return Integer.parseInt(userResponse) == answer;
    }
}
