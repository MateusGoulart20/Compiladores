
public class Simbolos {
    // Expressões Regulares???
    // D: [0-9]
    public static boolean isDigit(String alvo) {
        if (alvo.equals("0"))
            return true;
        if (alvo.equals("1"))
            return true;
        if (alvo.equals("2"))
            return true;
        if (alvo.equals("3"))
            return true;
        if (alvo.equals("4"))
            return true;
        if (alvo.equals("5"))
            return true;
        if (alvo.equals("6"))
            return true;
        if (alvo.equals("7"))
            return true;
        if (alvo.equals("8"))
            return true;
        if (alvo.equals("9"))
            return true;
        return false;
    }

    // L: [a-z, A-Z]
    public static boolean isLetter(String alvo) {
        if (alvo.equals("a"))
            return true;
        if (alvo.equals("b"))
            return true;
        if (alvo.equals("c"))
            return true;
        if (alvo.equals("d"))
            return true;
        if (alvo.equals("e"))
            return true;
        if (alvo.equals("f"))
            return true;
        if (alvo.equals("g"))
            return true;
        if (alvo.equals("h"))
            return true;
        if (alvo.equals("i"))
            return true;
        if (alvo.equals("j"))
            return true;
        if (alvo.equals("k"))
            return true;
        if (alvo.equals("l"))
            return true;
        if (alvo.equals("m"))
            return true;
        if (alvo.equals("n"))
            return true;
        if (alvo.equals("o"))
            return true;
        if (alvo.equals("p"))
            return true;
        if (alvo.equals("q"))
            return true;
        if (alvo.equals("r"))
            return true;
        if (alvo.equals("s"))
            return true;
        if (alvo.equals("t"))
            return true;
        if (alvo.equals("u"))
            return true;
        if (alvo.equals("v"))
            return true;
        if (alvo.equals("w"))
            return true;
        if (alvo.equals("x"))
            return true;
        if (alvo.equals("y"))
            return true;
        if (alvo.equals("z"))
            return true;
        return false;
    }

    // [\ \t\n]
    
    public static boolean isWaste(String alvo) {
        if (alvo.equals(" "))
            return true; // provalvemnete não tem
        if (alvo.equals("\t"))
            return true; // por preucação
        if (alvo.equals("\n"))
            return true; // .nextLine já retira
        return false;
    }

    public static boolean isPontoVirgula(String alvo) {
        return alvo.equals(";");
    }

    public static boolean isCloseChaves(String alvo) {
        return alvo.equals("}");
    }

    public static boolean isOpenChaves(String alvo) {
        return alvo.equals("{");
    }

    public static boolean isAspas(String alvo) {
        return alvo.equals("\"");
    }

    public static boolean isCloseParenteses(String alvo) {
        return alvo.equals(")");
    }

    public static boolean isOpenParenteses(String alvo) {
        return alvo.equals("(");
    }

    public static boolean isIgual(String alvo) {
        return alvo.equals("=");
    }

    public static boolean isUnary(String alvo) {
        if (alvo.equals("-"))
            return true;
        if (alvo.equals("+"))
            return true;
        return false;
    }

    public static boolean isBinary(String alvo) {
        if (alvo.equals("/"))
            return true;
        if (alvo.equals("*"))
            return true;
        return false;
    }

    public static boolean isComparator(String alvo) {
        return alvo.equals("<");
    }

    public static void w(String a){
        System.out.println(a);
    }
}
