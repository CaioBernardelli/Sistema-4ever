package appswing;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Evento;
import modelo.Ingresso;
import regras_negocio.Fachada;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Button;



public class TelaEvento {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_2;
	private JLabel lblData;
	private JButton button_1;
	private JButton button;
	private JButton button_2;
	private JButton button_3 ;
	private Label label_3;
	private Label label_4;
	private JTextField textField_1;
    private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;


	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaReuniao window = new TelaReuniao();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaEvento() {
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
		frame.setTitle("Evento");
		frame.setBounds(100, 100, 659, 362);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 431, 207);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2937032745322099178L;

			/**
			 * 
			 */
		
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		

		TextField textField_1 = new TextField();
		textField_1.setBounds(315, 229, 134, 22);
		frame.getContentPane().add(textField_1);
		
		TextField textField_2 = new TextField();
		textField_2.setBounds(315, 267, 137, 22);
		frame.getContentPane().add(textField_2);
		
		TextField textField_3 = new TextField();
		textField_3.setBounds(105, 267, 131, 22);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(102, 235, 134, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		  
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) 
					label_2.setText("selecionado="+ table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(35, 298, 587, 14);
		frame.getContentPane().add(label);

		label_2 = new JLabel("selecione");
		label_2.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(label_2);

		lblData = new JLabel(" data");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblData.setBounds(21, 237, 71, 14);
		frame.getContentPane().add(lblData);

		

		button_1 = new JButton("Criar");
		button_1.setToolTipText("");
		button_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            // Verifica se os campos de texto estão vazios
		            if (textField_1.getText().isEmpty() || textField_2.getText().isEmpty() ||
		                textField_3.getText().isEmpty() || textField_4.getText().isEmpty()) {
		                label.setText("Preencha todos os campos");
		                return;
		            }

		            String descricao = textField_3.getText().trim();
		            String capacidadeStr = textField_1.getText().trim();
		            String precoStr = textField_2.getText().trim();
		            String data = textField_4.getText().trim();

		            // Verifica se a string de data não está vazia
		            if (data.isEmpty()) {
		                throw new Exception("Campo de data vazio");
		            }
		            
		            if (!validarFormatoData(data)) {
		                throw new Exception("Formato de data inválido. Use o formato DD/MM/YYYY.");
		            }

		            // Tentativa de conversão para Integer (capacidade)
		            int capacidade = Integer.parseInt(capacidadeStr);

		            // Tentativa de conversão para Double (preço)
		            double preco = Double.parseDouble(precoStr);

		            // Verifica se a capacidade é um valor válido
		            if (capacidade <= 0) {
		                throw new Exception("Capacidade inválida. Deve ser maior que zero.");
		            }

		            // Outras verificações e chamadas de método, se necessário...

		            Fachada.criarEvento(data, descricao, capacidade, preco);
		            label.setText("Evento criado: " + descricao);
		            listagem();
		        } catch (NumberFormatException ex) {
		            label.setText("Erro de formato numérico: " + ex.getMessage());
		        } catch (Exception ex) {
		            label.setText("Erro: " + ex.getMessage());
		        }
		    }
		    private boolean validarFormatoData(String data) {
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        dateFormat.setLenient(false); // Não permite datas inválidas

		        try {
		            // Tenta fazer o parsing da data
		            Date parsedDate = dateFormat.parse(data);
		            return true; // Se não ocorrer exceção, a data é válida
		        } catch (ParseException e) {
		            return false; // Se ocorrer exceção, a data é inválida
		        }
		    }

		});


		
		
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(469, 249, 86, 23);
		frame.getContentPane().add(button_1);

		button = new JButton("Apagar Selecionado");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						Integer id = (Integer) table.getValueAt( table.getSelectedRow(), 0);
						//confirma��o
						Object[] options = { "Confirmar", "Cancelar" };
						int escolha = JOptionPane.showOptionDialog(null, "Confirma exclus�o: "+id, "Alerta",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if(escolha == 0) {
							Fachada.apagarEvento(id);
							label.setText("exclus�o realizada");
							listagem();
						}
						else
							label.setText("exclusao cancelada");
					}
					else
						label.setText("selecione uma linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(462, 98, 160, 23);
		frame.getContentPane().add(button);

		button_2 = new JButton("Listar");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(462, 57, 160, 23);
		frame.getContentPane().add(button_2);
		
	
		Label label_1 = new Label("descrição");
		label_1.setFont(new Font("Georgia", Font.PLAIN, 12));
		label_1.setBounds(35, 267, 62, 22);
		frame.getContentPane().add(label_1);
		
		label_3 = new Label("capacidade");
		label_3.setBounds(242, 229, 62, 22);
		frame.getContentPane().add(label_3);
		
		label_4 = new Label("preço");
		label_4.setBounds(242, 267, 62, 22);
		frame.getContentPane().add(label_4);
		
		Button button_3 = new Button("Ingressos");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            // Verifica se uma linha está selecionada
			            if (table.getSelectedRow() >= 0) {
			                // Obtém o evento selecionado na tabela
			                Evento eventoSelecionado = getEventoSelecionado();

			                // Verifica se o evento foi encontrado
			                if (eventoSelecionado != null) {
			                    // Obtém a lista de ingressos do evento selecionado
			                    ArrayList<Ingresso> ingressos = eventoSelecionado.getIngressos();

			                    // Verifica se há ingressos associados ao evento
			                    if (!ingressos.isEmpty()) {
			                        // Monta a mensagem com os ingressos
			                        StringBuilder mensagem = new StringBuilder("Ingressos do evento " + eventoSelecionado.getId() + ":\n");
			                        for (Ingresso ing : ingressos) {
			                            mensagem.append("Código: ").append(ing.getCodigo()).append(", Telefone: ").append(ing.getTelefone()).append("\n");
			                        }

			                        // Exibe a mensagem em um JOptionPane.showOptionDialog
			                        JOptionPane.showOptionDialog(null, mensagem.toString(), "Ingressos do Evento", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
			                    } else {
			                        JOptionPane.showMessageDialog(null, "Não há ingressos associados a este evento.", "Ingressos do Evento", JOptionPane.INFORMATION_MESSAGE);
			                    }
			                } else {
			                    JOptionPane.showMessageDialog(null, "Evento não encontrado.", "Ingressos do Evento", JOptionPane.INFORMATION_MESSAGE);
			                }
			            } else {
			                JOptionPane.showMessageDialog(null, "Selecione uma linha antes de listar os ingressos.", "Ingressos do Evento", JOptionPane.INFORMATION_MESSAGE);
			            }
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			        }
			    }

		
		
			
			
			private Evento getEventoSelecionado() {
			    // Obtém o ID do evento selecionado
			    Integer idEvento = (Integer) table.getValueAt(table.getSelectedRow(), 0);

			    // Busca o evento na lista de eventos
			    for (Evento ev : Fachada.listarEventos()) {
			        if (ev.getId() == idEvento) {
			            return ev;
			        }
			    }

			    return null; // Retorna null se o evento não for encontrado
			}

		});
	
		button_3.setBounds(467, 144, 155, 22);
		frame.getContentPane().add(button_3);
		
		


	}

	public void listagem() {
		try{
			ArrayList<Evento> lista = Fachada.listarEventos();

			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2) da tabela
			model.addColumn("Id");
			model.addColumn("Data");
			model.addColumn("Capaticade");
			model.addColumn("Quantidade de Ingressos");
			model.addColumn("Total Arrecadado");
			model.addColumn("Lotado");
			
			//criar as linhas da tabela
			String texto;
			for(Evento ev : lista) {
				if(ev.quantidadeIngressos()==0)
					model.addRow(new Object[]{ev.getId(), ev.getData(), ev.getCapacidade(),"sem ingressos,",ev.totalArrecadado(),ev.lotado()});
				else {
					model.addRow(new Object[]{ev.getId(), ev.getData(), ev.getCapacidade(),ev.quantidadeIngressos(),ev.totalArrecadado(),ev.lotado()});
					}
					
			}
			table.setModel(model);
			label_2.setText("resultados: "+lista.size()+ " linhas   - selecione uma linha");

			//redimensionar a coluna 2
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 		//desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(50);	
			table.getColumnModel().getColumn(1).setMaxWidth(100);	
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}

