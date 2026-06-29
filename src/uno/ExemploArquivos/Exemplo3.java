package aulas.arquivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class Exemplo3 {
    public static void main(String[] args) {
        
        File arq = new File("./Arquivo1.txt");
        
        FileReader leitor;
        
        BufferedReader buffer;
        
        try {
            
            leitor = new FileReader(arq);
            buffer = new BufferedReader(leitor);
            
            String linha;
                        
            while( buffer.ready() ) {
                linha = buffer.readLine();
                System.out.println(linha);
            }
            
            buffer.close();
            leitor.close();
            
        } catch(IOException ex) {
            
            System.out.println( "erro IOException ocorreu: " + ex.getMessage() );
            
        } finally { 
            buffer = null;
            leitor = null;
            arq = null;            
        }
                
    }
}