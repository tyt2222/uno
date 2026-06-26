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
                    aguarde.append("PILHA:\n");
                    for (String line : topo.getAsciiLines()) {
                        aguarde.append(line).append("\n");
                    }
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
            tela.append("PILHA:\n");
            for (String line : topo.getAsciiLines()) {
                tela.append(line).append("\n");
            }
            tela.append("JOGADA DO OPONENTE: ").append(ultimaJogada).append("\n");
            tela.append("======================================\n\n");
            
            tela.append("Suas Cartas:\n");

            int cartasPorLinha = 5;
            for (int i = 0; i < atual.getMao().size(); i += cartasPorLinha) {
                int limite = Math.min(i + cartasPorLinha, atual.getMao().size());
                List<Carta> linhaCartas = atual.getMao().subList(i, limite);
                
                for (int linha = 0; linha < 5; linha++) {
                    for (Carta c : linhaCartas) {
                        tela.append(c.getAsciiLines()[linha]).append("   ");
                    }
                    tela.append("\n");
                }
                
                for (int j = i; j < limite; j++) {
                    String idx = "[" + j + "]";
                    int espacos = (11 - idx.length()) / 2;
                    StringBuilder pad = new StringBuilder();
                    for(int p = 0; p < espacos; p++) pad.append(" ");
                    tela.append(pad).append(idx).append(pad);
                    if ((pad.length() * 2 + idx.length()) < 11) tela.append(" ");
                    tela.append("   ");
                }
                tela.append("\n\n");
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
