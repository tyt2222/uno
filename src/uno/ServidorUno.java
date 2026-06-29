package uno;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorUno {

    public static void main(String[] args) throws Exception {
        int porta = 12345;
        int backlog = 2;
        InetAddress endereco = InetAddress.getByName("localhost");

        ServerSocket servidor = new ServerSocket(porta, backlog, endereco);
        System.out.println("Servidor Uno Inicializado na porta " + porta + ".\n");

        List<Jogador> jogadores = new ArrayList<>();

        System.out.println("Esperando Jogador 1 conectar...");
        Socket conexao1 = servidor.accept();
        System.out.println("Jogador 1 conectado de: " + conexao1.getInetAddress().getHostAddress());
        Jogador j1 = new Jogador("Jogador 1", conexao1);
        jogadores.add(j1);
        j1.enviarMensagem("Voce e o Jogador 1. Aguardando outro jogador...\n");

        System.out.println("Esperando Jogador 2 conectar...");
        Socket conexao2 = servidor.accept();
        System.out.println("Jogador 2 conectado de: " + conexao2.getInetAddress().getHostAddress());
        Jogador j2 = new Jogador("Jogador 2", conexao2);
        jogadores.add(j2);
        j2.enviarMensagem("Voce e o Jogador 2.\n");

        System.out.println("Todos conectados. Iniciando a partida!");

        Uno jogo = new Uno(jogadores);
        jogo.jogar();

        System.out.println("Partida finalizada. Encerrando servidor.");
        conexao1.close();
        conexao2.close();
        servidor.close();
    }
}
