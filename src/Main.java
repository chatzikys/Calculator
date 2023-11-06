import Calcpkg.Calculator;

public class Main {
    public static void main(String[] args) {
        Calculator c = new Calculator("114^(1/2)");
        c.infixToRPN();
        c.computePRN();
    }
}


