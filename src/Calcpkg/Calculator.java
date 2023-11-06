package Calcpkg;
import java.util.*;

public class Calculator {
    private String expression;
    private Queue<String> output = new LinkedList<>();
    private ArrayList<String> tokens = new ArrayList<>();
    private Stack<Character> operatorStack = new Stack<>();

    private Stack<Double> compute = new Stack<>();


    public Calculator() {

    }

    public int getOrder(Character c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^', '√' -> 3;
            default -> -1;
        };

    }

    public double calculate(double a, double b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            case '^' -> Math.pow(a, b);
            default -> -1;
        };
    }

    public void infixToRPN() {
        if (expression.isBlank()) {
            System.out.println("Expression Is Blank");
            return;
        }
        StringTokenizer exp = new StringTokenizer(expression, "(+-*/^√ )", true);
        while (exp.hasMoreTokens()) {
            tokens.add(exp.nextToken());
        }
        //System.out.println(tokens);
        while (!tokens.isEmpty()) {
            char op;
            try {
                Integer.parseInt(tokens.getFirst());
                output.add(tokens.getFirst());
                tokens.removeFirst();
                continue;

            } catch (Exception e) {
                //System.out.println(e);
                op = tokens.getFirst().charAt(0);
                tokens.removeFirst();
            }
            if (op == ' ')
                continue;
            if (op != '(' && op != ')') {
                while (!operatorStack.isEmpty() && getOrder(operatorStack.peek()) >= getOrder(op)) {
                    output.add(String.valueOf(operatorStack.pop()));
                }
                operatorStack.push(op);
            }
            if (op == '(') {
                operatorStack.push(op);
            }
            if (op == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    output.add(String.valueOf(operatorStack.pop()));
                }
                if (operatorStack.isEmpty()) {
                    System.out.println("Error Mismatched parenthesis");
                    return;
                } else if (operatorStack.peek() == '(') {
                    operatorStack.pop();
                }

            }
        }
        while (!operatorStack.isEmpty())
            output.add(String.valueOf(operatorStack.pop()));

        System.out.println(output);
    }

    public double computePRN() {
        char op;
        while (!output.isEmpty()) {
            String item = output.poll();
            if (item != null) {
                try {
                    double d = Double.parseDouble(item);
                    compute.push(d);
                } catch (NumberFormatException e) {
                    op = item.charAt(0);
                    double b = compute.pop();
                    double a = compute.pop(); // A is the 1st term.
                    compute.push(calculate(a, b, op));
                }
            }
        }
        return compute.pop();
    }
    public Double evaluateExpression(String text) {
        this.expression = text;
        if(!(expression.isBlank())) {
            infixToRPN();
            return computePRN();
        }
        return null;
    }
}