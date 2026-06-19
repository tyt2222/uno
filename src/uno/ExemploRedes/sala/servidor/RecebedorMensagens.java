package aulas.rede.sala.servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class RecebedorMensagens implements Runnable {
    
    private Socket conexao;
    private ObjectInputStream input;
    private ArrayList<ObjectOutputStream> outputs;

    public RecebedorMensagens(Socket conexao, ArrayList<ObjectOutputStream> outputs) throws Exception {
        this.conexao = conexao;
        
        this.input = new ObjectInputStream( conexao.getInputStream() );
        
        this.outputs = outputs;
    }
    
    @Override
    public void run() {
        
        while(true) {
        
            try {

                String mensagem = (String) input.readObject();

                for( ObjectOutputStream output : outputs ) {               
                    try {
                        output.writeObject( mensagem );
                    } catch(Exception ex) {}
                }

            } catch(Exception ex) {}
        
        }
        
        
    }

}