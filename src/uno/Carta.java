/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

public class Carta {

    private Cor cor;
    private Valor valor;

    public Carta(Cor cor, Valor valor) {
        setCor(cor);
        setValor(valor);
    }

    @Override
    public String toString() {
        return getValor() + "-" + getCor(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public String[] getAsciiLines() {
        String colorCode = cor.getCodigoAnsi();
        String reset = Cor.RESET;
        String valStr = valor.toString();
        valStr = valStr.length() > 7 ? valStr.substring(0, 7) : valStr;
        
        String corStr = cor.toString();
        corStr = corStr.length() > 7 ? corStr.substring(0, 7) : corStr;
        
        int leftPad = (7 - corStr.length()) / 2;
        StringBuilder centerBuilder = new StringBuilder();
        for (int i = 0; i < leftPad; i++) centerBuilder.append(" ");
        centerBuilder.append(corStr);
        while (centerBuilder.length() < 7) centerBuilder.append(" ");
        String center = centerBuilder.toString();
        
        String[] lines = new String[5];
        lines[0] = "┌─────────┐";
        lines[1] = String.format("│ %s%-7s%s │", colorCode, valStr, reset);
        lines[2] = String.format("│ %s%s%s │", colorCode, center, reset);
        lines[3] = String.format("│ %s%7s%s │", colorCode, valStr, reset);
        lines[4] = "└─────────┘";
        return lines;
    }

    private void setCor(Cor cor) {
        this.cor = cor;
    }

    private void setValor(Valor valor) {
        this.valor = valor;
    }

    public Cor getCor() {
        return cor;
    }

    public Valor getValor() {
        return valor;
    }

}
