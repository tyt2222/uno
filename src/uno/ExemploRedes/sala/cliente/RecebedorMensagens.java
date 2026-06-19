package aulas.rede.sala.cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RecebedorMensagens implements Runnable {
    
    private Socket conexao; // conexao com o servidor
    private ObjectInputStream input; // entrada que vem do servidor

    public RecebedorMensagens(Socket conexao) throws Exception {
        this.conexao = conexao;
        
        this.input = new ObjectInputStream( conexao.getInputStream() );        
    }
    
    @Override
    public void run() {
        
        while(true) {
        
            try {

                String mensagem = (String) input.readObject(); // mensagem recebida do servidor
                System.out.println(mensagem);

            } catch(Exception ex) {}            
            
        }
        
        
    }

}