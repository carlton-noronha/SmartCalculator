package utility;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class PostfixExpressionEvaluator {

    /**
     *
     * @param postExp a list consisting of the postfix expression
     * @param variables a map consisting of values of variables
     * @return BigInteger object of the result of postfix expression evaluation
     */
    public static BigInteger evaluatePostfixExpression(List<String> postExp, Map<String, BigInteger> variables) {

        Deque<BigInteger> stack = new ArrayDeque<>();

        for (String s : postExp) {
            if (PostfixExpressionGenerator.isOperator(s)) {
                BigInteger operand1 = stack.pollLast();
                BigInteger operand2 = stack.pollLast();
                BigInteger result = performCalculation(s, operand1, operand2);
                stack.offerLast(result);
            } else {
                try {
                    stack.offerLast(new BigInteger(s));
                } catch (NumberFormatException notANumber) {
                    stack.offerLast(variables.getOrDefault(s, BigInteger.ZERO));
                }

            }
        }

        return stack.pollLast();
    }

    /**
     *
     * @param op an operator
     * @param operand1  first BigInteger object to be evaluated
     * @param operand2 second BigInteger object to be evaluated
     * @return result of the arithmetic operation
     */

    private static BigInteger performCalculation(String op, BigInteger operand1, BigInteger operand2) {
        BigInteger result = BigInteger.ZERO;
        switch (op) {
            case "+":
                result = operand2.add(operand1);
                break;
            case "-":
                result = operand2.subtract(operand1);
                break;
            case "*":
                result = operand2.multiply(operand1);
                break;
            case "/":
                result = operand2.divide(operand1);
                break;
            case "^":
                result = operand2.pow(operand1.intValue());
                break;
        }
        return result;
    }

}
