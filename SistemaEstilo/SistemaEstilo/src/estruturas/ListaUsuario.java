package estruturas;

import model.Usuario;

public class ListaUsuario {
    private NoUsuario inicio;

    public ListaUsuario() {
        this.inicio = null;
    }

    public void inserir(Usuario valor) {
        if (this.inicio == null) {
            this.inicio = new NoUsuario(valor);
            return;
        }
        NoUsuario atual = this.inicio;
        while (atual.getProximo() != null) {
            atual = atual.getProximo();
        }
        atual.setProximo(new NoUsuario(valor));
    }//Fim do inserir

    public Usuario buscar(String nomeUsuario) {
        NoUsuario atual = this.inicio;
        while (atual != null) {
            if (atual.getDado().getNomeUsuario().equals(nomeUsuario)) {
                return atual.getDado();
            }
            atual = atual.getProximo();
        }
        return null;
    }//Fim do buscar

    public Usuario obter(int indice) {
        int qtd = 0;
        NoUsuario atual = this.inicio;
        while (atual != null) {
            if (qtd == indice) {
                return atual.getDado();
            }
            qtd++;
            atual = atual.getProximo();
        }
        return null;
    }//Fim do obter

    public int getTamanho() {
        int qtd = 0;
        NoUsuario atual = this.inicio;
        while (atual != null) {
            qtd++;
            atual = atual.getProximo();
        }
        return qtd;
    }//Fim do getTamanho

    public NoUsuario getInicio() {
        return inicio;
    }

}//Fim da Classe
