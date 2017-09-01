/**
 *
 * @author James Wirth
 */
import java.util.Scanner;
import java.lang.Math;

class Solver {
    
    public static double solveLinearEquationFor(String x, String equation){
        
        //Init scanner to go through string
        Scanner scanner = new Scanner(System.in);
        Scanner equationScanner = new Scanner(equation);
     
        String currentWorkingTerm ="";
        
        //The algorithm will split the linear equation into 2 parts: the total of all of the constants, and total of all of the coefficients
        double constantTotal = 0;
        double coefficientTotal = 0;
        
        //Declare 2 boolean variables to hold whether the scanner has gone past the "=" sign
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
            
            /*System.out.println(currentWorkingTerm);
            System.out.println("Const:" + constantTotal);
            System.out.println("Coeff" + coefficientTotal);  */
        }
        //Now we have put all of the terms into the form: 
        // (coefficient * x) + (constant) = 0
        // Therefore, (coefficient * x) = (-1 * constant)
        // And finally, x = (-1*constant) / coefficient
        return (-1*constantTotal)/coefficientTotal;
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