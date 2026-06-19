package aulas.rede.jogo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class JogoTerminal {
    
    private Scanner console;
    
    private JogoDaVelha tabuleiro;
    private String marcador;
    
    private boolean suaVez;
    private boolean fim;
    
    private Sons sons;
    
    private Socket servidorConexao;
    private ObjectInputStream servidorEntrada;
    private ObjectOutputStream servidorSaida;

    public JogoTerminal() throws Exception {
        
        tabuleiro = new JogoDaVelha();
        
        console = new Scanner(System.in);
        
//        https://www.freesound.org/
        sons = new Sons("./sounds/comecar.wav", "./sounds/mover.wav", "./sounds/errar.wav", "./sounds/ganhar.wav", "./sounds/perder.wav");
        
        conectar();        
        iniciar();        
        jogar();
        
    }
    
    public void conectar() throws Exception {
        
        servidorConexao = new Socket( InetAddress.getByName( ConfigTXT.getIp() ), ConfigTXT.getPorta() );
        System.out.println( "Conexão Realizada: " + servidorConexao.getLocalAddress().getHostName() + ":" + servidorConexao.getLocalPort() + "\n" );
        
        servidorSaida = new ObjectOutputStream( servidorConexao.getOutputStream() );
        
        servidorEntrada = new ObjectInputStream( servidorConexao.getInputStream() );
        
        String mensagem = (String) servidorEntrada.readObject();
        String[] info = mensagem.split(";");
        setMarcador( info[0] );
        
        if( info[1].equals("true") ) {
            setSuaVez(true);
        } else {
            setSuaVez(false);
        }
        
    }
    
    public void iniciar() {
        
        tabuleiro.iniciar();        
        fim = false;
        
    }
    
    public void jogar() throws Exception {
        
        sons.comecar();
        
        int linha = -1, coluna = -1;
        String mensagem;
        
        while( fim == false ) {
            
            // não é a sua vez
            if( isSuaVez() == false ) {
                
                System.out.println( tabuleiro );
                System.out.println("Espere sua vez.");
                
                // recebe pelo servidor a jogada do seu adversário
                // linha;coluna;marcador
                mensagem = (String) servidorEntrada.readObject();
                
                sons.mover();
                
                String[] info = mensagem.split(";");
                
                linha = Integer.parseInt( info[0] );
                coluna = Integer.parseInt( info[1] );
                
                tabuleiro.marcar( linha, coluna, info[2] );
                
                // é sua vez
                setSuaVez(true);
                
                checarTermino();
                
            }
            
            // é sua vez
            boolean marcado = false;            
            while( fim == false && 
                    marcado == false ) {
                
                System.out.println( tabuleiro );
            
                System.out.print("Informe a linha (1, 2 ou 3): ");
                linha = console.nextInt();

                System.out.print("Informe a coluna (1, 2 ou 3): ");
                coluna = console.nextInt();
                
                try {                    
                                     
                    tabuleiro.marcar( linha, coluna, getMarcador() );
                    
                    marcado = true;
                    
                } catch(Exception ex) {
                    sons.errar();
                    System.out.println( ex.getMessage() );
                }                
                
            }
            
            // envia sua jogada para o servidor, para que este então a envie para seu adversário
            mensagem = linha + ";" + coluna + ";" + getMarcador();
            servidorSaida.writeObject(mensagem);
            servidorSaida.flush();
            
            sons.mover();
            
             setSuaVez(false);
            
            checarTermino();
            
        }
    }
    
    private void checarTermino() throws Exception {
        
        String marcadorGanhador = tabuleiro.ganhador();
            if( marcadorGanhador != null ) {
                
                fim = true;
                
                System.out.println( tabuleiro );
                
                Thread.sleep(1500); // terminar o son da jogada
                
                if( marcadorGanhador.equals( getMarcador() ) ) {
                    
                    sons.ganhar();
                    System.out.print("Você Ganhou! :)\n");
                    
                } else {
                    
                    sons.perder();
                    if( marcadorGanhador.equals("empate") ) {
                        System.out.print("Você Empatou! :|\n");
                    } else {
                        System.out.print("Você Perdeu! :(\n");
                    }
                    
                }
                
            }
            
            if( fim == true ) {
                checarReinicio();
            }
        
    }
    
    private void checarReinicio() throws Exception {
        
        char resposta = ' ';        
        while( resposta != 'S' &&
                resposta != 'N' ) {
            
            System.out.print("Deseja Jogar Novamente (S/N): ");
            resposta = console.next().charAt(0);
            
            if( resposta != 'S' &&
                resposta != 'N' ) {
                System.out.println("Resposta Inválida!");
            }
            
        }
        
        if( resposta == 'S' ) {
            iniciar();
        } else {
            servidorEntrada.close();
            servidorSaida.close();
            servidorConexao.close();
        }
        
    }
    
    public String getMarcador() {
        return marcador;
    }

    private void setMarcador(String marcador) {
        if( marcador.equals("X") || marcador.equals("O") ) {
            this.marcador = marcador;
        } else {
            throw new IllegalArgumentException("O marcador deve ser X ou O.");
        }
    }

    private boolean isSuaVez() {
        return suaVez;
    }

    private void setSuaVez(boolean suaVez) {
        this.suaVez = suaVez;
    }
    
}