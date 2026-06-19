package aulas.rede.jogo;

import java.io.Serializable;

public class Jogada implements Serializable {
        
    private int linha, coluna;
    private String marcador;

    public Jogada(int linha, int coluna, String marcador) throws Exception {
        setLinha(linha);
        setColuna(coluna);
        setMarcador(marcador);
    }

    public int getLinha() {
        return linha;
    }

    private void setLinha(int linha) throws Exception {
        if( linha < 1 || linha > 3  ) {
            throw new Exception("linha inválida, devendo ser 1, 2 ou 3.");
        }
        
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    private void setColuna(int coluna) throws Exception {
        if( coluna < 1 || coluna > 3  ) {
            throw new Exception("coluna inválida, devendo ser 1, 2 ou 3.");
        }
        
        this.coluna = coluna;
    }

    public String getMarcador() {
        return marcador;
    }

    private void setMarcador(String marcador) throws Exception {
        if( marcador.equals("X") || marcador.equals("O") ) {
            this.marcador = marcador;
        } else {
            throw new Exception("O marcador deve ser X ou O.");
        }
    }

    @Override
    public String toString() {
        return getLinha() + ";" + getColuna() + ";" + getMarcador();
    }

}