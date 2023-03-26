import java.util.ArrayList;
import java.util.Stack;

/*
boolean	: empty() : Tests if this stack is empty.
E : peek() : Looks at the object at the top of this stack without removing it from the stack.
E : pop() : Removes the object at the top of this stack and returns that object as the value of this function.
E : push(E item) : Pushes an item onto the top of this stack.
int : search(Object o) :Returns the 1-based position where an object is on this stack.
*/
public class Sintatico {
    static Stack<Integer> pilha = new Stack<Integer>();

    public static void analise(ArrayList<Integer> tokenList, boolean ls) {
        if (ls)
            if (pilha.empty())
                System.out.println("Pilha vazia iniciada");
    }
}
