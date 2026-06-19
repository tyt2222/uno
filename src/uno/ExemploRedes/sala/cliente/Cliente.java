package aulas.rede.sala.cliente;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws Exception {
        
        int porta = 12345;
        InetAddress endereco = InetAddress.getByName("10.105.70.236");
        
        Socket conexao = new Socket(endereco, porta);
        System.out.println( "Conexão Realizada: " + conexao.getLocalAddress().getHostName() + ":" + conexao.getLocalPort() + "\n" );
        
        ObjectOutputStream output;
        output = new ObjectOutputStream( conexao.getOutputStream() );
        output.flush();
        
        RecebedorMensagens recebedor = new RecebedorMensagens(conexao);
        Thread thread = new Thread(recebedor);
        thread.start();
        
        Scanner console = new Scanner(System.in);
        
        // Thread para o cliente compor uma mensagem pelo teclado no terminal e enviar/escrever tal mensagem parao servidor
        String usuario = "Mario: ";
        while(true) {
            
            String mensagem = console.nextLine();
            output.writeObject(usuario + mensagem);
            
        }
        
    }
}