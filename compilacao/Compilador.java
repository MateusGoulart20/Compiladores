
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class Compilador {
    static private boolean lt = false;
    static private boolean ls = false;
    static private boolean lse = false;
    static private boolean ts = false;
    static private ArrayList<Token> tokenList;

    public static ArrayList<Token> getTokenList() {
        return tokenList;
    }

    public static void main(String[] args) {
        System.out.println("Comando reconhecidos = " + args.length);
        int arquivosReconhecidos = parametrosCompilacao(args);
        System.out.println("Arquivos fontes reconhecidos = " + arquivosReconhecidos);

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
            System.out.println("Arquivo Encontrado");
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

                Sintatico.analise(tokenList);
                myReader.close();

                Sematico.analise(tokenList);
                if (Sematico.erro)
                    System.out.println("Sematico erro");
                Intermediario.write_lexema(tokenList);

            } catch (FileNotFoundException e) {
                System.out.println("File Not Found Exception");
                e.printStackTrace();
            } finally {
                System.out.println("Executado");
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
            if (argumento.equals("-tudo")) {
                lt = true;
                ls = true;
                lse = true;
                ts = true;
            }

            if (arquivoExistente(argumento)) {
                i++;
            }
        }
        Lexico.lt(lt);
        Sintatico.ls(ls);
        Sematico.lse(lse);
        Sematico.ts(ts);
        return i;
    }

    private static boolean arquivoExistente(String argumento) {
        File arquivoFonte = new File(argumento); // Pega a primeira localização de item.
        if (arquivoFonte.exists())
            return true; // existe portanto executa o código
        return false;
    }

}