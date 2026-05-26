package model;

public abstract class Usuario {
    private int id;
    private String nomeUsuario;
    private String senha;

    public Usuario(int id, String nomeUsuario, String senha) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public boolean login(String nomeUsuario, String senha) {
        return this.nomeUsuario.equals(nomeUsuario) && this.senha.equals(senha);
    }

    public abstract String getTipoUsuario();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        return "[" + getTipoUsuario() + "] " + nomeUsuario + " (ID: " + id + ")";
    }

}//Fim da Classe
