import java.util.ArrayList;
public class Lexico {
    // Tokens
    private static boolean lt;
    public static void lt(boolean entrada){
        lt=entrada;
    }

    public static String analise(String entrada, int linha, ArrayList<String> tokenList) {
        int length = entrada.length(), // tamanho da string a ser analisada
                i = 0, // iterador zerado
                base = 0, // comeco do token
                ponta = 0, // analisando a ponta da string do token
                memoria = 0, // memoria
                tipo = 0; // tipo do token
        String resposta = "", corte = ""; // reposta e corte de analise
        // System.out.println(entrada.substring(i,i+1));
        while (i < length) { //
            // System.out.print(entrada.charAt(i));
            ponta = tipar_caracter(String.valueOf(entrada.charAt(i)));
            // Se não tinha nada antes, marca o inicio

            memoria = tipar_token(tipo, ponta);
            //System.out.println("ponta:" + ponta + " memoria:" + memoria + " base:" + base + "  i:" + i + "  charAt: < " + entrada.charAt(i) + " >");
            if (memoria != tipo && memoria != -1) {
                // mudança de tipo de token sem alterar o inicio
                tipo = memoria;
            }
            if (memoria == 0) {
                base = i + 1;
                tipo = memoria;// espaço vazio, puxando o inicio do token
            }

            //System.out.println(memoria);
            if (memoria == -1) { // imprime senão mudança de conteúdo.
                if (tipo == 1) {
                    corte = entrada.substring(base, i);
                    tipo = confirmId(corte);
                }
                resposta = addResposta(tipo);
                if(lt) // habilitacao de impressao
                System.out.println("(TOKEN:" + resposta + ", LEXEMA:"+entrada.substring(base, i)+", LINHA:" + linha +", COLUNA:"+(base+1)+")");
                tokenList.add(resposta);
                base = i;
                i--;
                ponta = 0;
                memoria = 0;
                tipo = 0;
            }
            i++; // incrementa
        }
        //w("final");
        // imprime finalização do conteúdo.
        if (tipo == 1) {
            corte = entrada.substring(base, i);
            tipo = confirmId(corte);
        }
        resposta = addResposta(tipo);
        if(lt) // habilitacao de impressao
        System.out.println("(TOKEN:" + resposta + ", LEXEMA:"+entrada.substring(base, i)+", LINHA:" + linha +", COLUNA:"+(base+1)+")");
        tokenList.add(resposta);
        return resposta;
    }

    private static int tipar_token(int tipo_token, int tipo_caracter) {
        switch (tipo_token) {
            case 0:
                if (tipo_caracter == 1)
                    return 1; // id
                if (tipo_caracter == 2)
                    return 2; // number
                if (tipo_caracter == 3)
                    return 3; // atribuicao
                if (tipo_caracter == 4)
                    return 4; // comparacao
                if (tipo_caracter == 5)
                    return 5; // unario
                if (tipo_caracter == 6)
                    return 6; // binario
                if (tipo_caracter == 20)
                    return 20; // aspas
                if (tipo_caracter == 21)
                    return 21; // ponto virgula
                if (tipo_caracter == 22)
                    return 22; // OP
                if (tipo_caracter == 23)
                    return 23; // CP
                if (tipo_caracter == 24)
                    return 24; // OC
                if (tipo_caracter == 25)
                    return 25; // CC
                break;
            case 1:
                if (tipo_caracter == 1)
                    return 1;
                if (tipo_caracter == 2)
                    return 1;
                return -1;
            case 2:
                if (tipo_caracter == 2)
                    return 2;
                return -1;
            case 3:
                if (tipo_caracter == 3)
                    return 4;
                return -1;
            case 4:
                return -1;
            case 5:
                return -1;
            case 6:
                return -1;
            case 20:
                return -1;
            case 21:
                return -1;
            case 22:
                return -1;
            case 23:
                return -1;
            case 24:
                return -1;
            case 25:
                return -1;
            default:
                return 0;
        }
        return 0;
    }

    // retorna o tipo desde da cabeça
    private static int tipar_caracter(String c) {
        if (Simbolos.isLetter(c))
            return 1; // deve ser um id
        if (Simbolos.isDigit(c))
            return 2; // deve ser um numero
        if (Simbolos.isIgual(c))
            return 3; // é o simbolo de igual atribuição ou igualdade
        if (Simbolos.isComparator(c))
            return 4; // é < ou ==
        if (Simbolos.isUnary(c))
            return 5;// é - ou +
        if (Simbolos.isBinary(c))
            return 6;// é / ou *
        if (Simbolos.isAspas(c))
            return 20;// é "
        if (Simbolos.isPontoVirgula(c))
            return 21;// é o ;
        if (Simbolos.isOpenParenteses(c))
            return 22;// é o (
        if (Simbolos.isCloseParenteses(c))
            return 23;// é o )
        if (Simbolos.isOpenChaves(c))
            return 24;// é o {
        if (Simbolos.isCloseChaves(c))
            return 25;// é o }
        if (Simbolos.isWaste(c))
            return 0; // é espaço, pular linha, ou tab
        return 0;
    }

    // retorna o nome(string) do tipo(int)
    public static String addResposta(int tipo) {
        switch (tipo) {
            case 1:
                return "id";
            case 2:
                return "number";
            case 3:
                return "atribute";
            case 4:
                return "condicao";
            case 5:
                return "unary";
            case 6:
                return "binary";
            case 11:
                return "read";
            case 12:
                return "write";
            case 13:
                return "if";
            case 14:
                return "else";
            case 15:
                return "while";
            case 20:
                return "aspas";
            case 21:
                return "pontoVirgula";
            case 22:
                return "openP";
            case 23:
                return "closeP";
            case 24:
                return "openC";
            case 25:
                return "closeC";
            default:
                return "error[" + tipo + "]";
        }
    }

    // analisa se o id é uma palavra reservada
    private static int confirmId(String entrada) {
        if (entrada.equals("read"))
            return 11;
        if (entrada.equals("write"))
            return 12;
        if (entrada.equals("if"))
            return 13;
        if (entrada.equals("else"))
            return 14;
        if (entrada.equals("while"))
            return 15;
        return 1;
    }

    /*
     * Tokens
     * tipos:
     * 1 id,
     * 11 read
     * 12 write
     * 13 if
     * 14 else
     * 15 while
     * 2 number,
     * 3 atribute
     * 4 condicao
     * 5 unary,
     * 6 binary
     *//*
        * /
        * id: {L} ( {L} | {D} )*
        * read = id: "read"
        * write = id: "write"
        * if = id: "if"
        * else = id: "else"
        * while = id: "while"
        * atribute: "="
        * number: {D}+
        * condicao: "<" | "=="
        * unary: "-" | "+"
        * binary: "*" | "/"
        */

    /*
     * Não terminais
     * <PROGRAM>
     * <STMT_LIST>
     * <STMT>
     * 
     * <operando>
     * <unary>
     * <binary>
     * 
     * <mensagem>
     * 
     * <string>
     * 
     * <TERMO>
     * 
     * 
     * 
     * <math_pointer>
     * <math_unary>
     * <math_binary>
     * <math_openP>
     * <math_operando>
     * <math_closeP>
     * 
     * <condicional_exp>
     * <condicional_se>
     * <condicional_else>
     * 
     * <loop_while>
     */
    /*
     * Gramática
     * <PROGRAM> ::= <STMT_LIST>;
     * <STMT_LIST> ::= <STMT> ";" <STMT_LIST> | î;
     * <STMT> ::= read id
     * | write <mensagem>
     * | id atribute <math_pointer>
     * | <condicional_se>
     * | <loop_while>;
     * 
     * <math_pointer> ::= unary <math_unary> | openP <math_openP> | <operando>
     * <math_operando>;
     * <math_unary> ::= openP <math_openP> | <operando> <math_operando>;
     * <math_binary> ::= openP <math_openP> | <operando> <math_operando>;
     * <math_openP> ::= openP <math_openP> | <operando> <math_operando> | unary
     * <math_unary>;
     * <math_operando> ::= unary <math_unary> | binary <math_binary> | closeP
     * <math_closeP> | î;
     * <math_closeP> ::= closeP <math_closeP> | binary <math_binary> | î;
     * 
     * <operando> ::= number | id;
     * <string> ::= id <string> | î;
     * <mensagem> ::= aspas <string> aspas | <operando>;
     * 
     * <condicional_exp> ::= <math_pointer> condicao <math_pointer>;
     * <condicional_se> ::= if <condicional_exp> "{" <STMT_LIST> "}"
     * <condicional_else>;
     * <condicional_else> ::= else "{" <STMT_LIST> "}" |î;
     * 
     * <loop_while> ::= while <condicional_exp> "{" <STMT_LIST> "}";
     */

    public static void w(String a) {
        System.out.println(a);
    }
}
