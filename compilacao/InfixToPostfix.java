import java.util.*;

public class InfixToPostfix {
    
    public static void main(String[] args) {
        String infix = "(A+B)*C-(D-E)*(F+G)";
        String postfix = infixToPostfix(infix);
        System.out.println(postfix); // Output: AB+C*DE-FG+*-
    }
    
    public static String infixToPostfix(String infix) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            if (Character.isLetterOrDigit(c)) {
                postfix += c;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix += stack.pop();
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            postfix += stack.pop();
        }
        
        return postfix;
    }
    
    public static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
}
