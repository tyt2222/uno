/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Carta> mao;
    
    private Socket conexao;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Jogador(String nome, Socket conexao) {
        setNome(nome);
        this.mao = new ArrayList<>();
        this.conexao = conexao;
        
        try {
            this.output = new ObjectOutputStream(conexao.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(conexao.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    
    public void enviarMensagem(String mensagem) {
        try {
            output.writeObject(mensagem);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int receberJogada() {
        try {
            Object obj = input.readObject();
            if (obj instanceof Integer) {
                return (Integer) obj;
            } else if (obj instanceof String) {
                return Integer.parseInt((String) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }
}
