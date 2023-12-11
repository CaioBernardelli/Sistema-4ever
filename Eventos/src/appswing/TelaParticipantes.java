package appswing;



import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Convidado;
import modelo.Evento;
import modelo.Ingresso;
import modelo.Participante;
import regras_negocio.Fachada;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

public class TelaParticipantes {

	
	
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
	public TelaParticipantes() {
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
		
		frame.setTitle("Participante");
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
			private static final long serialVersionUID = 3864566080405659451L;

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

		
		JLabel lblNewLabel = new JLabel("CPF");
		lblNewLabel.setBounds(25, 247, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nascimento");
		lblNewLabel_1.setBounds(178, 247, 70, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Empresa");
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

			            String cpf  = textField.getText().trim();
			            String nascimento = textField_1.getText().trim();
			            String empresa = textField_2.getText().trim();
			           

			            // Verifica se a string de data não está vazia
			            if (nascimento.isEmpty()) {
			                throw new Exception("Campo de data vazio");
			            }
			            
			            if (!validarFormatoData(nascimento)) {
			                throw new Exception("Formato de data inválido. Use o formato DD/MM/YYYY.");
			            }

			          
			            if (Fachada.participanteExiste(cpf)) {
			                throw new Exception("Já existe um participante com o CPF informado.");
			            }

			            if (!empresa.isEmpty()) {
			                Fachada.criarConvidado(cpf, nascimento, empresa);
			            	lblNewLabel_3.setText("Convidado criado: " + cpf);
			            } else {
			                // Se a empresa estiver vazia, cria um participante regular
			                Fachada.criarParticipante(cpf, nascimento);
			            	lblNewLabel_3.setText("Participante criado: " + cpf);
			            }
			            listagem();
			        } catch (NumberFormatException ex) {
			        	lblNewLabel_3.setText("Erro de formato numérico: " + ex.getMessage());
			        } catch (Exception ex) {
			        	lblNewLabel_3.setText("Erro: " + ex.getMessage());
			        }
			    }
			    private boolean validarFormatoData(String nascimento) {
			        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			        dateFormat.setLenient(false); // Não permite datas inválidas

			        try {
			            // Tenta fazer o parsing da data
			            Date parsedDate = dateFormat.parse(nascimento);
			            return true; // Se não ocorrer exceção, a data é válida
			        } catch (ParseException e) {
			            return false; // Se ocorrer exceção, a data é inválida
			        }
					
				
			}
				 
		});
		btnNewButton.setBounds(390, 258, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Listar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(503, 29, 130, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Apagar");
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (table.getSelectedRow() >= 0) {
		                String cpf = (String) table.getValueAt(table.getSelectedRow(), 0);
		                
		                // Confirmação
		                Object[] options = { "Confirmar", "Cancelar" };
		                int escolha = JOptionPane.showOptionDialog(null, "Confirma exclusão do participante com CPF: " + cpf, "Alerta",
		                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		                
		                if (escolha == 0) {
		                    Fachada.apagarParticipante(cpf); // Chame o método de apagar participante da sua Fachada
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
		
		JButton btnNewButton_3 = new JButton("Ingressos");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{try {
			    // Verifica se uma linha está selecionada
			    if (table.getSelectedRow() >= 0) {
			        // Obtém o evento selecionado na tabela
			        Participante  participanteSelecionado = getParticipanteSelecionado();

			        // Verifica se o evento foi encontrado
			        if (participanteSelecionado  != null) {
			            // Obtém a lista de ingressos do evento selecionado
			            ArrayList<Ingresso> ingressos = participanteSelecionado.getIngressos();

			            // Verifica se há ingressos associados ao evento
			            if (!ingressos.isEmpty()) {
			                // Monta a mensagem com os ingressos
			                StringBuilder mensagem = new StringBuilder("Ingressos do evento " + participanteSelecionado .getCpf() + ":\n");
			                for (Ingresso ing : ingressos) {
			                    mensagem.append("Código: ").append(ing.getCodigo()).append(", Telefone: ").append(ing.getTelefone()).append("\n");
			                }

	                        // Exibe a mensagem em um JOptionPane.showOptionDialog
	                        JOptionPane.showOptionDialog(null, mensagem.toString(), "Ingressos do Participante", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Não há ingressos associados a este Participante.", "Ingressos do Paticipante", JOptionPane.INFORMATION_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Participante não encontrado.", "Ingressos do Participante", JOptionPane.INFORMATION_MESSAGE);
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Selecione uma linha antes de listar os ingressos.", "Ingressos do Participante", JOptionPane.INFORMATION_MESSAGE);
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	
	
		
		
		private Participante getParticipanteSelecionado() {
		    // Obtém o ID do evento selecionado
		    String cpfParticipante = (String) table.getValueAt(table.getSelectedRow(), 0);

		    // Busca o evento na lista de eventos
		    for (Participante p: Fachada.listarParticipantes()) {
		        if (p.getCpf() == cpfParticipante) {
		            return p;
		        }
		    }

		    return null; // Retorna null se o evento não for encontrado
		}

	});
		btnNewButton_3.setBounds(503, 124, 130, 23);
		frame.getContentPane().add(btnNewButton_3);
		
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
				ArrayList<Participante> lista = Fachada.listarParticipantes();

				//objeto model contem todas as linhas e colunas da tabela
				DefaultTableModel model = new DefaultTableModel();

				//criar as colunas (0,1,2) da tabela
				model.addColumn("cpf");
				model.addColumn("nascmento");
				model.addColumn("Idade");
				model.addColumn("empresa");
	
				
				//criar as linhas da tabela
				String texto;
				for (Participante p : lista) {
		            if (p instanceof Convidado) {
		                // Se o participante for um Convidado, exibe a empresa
		                Convidado convidado = (Convidado) p;
		                model.addRow(new Object[]{p.getCpf(), p.getNascimento(), p.calcularIdade(), convidado.getEmpresa()});
		            } else {
		                // Se o participante não for um Convidado, não exibe a empresa
		                model.addRow(new Object[]{p.getCpf(), p.getNascimento(), p.calcularIdade(), ""});
		            }
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
