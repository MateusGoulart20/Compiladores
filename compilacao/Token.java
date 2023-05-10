public class Token {
    public int linha, coluna;
    public String token, lexema;

    @Override
    public String toString() {
        return lexema;
    }

    public Token(String t, int l, int c, String lex){
        this.token = t;
        this.linha = l;
        this.coluna = c;
        this.lexema = lex;
    }
    public Token(String t, String lex){
        this.token = t;
        this.linha = 0;
        this.coluna = 0;
        this.lexema = lex;
    }
}
