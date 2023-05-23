import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class NasmMaker {
    public static String inner_archive, asm_archive;
    private static ArrayList<String> data = new ArrayList<String>(),  bss = new ArrayList<String>(), text = new ArrayList<String>(), main = new ArrayList<String>();
    public static int stage = 0;
    private static boolean nasm;

    public static void nasm(boolean a){
        nasm = a;
    }
    public static void w(String a){
        if(nasm)
            System.out.println(a);
    }

    public static void writeAssembly() throws IOException{
        System.out.println("### NASM MAKER ###");
        inner_archive = Intermediario.archive_name;
        Scanner myReader = new Scanner(new File(inner_archive));
        asm_archive = inner_archive.substring(0, inner_archive.length()-10);
        asm_archive += ".asm";
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(asm_archive)));
        ini();
        // agora o ambiente está pronto
        stage++;
        String linha;
        while(myReader.hasNext()){
            linha = myReader.nextLine();
            process(linha);
        }
        for(String word : data) br.write(word+"\n");
        br.write("\n");
        for(String word : bss)  br.write(word+"\n");
        br.write("\n");
        for(String word : text) br.write(word+"\n");
        br.write("\n");
        for(String word : main) br.write(word+"\n");
        
        br.close(); myReader.close();
        stage =0; // finalização completa
    }

    public static void process(String linha){
        int manis = linha.indexOf(' ');
        String manipulacion = linha.substring(manis+1), resp = "\t";
        String oper1, oper2, local;
        switch (linha.substring(0,manis)){
            // leitura de um texto
            case "TXT":
                //manipulacion = linha.substring(manis);
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                resp += oper1;
                resp += " db ";
                resp += manipulacion.substring(manis+1, manipulacion.length());
                resp += ", 10, 0";
                //resp += "\n\t.len equ $ - "+oper1;
                data.add(resp);
                break;
            // Declarando variavel
            case "VAR":
                //manipulacion = linha.substring(manis);
                resp += manipulacion;
                resp += "\tresd 2";
                bss.add(resp);
                break;
            case "RCB":
                //manipulacion = linha.substring(manis);
                manis = manipulacion.indexOf(' ');
                resp += "mov eax, ";
                resp += manipulacion.substring(manis+1, manipulacion.length());
                resp += "\n\tmov [";
                resp += manipulacion.substring(0, manis);
                resp += "], eax; ATRIBUICAO";
                main.add(resp);
                break;
            case "ROT":
                //manipulacion = linha.substring(manis);
                resp += manipulacion;
                resp += ":; ROTULO";
                main.add(resp);
                break;
            case "CMP":
                //manipulacion = linha.substring(manis)
                String cmp, left, right, jmp;
                // pegar a comparacao
                System.out.println("##"+manipulacion);

                manis = manipulacion.indexOf(' ');
                cmp = manipulacion.substring(0, manis);
                System.out.println("\t#"+cmp);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                left = manipulacion.substring(0, manis);
                System.out.println("\t#"+left);

                manipulacion = manipulacion.substring(++manis);
                
                manis = manipulacion.indexOf(' ');
                right = manipulacion.substring(0, manis);
                System.out.println("\t#"+right);

                jmp = manipulacion.substring(++manis);
                System.out.println("\t#"+jmp);

                if(cmp.equals("<")) cmp = "jnge";
                if(cmp.equals("==")) cmp = "jne";

                resp += "mov ebx, ["+left+"]\n\tmov ecx, ["+right+"]; posicionamento condicao\n\t";
                resp += "cmp ebx, ecx ;comparacao\n\t"+cmp+" "+jmp+";salto se condicao atendida"; 
                main.add(resp);
                break;

            case "SOMA":
                //String oper1, oper2, local;
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper2 = manipulacion.substring(0, manis);

                local = manipulacion.substring(++manis);

                resp += "mov ebx, ["+oper1+"]\n\t";
                resp += "mov ecx, ["+oper2+"]\n\t";
                resp += "add ebx, ecx\n\t";
                resp += "mov ["+local+"], ecx";
                
                main.add(resp);

                break;
            case "SUBT":
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper2 = manipulacion.substring(0, manis);

                local = manipulacion.substring(++manis);

                resp += "mov ebx, ["+oper1+"]\n\t";
                resp += "mov ecx, ["+oper2+"]\n\t";
                resp += "sub ebx, ecx\n\t";
                resp += "mov ["+local+"], ecx";
                
                main.add(resp);
                break;
            case "MULT":
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper2 = manipulacion.substring(0, manis);

                local = manipulacion.substring(++manis);

                resp += "mov al, ["+oper1+"]\n\t";
                resp += "mov bl, ["+oper2+"]\n\t";
                resp += "mul bl\n\t";
                resp += "mov ["+local+"], ax";
                
                main.add(resp);
                break;
            case "DIVD":
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper2 = manipulacion.substring(0, manis);

                local = manipulacion.substring(++manis);

                resp += "mov ax, ["+oper1+"]\n\t";
                resp += "mov dx, 0\n\t";
                resp += "mov cx, ["+oper2+"]\n\t";
                resp += "div cx\n\t";
                resp += "mov ["+local+"], ax";
                
                main.add(resp);
                break;
            case "WRITS":
                w("$string:"+manipulacion);
                //manis = manipulacion.indexOf(' ');
                //if(manis > 0) manipulacion = manipulacion.substring(0, manis);

                resp += "push "+manipulacion;
                resp += "\n\tcall _printf; WRITS escreve string";
                main.add(resp);
                break;
            case "WRITV":
                //manis = manipulacion.indexOf(' ');
                //manipulacion = manipulacion.substring(0, manis);
                resp += "mov eax, ["+manipulacion+"]\n\t";
                resp += "push eax\n\t";
                resp += "push formato\n\t";
                resp += "call _printf; WRITV escreve variavel";
                main.add(resp);
                break;
            case "READ":
                //manis = manipulacion.indexOf(' ');
                //manipulacion = manipulacion.substring(0, manis);

                resp += "push "+manipulacion;
                resp += "\n\tpush formato";
                resp += "\n\tcall _scanf";
                main.add(resp);
                break;
            default:
                w("$NasmMaker ERROR");
        }


    }

    private static void ini (){
        data.add(" section .data");
        data.add("\tpula db 10, 0");
        data.add("\tformato db \"%d\", 0");
        bss.add(" section .bss");
        text.add(" section .text");
        text.add("\tglobal _main");
        text.add("\textern _printf");
        text.add("\textern _scanf");
        main.add("_main:");
    }
}