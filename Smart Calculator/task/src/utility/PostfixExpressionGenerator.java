package utility;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostfixExpressionGenerator {

    /**
     *
     * @param completelyFilteredString a ready to process expression which has spaces removed, signs properly formatted
     * @return a list representing the postfix expression
     */

    public static List<String> generatePostfixExpression(String completelyFilteredString) {

        Deque<String> stack = new ArrayDeque<>();
        List<String> postfixExpression = new ArrayList<>();

        Pattern operandPattern = Pattern.compile("([a-zA-Z]+|[0-9]+)");
        Matcher operandMatcher = operandPattern.matcher(completelyFilteredString);

        for (int i = 0; i < completelyFilteredString.length(); ++i) {
            char currentChar = completelyFilteredString.charAt(i);
            if (currentChar == '(') {
                stack.offerLast(String.valueOf(currentChar));
            } else if (isOperator(String.valueOf(currentChar))) {
                while (!stack.isEmpty() &&
                        topHasHigherPriority(stack.peekLast(), String.valueOf(currentChar)) &&
                        isNotOpeningParentheses(stack.peekLast())) {
                    postfixExpression.add(stack.pollLast());
                }
                stack.offerLast(String.valueOf(currentChar));
            } else if (currentChar == ')') {
                while (!stack.isEmpty() && isNotOpeningParentheses(stack.peekLast())) {
                    postfixExpression.add(stack.pollLast());
                }
                stack.pollLast();
            } else {
                if (operandMatcher.find()) {
                    postfixExpression.add(operandMatcher.group());
                    i = operandMatcher.end() - 1;
                }
            }
        }

        while (!stack.isEmpty() && !Objects.equals(stack.peekLast(), "(") &&
                !Objects.equals(stack.peekLast(), ")")) {
            postfixExpression.add(stack.pollLast());
        }

        return postfixExpression;
    }

    /**
     *
     * @param op an arithmetic operator
     * @return true if op is a valid arithmetic op, false otherwise
     */

    public static boolean isOperator(String op) {
        switch (op) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                return true;
            default:
                return false;
        }
    }

    /**
     *
     * @param s an operator
     * @return true if is not a opening parentheses, false otherwise
     */

    private static boolean isNotOpeningParentheses(String s) {
        return !Objects.equals(s, "(");
    }

    /**
     *
     * @param op1 topmost operator on stack
     * @param op2 operator to be compared with with topmost stack operator
     * @return true if op1 > op2, false otherwise. For same priority may return vice versa if symbol is exponentiation
     */

    private static boolean topHasHigherPriority(String op1, String op2) {
        int op1Weight = getWeight(op1);
        int op2Weight = getWeight(op2);

        if (op1Weight == op2Weight) {
            return !isRightAssociative(op1);
        }

        return op1Weight > op2Weight;
    }

    /**
     *
     * @param op an operator
     * @return priority of operator
     */

    private static int getWeight(String op) {
        int weight = -1;
        switch (op) {
            case "+":
            case "-":
                weight = 1;
                break;
            case "*":
            case "/":
                weight = 2;
                break;
            case "^":
                weight = 4;
                break;

        }
        return weight;
    }

    /**
     *
     * @param op an operator
     * @return true if op is ^ as it has Right Associative i.e. 2 ^ 2 ^ 3 = 256, false otherwise
     */
    private static boolean isRightAssociative(String op) {
        return Objects.equals(op, "^");
    }

}
