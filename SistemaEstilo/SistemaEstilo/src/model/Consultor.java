package model;

public class Consultor extends Usuario {

    public Consultor(int id, String nomeUsuario, String senha) {
        super(id, nomeUsuario, senha);
    }

    @Override
    public String getTipoUsuario() {
        return "Consultor";
    }

}//Fim da Classe
