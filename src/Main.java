import Calcpkg.Calculator;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Calculator c = new Calculator();

        String exp = JOptionPane.showInputDialog(null, "", "CALCULATOR", JOptionPane.QUESTION_MESSAGE);
        JOptionPane.showMessageDialog(null, "The answer is : " + c.evaluateExpression(exp), "RESULT", JOptionPane.INFORMATION_MESSAGE);
    }
}


