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
    private static boolean nasm;

    public static void nasm(boolean a){
        nasm = a;
    }
    public static void w(String a){
        if(nasm)
            System.out.println(a);
    }

    public static void writeAssembly() throws IOException{
        // Tratamento de arquivo
        inner_archive = Intermediario.archive_name;
        Scanner myReader = new Scanner(new File(inner_archive));
        asm_archive = inner_archive.substring(0, inner_archive.length()-10);
        asm_archive += ".asm";
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(asm_archive)));
        ini();
        
        // Leitura
        while(myReader.hasNext()) process(myReader.nextLine());
        
        // Escrita
        for(String word : data) br.write(word+"\n");
        br.write("\n");
        for(String word : bss)  br.write(word+"\n");
        br.write("\n");
        for(String word : text) br.write(word+"\n");
        br.write("\n");
        for(String word : main) br.write(word+"\n");
        
        br.close(); myReader.close();
    }

    public static void process(String linha){
        // Tratamento dos comandos individuais do Intermediário
        int manis = linha.indexOf(' ');
        String manipulacion = linha.substring(manis+1), resp = "\t";
        String oper1, oper2, local;
        switch (linha.substring(0,manis)){
            // escolha de comandos
            case "TXT":
                //Tratamento texto impressao
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                resp += oper1;
                resp += " db ";
                resp += manipulacion.substring(manis+1, manipulacion.length());
                resp += ", 10, 0";
                
                data.add(resp);
                break;
            case "VAR":
                // Declarando variavel
                resp += manipulacion;
                resp += "\tresd 2";

                bss.add(resp);
                break;
            case "RCB":
                // Recebendo informação, semelhante ao carregamento na memoria de algo
                manis = manipulacion.indexOf(' ');
                oper1 = manipulacion.substring(0, manis);
                oper2 = manipulacion.substring(++manis);

                oper2 = oper(oper2);

                resp += "mov eax, "+oper2+"; de\n\t";
                resp += "mov ["+oper1+"], eax; para  ATRIBUICAO\n";

                main.add(resp);
                break;
            case "ROT":
                // Impressao de rotulo
                resp += manipulacion;
                resp += ":; ROTULO\n";

                main.add(resp);
                break;
            case "CMP":
                // Comparacao associada a salto
                String cmp, left, right, jmp;
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
                // Soma
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
                // Subtração
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
                // Multiplicação
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
                // Divisao
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
                // Escrita de texto
                w("$string:"+manipulacion);
                resp += "push "+manipulacion;
                resp += "\n\tcall _printf; WRITS escreve string\n";
                
                main.add(resp);
                break;
            case "WRITV":
                // Escrita de variavel
                resp += "mov eax, ["+manipulacion+"]\n\t";
                resp += "push eax\n\t";
                resp += "push formato\n\t";
                resp += "call _printf; WRITV escreve variavel\n";

                main.add(resp);
                break;
            case "READ":
                // Leitura de variavel
                resp += "push "+manipulacion;
                resp += "\n\tpush formato";
                resp += "\n\tcall _scanf\n";

                main.add(resp);
                break;
            case "JMP":
                // salto para rotulo
                resp += "jmp "+manipulacion+" ; JMP\n";

                main.add(resp);
                break;
            default:
                System.out.println("\t$$$$$$$$$$$NasmMaker ERROR");
        }

    }
    private static String oper(String a){
        // verifica se é numero caso não coloca [ ]
        if(!(a.matches("[0-9]*"))) a = "["+a+"]";
        return a;
    }

    private static void ini (){
        // inicializa os processos
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