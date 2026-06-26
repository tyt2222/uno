/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

public enum Cor {
    VERMELHO("\u001B[31m"),
    AZUL("\u001B[34m"),
    VERDE("\u001B[32m"),
    AMARELO("\u001B[33m");

    private final String codigoAnsi;

    Cor(String codigoAnsi) {
        this.codigoAnsi = codigoAnsi;
    }

    public String getCodigoAnsi() {
        return codigoAnsi;
    }
    
    public static final String RESET = "\u001B[0m";
}
