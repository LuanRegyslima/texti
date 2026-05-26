package model;

import estruturas.FilaPessoa;
import estruturas.ListaUsuario;
import estruturas.NoUsuario;

public class Sistema {
    private ListaUsuario listaUsuarios;
    private FilaPessoa filaAtendimento;
    private int proximoIdUsuario;
    private int proximoIdPessoa;
    private Usuario usuarioLogado;

    public Sistema() {
        this.listaUsuarios = new ListaUsuario();
        this.filaAtendimento = new FilaPessoa();
        this.proximoIdUsuario = 1;
        this.proximoIdPessoa = 1;
        this.usuarioLogado = null;

        // Usuarios padrao
        listaUsuarios.inserir(new Administrador(proximoIdUsuario++, "admin", "admin123"));
        listaUsuarios.inserir(new Consultor(proximoIdUsuario++, "consultor", "cons123"));
    }

    public Usuario login(String nomeUsuario, String senha) {
        NoUsuario atual = listaUsuarios.getInicio();
        while (atual != null) {
            if (atual.getDado().login(nomeUsuario, senha)) {
                this.usuarioLogado = atual.getDado();
                return this.usuarioLogado;
            }
            atual = atual.getProximo();
        }
        return null;
    }//Fim do login

    public void logout() {
        this.usuarioLogado = null;
    }

    public boolean cadastrarAdministrador(String nome, String senha) {
        if (!isAdmin()) return false;
        if (listaUsuarios.buscar(nome) != null) return false;
        listaUsuarios.inserir(new Administrador(proximoIdUsuario++, nome, senha));
        return true;
    }//Fim do cadastrarAdministrador

    public boolean cadastrarConsultor(String nome, String senha) {
        if (!isAdmin()) return false;
        if (listaUsuarios.buscar(nome) != null) return false;
        listaUsuarios.inserir(new Consultor(proximoIdUsuario++, nome, senha));
        return true;
    }//Fim do cadastrarConsultor

    public Pessoa cadastrarPessoa(String nome, String telefone, String email) {
        if (!isAdmin()) return null;
        Pessoa p = new Pessoa(proximoIdPessoa++, nome, telefone, email);
        filaAtendimento.enfileirar(p);
        return p;
    }//Fim do cadastrarPessoa

    public Pessoa atenderProximo() {
        if (!isAdmin()) return null;
        return filaAtendimento.desenfileirar();
    }//Fim do atenderProximo

    public Pessoa verProximoDaFila() {
        return filaAtendimento.espiar();
    }

    public boolean isAdmin() {
        return usuarioLogado instanceof Administrador;
    }

    public Usuario getUsuarioLogado() { return usuarioLogado; }
    public ListaUsuario getListaUsuarios() { return listaUsuarios; }
    public FilaPessoa getFilaAtendimento() { return filaAtendimento; }
    public int getTamanhoFila() { return filaAtendimento.getTamanho(); }

}//Fim da Classe
