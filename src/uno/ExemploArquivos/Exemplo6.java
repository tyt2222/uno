package aulas.arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Exemplo6 {
    public static void main(String[] args) {
        
        Pessoa pessoa1 = null;
        Pessoa pessoa2 = null;
        
        File arq = new File("Arquivo.ser");
        FileInputStream fluxo;
        ObjectInputStream leitor;
        
        try {
        
            fluxo = new FileInputStream(arq);
            leitor = new ObjectInputStream(fluxo);
            
            pessoa1 = (Pessoa) leitor.readObject();
            pessoa2 = (Pessoa) leitor.readObject();
            
            leitor.close();
            fluxo.close();
        
        } catch( Exception ex ) {
            System.out.println( "erro Exception ocorreu: " + ex.getMessage() );
        } finally {
            leitor = null;
            fluxo = null;
        }
        
        System.out.println(pessoa1);
        System.out.println(pessoa2);
    }
}