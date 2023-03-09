
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Compilador {
    public static void main(String[] args) {
        System.out.println("Number of Command Line Argument = "+args.length);
        if(args.length == 0)return;

        File arquivoFonte = new File(args[0]);
        //File arquivoFonte = new File("texto.txt");
        if( arquivoFonte.exists() ){
            System.out.println("Arquivo Encontrado");
            try{
                Scanner myReader = new Scanner(arquivoFonte);
                int linha = 0;
                //String mensagem;
                //w("inside");
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    linha++;
                    //w(Integer.toString(linha));
                    Lexico.analise(data, linha);
                    //System.out.println(mensagem);



                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found Exception");
                e.printStackTrace();
            } finally {
                System.out.println("Executado");
            }

        }else{
            System.out.println("Arquivo Não Encontrado");
        }
	}
    public static void w(String a){
        System.out.println(a);
    }
}