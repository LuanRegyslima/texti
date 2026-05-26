package estruturas;

import model.Usuario;

public class NoUsuario {
    private NoUsuario proximo;
    private Usuario dado;

    public NoUsuario(Usuario dado) {
        this.dado = dado;
        this.proximo = null;
    }

    public NoUsuario getProximo() {
        return proximo;
    }

    public void setProximo(NoUsuario proximo) {
        this.proximo = proximo;
    }

    public Usuario getDado() {
        return dado;
    }

    public void setDado(Usuario dado) {
        this.dado = dado;
    }

}//Fim da Classe
