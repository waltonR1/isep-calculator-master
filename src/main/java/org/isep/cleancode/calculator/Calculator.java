package org.isep.cleancode.calculator;

import java.util.Stack;

public class Calculator {
    public double evaluateMathExpression(String expression) {

//      basicPositiveInteger and basicFloatingPointNumber
//      return basicPositiveInteger_basicFloatingPointNumber(expression);

//      basicAddition and multipleAddition and subtractions
//      return basicAddition_multipleAddition_subtractions(expression);

//      multiplications and multipleOperations
//      return multiplications_multipleOperations(expression);

//      basicNegativeInteger
//      return basicNegativeInteger(expression);

//      multipleOperationsWithParenthesis
        return multipleOperationsWithParenthesis(expression);
    }


    private double basicPositiveInteger_basicFloatingPointNumber (String expression) {
        expression = expression.replaceAll(" ", "");
        double result = Double.parseDouble(expression);
        return result;
    };

    private double basicAddition_multipleAddition_subtractions(String expression) {
        expression = expression.replaceAll(" ", "");
        if (expression.contains("+") || expression.contains("-")) {
            String[] parts = expression.split("(?<=[0-9])(?=[+-])");
            double result = 0;
            for (String part : parts) {
                result += Double.parseDouble(part);
            }
            return result;
        }
        return Double.parseDouble(expression);
    }

    private double multiplications_multipleOperations (String expression) {
        expression = expression.replaceAll(" ", "");
        Stack<Double> stack = new Stack<>();
        double num = 0;
        char op = '+';

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                sb.append(ch);
                while (i + 1 < expression.length() &&
                        (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    sb.append(expression.charAt(++i));
                }
                num = Double.parseDouble(sb.toString());
            }

            if (!Character.isDigit(ch) && ch != '.' || i == expression.length() - 1) {
                if (op == '+') {
                    stack.push(num);
                } else if (op == '-') {
                    stack.push(-num);
                } else if (op == '*') {
                    stack.push(stack.pop() * num);
                }
                op = ch;
                num = 0;
            }
        }

        double result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private double basicNegativeInteger(String expression){
        expression = expression.replaceAll(" ", "");
        Stack<Double> stack = new Stack<>();
        double num = 0;
        char op = '+';

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            boolean isNegative = false;
            if (ch == '-' && (i == 0 || "+-*".indexOf(expression.charAt(i - 1)) >= 0)) {
                isNegative = true;
                i++;
                ch = expression.charAt(i);
            }

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                sb.append(ch);
                while (i + 1 < expression.length() &&
                        (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    sb.append(expression.charAt(++i));
                }
                num = Double.parseDouble(sb.toString());
                if (isNegative) num = -num;
            }

            if (!Character.isDigit(ch) && ch != '.' || i == expression.length() - 1) {
                if (op == '+') {
                    stack.push(num);
                } else if (op == '-') {
                    stack.push(-num);
                } else if (op == '*') {
                    stack.push(stack.pop() * num);
                }
                op = ch;
                num = 0;
            }
        }

        double result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private double multipleOperationsWithParenthesis(String expression) {
        expression = expression.replaceAll(" ", "");
        return sub(expression.toCharArray(), new int[]{0});
    }

    private double sub(char[] chars, int[] indexRef) {
        Stack<Double> stack = new Stack<>();
        double num = 0;
        char op = '+';

        while (indexRef[0] < chars.length) {
            char ch = chars[indexRef[0]];

            boolean isNegative = false;
            if (ch == '-' && (indexRef[0] == 0 || "+-*(".indexOf(chars[indexRef[0] - 1]) >= 0)) {
                isNegative = true;
                indexRef[0]++;
                ch = chars[indexRef[0]];
            }

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                sb.append(ch);
                while (indexRef[0] + 1 < chars.length &&
                        (Character.isDigit(chars[indexRef[0] + 1]) || chars[indexRef[0] + 1] == '.')) {
                    sb.append(chars[++indexRef[0]]);
                }
                num = Double.parseDouble(sb.toString());
                if (isNegative) num = -num;
            } else if (ch == '(') {
                indexRef[0]++;  // 跳过 '('
                num = sub(chars, indexRef);  // 递归进入子表达式
                if (isNegative) num = -num;
            }

            // 遇到操作符 或 表达式结束 或 遇到 ')'
            if ("+-*)".indexOf(ch) >= 0 || indexRef[0] == chars.length - 1) {
                switch (op) {
                    case '+': stack.push(num); break;
                    case '-': stack.push(-num); break;
                    case '*': stack.push(stack.pop() * num); break;
                }
                op = ch;
                num = 0;
            }

            if (ch == ')') {
                break; // 结束当前子表达式
            }

            indexRef[0]++;
        }

        double result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

}
