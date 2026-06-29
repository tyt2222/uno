package aulas.arquivos;

import java.io.File;

public class Exemplo1 {
    public static void main(String[] args) {
        
        File arquivo0 = new File("./Arquivo0.txt");
        File arquivo1 = new File("./Arquivo1.txt");
        File arquivo2 = new File(".");
        
        System.out.println( arquivo0.exists() );
        System.out.println( arquivo1.exists() );
        
        System.out.println("###");
        
        System.out.println( arquivo0.isDirectory() );
        System.out.println( arquivo1.isDirectory() );
        System.out.println( arquivo2.isDirectory() );
        
        System.out.println("###");
        
        System.out.println( arquivo1.canExecute() );
        System.out.println( arquivo1.canRead() );
        System.out.println( arquivo1.canWrite() );
        
    }
}