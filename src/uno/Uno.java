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
    public Scanner scanner;
    public final int QTD_CARTAS = 7;

    public Uno() {
        baralho = new ArrayList<>();
        descarte = new ArrayList<>();
        jogadores = new ArrayList<>();
        scanner = new Scanner(System.in);
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

        jogadores.add(new Jogador("Jogador 1"));
        jogadores.add(new Jogador("Jogador 2"));

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

    public void jogar() {
        boolean fim = false;

        while (!fim) {
            Jogador atual = jogadores.get(turnoAtual % jogadores.size());
            Carta topo = descarte.get(descarte.size() - 1);

            System.out.println("\nTurno: " + atual.getNome());
            System.out.println("Pilha: " + topo);
            System.out.println("Mao: ");

            for (int i = 0; i < atual.getMao().size(); i++) {
                System.out.println(i + ": " + atual.getMao().get(i));
            }

            System.out.print("Indice ou -1 para comprar: ");
            int escolha = scanner.nextInt();

            if (escolha == -1) {
                atual.getMao().add(comprarCarta());
                System.out.println("Comprou uma carta");
            } else if (escolha >= 0 && escolha < atual.getMao().size()) {
                Carta cartaJogada = atual.getMao().get(escolha);

                if (jogadaValida(topo, cartaJogada)) {
                    descarte.add(atual.getMao().remove(escolha));
                    System.out.println("Jogou: " + cartaJogada);

                    if (atual.getMao().isEmpty()) {
                        System.out.println(atual.getNome() + "Venceu");
                        fim = true;
                    }
                } else {
                    System.out.println("Invalido");
                }
            } else {
                System.out.println("Invalido");
            }

            turnoAtual++;
        }

    }

}
