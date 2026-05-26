package model;

public class Administrador extends Usuario {

    public Administrador(int id, String nomeUsuario, String senha) {
        super(id, nomeUsuario, senha);
    }

    @Override
    public String getTipoUsuario() {
        return "Administrador";
    }

}//Fim da Classe
