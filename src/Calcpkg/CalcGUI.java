package Calcpkg;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalcGUI extends JFrame{
    private JTextField inputField;
    public CalcGUI(){
        setTitle("Calculator");
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 6));

        String[] buttonChars = {
                "(", ")", "sin(", "cos(","tan(","←",
                "7", "8", "9", "+","°","ln(",
                "4", "5", "6", "-","√","",
                "1", "2", "3", "*","^","",
                "C", "0", ".", "/","=","",
        };
        for(String c : buttonChars){
            JButton button = new JButton(c);
            button.addActionListener(this::buttonClicked);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void buttonClicked(ActionEvent e){
        JButton source = (JButton) e.getSource();
        String buttonLabel = source.getText();

        if (buttonLabel.equals("=")) {
            try {
                Calculator calculator = new Calculator();
                Double result = calculator.evaluateExpression(inputField.getText());
                inputField.setText(result.toString());
            } catch (Exception ex) {
                inputField.setText("Error");
            }
        } else if (buttonLabel.equals("C")) {
            inputField.setText("");
        }else if (buttonLabel.equals("←")) {
            String s = inputField.getText();
            if (!s.isBlank()) {
                inputField.setText(s.substring(0,s.length()-1));
            }
            else{
                inputField.setText(s.substring(0,s.length()));
            }
        } else {
            inputField.setText(inputField.getText() + buttonLabel);
        }
    }

}
