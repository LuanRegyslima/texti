package ui;

import model.*;
import estruturas.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private Sistema sistema;
    private JTabbedPane abas;
    private DefaultTableModel modeloFila;
    private DefaultTableModel modeloUsuarios;

    public TelaPrincipal(Sistema sistema) {
        this.sistema = sistema;
        inicializar();
    }

    private void inicializar() {
        Usuario u = sistema.getUsuarioLogado();
        setTitle("Sistema de Atendimento - " + u.getTipoUsuario() + ": " + u.getNomeUsuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        abas = new JTabbedPane();
        abas.addTab("Fila de Atendimento", criarAbaFila());

        if (sistema.isAdmin()) {
            abas.addTab("Cadastrar Pessoa", criarAbaCadastrarPessoa());
            abas.addTab("Realizar Atendimento", criarAbaAtendimento());
            abas.addTab("Usuarios", criarAbaUsuarios());
            abas.addTab("Cadastrar Usuario", criarAbaCadastrarUsuario());
        }

        add(abas, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblUsuario = new JLabel("Logado como: " + u.getNomeUsuario() + " (" + u.getTipoUsuario() + ")");
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 11));
        JButton btnLogout = new JButton("Sair");
        btnLogout.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(this, "Deseja sair?", "Confirmacao", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                sistema.logout();
                dispose();
                new TelaLogin(sistema).setVisible(true);
            }
        });
        rodape.add(lblUsuario);
        rodape.add(btnLogout);
        add(rodape, BorderLayout.SOUTH);
    }//Fim do inicializar

    // ── Aba: Fila de Atendimento ──────────────────────────────────────────────

    private JPanel criarAbaFila() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"Posicao", "ID", "Nome", "Telefone", "E-mail"};
        modeloFila = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelaFila = new JTable(modeloFila);
        tabelaFila.getTableHeader().setReorderingAllowed(false);

        JLabel lblInfo = new JLabel("Pessoas aguardando: " + sistema.getTamanhoFila());

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> {
            atualizarTabelaFila();
            lblInfo.setText("Pessoas aguardando: " + sistema.getTamanhoFila());
        });

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(lblInfo);
        topo.add(btnAtualizar);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaFila), BorderLayout.CENTER);

        atualizarTabelaFila();
        return painel;
    }//Fim do criarAbaFila

    private void atualizarTabelaFila() {
        modeloFila.setRowCount(0);
        FilaPessoa fila = sistema.getFilaAtendimento();
        for (int i = 0; i < fila.getTamanho(); i++) {
            Pessoa p = fila.obter(i);
            modeloFila.addRow(new Object[]{i + 1, p.getId(), p.getNome(), p.getTelefone(), p.getEmail()});
        }
    }//Fim do atualizarTabelaFila

    // ── Aba: Cadastrar Pessoa ─────────────────────────────────────────────────

    private JPanel criarAbaCadastrarPessoa() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField fNome  = new JTextField(20);
        JTextField fTel   = new JTextField(20);
        JTextField fEmail = new JTextField(20);
        JLabel lblStatus  = new JLabel(" ");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Cadastrar nova pessoa na fila");
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        painel.add(titulo, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        painel.add(new JLabel("Nome *:"), gbc);
        gbc.gridx = 1; painel.add(fNome, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        painel.add(new JLabel("Telefone *:"), gbc);
        gbc.gridx = 1; painel.add(fTel, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        painel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1; painel.add(fEmail, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        painel.add(lblStatus, gbc);

        JButton btnCadastrar = new JButton("Adicionar a Fila");
        gbc.gridy = 5;
        painel.add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(e -> {
            String nome  = fNome.getText().trim();
            String tel   = fTel.getText().trim();
            String email = fEmail.getText().trim();

            if (nome.isEmpty() || tel.isEmpty()) {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Preencha nome e telefone.");
                return;
            }

            Pessoa nova = sistema.cadastrarPessoa(nome, tel, email.isEmpty() ? "-" : email);
            if (nova != null) {
                lblStatus.setForeground(new Color(0, 130, 0));
                lblStatus.setText("Pessoa adicionada! Posicao na fila: " + sistema.getTamanhoFila());
                fNome.setText(""); fTel.setText(""); fEmail.setText("");
                atualizarTabelaFila();
            }
        });

        return painel;
    }//Fim do criarAbaCadastrarPessoa

    // ── Aba: Realizar Atendimento ─────────────────────────────────────────────

    private JPanel criarAbaAtendimento() {
        JPanel painel = new JPanel(new BorderLayout(5, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblProximo = new JLabel("Clique no botao para atender o proximo da fila.");
        lblProximo.setFont(new Font("Arial", Font.PLAIN, 13));

        JTextArea areaInfo = new JTextArea(6, 40);
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaInfo.setBorder(BorderFactory.createTitledBorder("Dados do Atendido"));
        areaInfo.setBackground(new Color(245, 245, 245));

        JButton btnAtender = new JButton("Atender Proximo da Fila");
        btnAtender.setFont(new Font("Arial", Font.BOLD, 13));

        btnAtender.addActionListener(e -> {
            Pessoa prox = sistema.verProximoDaFila();
            if (prox == null) {
                JOptionPane.showMessageDialog(this, "A fila esta vazia!", "Aviso", JOptionPane.WARNING_MESSAGE);
                areaInfo.setText("");
                lblProximo.setText("Fila vazia.");
                return;
            }

            int op = JOptionPane.showConfirmDialog(this,
                    "Atender: " + prox.getNome() + "?", "Confirmar Atendimento", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                Pessoa atendida = sistema.atenderProximo();
                areaInfo.setText(
                    "Nome:      " + atendida.getNome()     + "\n" +
                    "ID:        " + atendida.getId()       + "\n" +
                    "Telefone:  " + atendida.getTelefone() + "\n" +
                    "E-mail:    " + atendida.getEmail()
                );
                Pessoa proximo = sistema.verProximoDaFila();
                lblProximo.setText(proximo != null
                        ? "Proximo da fila: " + proximo.getNome()
                        : "Fila vazia apos atendimento.");
                atualizarTabelaFila();
            }
        });

        abas.addChangeListener(e -> {
            Pessoa prox = sistema.verProximoDaFila();
            lblProximo.setText(prox != null
                    ? "Proximo da fila: " + prox.getNome()
                    : "Fila esta vazia.");
        });

        painel.add(lblProximo, BorderLayout.NORTH);
        painel.add(new JScrollPane(areaInfo), BorderLayout.CENTER);
        painel.add(btnAtender, BorderLayout.SOUTH);

        return painel;
    }//Fim do criarAbaAtendimento

    // ── Aba: Listar Usuarios ──────────────────────────────────────────────────

    private JPanel criarAbaUsuarios() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Usuario", "Tipo"};
        modeloUsuarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(modeloUsuarios);
        tabela.getTableHeader().setReorderingAllowed(false);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarTabelaUsuarios());

        painel.add(btnAtualizar, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizarTabelaUsuarios();
        return painel;
    }//Fim do criarAbaUsuarios

    private void atualizarTabelaUsuarios() {
        modeloUsuarios.setRowCount(0);
        ListaUsuario lista = sistema.getListaUsuarios();
        for (int i = 0; i < lista.getTamanho(); i++) {
            Usuario u = lista.obter(i);
            modeloUsuarios.addRow(new Object[]{u.getId(), u.getNomeUsuario(), u.getTipoUsuario()});
        }
    }//Fim do atualizarTabelaUsuarios

    // ── Aba: Cadastrar Usuario ────────────────────────────────────────────────

    private JPanel criarAbaCadastrarUsuario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField fNome      = new JTextField(20);
        JPasswordField fSenha = new JPasswordField(20);
        JLabel lblStatus      = new JLabel(" ");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));

        JRadioButton rbAdmin     = new JRadioButton("Administrador");
        JRadioButton rbConsultor = new JRadioButton("Consultor");
        rbConsultor.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAdmin); bg.add(rbConsultor);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Cadastrar novo usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        painel.add(titulo, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        painel.add(new JLabel("Nome de usuario *:"), gbc);
        gbc.gridx = 1; painel.add(fNome, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        painel.add(new JLabel("Senha *:"), gbc);
        gbc.gridx = 1; painel.add(fSenha, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        painel.add(new JLabel("Tipo *:"), gbc);
        JPanel tipoPainel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tipoPainel.add(rbAdmin);
        tipoPainel.add(rbConsultor);
        gbc.gridx = 1; painel.add(tipoPainel, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        painel.add(lblStatus, gbc);

        JButton btnCadastrar = new JButton("Cadastrar Usuario");
        gbc.gridy = 5;
        painel.add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(e -> {
            String nome  = fNome.getText().trim();
            String senha = new String(fSenha.getPassword()).trim();

            if (nome.isEmpty() || senha.isEmpty()) {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Preencha todos os campos.");
                return;
            }
            if (senha.length() < 4) {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Senha deve ter pelo menos 4 caracteres.");
                return;
            }

            boolean ok = rbAdmin.isSelected()
                    ? sistema.cadastrarAdministrador(nome, senha)
                    : sistema.cadastrarConsultor(nome, senha);

            if (ok) {
                lblStatus.setForeground(new Color(0, 130, 0));
                lblStatus.setText("Usuario '" + nome + "' cadastrado com sucesso!");
                fNome.setText(""); fSenha.setText("");
                atualizarTabelaUsuarios();
            } else {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Nome de usuario ja existe.");
            }
        });

        return painel;
    }//Fim do criarAbaCadastrarUsuario

}//Fim da Classe
