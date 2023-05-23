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
    private static ArrayList<String> variaveis = new ArrayList<String>(), comando = new ArrayList<String>(), constantes = new ArrayList<String>();
    private static Stack<String> escopo = new Stack<String>();
    private static int max_operation = 0, cont_if = 0, cont_while = 0, cont_write = 0;
    public static String archive_name;
    private static boolean inter;

    public static void inter(boolean a){
        inter = a;
    }
    public static void w(String a){
        if(inter)
            System.out.println(a);
    }

    public static void write(ArrayList<Token> tl, String argument) throws IOException {
        archive_name = argument.substring(0, argument.length()-4);
        archive_name += "_inter.txt";
        tokenList = tl;
        // Gerar as variaveis antes de começar o esquema
        gerar_comandos();
        gerar_variaveis();
        //try{
            //Fluxo de saida de um arquivo
            //OutputStream os =  // nome do arquivo que será escrito
            //Writer wr = new OutputStreamWriter(new FileOutputStream("file1.txt")); // criação de um escritor
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archive_name))); // adiciono a um escritor de buffer
            for (String t : constantes) br.write(t+"\n");
            for (String t : variaveis)  br.write("VAR "+t+"\n");
            for (String t : comando)    br.write(t+"\n");
            
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
        //} catch (IOException ioe){
            //System.out.println("FAIL");
        //}
        
    }
    private static void gerar_comandos(){
        ArrayList<Token> analise = new ArrayList<Token>();
        for(Token t : tokenList){
            switch (t.lexema) {
                case " ":
                case "":
                case "\n":
                    break;
                default:
                    analise.add(t);
                    if(t.lexema.equals(";")||t.lexema.equals("{")){
                        w("\t@Gerar comando "+analise);
                        command(analise);
                        analise = new ArrayList<Token>();
                    }
            }
        }
    }
    private static void command(List<Token> expressao){
        List<Token> esquerda, direita;
        int v;
        switch (expressao.get(0).token){
            // resolver atribuicao
            case "id":
                if(expressao.get(1).token.equals("atribute")){
                    // portanto é o começo de um atribuicao
                    // deve terminar com a variavel <var_*> contendo o restante da expressao
                    resolver_expressao(expressao.get(0).lexema, expressao.subList(2, expressao.size()-1));
                }
                return;
            
            // resolver if
            case "if":
                cont_if++; v=0;
                w("#if: "+expressao+" ["+cont_if+"]");
                expressao.remove(0);
                expressao.remove(0);
                expressao.remove(expressao.size()-1);
                expressao.remove(expressao.size()-1);
                w("%if: "+expressao);
                for(int i = 0; i<expressao.size(); i++)
                    if(expressao.get(i).token.equals("condicao"))
                        v=i;
                
                w("#"+expressao.get(v).token+": "+expressao.get(v));

                esquerda = expressao.subList(0, v);
                w("#ife: "+esquerda);
                resolver_expressao("if"+cont_if+"e", esquerda);

                w("$consistencia: "+expressao);

                direita = expressao.subList(2, expressao.size());
                w("#ifd: "+direita);
                resolver_expressao("if"+cont_if+"d", direita);
                    
                
                comando.add("CMP "+expressao.get(1).lexema+" if"+cont_if+"e if"+cont_if+"d if_out_false_"+cont_if);
                escopo.push("ROT if_out_false_"+cont_if);
                w("Abertura de if: "+cont_if);
                return;
            // resolver while
            case "while":    
                cont_while++; v = 0 ;
                w("#while: "+expressao+" ["+cont_while+"]");
                for(int i = 0; i<expressao.size(); i++){
                    if(expressao.get(i).token.equals("condicao")){
                        comando.add("ROT while_in_"+cont_while);
                        escopo.push("JMP while_in_"+cont_while+"\nROT while_out_"+cont_while);
                        resolver_expressao("while"+cont_while+"e", expressao.subList(2, i));
                        resolver_expressao("while"+cont_while+"d", expressao.subList(i+1, expressao.size()-2));
                        comando.add("CMP "+expressao.get(i).lexema+" while"+cont_while+"e while"+cont_while+"d while_out_"+cont_while);
                    }
                }
                return;
            // resolver fechada de corpo de comando
            case "closeC" :
                w("Fim do corpo de comando: while = "+cont_while+" if = "+cont_if+" | "+escopo.peek());
                if(expressao.get(1).lexema.equals("else")){
                    comando.add("JMP if_out_"+(cont_if-1));
                    comando.add(escopo.pop());
                    escopo.push("ROT if_out_"+(cont_if-1));
                }else{
                    comando.add(escopo.pop());
                }
                return;
            // resolver escrita de variável e string
            case "write":
                //writing(analise.subList(1, analise));
                boolean aspas = false;
                for(int i = 1; i<expressao.size(); i++){
                    if(expressao.get(i).token.equals("aspas")){
                        if(aspas){
                            cont_write++;
                            comando.add("WRITS text"+cont_write);
                        }
                        aspas = !aspas;
                    }
                    if(!aspas)if(expressao.get(i).token.equals("id")){                       
                        comando.add("WRITV "+expressao.get(i).lexema);
                    }
                }
                return;
            case "read":
                comando.add("READ "+expressao.get(1).lexema);

            }
    }
    private static void resolver_expressao(String t, List<Token> expressao){
        int index = 0, operation=0;
        w("Resolver expressao: ");
        while(true){
            w(expressao+" - ");
            expressao = desparentizar(expressao);
            index = maior_prioridade(expressao);
            if(index == 0)break;
            operation++;
            expressao = suprimir(expressao, index, operation);
        }
        if(operation == 0){
            comando.add("RCB "+t+" "+expressao.get(0).lexema);
        }else{
            comando.add("RCB "+t+" aux"+operation);
        }
        if(operation>max_operation)max_operation=operation;
        /*String debug = "//test:";
        for(int i =0; i<expressao.size();i++ )debug += expressao.get(i).lexema + " ";
        debug += "\n";
        comando.add(debug);*/
    }

    private static List<Token> suprimir(List<Token> expressao, int posi, int aux){
        Token auxiliar = new Token("aux", "aux"+aux); auxiliar.coluna=0;
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
        //System.out.println(expressao);
        // depois de qualquer operacao
        expressao.set(posi-1, auxiliar);
        expressao.remove(posi);
        expressao.remove(posi);
        return expressao.subList(0, expressao.size());
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
        //System.out.print(" : ");
        //if(expressao.size() > 2)
            for (int i=1; i<expressao.size() ; i++){
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
            }
        return expressao;
    }

    private static void gerar_variaveis(){ // posiciona todas as variaveis a serem usadas.
        int cont_if = 1, cont_while = 1, cont_text = 1; 
        boolean state = false, if_while = false, txt = false; // false - if, true - while
        String tex = "";

        for(PalavraValor p : declaradas){
            variaveis.add(p.lexema);
        }
        for(int i=1; i<= max_operation; i++){
            variaveis.add("aux"+i);
        }
        for(Token t : tokenList){
            switch (t.token) {
                case "if":
                    variaveis.add("if"+cont_if+"e");
                    variaveis.add("if"+cont_if+"d");
                    if_while = true;
                    state = false;
                    break;
                case "while":
                    variaveis.add("while"+cont_while+"e");
                    variaveis.add("while"+cont_while+"d");
                    if_while = true;
                    state = true;
                    break;
                case "openC":
                    if(if_while){
                        if(state){
                            cont_while++;
                        }else{
                            cont_if++;
                        }
                        if_while = false;
                    }
                    break;
                case "aspas":
                    if(txt){
                        constantes.add("TXT text"+cont_text+" \""+tex+"\"");
                        tex = "";
                        cont_text++;
                    }
                    txt = !txt;
                default:
                    if(txt)if(!(t.token.equals("aspas"))) tex += t.lexema + ' ';
            }
        }
    }
}