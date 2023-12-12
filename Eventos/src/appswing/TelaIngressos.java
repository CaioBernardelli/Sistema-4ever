package appswing;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.*;
import regras_negocio.Fachada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaIngressos {

    private JFrame frame;
    private JTable table;
    private JTextField textFieldCodigo;
    private JLabel label;
    private JTextField textFieldTelefone;
    private JTextField textFieldCPF;

    public TelaIngressos() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Ingressos");
        frame.setBounds(100, 100, 659, 362);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 431, 207);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(81, 244, 86, 20);
        frame.getContentPane().add(textFieldCodigo);
        textFieldCodigo.setColumns(10);
        
        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setBounds(25, 247, 46, 14);
        frame.getContentPane().add(lblCodigo); 
        
        textFieldTelefone = new JTextField();
        textFieldTelefone.setBounds(81, 274, 86, 20); // Ajuste a posição e o tamanho conforme necessário
        frame.getContentPane().add(textFieldTelefone);
        textFieldTelefone.setColumns(10);
        
        JLabel lbTelefone = new JLabel("Telefone");
        lbTelefone.setBounds(25, 277, 46, 19);
        frame.getContentPane().add(lbTelefone); 

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pesquisarIngresso();
            }
        });
        btnPesquisar.setBounds(258, 243, 100, 23);
        frame.getContentPane().add(btnPesquisar);

        label = new JLabel("");
        label.setForeground(Color.RED);
        label.setBounds(10, 309, 469, 14);
        frame.getContentPane().add(label);

        JButton btnListarIngressos = new JButton("Listar Ingressos");
        btnListarIngressos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarIngressos();
            }
        });
        btnListarIngressos.setBounds(450, 11, 183, 23);
        frame.getContentPane().add(btnListarIngressos);

        JButton btnCriarIngresso = new JButton("Criar Ingresso");
        btnCriarIngresso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarIngresso();
            }
        });
        
        btnCriarIngresso.setBounds(450, 45, 183, 23);
        frame.getContentPane().add(btnCriarIngresso);

        JButton btnApagarIngresso = new JButton("Apagar Ingresso");
        btnApagarIngresso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarIngresso();
            }
        });
        btnApagarIngresso.setBounds(450, 79, 183, 23);
        frame.getContentPane().add(btnApagarIngresso);
    }
    private void pesquisarIngresso() {
        try {
            String codigo = textFieldCodigo.getText().trim();

            if (codigo.isEmpty()) {
                label.setText("Preencha o código do ingresso");
                return;
            }

            Ingresso ingresso = Fachada.buscarIngressoPorCodigo(codigo);

            if (ingresso != null) {
                double precoFinal = ingresso.calcularPreço();
                label.setText("Detalhes do ingresso: Código: " + ingresso.getCodigo() +
                        ", Telefone: " + ingresso.getTelefone() +
                        ", Preço Final: " + precoFinal);
            } else {
                label.setText("Ingresso não encontrado");
            }
        } catch (Exception ex) {
            label.setText("Erro: " + ex.getMessage());
        }
    }


    private void listarIngressos() {
        try {
            ArrayList<Ingresso> listaDeIngressos = Fachada.listarIngresos();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Código");
            model.addColumn("Telefone");
            model.addColumn("Preço Final");

            for (Ingresso ingresso : listaDeIngressos) {
                double precoFinal = ingresso.calcularPreço();

                model.addRow(new Object[]{
                        ingresso.getCodigo(),
                        ingresso.getTelefone(),
                        precoFinal
                });
            }

            table.setModel(model);
        } catch (Exception ex) {
            label.setText("Erro ao listar ingressos: " + ex.getMessage());
        }
    }

    private void criarIngresso() {
        try {
            String codigo = textFieldCodigo.getText().trim();
            String telefone = textFieldTelefone.getText().trim();

            if (codigo.isEmpty() || telefone.isEmpty()) {
                label.setText("Preencha todos os campos");
                return;
            }

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                label.setText("Selecione um evento na lista");
                return;
            }

            int eventId = (int) table.getValueAt(selectedRow, 0); // Ajuste para pegar o ID do evento na tabela

            Evento evento = Fachada.localizarEvento(eventId); // Método para localizar o evento pelo ID

            if (evento == null) {
                label.setText("Evento não encontrado");
                return;
            }

            String cpfParticipante = textFieldCPF.getText();

            Participante participante = Fachada.localizarParticipante(cpfParticipante); // Método para localizar o participante pelo CPF

            if (participante == null) {
                label.setText("Participante não encontrado");
                return;
            }

            Fachada.criarIngresso(eventId, cpfParticipante, telefone);
            listarIngressos();
            label.setText("Ingresso criado com sucesso");
        } catch (Exception ex) {
            label.setText("Erro ao criar ingresso: " + ex.getMessage());
        }
    }



    private void apagarIngresso() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                label.setText("Selecione um ingresso para apagar");
                return;
            }

            String codigoIngresso = (String) table.getValueAt(selectedRow, 0);
            Fachada.apagarIngresso(codigoIngresso);

            listarIngressos();
            label.setText("Ingresso apagado com sucesso");
        } catch (Exception ex) {
            label.setText("Erro ao apagar ingresso: " + ex.getMessage());
        }
    }
}
