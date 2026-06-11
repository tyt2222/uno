/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Carta> mao;

    public Jogador(String nome) {
        setNome(nome);
        this.mao = new ArrayList<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public List<Carta> getMao() {
        return mao;
    }
    
    
    
    
}
