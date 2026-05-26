package estruturas;

import model.Pessoa;

public class NoPessoa {
    private NoPessoa proximo;
    private Pessoa dado;

    public NoPessoa(Pessoa dado) {
        this.dado = dado;
        this.proximo = null;
    }

    public NoPessoa getProximo() {
        return proximo;
    }

    public void setProximo(NoPessoa proximo) {
        this.proximo = proximo;
    }

    public Pessoa getDado() {
        return dado;
    }

    public void setDado(Pessoa dado) {
        this.dado = dado;
    }

}//Fim da Classe
