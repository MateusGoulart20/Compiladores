
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class Compilador {
    static private ArrayList<Integer> tokenList = new ArrayList<Integer>();

    public static void main(String[] args) {

        System.out.println("Number of Command Line Argument = " + args.length);
        if (args.length == 0)
            return;

        File arquivoFonte = new File(args[0]); // Pega a primeira localização de item.
        // File arquivoFonte = new File("texto.txt");
        if (arquivoFonte.exists()) {
            System.out.println("Arquivo Encontrado");
            try {
                Scanner myReader = new Scanner(arquivoFonte);
                int linha = 0;
                // String mensagem;
                // w("inside");
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    linha++;
                    // w(Integer.toString(linha));
                    Lexico.analise(data, linha, tokenList);
                    // System.out.println(mensagem);

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found Exception");
                e.printStackTrace();
            } finally {
                System.out.println("Executado");
            }

        } else {
            System.out.println("Arquivo Não Encontrado");
        }
        /*
         * System.out.println(tokenList.get(0));
         * for (int i : tokenList) {
         * System.out.println("+"+i);
         * }
         * está funcionando já
         */

    }

    public static void w(String a) {
        System.out.println(a);
    }/*
      * public static void ini(){
      * int i;
      * for(i=0; i<token_length; i++){
      * System.out.println(token_list[i]);
      * }
      * }
      */
}