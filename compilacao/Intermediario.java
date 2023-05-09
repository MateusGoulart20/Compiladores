import java.util.ArrayList;
import java.util.List;
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

    public static void write_lexema(ArrayList<Token> tl) {
        tokenList = tl;
        // Gerar as variaveis antes de começar o esquema
        gerar_variaveis();
        gerar_comandos();
        try{
            //Fluxo de saida de um arquivo
            //OutputStream os =  // nome do arquivo que será escrito
            //Writer wr = new OutputStreamWriter(new FileOutputStream("file1.txt")); // criação de um escritor
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("file1.txt"))); // adiciono a um escritor de buffer

            for (String t : variaveis){
                br.write(t+": ds 1\n");
            }
            for (String t : comando){
                br.write(t+"\n");
            }
            // Coisas que podem levar um pointer matematico
            // "=" atribute. -> atribute pointer ponto_virgula
            // if pointer condicao pointer open C
            for (Token i : tokenList){
                         
               
              
                br.write(i.lexema);
                if(i.lexema.equals(";")||i.lexema.equals("{")){
                    br.write("\n");
                }else{
                    br.write(" ");
                }
            }
            br.close();
        } catch (IOException ioe){
            System.out.println("FAIL");
        }
        
    }
    private static void gerar_comandos(){
        int cont_if = 1, cont_while = 1, aux, aux1, aux2, aux3;
        ArrayList<Token> analise = new ArrayList<Token>();
        for(Token t : tokenList){
            analise.add(t);
            if(t.token.equals("pontoVirgula")||t.token.equals("closeC")){
                if(analise.get(0).token.equals("id")){
                    if(analise.get(1).token.equals("atribute")){
                        // portanto é o começo de um atribuicao
                        // deve terminar com a variavel <var_*> contendo o restante da expressao
                        resolver_expressao("var_"+analise.get(0).lexema, analise.subList(2, analise.size()-1));
                    }
                }
                if(analise.get(0).token.equals("if")){
                    for(int i = 0; i<analise.size(); i++){
                        if(analise.get(i).token.equals("condicao")){
                            resolver_expressao("if_"+cont_if+"_e", analise.subList(1, i));
                            resolver_expressao("if_"+cont_if+"_d", analise.subList(i+1, analise.size()-1));
                            comando.add("COMPARE if_"+cont_if+"_e if_"+cont_if+"_d");
                        }
                    }
                    cont_if++;
                }
                if(analise.get(0).token.equals("while")){
                    for(int i = 0; i<analise.size(); i++){
                        if(analise.get(i).token.equals("condicao")){
                            comando.add("");
                            resolver_expressao("while_"+cont_while+"_e", analise.subList(1, i));
                            resolver_expressao("while_"+cont_while+"_d", analise.subList(i+1, analise.size()-1));
                            comando.add("COMPARE while_"+cont_while+"_e while_"+cont_while+"_d");
                        }
                    }
                    cont_while++;
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
        /*String debug = "//test:";
        for(int i =0; i<expressao.size();i++ )debug += expressao.get(i).lexema + " ";
        debug += "\n";
        comando.add(debug);*/
    }

    private static List<Token> suprimir(List<Token> expressao, int posi, int aux){
        Token auxiliar = new Token("aux", "aux_"+aux); auxiliar.coluna=0;
        if(expressao.get(posi).lexema.equals("+")){
            comando.add(auxiliar.lexema+" SOMA "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }
        if(expressao.get(posi).lexema.equals("-")){
            comando.add(auxiliar.lexema+" SUBT "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }
        if(expressao.get(posi).lexema.equals("*")){
            comando.add(auxiliar.lexema+" MULT "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }
        if(expressao.get(posi).lexema.equals("/")){
            comando.add(auxiliar.lexema+" DIVD "+expressao.get(posi-1).lexema+" "+expressao.get(posi+1).lexema);
        }

        // depois de qualquer operacao
        expressao.set(posi-1, auxiliar);
        for(int i = posi; i<expressao.size()-1; i++){
            expressao.set(i, expressao.get(i+1));
        }
        for(int i = posi; i<expressao.size()-1; i++){
            expressao.set(i, expressao.get(i+1));
        }
        
        return expressao.subList(0, expressao.size()-3);
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
                if(expressao.get(i-2).token.equals("openP")){
                    // precisa tirar os parenteses inuteis
                    for(int v = i-2; v<expressao.size()-1; v++){
                        expressao.set(v, expressao.get(v+1));
                    }
                    for(int v = i-1; v<expressao.size()-1; v++){
                        expressao.set(v, expressao.get(v+1));
                    }
                    return desparentizar(expressao);
                }
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
        for(int i=1; i<= 20; i++){
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