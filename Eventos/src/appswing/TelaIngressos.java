package appswing;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.*;
import regras_negocio.Fachada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TelaIngressos {

	
	
	private JScrollPane scrollPane;
	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
	//	EventQueue.invokeLater(new Runnable() {
	//		public void run() {
	//			try {
	//				TelaParticipantes window = new TelaParticipantes();
	//				window.frame.setVisible(true);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//	}
		//});
//	}

	/**
	 * Create the application.
	 */
	public TelaIngressos() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
				
			}
		});
		
		frame.setTitle("Ingressos"
				+ "");
		frame.setBounds(100, 100, 659, 362);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 431, 207);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 990766255421908251L;

			/**
			 * 
			 */
	

			/**
			 * 
			 */
			
			/**
			 * 
			 */
		
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		scrollPane.setViewportView(table);
		
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) 
				 lblNewLabel_3.setText("selecionado="+ table.getValueAt( table.getSelectedRow(), 0));
			}
		});
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
		textField = new JTextField();
		textField.setBounds(81, 244, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(258, 244, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(148, 277, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(25, 247, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setBounds(178, 247, 70, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Telefone");
		lblNewLabel_2.setBounds(78, 280, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Criar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  try {
			            // Verifica se os campos de texto estão vazios
			            if (textField_1.getText().isEmpty() ||  textField.getText().isEmpty()) {
			            	lblNewLabel_3.setText("Preencha todos os campos");
			                return;
			            }
                               
			            
			            
			            
			            String id  = textField.getText().trim();
			            String cpf = textField_1.getText().trim();
			            String telefone = textField_2.getText().trim();
			             

			            int idEvento = Integer.parseInt(id);
			            Evento evento = Fachada.localizarEvento(idEvento); // Método para localizar o evento pelo ID

			            if (evento == null) {
			            	lblNewLabel_3.setText("Evento não encontrado");
			                return;
			            }
			            
			            Participante participante = Fachada.localizarParticipante(cpf); // Método para localizar o participante pelo CPF

			            if (participante == null) {
			            	lblNewLabel_3.setText("Participante não encontrado");
			                return;
			            }


			            // Verifica se a string de data não está vazia
			            Fachada.criarIngresso(idEvento, cpf, telefone);
			            listagem();
			        } catch (NumberFormatException ex) {
			        	lblNewLabel_3.setText("Erro de formato numérico: " + ex.getMessage());
			        } catch (Exception ex) {
			        	lblNewLabel_3.setText("Erro: " + ex.getMessage());
			        }
			    }
			   
				 
		});
		btnNewButton.setBounds(390, 258, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		
		JButton btnNewButton_2 = new JButton("Apagar");
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (table.getSelectedRow() >= 0) {
		                String codigo = (String) table.getValueAt(table.getSelectedRow(), 0);
		                
		                // Confirmação
		                Object[] options = { "Confirmar", "Cancelar" };
		                int escolha = JOptionPane.showOptionDialog(null, "Confirma exclusão do p " + codigo, "Alerta",
		                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		                
		                if (escolha == 0) {
		                    Fachada.apagarIngresso(codigo); // Chame o método de apagar participante da sua Fachada
		                    lblNewLabel_4.setText("Exclusão realizada");
		                    listagem();
		                } else {
		                    lblNewLabel_4.setText("Exclusão cancelada");
		                }
		            } else {
		                lblNewLabel_4.setText("Selecione uma linha");
		            }
		        } catch (Exception erro) {
		            lblNewLabel_4.setText(erro.getMessage());
		        }
		    }
		});

		btnNewButton_2.setBounds(503, 76, 130, 23);
		frame.getContentPane().add(btnNewButton_2);
		
	    lblNewLabel_3 = new JLabel("");
	    lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setBounds(10, 309, 469, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(20, 218, 395, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
	}
		
		
		public void listagem() {
			try{
				ArrayList<Ingresso> lista = Fachada.listarIngresos();
			
				//objeto model contem todas as linhas e colunas da tabela
				DefaultTableModel model = new DefaultTableModel();

				//criar as colunas (0,1,2) da tabela
				model.addColumn("codigo");
				model.addColumn("telefone");
				model.addColumn("preço do ingresso");
				model.addColumn("preço do evento");
				model.addColumn("data do evento");
				model.addColumn("idade do participante");
	
				 				
				for(Ingresso i : lista)
				{
					Evento evento = i.getEvento();
		            Participante participante = i.getParticipante();
		            double precoEvento = evento.getPreco(); // Substitua isso pelo método correto
		            String dataEvento = evento.getData(); // Substitua isso pelo método correto
		            int idadeParticipante = participante.calcularIdade(); // Substitua isso pelo método correto
			
						model.addRow(new Object[]{i.getCodigo(), i.getTelefone(),i.calcularPreço(),precoEvento, dataEvento, idadeParticipante,});
				
				}
				table.setModel(model);
				lblNewLabel_4.setText("resultados: "+lista.size()+ " linhas   - selecione uma linha");

				//redimensionar a coluna 2
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 		//desabilita
				table.getColumnModel().getColumn(0).setMaxWidth(50);	
				table.getColumnModel().getColumn(1).setMaxWidth(100);	
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita
			}
			catch(Exception erro){
				lblNewLabel_4.setText(erro.getMessage());
			}
	}
}