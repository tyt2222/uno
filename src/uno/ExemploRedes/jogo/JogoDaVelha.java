package aulas.rede.jogo;

public class JogoDaVelha {
    
    private String[][] tabuleiro;
    
    public JogoDaVelha() {
        
        tabuleiro = new String[3][3];
        iniciar();
        
    }
    
    public void iniciar() {
        
        for(int linha = 0; linha < 3; linha++) {
            for(int coluna = 0; coluna < 3; coluna++) {
                tabuleiro[linha][coluna] = "  ";
            }
        }
        
    }
    
    public void marcar(int linha, int coluna, String marcador) throws Exception {
        
        linha -= 1;
        coluna -= 1;
        
        if( linha < 0 || linha > 2 || coluna < 0 || coluna > 2  ) {
            throw new Exception("linha e/ou coluna inválido(s), devendo ser 1, 2 ou 3.");
        }
        
        if( marcador.equals("X") == false && marcador.equals("O") == false ) {
            throw new Exception("marcador inválido, devendo ser X ou O.");
        }
        
        if( tabuleiro[linha][coluna].equals("  ") == false ) {
            throw new Exception("linha e/ou coluna inválido(s), posição ocupada.");
        }
        
        tabuleiro[linha][coluna] = marcador;
        
    }
    
    public String ganhador() {
        
        if( tabuleiro[0][0].equals("  ") == false &&
                tabuleiro[0][0].equals( tabuleiro[1][1] ) == true && 
                tabuleiro[1][1].equals( tabuleiro[2][2] ) == true ) {
            return tabuleiro[0][0];
        }
        
        if( tabuleiro[0][2].equals("  ") == false &&
                tabuleiro[0][2].equals( tabuleiro[1][1] ) == true && 
                tabuleiro[1][1].equals( tabuleiro[2][0] ) == true ) {
            return tabuleiro[0][2];
        }
        
        for( int i = 0; i < 3; i++ ) {
        
            if( tabuleiro[i][0].equals("  ") == false &&
                    tabuleiro[i][0].equals( tabuleiro[i][1] ) == true && 
                    tabuleiro[i][1].equals( tabuleiro[i][2] ) == true ) {
                return tabuleiro[i][0];
            }
            
            if( tabuleiro[0][i].equals("  ") == false &&
                    tabuleiro[0][i].equals( tabuleiro[1][i] ) == true && 
                    tabuleiro[1][i].equals( tabuleiro[2][i] ) == true ) {
                return tabuleiro[0][i];
            }
            
        }
        
        if( tabuleiro[0][0].equals("  ") == false &&
                tabuleiro[0][1].equals("  ") == false &&
                tabuleiro[0][2].equals("  ") == false &&
                tabuleiro[1][0].equals("  ") == false &&
                tabuleiro[1][1].equals("  ") == false &&
                tabuleiro[1][2].equals("  ") == false &&
                tabuleiro[2][0].equals("  ") == false &&
                tabuleiro[2][1].equals("  ") == false &&
                tabuleiro[2][2].equals("  ") == false ) {
            return "empate";
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        String resultado = "\n";
        
        for(int linha = 0; linha < 3; linha++) {
           for(int coluna = 0; coluna < 3; coluna++) {
              resultado += "[  " + tabuleiro[linha][coluna] + "  ]";
           }
           resultado += "\n";
        }
        
        return resultado;
    }
    
}