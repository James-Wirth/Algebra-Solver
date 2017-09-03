/**
 *
 * @author James Wirth
 */
import java.util.Scanner;
import java.lang.Math;

class Solver {
    
    public static double solveLinearEquationFor(String x, String equation){
        
        //Simplify every instance of a multiplication or a divide
        equation = expandDM(x, equation);
        
        //Init scanner to go through string
        Scanner scanner = new Scanner(System.in);
        Scanner equationScanner = new Scanner(equation);
     
        String currentWorkingTerm ="";
        
        //The algorithm will split the linear equation into 2 parts: the total of all of the constants, and total of all of the coefficients
        double constantTotal = 0;
        double coefficientTotal = 0;
        
        //Declare 2 boolean variables to hold whether the scanner has gone past the "=" sign and therefore whether the terms should be added or subtracted from the totals
        boolean positive = true;
        boolean hasPassedEquals = false;
        
        //While scanner is not at the end of the equation string
        while (equationScanner.hasNext()) {
            
            //Insert the current term (surrounded by spaces) into the variable 'currentWorkingTerm'
            currentWorkingTerm = equationScanner.next();
            
            //If this term is purely numerical, increment/decrement the constant total accordingly (depending on whether we have passed the "=" sign)
            if(isInteger(currentWorkingTerm)){
                if(positive==true){
                    constantTotal += Double.parseDouble(currentWorkingTerm);
                }
                else{
                    constantTotal -= Double.parseDouble(currentWorkingTerm);
                }
            }
            
            //If this term contains the unknown, increment/decrement the coefficient total accordingly 
            if(currentWorkingTerm.endsWith(x)){
                if(positive==true){
                    coefficientTotal += Double.parseDouble(currentWorkingTerm.substring(0, currentWorkingTerm.length() - 1));    
                }
                else{
                    coefficientTotal -= Double.parseDouble(currentWorkingTerm.substring(0, currentWorkingTerm.length() - 1));  
                }
            }
            
            //If the current term is the "=" sign, positive is now false.
            if(currentWorkingTerm.equals("=")){
                hasPassedEquals = true;
                positive=!positive;
            }
            //The "+" sign is positive if on the left side of the equals sign, and negative if on the right (i.e. we want all numbers/coefficients on the left, and 0 on the right)
            if(currentWorkingTerm.equals("+")){
                positive = !hasPassedEquals;
            }
            if(currentWorkingTerm.equals("-")){
                positive = hasPassedEquals;
            }
            
        }
        //Now we have put all of the terms into the form: 
        // (coefficient * x) + (constant) = 0
        // Therefore, (coefficient * x) = (-1 * constant)
        // And finally, x = (-1*constant) / coefficient
        return (-1*constantTotal)/coefficientTotal;
    }
    
    public static String expandDM(String x, String equation){
        
        //New scanner to go through the string
        Scanner scanner = new Scanner(System.in);
        Scanner equationScanner = new Scanner(equation);
        
        //Placeholder for current term
        String currentWorkingTerm; 
        
        //The value that will be returned
        String expanded="";
        
        //endIndex and startIndex mark the indexes in the 'equation' string which will be replaced with a shortened term
        int endIndex, startIndex = 0;
        //prev and next hold the terms on either side of the operator
        String prev = "", next = "";
        
        //The index of the current operator
        int operatorIndex = 0;

        //The replacements after a * or / calculation has been made
        double replacement;
        String replacementStr;
        
        while(equationScanner.hasNext()){
            
            currentWorkingTerm = next;
            next = equationScanner.next();
            
            //System.out.println("equation" + equation);
            
            //System.out.println("cwt: " + currentWorkingTerm);
            //System.out.println("n: " + next);
            //System.out.println("p: " + prev);
            
            //If a '*' exists and is further left than a '/' (if it exists), set the operator index to that of the first '*'
            if(equation.contains("*") && (equation.indexOf("*") < equation.indexOf("/") || !equation.contains("/"))){
                operatorIndex = equation.indexOf("*");
            }
            //If a '/' exists and is further left than a '*' (if it exists), set the operator index to that of the first '/'
            else if(equation.contains("/") && (equation.indexOf("/") < equation.indexOf("*") || !equation.contains("*"))){
                operatorIndex = equation.indexOf("/");
            }

            //Set endIndex to the index of the last character in the current bit of the equation
            endIndex = equation.indexOf(next.charAt(next.length()-1), operatorIndex);

            
            switch (currentWorkingTerm) {
                case "*":
                {
                    if(prev.endsWith("x")){
                        replacement = Double.parseDouble(prev.substring(0, prev.length() - 1)) * Double.parseDouble(next);
                        replacementStr = replacement+"x";
                    }
                    else if(next.endsWith("x")){
                        replacement = Double.parseDouble(next.substring(0, next.length() - 1)) * Double.parseDouble(prev);
                        replacementStr = replacement+"x";
                    }
                    else{
                        replacement = Double.parseDouble(next) * Double.parseDouble(prev);
                        replacementStr = replacement+"";
                    }
                    
                    StringBuilder buf = new StringBuilder(equation);
                    //Replace the old terms with a new term in the equation string
                    buf.replace(startIndex, endIndex+1, replacementStr);
                    
                    //Call function recursively with the shortened string
                    expanded = expandDM(x, buf.toString());
                    return expanded;
                }
                case "/":
                { 
                    if(prev.endsWith("x") && next.endsWith("x")){
                        replacement = (double)Math.round(Double.parseDouble(prev.substring(0, prev.length() - 1)) / Double.parseDouble(next.substring(0, next.length() - 1)));
                        replacementStr = replacement+"x";
                    }
                    else if(prev.endsWith("x")){
                        replacement = (double)Math.round(Double.parseDouble(prev.substring(0, prev.length() - 1)) / Double.parseDouble(next));
                        replacementStr = replacement+"x";
                    }
                    else if(next.endsWith("x")){
                        replacement = Double.parseDouble(prev) / Double.parseDouble(next.substring(0, next.length() - 1));
                        replacementStr = replacement+"x";
                    }
                    else{
                        replacement = Double.parseDouble(prev) / Double.parseDouble(next);
                        replacementStr = replacement+"";
                    }
                    StringBuilder buf = new StringBuilder(equation);
                    buf.replace(startIndex, endIndex+1, replacementStr);
                    
                    expanded = expandDM(x, buf.toString());
                    return expanded;
                }
                default:
                    //When the algorithm has reached the end of the equation, return the shortened string back down the recursive "tree"
                    if(!equationScanner.hasNext()){
                        return equation;
                    }   break;
            }
            prev = currentWorkingTerm;
            startIndex = equation.indexOf(prev);
        }
        return expanded;
    }
    
    //Method for evaluating whether string is numerical
    public static boolean isInteger(String str) {
        int size = str.length();

        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return size > 0;
    }
    
    //For the BOdmas part (coming soon)
    public static int countBrackets(String substring) {
        int count = 0;
        for (int i = 0; i < substring.length(); i++){
            if (substring.charAt(i)=='('){
                count++;
            }
            if (substring.charAt(i)==')'){
                count--;
            }
        }
        return 1;
    }
    
    public static double[] solveQuadraticFor(double a, double b, double c){
        double[] results = new double[2];
        if(a==0){
            return results;
        }
        double discriminant = Math.pow(b, 2) - 4*a*c;
        if(discriminant>0){
            results[0] = (-b+discriminant)/(2*a);
            results[1] = (-b-discriminant)/(2*a);
        }
        return results;
    }

}