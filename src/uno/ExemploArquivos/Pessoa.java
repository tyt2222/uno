package aulas.arquivos;

import java.io.Serializable;

public class Pessoa implements Serializable {
    
    private int idade;

    public Pessoa(int idade) {
        setIdade(idade);
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if( idade >= 0 ) {
            this.idade = idade;
        } else {
            throw new RuntimeException("idade deve ser >= 0");
        }
    }

    @Override
    public String toString() {
        return "Pessoa(" + getIdade() + ")";
    }

}