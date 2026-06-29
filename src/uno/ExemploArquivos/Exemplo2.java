package aulas.arquivos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class Exemplo2 {
    public static void main(String[] args) {
        
        File arq = new File("./Arquivo1.txt");
        
        FileReader leitor;
        
        try {
            
            leitor = new FileReader(arq);
            char c;
            
            while( leitor.ready() ) {
                
                c = (char) leitor.read();
                System.out.print(c);                             
            }
            
            leitor.close();
            
        } catch(IOException ex) {
            
            System.out.println( "erro IOException ocorreu: " + ex.getMessage() );
            
        } finally {            
            leitor = null;
            arq = null;            
        }
                
    }
}