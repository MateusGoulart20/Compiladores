
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class Compilador {
    static private boolean lt = false, ls = false, lse = false, ts = false, nasm = false, inter = false, chefe = false;
    static private ArrayList<Token> tokenList;

    public static ArrayList<Token> getTokenList() {
        return tokenList;
    }
    private static void w(String a){
        if(chefe)
            System.out.println(a);
    }

    public static void main(String[] args) {
        // Faz o reconhecimento do comando proposto.
        w("Comando reconhecidos = " + args.length);
        int arquivosReconhecidos = parametrosCompilacao(args);
        w("Arquivos fontes reconhecidos = " + arquivosReconhecidos);

        if (args.length == 0)
            return;
        for (String i : args)
            if (arquivoExistente(i))
                compilar(i);
    }

    private static void compilar(String argumento) {
        tokenList = new ArrayList<Token>();

        File arquivoFonte = new File(argumento); // Pega a primeira localização de item.
        if (arquivoFonte.exists()) { // existe portanto executa o código
            w("Arquivo Encontrado");
            try {
                Scanner myReader = new Scanner(arquivoFonte);
                int linha = 0;
                if (lt) {
                    System.out.println("Iniciado fase Lexica");
                }
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    linha++;
                    Lexico.analise(data, linha, tokenList);
                }

                w("### SINTATICO ###");
                Sintatico.analise(tokenList);
                myReader.close();

                w("### SEMATICO ###");
                Sematico.analise(tokenList);
                if (Sematico.erro) System.out.println("Semantico erro");

                w("### INTERMERDIARIO ###");
                try{
                    Intermediario.write(tokenList,argumento);
                }catch (IOException e){
                    System.out.println("#$# Falha a utilizar arquivo");
                }
                w("### NASM ASSEMBLY ###");
                try{
                    NasmMaker.writeAssembly();
                }catch (IOException e){
                    System.out.println("#$# Falha a utilizar arquivo");
                }

            } catch (FileNotFoundException e) {
                System.out.println("File Not Found Exception");
                e.printStackTrace();
            } finally {
                w("##FIM COMPILADOR");
            }
        }
    }

    private static int parametrosCompilacao(String[] argumentos) {
        int i = 0;
        for (String argumento : argumentos) {
            if (argumento.equals("-lt"))
                lt = true;
            if (argumento.equals("-ls"))
                ls = true;
            if (argumento.equals("-lse"))
                lse = true;
            if(argumento.equals("-ts"))
                ts = true;
            if(argumento.equals("-nasm"))
                nasm = true;
            if(argumento.equals("-inter"))
                inter = true;
            if(argumento.equals("-chefe"))
                chefe =true;    
            if (argumento.equals("-tudo")) {
                lt = true;
                ls = true;
                lse = true;
                ts = true;
                inter = true;
                nasm = true;
                chefe = true;
            }

            if (arquivoExistente(argumento)) {
                i++;
            }
        }
        Lexico.lt(lt);
        Sintatico.ls(ls);
        Sematico.lse(lse);
        Sematico.ts(ts);
        Intermediario.inter(inter);
        NasmMaker.nasm(nasm);
        return i;
    }

    private static boolean arquivoExistente(String argumento) {
        File arquivoFonte = new File(argumento); // Pega a primeira localização de item.
        if (arquivoFonte.exists())
            return true; // existe portanto executa o código
        return false;
    }

}