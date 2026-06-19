package aulas.rede.jogo;

import java.awt.Font;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JogoGUI extends JFrame {
    
    private JogoDaVelha tabuleiro;
    private String marcador;
    private boolean fim;
    
    private Sons sons;
    
    private Socket servidorConexao;
    private ObjectInputStream servidorEntrada;
    private ObjectOutputStream servidorSaida;
    
    private JButton jButton11;
    private JButton jButton12;
    private JButton jButton13;
    
    private JButton jButton21;
    private JButton jButton22;
    private JButton jButton23;
    
    private JButton jButton31;
    private JButton jButton32;
    private JButton jButton33;

    public JogoGUI() throws Exception {        
        
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize(525, 558);
        setResizable(false);
        
        setLayout(null);
        setLocationRelativeTo(null);
        
        
        jButton11 = new JButton("");
        jButton11.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton11.setBounds(0, 0, 175, 175);
        jButton11.addActionListener( (ae) -> escolherOpcao(1, 1) );
        add(jButton11);
        
        jButton12 = new JButton("");
        jButton12.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton12.setBounds(175, 0, 175, 175);
        jButton12.addActionListener( (ae) -> escolherOpcao(1, 2) );
        add(jButton12);
        
        jButton13 = new JButton("");
        jButton13.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton13.setBounds(350, 0, 175, 175);
        jButton13.addActionListener( (ae) -> escolherOpcao(1, 3) );
        add(jButton13);
        
        
        jButton21 = new JButton("");
        jButton21.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton21.setBounds(0, 175, 175, 175);
        jButton21.addActionListener( (ae) -> escolherOpcao(2, 1) );
        add(jButton21);
        
        jButton22 = new JButton("");
        jButton22.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton22.setBounds(175, 175, 175, 175);
        jButton22.addActionListener( (ae) -> escolherOpcao(2, 2) );
        add(jButton22);
        
        jButton23 = new JButton("");
        jButton23.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton23.setBounds(350, 175, 175, 175);
        jButton23.addActionListener( (ae) -> escolherOpcao(2, 3) );
        add(jButton23);
        
        
        jButton31 = new JButton("");
        jButton31.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton31.setBounds(0, 350, 175, 175);
        jButton31.addActionListener( (ae) -> escolherOpcao(3, 1) );
        add(jButton31);
        
        jButton32 = new JButton("");
        jButton32.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton32.setBounds(175, 350, 175, 175);
        jButton32.addActionListener( (ae) -> escolherOpcao(3, 2) );
        add(jButton32);
        
        jButton33 = new JButton("");
        jButton33.setFont( new Font("Arial", Font.PLAIN, 42) );
        jButton33.setBounds(350, 350, 175, 175);
        jButton33.addActionListener( (ae) -> escolherOpcao(3, 3) );
        add(jButton33);
        
        
        setVisible(true);
        
        tabuleiro = new JogoDaVelha();
        
        sons = new Sons("./sounds/comecar.wav", "./sounds/mover.wav", "./sounds/errar.wav", "./sounds/ganhar.wav", "./sounds/perder.wav");
        
        iniciar();
        
        desabilitarOpcoes();
        
        conectar();        
        
    }
    
    private void habilitarOpcoes() {
        
        if( jButton11.getText().equals("") == true ) {
            jButton11.setEnabled(true);
        }
        
        if( jButton12.getText().equals("") == true ) {
            jButton12.setEnabled(true);
        }
        
        if( jButton13.getText().equals("") == true ) {
            jButton13.setEnabled(true);
        }
        
        if( jButton21.getText().equals("") == true ) {
            jButton21.setEnabled(true);
        }
        
        if( jButton22.getText().equals("") == true ) {
            jButton22.setEnabled(true);
        }
        
        if( jButton23.getText().equals("") == true ) {
            jButton23.setEnabled(true);
        }
        
        if( jButton31.getText().equals("") == true ) {
            jButton31.setEnabled(true);
        }
        
        if( jButton32.getText().equals("") == true ) {
            jButton32.setEnabled(true);
        }
        
        if( jButton33.getText().equals("") == true ) {
            jButton33.setEnabled(true);
        }
        
    }
    
    private void desabilitarOpcoes() {
        
        jButton11.setEnabled(false);
        jButton12.setEnabled(false);
        jButton13.setEnabled(false);
        
        jButton21.setEnabled(false);
        jButton22.setEnabled(false);
        jButton23.setEnabled(false);
        
        jButton31.setEnabled(false);
        jButton32.setEnabled(false);
        jButton33.setEnabled(false);
        
    }

    private void escolherOpcao(int linha, int coluna) {
        
        try {
            
            marcarOpcao( linha, coluna, getMarcador() );        
            desabilitarOpcoes();
        
            tabuleiro.marcar( linha, coluna, getMarcador() );
            String mensagem = linha + ";" + coluna + ";" + getMarcador();
            
            servidorSaida.reset();
            servidorSaida.writeObject( new Jogada( linha, coluna, getMarcador() ) );
            servidorSaida.flush();
            
            sons.mover();
            
            checarTermino();
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog( this, ex.getMessage() );
            dispose();
        }

    }
    
    private void marcarOpcao(int linha, int coluna, String marcador) {
        
        JButton jButton = null;
        
        switch (linha) {
            case 1:
                switch (coluna) {
                    case 1:
                        jButton = jButton11;
                        break;
                    case 2:                        
                        jButton = jButton12;
                        break;
                    case 3:
                        jButton = jButton13;
                        break;
                }
                break;
            case 2:
                switch (coluna) {
                    case 1:
                        jButton = jButton21;
                        break;
                    case 2:
                        jButton = jButton22;
                        break;
                    case 3:
                        jButton = jButton23;
                        break;
                }
                break;
            case 3:
                switch (coluna) {
                    case 1:
                        jButton = jButton31;
                        break;
                    case 2:
                        jButton = jButton32;
                        break;
                    case 3:
                        jButton = jButton33;
                        break;
                }
                break;
        }
        
        jButton.setText(marcador);
        
    }
    
    // como Thread, para não atrapalhar a renderização do JFrame
    private void receberOpcao() {
        
        new Thread( () -> {
        
            try {
                
                while( isFim() == false ) {

                    Jogada jogada = (Jogada) servidorEntrada.readObject();

                    sons.mover();

                    marcarOpcao( jogada.getLinha(), jogada.getColuna(), jogada.getMarcador() );

                    tabuleiro.marcar( jogada.getLinha(), jogada.getColuna(), jogada.getMarcador() );

                    habilitarOpcoes();

                    checarTermino();
                
                }
                
                System.out.println("Fim da Thread receberOpcao().");

            } catch(Exception ex) {
                JOptionPane.showMessageDialog( this, ex.getMessage() );
                dispose();
            }
        
        } ).start();
        
    }
    
    private void inicarOpcoes() {
        
        jButton11.setText("");
        jButton12.setText("");
        jButton13.setText("");
        
        jButton21.setText("");
        jButton22.setText("");
        jButton23.setText("");
        
        jButton31.setText("");
        jButton32.setText("");
        jButton33.setText("");
        
        habilitarOpcoes();
        
    }
    
    public void conectar() throws Exception {
                
        servidorConexao = new Socket( InetAddress.getByName( ConfigTXT.getIp() ), ConfigTXT.getPorta() );
        JOptionPane.showMessageDialog(this, "Conexão Realizada: " + servidorConexao.getLocalAddress().getHostName() + ":" + servidorConexao.getLocalPort() + "\n" );
        
        servidorSaida = new ObjectOutputStream( servidorConexao.getOutputStream() );
        
        servidorEntrada = new ObjectInputStream( servidorConexao.getInputStream() );
        
        String mensagem = (String) servidorEntrada.readObject();
        String[] info = mensagem.split(";");
        setMarcador( info[0] );
        
        setTitle( "JOGO DA VELHA -- JOGADOR " + getMarcador() );
        
        if( info[1].equals("true") ) {
            habilitarOpcoes();
        } else {
            desabilitarOpcoes();
        }
        
        receberOpcao();
        
    }
     
    private void iniciar() throws Exception {
        
        sons.comecar();
        inicarOpcoes();
        tabuleiro.iniciar();
        setFim(false);
        
    }
    
    private void checarTermino() throws Exception {
        
        String marcadorGanhador = tabuleiro.ganhador();
            if( marcadorGanhador != null ) {
                
                desabilitarOpcoes();
                
//                Thread.sleep(1500); // terminar o son da jogada
                
                if( marcadorGanhador.equals( getMarcador() ) ) {
                    
                    sons.ganhar();
                    JOptionPane.showMessageDialog(this, "Você Ganhou! :)");
                    
                } else {
                    
                    sons.perder();
                    if( marcadorGanhador.equals("empate") ) {
                        JOptionPane.showMessageDialog(this, "Você Empatou! :|");
                    } else {
                        JOptionPane.showMessageDialog(this, "Você Perdeu! :(");
                    }
                    
                }
                
                checarReinicio();
                
            }
        
    }
    
    private void checarReinicio() throws Exception {
        
        String resposta = " ";        
        while( resposta.equals("S") == false &&
                resposta.equals("N") == false ) {
            
            resposta = JOptionPane.showInputDialog(this, "Deseja Jogar Novamente (S/N): ");

            if( resposta.equals("S") == false &&
                resposta.equals("N") == false ) {
                JOptionPane.showMessageDialog(this, "Resposta Inválida!");
            }            
            
        }
        
        if(  resposta.equals("S") == true ) {
            
            iniciar();
            
            if( getMarcador().equals("X") == true ) {
                habilitarOpcoes();
            } else {
                desabilitarOpcoes();   
            }
            
        } else {
            setFim(true);
            servidorEntrada.close();
            servidorSaida.close();
            servidorConexao.close();
            dispose();
        }
        
    }
     
    private String getMarcador() {
        return marcador;
    }

    private void setMarcador(String marcador) {
        if( marcador.equals("X") || marcador.equals("O") ) {
            this.marcador = marcador;
        } else {
            throw new IllegalArgumentException("O marcador deve ser X ou O.");
        }
    }

    private boolean isFim() {
        return fim;
    }

    private void setFim(boolean fim) {
        this.fim = fim;
    }
    

    public static void main(String[] args) {
        try {
            JogoGUI jogo = new JogoGUI();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
        }
    }
    
}