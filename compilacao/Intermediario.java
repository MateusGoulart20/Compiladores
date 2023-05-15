import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
// IO
import java.io.IOException;
//import java.io.OutputStream;
import java.io.FileOutputStream;
//import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;



public class Intermediario {
    private static ArrayList<PalavraValor> declaradas = Sematico.getDeclaradas();
    private static ArrayList<Token> tokenList;
    private static ArrayList<String> variaveis = new ArrayList<String>(), comando = new ArrayList<String>();
    private static Stack<String> escopo = new Stack<String>();
    private static int max_operation = 0;

    public static void write_lexema(ArrayList<Token> tl, String argument) {
        tokenList = tl;
        // Gerar as variaveis antes de começar o esquema
        gerar_comandos();
        gerar_variaveis();
        try{
            //Fluxo de saida de um arquivo
            //OutputStream os =  // nome do arquivo que será escrito
            //Writer wr = new OutputStreamWriter(new FileOutputStream("file1.txt")); // criação de um escritor
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(argument+"_inter.txt"))); // adiciono a um escritor de buffer

            for (String t : variaveis){
                br.write("VAR "+t+"\n");
            }
            for (String t : comando){
                br.write(t+"\n");
            }
            // Coisas que podem levar um pointer matematico
            // "=" atribute. -> atribute pointer ponto_virgula
            // if pointer condicao pointer open C
            /*br.write("\n");
            for (Token i : tokenList){
                br.write(i.lexema);
                if(i.lexema.equals(";")||i.lexema.equals("{")){
                    br.write("\n");
                }else{
                    br.write(" ");
                }
            }*/
            br.close();
        } catch (IOException ioe){
            System.out.println("FAIL");
        }
        
    }
    private static void gerar_comandos(){
        int cont_if = 1, cont_while = 1;
        ArrayList<Token> analise = new ArrayList<Token>();
        for(Token t : tokenList){
            analise.add(t);
            if(t.lexema.equals(";")||t.lexema.equals("{")){
                if(analise.get(0).token.equals("id")){
                    if(analise.get(1).token.equals("atribute")){
                        // portanto é o começo de um atribuicao
                        // deve terminar com a variavel <var_*> contendo o restante da expressao
                        resolver_expressao("var_"+analise.get(0).lexema, analise.subList(2, analise.size()-1));
                    }
                }
                if(analise.get(0).token.equals("if")){
                    //comando.add("if_in "+cont_if);
                    int v=0;
                    for(int i = 0; i<analise.size(); i++){
                        if(analise.get(i).token.equals("condicao")){
                            v=i;
                            resolver_expressao("if_"+cont_if+"_e", analise.subList(1, i));
                            resolver_expressao("if_"+cont_if+"_d", analise.subList(i+1, analise.size()-1));
                        }
                    }
                    comando.add("COMPARE "+analise.get(v).lexema+" if_"+cont_if+"_e if_"+cont_if+"_d\nJMP if_out_false_"+cont_if);
                    escopo.push("ROT if_out_false_"+cont_if);
                    cont_if++;
                }
                if(analise.get(0).token.equals("while")){
                    for(int i = 0; i<analise.size(); i++){
                        if(analise.get(i).token.equals("condicao")){
                            comando.add("ROT while_in_"+cont_while);
                            escopo.push("JMP while_in_"+cont_while+"\nROT while_out_"+cont_while);
                            resolver_expressao("while_"+cont_while+"_e", analise.subList(1, i));
                            resolver_expressao("while_"+cont_while+"_d", analise.subList(i+1, analise.size()-1));
                            comando.add("COMPARE "+analise.get(i).lexema+" while_"+cont_while+"_e while_"+cont_while+"_d\nJMP while_out_"+cont_while);
                        }
                    }
                    cont_while++;
                }
                if(analise.get(0).lexema.equals("}")){
                    //System.out.println("while:"+cont_while+" if:"+cont_if);
                    if(analise.get(1).lexema.equals("else")){
                        comando.add("JMP if_out_"+(cont_if-1));
                        comando.add(escopo.pop());
                        escopo.push("ROT if_out_"+(cont_if-1));
                    }else{
                        comando.add(escopo.pop());
                    }
                }
                if(analise.get(0).lexema.equals("write")){
                    boolean aspas = false;
                    for(int i = 1; i<analise.size(); i++){
                        if(analise.get(i).token.equals("id")){
                            if(aspas){
                                comando.add("WRITS "+analise.get(i).lexema);
                            }else{
                                comando.add("WRITV var_"+analise.get(i).lexema);
                            }
                        }
                        if(analise.get(i).token.equals("aspas"))aspas = !aspas;
                    }
                    
                }
                analise = new ArrayList<Token>();
            }
        }
    }

    private static void resolver_expressao(String t, List<Token> expressao){
        int index = 0, operation=0;
        while(true){
            expressao = desparentizar(expressao);
            index = maior_prioridade(expressao);
            if(index == 0)break;
            operation++;
            expressao = suprimir(expressao, index, operation);
        }
        if(operation == 0){
            comando.add("RECEBE "+t+" "+expressao.get(0).lexema);
        }else{
            comando.add("RECEBE "+t+" aux_"+operation);
        }
        if(operation>max_operation)max_operation=operation;
        /*String debug = "//test:";
        for(int i =0; i<expressao.size();i++ )debug += expressao.get(i).lexema + " ";
        debug += "\n";
        comando.add(debug);*/
    }

    private static List<Token> suprimir(List<Token> expressao, int posi, int aux){
        Token auxiliar = new Token("aux", "aux_"+aux); auxiliar.coluna=0;
        if(expressao.get(posi).lexema.equals("+")){
            comando.add("SOMA "+auxiliar.lexema+" "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }
        if(expressao.get(posi).lexema.equals("-")){
            comando.add("SUBT "+auxiliar.lexema+" "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }
        if(expressao.get(posi).lexema.equals("*")){
            comando.add("MULT "+auxiliar.lexema+" "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }
        if(expressao.get(posi).lexema.equals("/")){
            comando.add("DIVD "+auxiliar.lexema+" "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }

        // depois de qualquer operacao
        expressao.set(posi-1, auxiliar);
        for(int i = posi; i<expressao.size()-1; i++){
            expressao.set(i, expressao.get(i+1));
        }
        for(int i = posi; i<expressao.size()-1; i++){
            expressao.set(i, expressao.get(i+1));
        }
        
        return expressao.subList(0, expressao.size()-2);
    }

    private static int maior_prioridade(List<Token> expressao){
        int prioridade=0, maior_prioridade=0, index=0;
        Token t;
        for (int i=0; i<expressao.size() ; i++){
        t = expressao.get(i);
            if(t.token.equals("unary")){
                prioridade++;
                if(prioridade>maior_prioridade){
                    maior_prioridade = prioridade;
                    index=i;
                }
                prioridade--;
            }
            if(t.token.equals("binary")){
                prioridade+=2;
                if(prioridade>maior_prioridade){
                    maior_prioridade = prioridade;
                    index=i;
                }
                prioridade-=2;
            }
            if(t.token.equals("openP"))
                prioridade+=3;
            if(t.token.equals("closeP"))
                prioridade-=3;
        }
        return index;
    }

    private static List<Token> desparentizar(List<Token> expressao){
        Token t;
        for (int i=0; i<expressao.size() ; i++){
            t = expressao.get(i);
            if(t.token.equals("closeP")){
                //for(int erro = 0; erro<expressao.size(); erro++)System.out.print("["+expressao.get(erro).lexema+"]");
                //System.out.print("\n");
                if(expressao.get(i-2).token.equals("openP")){
                    // precisa tirar os parenteses inuteis
                    expressao.remove(i-2);
                    expressao.remove(i-1);
                    return desparentizar(expressao.subList(0, expressao.size()));
                }
            }
            if(expressao.size() == 2){
                System.out.print(expressao);
            }
        }
        return expressao;
    }

    private static void gerar_variaveis(){ // posiciona todas as variaveis a serem usadas.
        int cont_if = 1, cont_while = 1;
        boolean state = false; // false - if, true - while
        // if(0->1) pointer [condicao](1->2) pointer {(2->0)        
        
        for(PalavraValor p : declaradas){
            variaveis.add("var_"+p.lexema);
        }
        for(int i=1; i<= max_operation; i++){
            variaveis.add("aux_"+i);
        }
        for(Token t : tokenList){
            if(t.token.equals("if")){
                variaveis.add("if_"+cont_if+"_e");
                state = false;
            }
            if(t.token.equals("while")){
                variaveis.add("while_"+cont_while+"_e");
                state = true;
            }
            if(t.token.equals("condicao")){
                if(state){
                    variaveis.add("while_"+cont_while+"_d");
                }else{
                    variaveis.add("if_"+cont_if+"_d");
                }                
            }
            if(t.token.equals("openC")){
                if(state){
                    cont_while++;
                }else{
                    cont_if++;
                }
            }
        }
    }
}