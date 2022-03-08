package cz.fel.cvut.ts1;

public class ulcheyev {
    public long factorial(int n){
        if(n == 1){
            return n;
        }else{
            return n * factorial(n - 1);
        }
    }
}
