/**
 * A class that contains the static methods to parse lerp expressions.
 * 
 * @author Arthur Nunes-Harwitt
 */

public class Parser {

    private static int pos;
    private static String[] tokens;


    /**
     * Build a data structure representation of the lerp expression
     * written as a string.
     * 
     * @param s a String that contains the text of a lerp expression
     * @return an Expression data structure representing the lerp expression
     */
    public static Expression parse(String s){
        tokens = s.replace("(", " ( ").replace(")", " ) ").trim().split("( )+");
        pos = 0;
        try{
            Expression e = getExpression();
            if (pos < tokens.length){
                Errors.error("Too much input.", null);
            } else {
                return e;
            }
        } catch (NumberFormatException e) {
            Errors.error("Expression must start with a number or open parenthesis.", null);
        } catch (Exception e) {
            Errors.error("Unexpected error ", e);
        }
        return null; // Shouldn't get here. To satisfy Java.
    }

    /**
     * Gets the innermost expression using the tokens array
     * @return the expression tree
     */
    private static Expression getExpression(){
        chkExpression();
        switch(tokens[pos]) {
            case "+":
                pos++;
                Expression exp1 = getExpression();
                chkMid();
                Expression exp2 = getExpression();
                chkEnd();
                return new AddExp(exp1, exp2);
            case "-":
                pos++;
                chkMid();
                Expression exp3 = getExpression();
                chkEnd();
                if (tokens[pos].equals(")")) {
                    return new NegExp(exp3);
                } else {
                    Expression exp4 = getExpression();
                    return new SubExp(exp3, exp4);
                }
            case "*":
                pos++;
                Expression exp5 = getExpression();
                chkMid();
                Expression exp6 = getExpression();
                chkEnd();
                return new MulExp(exp5, exp6);
            case "/":
                pos++;
                Expression exp7 = getExpression();
                chkMid();
                Expression exp8 = getExpression();
                chkEnd();
                return new DivExp(exp7, exp8);
            case "sqrt":
                pos++;
                chkMid();
                Expression exp0 = getExpression();
                chkEnd();
                return new SqrtExp(exp0);
            case "(":
                pos++;
                Expression ex = getExpression();
                pos++;
                return ex;
            case ")":
                return null;
        }
        if ( tokens[pos].matches("[0-9]") ){
            pos++;
            return new NumExp(Double.parseDouble(tokens[pos-1]));
        }
        Errors.error("Unexpected operator: '" + tokens[pos] + "'.\n", null);
        return null;
    }

    /**
     * checks to see if the expression has an unexpected end of input.
     */
    private static void chkExpression(){
        if (pos >= tokens.length){
            Errors.error("Unexpected end of input.\n" , null);
        }
    }

    /**
     * checks to see if the if the expression has an unexpected token
     */
    private static void chkEnd(){
        if (pos < tokens.length && (!tokens[pos].equals(")"))){
            if (!tokens[pos].equals(")")){
                Errors.error("Unexpected token: " + tokens[pos] + "; expected ).\n", null);
            }
        }
    }

    /**
     * checks the middle of the expression for an unexpected end of input.
     */
    private static void chkMid(){

        if (pos < tokens.length && ((!tokens[pos].matches("[0-9]") && !tokens[pos].equals("(")))){
            Errors.error("Unexpected end of input.\n"  + pos, null);
        }
    }

}
