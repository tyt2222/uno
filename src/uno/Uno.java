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
    public List<String> historicoJogadas;
    public int turnoAtual;
    public final int QTD_CARTAS = 7;

    public Uno(List<Jogador> jogadoresConectados) {
        baralho = new ArrayList<>();
        descarte = new ArrayList<>();
        jogadores = jogadoresConectados;
        historicoJogadas = new ArrayList<>();
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
        historicoJogadas.clear();
        adicionarHistoricoELog("O jogo comecou!");

        broadcast("\n=== JOGO INICIADO ===");

        while (!fim) {
            Jogador atual = jogadores.get(turnoAtual % jogadores.size());
            Carta topo = descarte.get(descarte.size() - 1);

            for (Jogador j : jogadores) {
                if (j != atual) {
                    j.enviarMensagem(obterTelaPartida(j, atual, topo, historicoJogadas));
                }
            }

            atual.enviarMensagem(obterTelaPartida(atual, atual, topo, historicoJogadas));

            int escolha = atual.receberJogada();

            if (escolha == -1) {
                atual.getMao().add(comprarCarta());
                adicionarHistoricoELog(atual.getNome() + " comprou uma carta.");
            } else if (escolha >= 0 && escolha < atual.getMao().size()) {
                Carta cartaJogada = atual.getMao().get(escolha);

                if (jogadaValida(topo, cartaJogada)) {
                    descarte.add(atual.getMao().remove(escolha));
                    adicionarHistoricoELog(atual.getNome() + " jogou " + cartaJogada);

                    if (atual.getMao().isEmpty()) {
                        broadcast("\n=== " + atual.getNome() + " Venceu! ===");
                        adicionarHistoricoELog(atual.getNome() + " venceu o jogo.");
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

    public String obterTelaPartida(Jogador j, Jogador atual, Carta topo, List<String> historico) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 50; k++) {
            sb.append("\n");
        }

        List<String> colEsquerda = new ArrayList<>();
        colEsquerda.add("Suas Cartas:");
        colEsquerda.add("");

        List<Carta> mao = j.getMao();
        if (mao.isEmpty()) {
            colEsquerda.add("(Sem cartas)");
        } else {
            int cartasPorLinha = 4;
            for (int i = 0; i < mao.size(); i += cartasPorLinha) {
                int limite = Math.min(i + cartasPorLinha, mao.size());
                List<Carta> linhaCartas = mao.subList(i, limite);

                for (int linha = 0; linha < 5; linha++) {
                    StringBuilder sbLinha = new StringBuilder();
                    for (Carta c : linhaCartas) {
                        sbLinha.append(c.getAsciiLines()[linha]).append("   ");
                    }
                    colEsquerda.add(sbLinha.toString());
                }

                StringBuilder sbIndices = new StringBuilder();
                for (int idx = i; idx < limite; idx++) {
                    String label = "[" + idx + "]";
                    int espacos = (11 - label.length()) / 2;
                    StringBuilder pad = new StringBuilder();
                    for (int p = 0; p < espacos; p++) {
                        pad.append(" ");
                    }
                    sbIndices.append(pad).append(label).append(pad);
                    if ((pad.length() * 2 + label.length()) < 11) {
                        sbIndices.append(" ");
                    }
                    sbIndices.append("   ");
                }
                colEsquerda.add(sbIndices.toString());
                colEsquerda.add("");
            }
        }

        List<String> colDireita = new ArrayList<>();
        colDireita.add("PILHA:");
        colDireita.add("");
        for (String line : topo.getAsciiLines()) {
            colDireita.add(line);
        }
        colDireita.add("");
        colDireita.add("HISTORICO DE JOGADAS:");

        if (!historico.isEmpty()) {
            // Newest play is shown on top (normal color)
            String maisRecente = historico.get(historico.size() - 1);
            colDireita.add(maisRecente);

            // Past plays are shown below in reverse chronological order (dark gray)
            int count = 0;
            for (int idx = historico.size() - 2; idx >= 0 && count < 4; idx--) {
                String jogada = historico.get(idx);
                colDireita.add("\u001B[1;30m" + jogada + Cor.RESET);
                count++;
            }
        }

        List<String> combined = combinarColunas(colEsquerda, colDireita, "   │   ");
        for (String line : combined) {
            sb.append(line).append("\n");
        }

        sb.append("\n");
        if (j == atual) {
            sb.append("Numero da Carta ou -1 para comprar: ");
        } else {
            sb.append("Aguarde o turno do ").append(atual.getNome()).append("...\n");
        }

        return sb.toString();
    }

    private void logAcao(String acao) {
        try {
            java.net.URL classUrl = Uno.class.getResource("Uno.class");
            java.io.File logFile = null;
            if (classUrl != null && classUrl.getProtocol().equals("file")) {
                java.io.File classFile = new java.io.File(classUrl.toURI());
                logFile = new java.io.File(classFile.getParentFile(), "log.txt");
            } else {
                logFile = new java.io.File("src/uno/log.txt");
            }
            if (logFile.getParentFile() != null) {
                logFile.getParentFile().mkdirs();
            }
            try (java.io.FileWriter fw = new java.io.FileWriter(logFile, true); java.io.PrintWriter pw = new java.io.PrintWriter(fw)) {
                pw.println(new java.util.Date() + " - " + acao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adicionarHistoricoELog(String acao) {
        historicoJogadas.add(acao);
        logAcao(acao);
    }

    public static int getVisualLength(String s) {
        if (s == null) {
            return 0;
        }
        return s.replaceAll("\\u001B\\[[;\\d]*m", "").length();
    }

    public static String padRightVisual(String s, int width) {
        int visualLen = getVisualLength(s);
        int diff = width - visualLen;
        if (diff <= 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < diff; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static List<String> combinarColunas(List<String> colEsquerda, List<String> colDireita, String separador) {
        int maxLines = Math.max(colEsquerda.size(), colDireita.size());
        List<String> resultado = new ArrayList<>();

        int maxVisualWidth = 0;
        for (String s : colEsquerda) {
            int len = getVisualLength(s);
            if (len > maxVisualWidth) {
                maxVisualWidth = len;
            }
        }

        if (maxVisualWidth < 40) {
            maxVisualWidth = 40;
        }

        for (int i = 0; i < maxLines; i++) {
            String esq = i < colEsquerda.size() ? colEsquerda.get(i) : "";
            String dir = i < colDireita.size() ? colDireita.get(i) : "";

            String esqPadded = padRightVisual(esq, maxVisualWidth);
            resultado.add(esqPadded + separador + dir);
        }
        return resultado;
    }

}
