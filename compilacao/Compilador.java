
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Compilador {
    static final int token_length = 200;
    static int[] token_list = new int[token_length];

    public static void main(String[] args) {
        // ini(); // inicializando o a lista de tokens. java já limpa o vetor

        System.out.println("Number of Command Line Argument = " + args.length);
        if (args.length == 0)
            return;
        
        

        File arquivoFonte = new File(args[0]);
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
                    Lexico.analise(data, linha);
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

        System.out.println(token_list.toString());
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

    public static void addListToken(int tipo) {
        int i = 0;
        while (token_list[i] != 0)
            i++;
        token_list[i] = tipo;
    }
}