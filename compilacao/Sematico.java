import java.util.ArrayList;

public class Sematico {
    private static boolean lse, ts;
    public static boolean erro;
    private static ArrayList<PalavraValor> declaradas = new ArrayList<PalavraValor>();;

    public static ArrayList<PalavraValor> getDeclaradas() {
        return declaradas;
    }

    public static void lse(boolean entrada) { // impoe a opcao selecionada
        lse = entrada;
    }
    public static void ts(boolean entrada){
        ts = entrada;
    }

    public static void w(String entrada) { // escreve o log
        if (lse)
            System.out.println(entrada);
    }
    public static void wTS(String entrada) { // escreve o log
        System.out.println(entrada);
    }

    private static boolean usando_declarando = false;// false -> declarado ; true -> usando
    private static boolean atribute = false; // abriu uma atribuicao
    private static boolean read = false; // iniciou uma leitura
    //private static boolean wait = false; // esperando uma atribuicao
    private static PalavraValor last;
    private static boolean division = false;
    // saber se o id está sendo declarado ou sendo usado:
    
    // ativa após um if, write, =, while

    public static void analise(ArrayList<Token> tokenList) {

        boolean existe = false;
        boolean texto = false;
        
        for (Token token : tokenList) {

            if (token.token.equals("aspas"))
                texto = !texto;
            if (!texto) { // apenas observar fora de aspas.
                usando(token.token);
                if (token.token.equals("id")) {
                    existe = naLista(token);
                    if (usando_declarando) { // usando
                        if (existe) {
                            w("#Usando e foi declarado (ok)");
                        } else {
                            w("#Usando, mas não declarado (erro)");
                            erro = true;
                        }
                    } else { // declarando
                        if (existe) {
                            w("#Reatribuição (ok)");
                            /*
                             * else { como não tem tipo, nao existe motivo para ver se já foi declarado
                             * w("#Já existe (erro)");
                             * erro = true;
                             * }
                             */
                        } else {
                            w("#Declarando com sucesso (ok)");
                            declaradas.add(new PalavraValor(token.lexema));
                        }
                    }
                    last = encontrar(token.lexema);
                    w(coord(token));

                }
                if(token.token.equals("read"))
                    read = true;
                if(token.token.equals("id")){
                    if(read){
                        last.indeterminado = true;
                    }
                    if(atribute)
                        if(encontrar(token.lexema).indeterminado)
                            last.indeterminado = true;
                    if(division)
                        if(!(encontrar(token.lexema).indeterminado)) // Nao pode ser indeterminado
                            if(encontrar(token.lexema).valor == 0){
                                erro = true;
                                w("Erro por divisao por zero: "+coord(token));
                                
                            }
                }
                if(token.token.equals("atribute"))
                    indeterminar(last.lexema);    
                
                if(token.lexema.equals("/")) division = true;
                   
                    
            }
        }
        if(ts)
            imprimirTS();
    }
    private static void indeterminar(String alvo){
        for(PalavraValor procura : declaradas ){
            if(procura.lexema.equals(alvo))
                procura.indeterminado = true;
        }
    }
    private static PalavraValor encontrar(String alvo){
        for(PalavraValor procura : declaradas ){
            if(procura.lexema.equals(alvo))
                return procura;
        }
        w("Nao encontrado: "+alvo);
        erro=true;
        return null;
    }
    
    private static void imprimirTS(){
        wTS("--- Tabela Simbolo ---");
        for(PalavraValor simbolo : declaradas){
            wTS(" "+simbolo.lexema);
        }
        wTS("--- ############## ---");
    }

    private static void usando(String entrada){
        switch (entrada){
            case "=":
                atribute = true;
            case "if":
            case "write":
            case "while":
                usando_declarando = true;
                return;
            case "pontoVirgula":
            case "openC":
                usando_declarando = false;
                atribute = false;
                read = false;
                //wait = false;
                return;
            default: return;
        }
    }


    private static boolean naLista(Token tl) { // verifica se algo já está na lista
        if (tl == null)
            return false;
        if (declaradas == null)
            return false;
        String lexema = tl.lexema;
        for (PalavraValor existe : declaradas) {
            if (existe.lexema.equals(lexema))
                return true;
        }
        return false;
    }

    private static String coord(Token token) { // retorna as coordenadas do token.
        return "[" + token.lexema + "][L " + token.linha + "][C " + token.coluna + "]";
    }
}
/*
 * verificação da declaração prévia de variável; // ok
 * verificação da declaração duplicada de variável; // nao necessaria por nao
 * haver declaracao de variavel e diferenca de tipos
 * verificar divisão por zero (operação explícita e/ou por atribuição de
 * variável); e
 * verificação de tipos em operações lógicas e matemática com variáveis. //
 * apenas um tipo de variavel
 */
