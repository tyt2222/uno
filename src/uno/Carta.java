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

    private void setCor(Cor cor) {
        this.cor = cor;
    }

    private void setValor(Valor valor) {
        this.valor = valor;
    }

    private Cor getCor() {
        return cor;
    }

    private Valor getValor() {
        return valor;
    }

}
