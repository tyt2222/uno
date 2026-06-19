package uno;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteUno {
    public static void main(String[] args) throws Exception {
        int porta = 12345;
        InetAddress endereco = InetAddress.getByName("localhost");
        
        System.out.println("Conectando ao Servidor Uno...");
        Socket conexao = new Socket(endereco, porta);
        System.out.println("Conectado com sucesso!\n");
        
        ObjectOutputStream output = new ObjectOutputStream(conexao.getOutputStream());
        output.flush();
        ObjectInputStream input = new ObjectInputStream(conexao.getInputStream());
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
                Object obj = input.readObject();
                if (obj instanceof String) {
                    String mensagem = (String) obj;
                    System.out.println(mensagem);
                    
                    if (mensagem.endsWith("Numero da Carta ou -1 para comprar: ")) {
                        String entrada = scanner.nextLine();
                        output.writeObject(entrada);
                        output.flush();
                    } else if (mensagem.contains("Venceu!")) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("\nConexao com o servidor perdida.");
                break;
            }
        }
        
        scanner.close();
        output.close();
        input.close();
        conexao.close();
        System.out.println("Jogo encerrado.");
    }
}
