package calculator;

import RegularExpressions.Regex;
import utility.BalancedParentheses;
import utility.PostfixExpressionEvaluator;
import utility.PostfixExpressionGenerator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        final Pattern variableNamePattern = Pattern.compile(Regex.validVariableName);
        final Pattern assignmentPattern = Pattern.compile(Regex.validAssignmentPattern);
        final Pattern filteringPattern = Pattern.compile(Regex.oneOrMorePlusMinus);

        Map<String, BigInteger> variables = new HashMap<>();

        try (final Scanner scanner = new Scanner(System.in)) {

            do {

                String command = scanner.nextLine();

                if (command.isEmpty()) {
                    continue;
                } else if (Objects.equals(command.trim(), "/exit")) {
                    System.out.println("Bye!");
                    break;
                } else if (Objects.equals(command.trim(), "/help")) {
                    System.out.println(
                            "+ => Addition\n- => Subtraction\n* => Multiplication\n/ => Division\n" +
                            "^ => Exponent\nVariables allow for assignment and arithmetic only not for both\n" +
                            " i.e. a = b + c + 25 is invalid, while a = 25 or a * b ^ c is valid"
                            + "\nParentheses'(' and ')' are allowed"
                    );
                    continue;
                } else if (command.matches(Regex.invalidCommand)) {
                    System.out.println("Unknown command");
                    continue;
                }

                if (command.matches(Regex.validExpression)) {

                    String parentheses = command.replaceAll(Regex.parenthesesExpression, "").trim();

                    if (!BalancedParentheses.balancedParentheses(parentheses)) {
                        System.out.println("Invalid expression");
                        continue;
                    }

                    String partialFilteredExpression = command.replaceAll("\\s+", "");


                    Matcher matcher = filteringPattern.matcher(partialFilteredExpression);

                    String completelyFilteredString = filterString(matcher, partialFilteredExpression);


                    List<String> postfixExpression = PostfixExpressionGenerator.
                            generatePostfixExpression(completelyFilteredString);

                    BigInteger sum = PostfixExpressionEvaluator.
                            evaluatePostfixExpression(postfixExpression, variables);
                    System.out.println(sum);
                    continue;
                }

                if (assignmentPattern.matcher(command).matches()) {
                    String[] assignmentElements = command.split("\\s*=\\s*");
                    try {
                        BigInteger value = new BigInteger(assignmentElements[1].trim());
                        variables.put(assignmentElements[0].trim(), value);
                    } catch (NumberFormatException notANumber) {
                        if (!variables.containsKey(assignmentElements[1].trim())) {
                            System.out.println("Unknown variable");
                        } else {
                            BigInteger value = variables.getOrDefault(assignmentElements[1].trim(), BigInteger.ZERO);
                            variables.put(assignmentElements[0].trim(), value);
                        }
                    }

                } else {
                    if (variableNamePattern.matcher(command.trim()).matches()) {
                        if (variables.containsKey(command.trim())) {
                            System.out.println(variables.get(command.trim()));
                        } else {
                            System.out.println("Unknown variable");
                        }
                    } else {
                        if (command.matches("[-+]?\\d+")) {
                            System.out.println(Integer.parseInt(command));
                        } else if (command.matches(Regex.invalidIdentifier)) {
                            System.out.println("Invalid identifier");
                        } else if (command.matches(Regex.unknownVariable)) {
                            System.out.println("Unknown variable");
                        } else if (command.matches(Regex.invalidExpression)) {
                            System.out.println("Invalid expression");
                        } else {
                            System.out.println("Invalid assignment");
                        }
                    }
                }

            } while (true);
        }
    }

    /**
     *
     * @param matcher matcher object for pattern Regex.oneOrMorePlusMinus
     * @param s expression to be formatted by getting rid of extra + and - signs for example +++ = + while -- = + and
     *          --- = - and son on.
     * @return fortmatted expression
     */
    private static String filterString(Matcher matcher, String s) {

        String result = s;
        while (matcher.find()) {
            int diff = matcher.end() - matcher.start();
            if (diff > 1) {
                String operator = matcher.group();
                if (operator.charAt(0) == '-' && diff % 2 == 1) {
                    result = result.replaceFirst(operator, "-");
                } else if (operator.charAt(0) == '-') {
                    result = result.replaceFirst(operator, "+");
                } else {
                    result = result.replaceFirst("\\++", "+");
                }
            }
        }

        return result;
    }

}
