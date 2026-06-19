/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Uno {

    public List<Carta> baralho;
    public List<Carta> descarte;
    public List<Jogador> jogadores;
    public int turnoAtual;
    public final int QTD_CARTAS = 7;

    public Uno(List<Jogador> jogadoresConectados) {
        baralho = new ArrayList<>();
        descarte = new ArrayList<>();
        jogadores = jogadoresConectados;
        turnoAtual = 0;
        inicializarJogo();
    }

    public void inicializarJogo() {

        for (Cor c : Cor.values()) {
            for (Valor v : Valor.values()) {
                baralho.add(new Carta(c, v));
                baralho.add(new Carta(c, v));
            }
        }

        Collections.shuffle(baralho);

        for (Jogador j : jogadores) {
            for (int i = 0; i < QTD_CARTAS; i++) {
                j.getMao().add(comprarCarta());
            }
        }

        descarte.add(comprarCarta());

    }

    public Carta comprarCarta() {
        if (baralho.isEmpty()) {
            Carta topo = descarte.remove(descarte.size() - 1);
            baralho.addAll(descarte);
            descarte.clear();
            descarte.add(topo);
            Collections.shuffle(baralho);
        }

        return baralho.remove(baralho.size() - 1);
    }

    public boolean jogadaValida(Carta topo, Carta jogada) {
        return topo.getCor() == jogada.getCor() || topo.getValor() == jogada.getValor();
    }

    public void broadcast(String mensagem) {
        for (Jogador j : jogadores) {
            j.enviarMensagem(mensagem);
        }
    }

    public void jogar() {
        boolean fim = false;
        String ultimaJogada = "O jogo comecou!";
        
        broadcast("\n=== JOGO INICIADO ===");

        while (!fim) {
            Jogador atual = jogadores.get(turnoAtual % jogadores.size());
            Carta topo = descarte.get(descarte.size() - 1);

            for (Jogador j : jogadores) {
                if (j != atual) {
                    StringBuilder aguarde = new StringBuilder();
                    for (int k = 0; k < 50; k++) {
                        aguarde.append("\n");
                    }
                    aguarde.append("======================================\n");
                    aguarde.append("PILHA: ").append(topo).append("\n");
                    aguarde.append("JOGADA DO OPONENTE: ").append(ultimaJogada).append("\n");
                    aguarde.append("======================================\n\n");
                    aguarde.append("Aguarde o turno do ").append(atual.getNome()).append("...\n");
                    j.enviarMensagem(aguarde.toString());
                }
            }

            StringBuilder tela = new StringBuilder();
            
            for (int k = 0; k < 50; k++) {
                tela.append("\n");
            }
            
            tela.append("======================================\n");
            tela.append("PILHA: ").append(topo).append("\n");
            tela.append("JOGADA DO OPONENTE: ").append(ultimaJogada).append("\n");
            tela.append("======================================\n\n");
            
            tela.append("Suas Cartas:\n");

            for (int i = 0; i < atual.getMao().size(); i++) {
                tela.append(String.format("[%d] %-14s", i, atual.getMao().get(i).toString()));
                
                if ((i + 1) % 3 == 0) {
                    tela.append("\n");
                } else if (i != atual.getMao().size() - 1) {
                    tela.append(" |  ");
                }
            }
            if (atual.getMao().size() % 3 != 0) {
                tela.append("\n");
            }

            tela.append("\nNumero da Carta ou -1 para comprar: ");
            
            atual.enviarMensagem(tela.toString());
            
            int escolha = atual.receberJogada();

            if (escolha == -1) {
                atual.getMao().add(comprarCarta());
                ultimaJogada = atual.getNome() + " comprou uma carta.";
            } else if (escolha >= 0 && escolha < atual.getMao().size()) {
                Carta cartaJogada = atual.getMao().get(escolha);

                if (jogadaValida(topo, cartaJogada)) {
                    descarte.add(atual.getMao().remove(escolha));
                    ultimaJogada = atual.getNome() + " jogou " + cartaJogada;

                    if (atual.getMao().isEmpty()) {
                        broadcast("\n=== " + atual.getNome() + " Venceu! ===");
                        fim = true;
                    }
                } else {
                    atual.enviarMensagem("Jogada Invalida!\n");
                    turnoAtual--;
                }
            } else {
                atual.enviarMensagem("Opcao Invalida!\n");
                turnoAtual--;
            }

            turnoAtual++;
        }

    }

}
