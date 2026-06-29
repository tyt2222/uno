package aulas.arquivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exemplo4 {
    public static void main(String[] args) {
        
        File arq = new File("./Arquivo2.txt");
        
        FileWriter escritor;
        
        try {
            
            if( arq.exists() == true ) {
                escritor = new FileWriter(arq, true);
            } else {
                escritor = new FileWriter(arq);
            }
            
            escritor.write("\n\taula de arquivos");
            
            escritor.close();
            
        } catch( IOException ex ) {
            System.out.println( "erro IOException ocorreu: " + ex.getMessage() );
        } finally {
            escritor = null;
            arq = null;
        }
        
    }
}