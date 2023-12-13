package appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class TelaPrincipal {
    private JFrame frame;
    private JMenu mnEventos;
    private JMenu mnParticipantes;
    private JMenu mnIngressos;
    private JLabel label;
    private JMenu menu;
    private JMenu menu_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal window = new TelaPrincipal();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TelaPrincipal() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Sistema 4ever");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        label = new JLabel("");
        label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("Inicializando...");
        label.setBounds(0, 0, 434, 249);
        ImageIcon imagem = new ImageIcon(getClass().getResource("/arquivo/titulo.png"));
        imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        label.setIcon(imagem);
        frame.getContentPane().add(label);
        frame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        mnEventos = new JMenu("Eventos");
        mnEventos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Adicione aqui o código para exibir a tela de eventos
                TelaEvento tela = new TelaEvento();
            }
        });
        menuBar.add(mnEventos);

        mnParticipantes = new JMenu("Participantes");
        mnParticipantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Adicione aqui o código para exibir a tela de participantes
                TelaParticipantes tela = new TelaParticipantes();
            }
        });
        menuBar.add(mnParticipantes);

        mnIngressos = new JMenu("Ingressos");
        mnIngressos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Adicione aqui o código para exibir a tela de ingressos
                TelaIngressos tela = new TelaIngressos();
            }
        });
        menuBar.add(mnIngressos);

        menu = new JMenu("Sobre");
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "IFPB - TSI \nPOO \nCaio e Gabriel", "Sobre o autor", 1);
            }
        });
        menuBar.add(menu);

        menu_1 = new JMenu("Sair");
        menu_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        menuBar.add(menu_1);
    }
}
