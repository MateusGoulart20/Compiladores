import java.util.ArrayList;
import java.util.List;
public class testing {
    private static ArrayList<Integer> lista = new ArrayList<Integer>();
    public static void main(String[] args) {
        lista.add(2);
        lista.add(5);
        lista.add(12);
        lista.add(25);
        System.out.println(lista);
        List<Integer> lista2 = lista.subList(0, lista.size()); 
        System.out.println(lista2);
        lista2.remove(2);
        System.out.println(lista2);
    }
}
