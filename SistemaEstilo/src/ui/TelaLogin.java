package ui;

import model.Sistema;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private Sistema sistema;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JLabel labelErro;

    public TelaLogin() {
        this.sistema = new Sistema();
        inicializar();
    }

    public TelaLogin(Sistema sistema) {
        this.sistema = sistema;
        inicializar();
    }

    private void inicializar() {
        setTitle("Login - Sistema de Atendimento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Sistema de Atendimento ao Publico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        painel.add(new JLabel("Usuario:"), gbc);
        campoUsuario = new JTextField(15);
        gbc.gridx = 1;
        painel.add(campoUsuario, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        painel.add(new JLabel("Senha:"), gbc);
        campoSenha = new JPasswordField(15);
        gbc.gridx = 1;
        painel.add(campoSenha, gbc);

        labelErro = new JLabel(" ", SwingConstants.CENTER);
        labelErro.setForeground(Color.RED);
        labelErro.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painel.add(labelErro, gbc);

        JButton btnEntrar = new JButton("Entrar");
        gbc.gridy = 4;
        painel.add(btnEntrar, gbc);

        JLabel hint = new JLabel("admin/admin123  |  consultor/cons123", SwingConstants.CENTER);
        hint.setFont(new Font("Arial", Font.ITALIC, 10));
        hint.setForeground(Color.GRAY);
        gbc.gridy = 5;
        painel.add(hint, gbc);

        btnEntrar.addActionListener(e -> fazerLogin());
        campoUsuario.addActionListener(e -> fazerLogin());
        campoSenha.addActionListener(e -> fazerLogin());

        add(painel);
    }//Fim do inicializar

    private void fazerLogin() {
        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            labelErro.setText("Preencha todos os campos.");
            return;
        }

        Usuario u = sistema.login(usuario, senha);
        if (u != null) {
            dispose();
            new TelaPrincipal(sistema).setVisible(true);
        } else {
            labelErro.setText("Usuario ou senha incorretos.");
            campoSenha.setText("");
        }
    }//Fim do fazerLogin

}//Fim da Classe
