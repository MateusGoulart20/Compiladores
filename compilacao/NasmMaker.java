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
                oper1 = manipulacion.substring(0, manis);
                oper2 = manipulacion.substring(++manis);

                oper2 = oper(oper2);

                resp += "mov eax, "+oper2+"; de\n\t";
                resp += "mov ["+oper1+"], eax; para  ATRIBUICAO\n";
                main.add(resp);
                break;
            case "ROT":
                //manipulacion = linha.substring(manis);
                resp += manipulacion;
                resp += ":; ROTULO\n";
                main.add(resp);
                break;
            case "CMP":
                //manipulacion = linha.substring(manis)
                String cmp, left, right, jmp;
                // pegar a comparacao
                w("##"+manipulacion);

                manis = manipulacion.indexOf(' ');
                cmp = manipulacion.substring(0, manis);
                w("\t#"+cmp);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                left = manipulacion.substring(0, manis);
                w("\t#"+left);

                manipulacion = manipulacion.substring(++manis);
                
                manis = manipulacion.indexOf(' ');
                right = manipulacion.substring(0, manis);
                w("\t#"+right);

                jmp = manipulacion.substring(++manis);
                w("\t#"+jmp);

                if(cmp.equals("<")) cmp = "jae";
                if(cmp.equals("==")) cmp = "jne";

                resp += "mov ebx, ["+left+"]\n\tmov ecx, ["+right+"]; posicionamento condicao\n\t";
                resp += "cmp ebx, ecx ;comparacao\n\t"+cmp+" "+jmp+";salto se condicao atendida\n";
                main.add(resp);
                break;

            case "SOMA":
                //String oper1, oper2, local;
                manis = manipulacion.indexOf(' ');
                local = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                oper1 = oper(oper1);
                oper2 = manipulacion.substring(++manis);
                oper2 = oper(oper2);
                
                resp += "mov ebx, "+oper1+"\n\t";
                resp += "mov ecx, "+oper2+"\n\t";
                resp += "add ebx, ecx\n\t";
                resp += "mov ["+local+"], ebx; SOMA\n";
                
                main.add(resp);
                
                break;
            case "SUBT":
                manis = manipulacion.indexOf(' ');
                local = manipulacion.substring(0, manis);
                
                manipulacion = manipulacion.substring(++manis);
                
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                oper1 = oper(oper1);
                
                oper2 = manipulacion.substring(++manis);
                oper2 = oper(oper2);

                resp += "mov ebx, "+oper1+"\n\t";
                resp += "mov ecx, "+oper2+"\n\t";
                resp += "sub ebx, ecx\n\t";
                resp += "mov ["+local+"], ebx; SUBT \n";
                
                main.add(resp);
                break;
            case "MULT":
                manis = manipulacion.indexOf(' ');
                local = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                oper1 = oper(oper1);

                oper2 = manipulacion.substring(++manis);
                oper2 = oper(oper2);

                resp += "mov ax, "+oper1+"\n\t";
                resp += "mov bx, "+oper2+"\n\t";
                resp += "mul bx\n\t";
                resp += "mov ["+local+"], eax; MULT\n";
                
                main.add(resp);
                break;
            case "DIVD":
                manis = manipulacion.indexOf(' ');
                local = manipulacion.substring(0, manis);

                manipulacion = manipulacion.substring(++manis);

                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                oper1 = oper(oper1);

                oper2 = manipulacion.substring(++manis);
                oper2 = oper(oper2);

                resp += "mov eax, "+oper1+"\n\t";
                resp += "mov edx, 0\n\t";
                resp += "mov ecx, "+oper2+"\n\t";
                resp += "div ecx\n\t";
                resp += "mov ["+local+"], eax; DIVD \n";
                
                main.add(resp);
                break;
            case "WRITS":
                w("$string:"+manipulacion);
                //manis = manipulacion.indexOf(' ');
                //if(manis > 0) manipulacion = manipulacion.substring(0, manis);

                resp += "push "+manipulacion;
                resp += "\n\tcall _printf; WRITS escreve string\n";
                main.add(resp);
                break;
            case "WRITV":
                //manis = manipulacion.indexOf(' ');
                //manipulacion = manipulacion.substring(0, manis);
                resp += "mov eax, ["+manipulacion+"]\n\t";
                resp += "push eax\n\t";
                resp += "push formato\n\t";
                resp += "call _printf; WRITV escreve variavel\n";
                main.add(resp);
                break;
            case "READ":
                //manis = manipulacion.indexOf(' ');
                //manipulacion = manipulacion.substring(0, manis);

                resp += "push "+manipulacion;
                resp += "\n\tpush formato";
                resp += "\n\tcall _scanf\n";
                main.add(resp);
                break;
            case "JMP":
                resp += "jmp "+manipulacion+" ; JMP\n";
                main.add(resp);
                break;
            default:
                System.out.println("\t$$$$$$$$$$$NasmMaker ERROR");
        }

    }
    private static String oper(String a){
        if(!(a.matches("[0-9]*"))) a = "["+a+"]";
        return a;
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