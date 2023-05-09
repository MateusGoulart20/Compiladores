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
    static Stack<String> pilha = new Stack<String>();
    static private boolean ls, erro = false;
    static private ArrayList<Token> tokenList;

    public static void ls(boolean entrada) {
        ls = entrada;
    }

    public static void w(String entrada) {
        if (ls)
            System.out.println(entrada);
    }

    private static void empilha(String entrada) {
        w("Empilha: " + entrada);
        pilha.push(entrada);
    }

    private static void desempilha() {
        w("Desempilha: " + pilha.peek());
        pilha.pop();
    }

    private static void inesperado(String terminal, Token token) {
        w("Erro na linha[" + token.linha + "] e coluna[" + token.coluna + "]\n  " + terminal + " : Nao esperava "
                + token.token);
        erro = true;
    }
    private static void inesperado(Token token) {
        w("Erro na linha[" + token.linha + "] e coluna[" + token.coluna + "]\n  " + token.token + " : Inesperado ");
        erro = true;
    }

    public static void analise(ArrayList<Token> entrada) {
        tokenList = new ArrayList<Token>();
        for (Token pas : entrada) {
            tokenList.add(pas);
        }
        empilha("$");
        empilha("<PROGRAM>");
        int x = 1;
        for (Token token : tokenList) {
            String i = token.token;
            w("   #Posicao : " + x + " #Topo: " + pilha.peek() + " #Entrada na lista: " + i);
            while (compara(i)) {
                if (erro)
                    return;
                tratamento(token);
            }
            x++;
        }
        w("Finalizando com:" + pilha.peek());
        desempilha();
        w(pilha.peek());
    }

    private static void tratamento(Token i) {
        String token = i.token;
        switch (pilha.peek()) {
            case "<PROGRAM>":
                switch (token) {
                    case "$":
                    case "id":
                    case "read":
                    case "write":
                    case "if":
                    case "while":
                        procedimento(0);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<STMT_LIST>":
                switch (token) {
                    case "$":
                    case "closeC":
                        procedimento(2);
                        return;
                    case "id":
                    case "read":
                    case "write":
                    case "if":
                    case "while":
                        procedimento(1);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<STMT>":
                switch (token) {
                    case "id":
                        procedimento(5);
                        return;
                    case "read":
                        procedimento(3);
                        return;
                    case "write":
                        procedimento(4);
                        return;
                    case "if":
                        procedimento(6);
                        return;
                    case "while":
                        procedimento(7);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }

            case "<operando>":
                switch (token) {
                    case "id":
                        procedimento(26);
                        return;
                    case "number":
                        procedimento(25);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<mensagem>":
                switch (token) {
                    case "pontoVirgula":
                        procedimento(31);
                        return;
                    case "aspas":
                        procedimento(29);
                        return;
                    case "id":
                        procedimento(30);
                        return;
                    case "number":
                        procedimento(30);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<string>":
                switch (token) {
                    case "aspas":
                        procedimento(28);
                        return;
                    case "id":
                        procedimento(27);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<math_pointer>":
                switch (token) {
                    case "openP":
                        procedimento(9);
                        return;
                    case "id":
                    case "number":
                        procedimento(10);
                        return;
                    case "unary":
                        procedimento(8);
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<math_unary>":
                switch (token) {
                    case "openP":
                        procedimento(11);
                        return;
                    case "id":
                    case "number":
                        procedimento(12);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<math_binary>":
                switch (token) {
                    case "openP":
                        procedimento(13);
                        return;
                    case "id":
                    case "number":
                        procedimento(14);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<math_openP>":
                switch (token) {
                    case "openP":
                        procedimento(15);
                        return;
                    case "id":
                    case "number":
                        procedimento(16);
                        return;
                    case "unary":
                        procedimento(17);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<math_operando>":
                switch (token) {
                    case "pontoVirgula":
                    case "openC":
                    case "condicao":
                        procedimento(21);
                        return;
                    case "closeP":
                        procedimento(20);
                        return;
                    case "unary":
                        procedimento(18);
                        return;
                    case "binary":
                        procedimento(19);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<math_closeP>":
                switch (token) {
                    case "pontoVirgula":
                    case "openC":
                    case "condicao":
                        procedimento(24);
                        return;
                    case "closeP":
                        procedimento(22);
                        return;
                    case "binary":
                        procedimento(23);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<condicional_exp>":
                switch (token) {
                    case "openP":
                    case "id":
                    case "number":
                    case "unary":
                        procedimento(32);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<condicional_se>":
                switch (token) {
                    case "if":
                        procedimento(33);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<condicional_else>":
                switch (token) {
                    case "pontoVirgula":
                        procedimento(35);
                        return;
                    case "else":
                        procedimento(34);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            case "<loop_while>":
                switch (token) {
                    case "while":
                        procedimento(36);
                        return;
                    default:
                        inesperado(pilha.peek(), i);
                        return;
                }
            default:
            inesperado(i);
                return;
        }
    }

    private static void procedimento(int entrada) {
        w(" &Procedimento: " + entrada);
        switch (entrada) {
            case 0:
                desempilha();
                empilha("<STMT_LIST>");
                return;
            case 1:
                empilha("pontoVirgula");
                empilha("<STMT>");
                return;
            case 2:
            case 21:
            case 24:
            case 28:
            case 31:
            case 35:
                desempilha();
                return; // retornar vazio
            case 3:
                desempilha();
                empilha("id");
                empilha("read");
                return;
            case 4:
                desempilha();
                empilha("<mensagem>");
                empilha("write");
                return;
            case 5:
                desempilha();
                empilha("<math_pointer>");
                empilha("atribute");
                empilha("id");
                return;
            case 6:
                desempilha();
                empilha("<condicional_se>");
                return;
            case 7:
                desempilha();
                empilha("<loop_while>");
                return;
            case 8:
                desempilha();
                empilha("<math_unary>");
                empilha("unary");
                return;
            case 9:
            case 11:
            case 13:
            case 15:
                desempilha();
                empilha("<math_openP>");
                empilha("openP");
                return;
            case 10:
            case 12:
            case 14:
            case 16:
                desempilha();
                empilha("<math_operando>");
                empilha("<operando>");
                return;
            case 17:
            case 18:
                desempilha();
                empilha("<math_unary>");
                empilha("unary");
                return;
            case 19:
            case 23:
                desempilha();
                empilha("<math_binary>");
                empilha("binary");
                return;
            case 20:
            case 22:
                desempilha();
                empilha("<math_closeP>");
                empilha("closeP");
                return;
            case 25:
                desempilha();
                empilha("number");
                return;
            case 26:
                desempilha();
                empilha("id");
                return;
            case 27:
                empilha("id");
                return;
            case 29:
                empilha("aspas");
                empilha("<string>");
                empilha("aspas");
                return;
            case 30:
                empilha("<operando>");
                return;
            case 32:
                desempilha();
                empilha("<math_pointer>");
                empilha("condicao");
                empilha("<math_pointer>");
                return;
            case 33:
                desempilha();
                empilha("<condicional_else>");
                empilha("closeC");
                empilha("<STMT_LIST>");
                empilha("openC");
                empilha("<condicional_exp>");
                empilha("if");
                return;
            case 34:
                desempilha();
                empilha("closeC");
                empilha("<STMT_LIST>");
                empilha("openC");
                empilha("else");
                return;
            case 36:
                desempilha();
                empilha("closeC");
                empilha("<STMT_LIST>");
                empilha("openC");
                empilha("<condicional_exp>");
                empilha("while");
                return;
            default:
                w("Erro Gravissimo: procedimento não reconhecido");
        }
    }

    private static boolean compara(String token) {
        if (token.equals(pilha.peek())) {
            desempilha();
            return false; // pode passar para o proximo
        } else {
            return true; // precisa fazer tratamento
        }
    }
}
