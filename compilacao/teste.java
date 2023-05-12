public class teste {

    public static void main(String[] args) {/* 
        int base = 10, result, i  =0, v =0;
        while(i<base){
            v=0;
            while(v<i+1){
                result = fatorial(i)/ (fatorial(v)*fatorial(i-v));
                System.out.print(result+" ");
            v++;}
            System.out.println();
        i++;}
        System.out.println("------");*/

        int e1 = 6, e2 = 6  , e3 = 6, r = 0;
        if(e1+e2<e3)r++;
        if(e1+e2==e3)r++;
        if(e1+e3<e2)r++;
        if(e1+e3==e2)r++;
        if(e3+e2<e1)r++;
        if(e3+e2==e1)r++;
        if(r == 1){
            System.out.println("n eh triangulo");
        }else{
            System.out.println("eh triangulo");
            if(e2==e3)r++;
            if(e1==e3)r++;
            if(e2==e1)r++;
            if(r<1){
                System.out.println("escaleno");
            }
            if(r==1){
                System.out.println("isolceles");
            }if(1<r){
                System.out.println("equilatero");
            }
        }




    }
    private static int fatorial(int entrada){
        int r = 1, cont = 1;
        while(entrada >= cont){
            r = r*cont;
            cont++;
        }
        return r;
    }
    



}
