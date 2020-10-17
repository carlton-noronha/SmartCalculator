package utility;

import java.util.ArrayDeque;
import java.util.Deque;

public class BalancedParentheses {

    /**
     *
     * @param parentheses an expression which may consist of parentheses
     * @return true if the expression consist of balanced parentheses, false otherwise
     */

    public static boolean balancedParentheses(String parentheses) {
        Deque<Character> stack = new ArrayDeque<>();
        int length = parentheses.length();

        for (int i = 0; i < length; ++i) {
            if (parentheses.charAt(i) == '(') {
                stack.offerLast(parentheses.charAt(i));
            } else {
                Character parenthesesOnTopOfStack = stack.peekLast();

                if (parenthesesOnTopOfStack == null) {
                    return false;
                }

                if (parenthesesOnTopOfStack == '(' && parentheses.charAt(i) == ')') {
                    stack.pollLast();
                } else {
                    break;
                }
            }
        }

        return stack.isEmpty();
    }

}
