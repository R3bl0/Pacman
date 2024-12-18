import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Menu extends JFrame implements ActionListener {

    private JButton nowa_gra = new JButton("Nowa Gra");
    private JButton ranking = new JButton("Ranking");
    private JButton wyjscie = new JButton("Wyjście");
    private int x;
    private int y;

    public Menu() {
        super("PACMAN");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        jPanel.setBackground(Color.BLACK);
        nowa_gra.setBackground(Color.WHITE);
        ranking.setBackground(Color.WHITE);
        wyjscie.setBackground(Color.WHITE);
        nowa_gra.setBorderPainted(true);
        nowa_gra.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        ranking.setBorderPainted(true);
        ranking.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        wyjscie.setBorderPainted(true);
        wyjscie.setBorder(BorderFactory.createLineBorder(Color.RED));
        setResizable(false);
        jPanel.add(nowa_gra);
        jPanel.add(ranking);
        jPanel.add(wyjscie);

        wyjscie.addActionListener(this);
        ranking.addActionListener(this);
        nowa_gra.addActionListener(this);

        add(jPanel);
        setSize(300, 80);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wyjscie) {
            System.exit(0);
        }
        if (e.getSource() == ranking) {
            JFrame jFrame = new JFrame();
            jFrame.setTitle("RANKING");
            jFrame.setSize(300, 200);
            jFrame.setResizable(false);
            jFrame.setLocationRelativeTo(null);
            JList<String> jList = new JList<>();
            DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            jList.setModel(defaultListModel);
            jFrame.add(new JScrollPane(jList));
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("ranking.txt"))) {
                ArrayList<Pacman> arrayList = (ArrayList<Pacman>) inputStream.readObject();
                arrayList.sort(new Comparator<Pacman>() {
                    @Override
                    public int compare(Pacman o1, Pacman o2) {
                        return Integer.compare(o2.getPunkty(), o1.getPunkty());
                    }
                });
                for (int i=0; i<arrayList.size(); i++){
                    defaultListModel.addElement(String.valueOf(arrayList.get(i)));
                }
            } catch (ClassNotFoundException | IOException ex) {
                if (!(ex instanceof EOFException)) ex.printStackTrace();
            }
            jFrame.setVisible(true);
        }
        if (e.getSource() == nowa_gra) {
            gra();
        }
    }


    public void gra() {
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        jFrame.setSize(250, 80);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jPanel.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel("Podaj x");
        jLabel.setForeground(Color.WHITE);
        jPanel.add(jLabel);
        JTextField jTextField = new JTextField(3);
        jPanel.add(jTextField);
        JLabel jLabel2 = new JLabel("Podaj y");
        jLabel2.setForeground(Color.WHITE);
        jPanel.add(jLabel2);
        JTextField jTextField2 = new JTextField(3);
        jPanel.add(jTextField2);
        JButton b = new JButton("Ok");
        jPanel.add(b);
        jPanel.setBackground(Color.BLACK);
        b.setBorderPainted(true);
        b.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        b.addActionListener(e -> {
            if (Integer.parseInt(jTextField.getText())>=10 && Integer.parseInt(jTextField2.getText())>=10 && Integer.parseInt(jTextField.getText())<=100 && Integer.parseInt(jTextField2.getText())<=100){
                x = Integer.parseInt(jTextField.getText());
                y = Integer.parseInt(jTextField2.getText());
                Pacman gracz = new Pacman();
                Plansza plansza = new Plansza(x,y);
                Gra gra= new Gra(plansza, gracz);
                jFrame.dispose();
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Złe wartości podaj jeszcze raz");
            }
        });
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }
}
