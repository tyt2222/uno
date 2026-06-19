package aulas.rede.sala.servidor;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    public static void main(String[] args) throws Exception {
        
        
        int porta = 12345;
        int backlog = 100;
        InetAddress endereco = InetAddress.getByName("10.105.70.236");

        
        ServerSocket servidor;
        servidor = new ServerSocket(porta, backlog, endereco);
        System.out.println("Servidor Inicializado ( " + servidor + " ).\n");
        
        
        ArrayList<ObjectOutputStream> outputs = new ArrayList<>();
        
        // Thread que espera por novas conexões
        while(true) {
            
            Socket conexao;        
            System.out.println( "Esperando por Conexão.\n" );
            conexao =  servidor.accept();
            System.out.println( "Conexão Recebida: " + conexao.getInetAddress() + "\n" );

            ObjectOutputStream output;
            output = new ObjectOutputStream( conexao.getOutputStream() );
            output.flush();
            
            outputs.add( output );

            RecebedorMensagens broadcastMensagens = new RecebedorMensagens(conexao, outputs);
            Thread thread = new Thread(broadcastMensagens);
            thread.start();
            
        }
        
    }
}