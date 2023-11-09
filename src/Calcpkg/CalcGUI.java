package Calcpkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalcGUI extends JFrame {
    private final JTextField inputField;
    private static Double result = null;

    public CalcGUI() {
        setTitle("Calculator");
        setLayout(new BorderLayout());
        ImageIcon img = new ImageIcon("assets/icon.png");
        setIconImage(img.getImage());
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(true);
        add(inputField, BorderLayout.NORTH);
        Font largeFont = new Font("Arial", Font.BOLD, 25); // Choose the font size you prefer
        inputField.setFont(largeFont);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 6));

        String[] buttonChars = {
                "sin(", "°", "(", ")", "C", "←",
                "cos(", "ln(", "7", "8", "9", "/",
                "tan(", "√", "4", "5", "6", "*",
                "π", "^", "1", "2", "3", "-",
                "^-1", "Ans", "0", ".", "=", "+",
        };

        for (String c : buttonChars) {
            JButton button = new JButton(c);
            button.addActionListener(this::buttonClicked);
            button.setFont(new Font("Arial", Font.BOLD, 15));
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        setSize(400, 300); // Set the size of the JFrame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void buttonClicked(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonLabel = source.getText();
        if (buttonLabel.equals("=")) {
            try {
                Calculator calculator = new Calculator();
                result = calculator.evaluateExpression(inputField.getText());
                inputField.setText(result.toString());
            } catch (Exception ex) {
                inputField.setText("Error" + ex);
            }
        } else if (buttonLabel.equals("C")) {
            inputField.setText("");
        } else if (buttonLabel.equals("←")) {
            String s = inputField.getText();
            if (!s.isBlank()) {
                inputField.setText(s.substring(0, s.length() - 1));
            }
        } else if (buttonLabel.equals("Ans") && result != null) {
            inputField.setText(inputField.getText() + result);
        } else if (buttonLabel.equals("Ans")) {
            inputField.setText("Error");
        } else if (buttonLabel.equals("^-1")) {
            inputField.setText(inputField.getText() + "arc");
        } else {
            inputField.setText(inputField.getText() + buttonLabel);
        }
    }

}
