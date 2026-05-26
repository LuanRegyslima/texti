package estruturas;

import model.Pessoa;

public class FilaPessoa {
    private NoPessoa frente;
    private NoPessoa fundo;

    public FilaPessoa() {
        this.frente = null;
        this.fundo = null;
    }

    public void enfileirar(Pessoa valor) {
        NoPessoa novo = new NoPessoa(valor);
        if (this.fundo == null) {
            this.frente = novo;
            this.fundo = novo;
            return;
        }
        this.fundo.setProximo(novo);
        this.fundo = novo;
    }//Fim do enfileirar

    public Pessoa desenfileirar() {
        if (this.frente == null) {
            return null;
        }
        Pessoa dado = this.frente.getDado();
        this.frente = this.frente.getProximo();
        if (this.frente == null) {
            this.fundo = null;
        }
        return dado;
    }//Fim do desenfileirar

    public Pessoa espiar() {
        if (this.frente == null) {
            return null;
        }
        return this.frente.getDado();
    }//Fim do espiar

    public Pessoa obter(int indice) {
        int qtd = 0;
        NoPessoa atual = this.frente;
        while (atual != null) {
            if (qtd == indice) {
                return atual.getDado();
            }
            qtd++;
            atual = atual.getProximo();
        }
        return null;
    }//Fim do obter

    public boolean estaVazia() {
        return this.frente == null;
    }//Fim do estaVazia

    public int getTamanho() {
        int qtd = 0;
        NoPessoa atual = this.frente;
        while (atual != null) {
            qtd++;
            atual = atual.getProximo();
        }
        return qtd;
    }//Fim do getTamanho

}//Fim da Classe
