public class Token {
    public int linha, coluna;
    public String token, lexema;

    public Token(String t, int l, int c, String lex){
        this.token = t;
        this.linha = l;
        this.coluna = c;
        this.lexema = lex;
    }
}
