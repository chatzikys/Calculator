package Calcpkg;

import java.util.*;

public class Calculator {
    private String expression;
    private final Queue<String> output = new LinkedList<>();
    private final ArrayList<String> tokens = new ArrayList<>();
    private final Stack<String> operatorStack = new Stack<>();
    private final Stack<Double> compute = new Stack<>();
    public Calculator() {
    }
    public int getOrder(String c) {
        return switch (c) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^", "√" -> 3;
            case "sin", "cos", "tan", "°", "ln","arcsin", "arcos", "arctan","sinh","cosh","tanh" -> 4;
            default -> -1;
        };

    }
    public double evaluate(double a, double b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "^" -> Math.pow(a, b);
            case "√" -> Math.sqrt(a);
            case "sin" -> Math.sin(a);
            case "cos" -> Math.cos(a);
            case "tan" -> Math.tan(a);
            case "ln" -> Math.log(a);
            case "°" -> a * Math.PI / 180;
            case "arcsin" -> Math.asin(a);
            case "arccos" -> Math.acos(a);
            case "arctan" -> Math.atan(a);
            case "sinh" -> Math.sinh(a);
            case "cosh" -> Math.cosh(a);
            case "tanh" -> Math.tanh(a);
            default -> -1;
        };
    }
    public void infixToRPN() {
        if (expression.isBlank()) {
            System.out.println("Expression Is Blank");
            return;
        }
        tokenizeExpression();
        //System.out.println(tokens);
        while (!tokens.isEmpty()) {
            String operator;
            try {
                Double.parseDouble(tokens.getFirst());
                output.add(tokens.getFirst());
                tokens.removeFirst();
                continue;

            } catch (Exception e) {
                //System.out.println(e);
                operator = tokens.getFirst();
                tokens.removeFirst();
            }
            if (Objects.equals(operator, " ")) {
                break;
            } else if (Objects.equals(operator, "π")) {
                output.add(String.valueOf(Math.PI));
            }else if (Objects.equals(operator, "e")) {
                output.add(String.valueOf(Math.exp(1)));
            } else if (!Objects.equals(operator, "(") && !Objects.equals(operator, ")")) {
                while (!operatorStack.isEmpty() && getOrder(operatorStack.peek()) >= getOrder(operator)) {
                    output.add(String.valueOf(operatorStack.pop()));
                }
                operatorStack.push(operator);
            } else if (Objects.equals(operator, "(")) {
                operatorStack.push(operator);
            } else if (Objects.equals(operator, ")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    output.add(String.valueOf(operatorStack.pop()));
                }
                if (operatorStack.isEmpty()) {
                    System.out.println("Error Mismatched parenthesis");
                    return;
                } else if (Objects.equals(operatorStack.peek(), "(")) {
                    operatorStack.pop();
                }
            }
        }
        while (!operatorStack.isEmpty())
            output.add(String.valueOf(operatorStack.pop()));

        System.out.println(output);
    }

    private void tokenizeExpression() {
        StringTokenizer exp = new StringTokenizer(expression, "(+ -*/^√°)", true);//Tokenizes the initial expression.
        while (exp.hasMoreTokens()) {
            tokens.add(exp.nextToken());
        }
        fixMinus();
    }

    private void fixMinus()
    {
        for (int i = 0; i < tokens.size(); i++) {
            if(!Objects.equals(tokens.get(i), "-"))
            {
                continue;
            }
            else if(i == 0)
            {
                tokens.add(0, "0");
                continue;
            }
            else if(!isDouble(tokens.get(i-1)))
            {
                tokens.add(i, "0");
           }
        }
    }
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
            } catch (Exception e) {
                return false;
            }
        }
    public double evaluateRPN() {
        String operator;
        while (!output.isEmpty()) {
            String item = output.poll();
            if (item != null) {
                try {
                    double d = Double.parseDouble(item);
                    compute.push(d);
                } catch (NumberFormatException e) {
                    operator = item;
                    if (singleParam(operator)) {
                        double a = compute.pop();
                        compute.push(evaluate(a, a, operator));
                    } else {
                        double b = compute.pop();
                        double a = compute.pop(); // A is the 1st term.
                        compute.push(evaluate(a, b, operator));
                    }
                }
            }
        }
        return compute.pop();
    }
    private boolean singleParam(String operator) {
        return switch (operator) {
            case "sin", "cos", "tan", "√", "°", "ln","arcsin", "arcos", "arctan", "sinh","cosh","tanh"-> true;
            default -> false;
        };
    }
    public Double evaluateExpression(String text) {
        this.expression = text;
        if (!(expression.isBlank())) {
            infixToRPN();
            return evaluateRPN();
        }
        return null;
    }
}