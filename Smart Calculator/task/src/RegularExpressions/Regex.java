package RegularExpressions;

public class Regex {
    public static final String validVariableName = "\\b[a-zA-Z]+\\b";
    public static final String validAssignmentPattern = "\\s*[a-zA-Z]+\\s*=\\s*[-+]?(\\d+|[a-zA-Z]+)\\s*";
    public static final String validExpression =
            "(\\s*\\(*([a-zA-Z]+|\\d+)\\s*\\)*\\s*(-+|\\++|\\*|/|\\^)\\s*)+([a-zA-Z]+|\\d+)\\)*";
    public static final String invalidCommand = "^/.+";
    public static final String parenthesesExpression = "[-\\s*\\w+^=/]+";
    public static final String unknownVariable = "\\b[a-zA-Z0-9]+\\b";
    public static final String invalidExpression = ".+(\\*{2,}|/{2,}|\\^{2,}).+";
    public static final String invalidIdentifier = "[a-zA-Z0-9]+\\s*=\\s*(\\d+|[a-zA-Z]+)";
    public static final String oneOrMorePlusMinus = "(-+|\\++)";
}
