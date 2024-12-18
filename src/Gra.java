import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gra extends JFrame implements Serializable{
    private JTable plansza;
    private Pacman pacman;
    private int czas=300;
    private int c;
    private int px;
    private int py;
    private List<Duch> listaDuchow = new ArrayList<>();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private int counter=0;
    private JFrame cpz = new JFrame("Czas/Punkty/Zycie");
    private int losCzy=0;
    private boolean stopDuszki;
    public Gra(Plansza model, Pacman pacman) {
        this.plansza = new JTable();
        this.pacman = pacman;
        plansza.setModel(model);
        setLayout(new BorderLayout());
        add(new JScrollPane(plansza));
        setBackground(Color.BLACK);
        plansza.setCellSelectionEnabled(false);
        plansza.setTableHeader(null);
        pack();
        setSize(plansza.getRowCount()*20, plansza.getColumnCount()*20);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
        generowanieDuchow();
        pozycjaPacman(pacman);
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value != null && value.equals(0)) {
                    rendererComponent.setBackground(Color.BLUE);
                    rendererComponent.setForeground(Color.BLUE);
                    super.setIcon(null);
                } else if (value != null && value.equals(pacman)) {
                    if (counter%2==1){
                        ImageIcon imageIcon = new ImageIcon(pacman.getIcon().getImage());
                        Image scaled = imageIcon.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
                        super.setIcon(new ImageIcon(scaled));
                    }else {
                        ImageIcon imageIcon = new ImageIcon("src/pacman.png");
                        Image scaled = imageIcon.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
                        super.setIcon(new ImageIcon(scaled));
                    }
                    rendererComponent.setForeground(Color.BLACK);
                    rendererComponent.setBackground(Color.BLACK);
                } else if (value != null && value.equals(1)) {
                    rendererComponent.setBackground(Color.BLACK);
                    rendererComponent.setForeground(Color.BLACK);
                    super.setIcon(null);
                } else if (value != null && value.equals(2)) {
                    rendererComponent.setBackground(Color.BLACK);
                    super.setIcon(null);
                    setText("o");
                    rendererComponent.setForeground(Color.YELLOW);
                }else if (value != null && value instanceof Duch){
                    rendererComponent.setBackground(Color.BLACK);
                    ImageIcon imageIcon = new ImageIcon("src/ghostt.png");
                    Image scaled = imageIcon.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
                    super.setIcon(new ImageIcon(scaled));
                    rendererComponent.setForeground(Color.BLACK);
                } else{
                    ImageIcon imageIcon = new ImageIcon("src/cherry.png");
                    Image scaled = imageIcon.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
                    super.setIcon(new ImageIcon(scaled));
                    rendererComponent.setBackground(Color.BLACK);
                }

                return rendererComponent;
            }
        };
        plansza.setDefaultRenderer(Object.class, defaultTableCellRenderer);
        plansza.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'w') {
                    if (!plansza.getValueAt(pacman.getY() - 1, pacman.getX()).equals(0)) {
                        pacman.setIcon(new ImageIcon("src/pacman_up.png"));
                        Object pom = plansza.getValueAt(pacman.getY() - 1, pacman.getX());
                        if (pom instanceof Duch) {
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            plansza.setValueAt(pacman, py, px);
                            pacman.setX(px);
                            pacman.setY(py);
                            pacman.odejmijZycie();
                            plansza.repaint();
                        } else if (pom.equals(1) || pom.equals(2)) {
                            if (plansza.getValueAt(pacman.getY() - 1, pacman.getX()).equals(2)) {
                                pacman.dodajPunkty();
                            }
                            plansza.setValueAt(pacman, pacman.getY() - 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() - 1);
                            plansza.repaint();
                        } else if (pom.equals(3) || pom.equals(4) || pom.equals(5) || pom.equals(6) || pom.equals(7) || pom.equals(8) || pom.equals(9) || pom.equals(10) || pom.equals(11) || pom.equals(12) || pom.equals(21) || pom.equals(22)) {
                            pacman.dodajPunkty20();
                            plansza.setValueAt(pacman, pacman.getY() - 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() - 1);
                            plansza.repaint();
                        } else if (pom.equals(13)) {
                            pacman.setZycia(0);
                            plansza.setValueAt(pacman, pacman.getY() - 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() - 1);
                            plansza.repaint();
                        } else if (pom.equals(14)) {
                            listaDuchow=null;
                            plansza.setValueAt(pacman, pacman.getY() - 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() - 1);
                            plansza.repaint();
                        } else if (pom.equals(15) || pom.equals(16) || pom.equals(17)) {
                            pacman.dodajZycie();
                            plansza.setValueAt(pacman, pacman.getY() - 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() - 1);
                            plansza.repaint();
                        } else if (pom.equals(18) || pom.equals(19) || pom.equals(20) || pom.equals(23)) {
                            stopDuszki=true;
                            plansza.setValueAt(pacman, pacman.getY() - 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() - 1);
                            plansza.repaint();
                        }
                    }
                }
                if (e.getKeyChar() == 'a') {
                    if (!plansza.getValueAt(pacman.getY(), pacman.getX() - 1).equals(0)) {
                        pacman.setIcon(new ImageIcon("src/pacman_left.png"));
                        Object pom = plansza.getValueAt(pacman.getY(), pacman.getX() - 1);
                        if (pom instanceof Duch) {
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            plansza.setValueAt(pacman, py, px);
                            pacman.setX(px);
                            pacman.setY(py);
                            pacman.odejmijZycie();
                            plansza.repaint();
                        } else if (pom.equals(1) || pom.equals(2)) {
                            if (plansza.getValueAt(pacman.getY(), pacman.getX() - 1).equals(2)) {
                                pacman.dodajPunkty();
                            }
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() - 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() - 1);
                            plansza.repaint();
                        } else if (pom.equals(3) || pom.equals(4) || pom.equals(5) || pom.equals(6) || pom.equals(7) || pom.equals(8) || pom.equals(9) || pom.equals(10) || pom.equals(11) || pom.equals(12) || pom.equals(21) || pom.equals(22)) {
                            pacman.dodajPunkty20();
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() - 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() - 1);
                            plansza.repaint();
                        } else if (pom.equals(13)) {
                            pacman.setZycia(0);
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() - 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() - 1);
                            plansza.repaint();
                        } else if (pom.equals(14)) {
                            listaDuchow=null;
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() - 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() - 1);
                            plansza.repaint();
                        } else if (pom.equals(15) || pom.equals(16) || pom.equals(17)) {
                            pacman.dodajZycie();
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() - 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() - 1);
                            plansza.repaint();
                        } else if (pom.equals(18) || pom.equals(19) || pom.equals(20) || pom.equals(23)) {
                            stopDuszki=true;
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() - 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() - 1);
                            plansza.repaint();
                        }
                    }
                }
                if (e.getKeyChar() == 's') {
                    if (!plansza.getValueAt(pacman.getY() + 1, pacman.getX()).equals(0)) {
                        pacman.setIcon(new ImageIcon("src/pacman_down.png"));
                        Object pom = plansza.getValueAt(pacman.getY() + 1, pacman.getX());
                        if (pom instanceof Duch) {
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            plansza.setValueAt(pacman, py, px);
                            pacman.setX(px);
                            pacman.setY(py);
                            pacman.odejmijZycie();
                            plansza.repaint();
                        } else if (pom.equals(1) || pom.equals(2)) {
                            if (plansza.getValueAt(pacman.getY() + 1, pacman.getX()).equals(2)) {
                                pacman.dodajPunkty();
                            }
                            plansza.setValueAt(pacman, pacman.getY() + 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() + 1);
                            plansza.repaint();
                        } else if (pom.equals(3) || pom.equals(4) || pom.equals(5) || pom.equals(6) || pom.equals(7) || pom.equals(8) || pom.equals(9) || pom.equals(10) || pom.equals(11) || pom.equals(12) || pom.equals(21) || pom.equals(22)) {
                            pacman.dodajPunkty20();
                            plansza.setValueAt(pacman, pacman.getY() + 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() + 1);
                            plansza.repaint();
                        } else if (pom.equals(13)) {
                            pacman.setZycia(0);
                            plansza.setValueAt(pacman, pacman.getY() + 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() + 1);
                            plansza.repaint();
                        } else if (pom.equals(14)) {
                            listaDuchow=null;
                            plansza.setValueAt(pacman, pacman.getY() + 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() + 1);
                            plansza.repaint();
                        } else if (pom.equals(15) || pom.equals(16) || pom.equals(17)) {
                            pacman.dodajZycie();
                            plansza.setValueAt(pacman, pacman.getY() + 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() + 1);
                            plansza.repaint();
                        } else if (pom.equals(18) || pom.equals(19) || pom.equals(20) || pom.equals(23)) {
                            stopDuszki=true;
                            plansza.setValueAt(pacman, pacman.getY() + 1, pacman.getX());
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setY(pacman.getY() + 1);
                            plansza.repaint();
                        }
                    }
                }
                if (e.getKeyChar() == 'd') {
                    if (!plansza.getValueAt(pacman.getY(), pacman.getX() + 1).equals(0)) {
                        pacman.setIcon(new ImageIcon("src/pacman_right.png"));
                        Object pom = plansza.getValueAt(pacman.getY(), pacman.getX() + 1);
                        if (pom instanceof Duch) {
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            plansza.setValueAt(pacman, py, px);
                            pacman.setX(px);
                            pacman.setY(py);
                            pacman.odejmijZycie();
                            plansza.repaint();
                        } else if (pom.equals(1) || pom.equals(2)) {
                            if (plansza.getValueAt(pacman.getY(), pacman.getX() + 1).equals(2)) {
                                pacman.dodajPunkty();
                            }
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() + 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() + 1);
                            plansza.repaint();
                        } else if (pom.equals(3) || pom.equals(4) || pom.equals(5) || pom.equals(6) || pom.equals(7) || pom.equals(8) || pom.equals(9) || pom.equals(10) || pom.equals(11) || pom.equals(12) || pom.equals(21) || pom.equals(22)) {
                            pacman.dodajPunkty20();
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() + 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() + 1);
                            plansza.repaint();
                        } else if (pom.equals(13)) {
                            pacman.setZycia(0);
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() + 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() + 1);
                            plansza.repaint();
                        } else if (pom.equals(14)) {
                            listaDuchow=null;
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() + 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() + 1);
                            plansza.repaint();
                        } else if (pom.equals(15) || pom.equals(16) || pom.equals(17)) {
                            pacman.dodajZycie();
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() + 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() + 1);
                            plansza.repaint();
                        } else if (pom.equals(18) || pom.equals(19) || pom.equals(20) || pom.equals(23)) {
                            listaDuchow=null;
                            plansza.setValueAt(pacman, pacman.getY(), pacman.getX() + 1);
                            plansza.setValueAt(1, pacman.getY(), pacman.getX());
                            pacman.setX(pacman.getX() + 1);
                            plansza.repaint();
                        }
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        plansza.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()=='q'|| e.getKeyChar()=='Q'){
                    c=e.getKeyChar();
                }
                if (((c=='q' && e.isShiftDown())||(c=='Q')) && e.isControlDown()){
                    Thread.currentThread().interrupt();
                    Menu menu = new Menu();
                    dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        oknoCPZ();
        animacja();
        uruchomDuszki();
        runUlepszenie();
        koniecGry();
    }

    public void animacja(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    if (pacman.getZycia()==0 || czas==0) Thread.currentThread().interrupt();
                    counter++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    public void koniecGry(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    if (pacman.getZycia()==0 || czas==0){
                        JOptionPane.showMessageDialog(null, "Koniec gry");
                        dispose();
                        cpz.dispose();
                        Thread.currentThread().interrupt();
                        JFrame jFrame = new JFrame();
                        jFrame.setLayout(new FlowLayout());
                        JLabel jLabel = new JLabel("Podaj nick");
                        jFrame.add(jLabel);
                        JTextField jTextField = new JTextField(15);
                        jFrame.add(jTextField);
                        JButton b = new JButton("Ok");
                        jFrame.add(b);
                        b.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                pacman.setNazwa(jTextField.getText());
                                try (FileOutputStream fileOutputStream = new FileOutputStream(new File("ranking.txt"))) {
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                                    ArrayList<Pacman> listaPacmanow = Pacman.getPacmany();
                                    listaPacmanow.add(pacman);
                                    objectOutputStream.writeObject(listaPacmanow);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                jFrame.dispose();
                                Menu menu = new Menu();
                            }
                        });
                        jFrame.setSize(250, 100);
                        jFrame.setLocationRelativeTo(null);
                        jFrame.setVisible(true);
                    }
                }
            }
        });
        thread.start();
    }
    public void oknoCPZ(){
        cpz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cpz.setLayout(new FlowLayout());
        jLabel1.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel2.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel3.setFont(new Font("Arial", Font.BOLD, 14));
        cpz.add(jLabel1);
        cpz.add(jLabel2);
        cpz.add(jLabel3);
        pkt();
        czas();
        hp();
        cpz.setAlwaysOnTop(true);
        cpz.setSize(200, 100);
        cpz.setVisible(true);
        plansza.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()=='q'|| e.getKeyChar()=='Q'){
                    c=e.getKeyChar();
                }
                if (((c=='q' && e.isShiftDown())||(c=='Q')) && e.isControlDown()){
                    Thread.currentThread().interrupt();
                    cpz.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    public void hp(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()&&pacman.getZycia()>=0){
                    jLabel3.setText("Hp: "+pacman.getZycia());
                    if (pacman.getZycia()==0 || czas==0){
                        Thread.currentThread().interrupt();
                        jLabel3.setText("Hp: "+pacman.getZycia());
                    }
                }
            }
        });

        thread.start();
    }
    public void pkt(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    if (pacman.getZycia()==0 || czas==0) Thread.currentThread().interrupt();
                    jLabel2.setText("Pkt: "+pacman.getPunkty());
                }
            }
        });
        thread.start();
    }
    public void czas(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()&& czas>=0){
                    if (pacman.getZycia()==0 || czas==0){
                        Thread.currentThread().interrupt();
                        jLabel1.setText("Czas: "+czas);
                    }
                    jLabel1.setText("Czas: "+czas);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    czas--;
                }
            }
        });
        thread.start();
    }
    public void uruchomDuszki(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                duszki();
            }
        });
        thread.start();
    }
    public void runUlepszenie(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ulepszenie();
            }
        });
        thread.start();
    }
    public void ulepszenie(){
        while (!Thread.interrupted()){
            if (pacman.getZycia()==0 || czas==0) Thread.currentThread().interrupt();
            if (listaDuchow.size()==0) Thread.currentThread().interrupt();
            losCzy=(int)(Math.random()*4)+1;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void duszki(){
        Object nast;
        Object pop=2;
        while (!Thread.interrupted()){
            if (pacman.getZycia()==0 || czas==0) Thread.currentThread().interrupt();
            if (listaDuchow.size()==0) Thread.currentThread().interrupt();
            if (stopDuszki) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                stopDuszki=false;
            }
            for (Duch duch:listaDuchow){
                int rand = (int)(Math.random()*4+1);
                switch (rand){
                    case 1:
                        if (!plansza.getValueAt(duch.getY()-1, duch.getX()).equals(0) && !(plansza.getValueAt(duch.getY()-1, duch.getX()) instanceof Duch)){
                            if (plansza.getValueAt(duch.getY()-1, duch.getX()).equals(pacman)){
                                plansza.setValueAt(duch, duch.getY() - 1, duch.getX());
                                plansza.setValueAt(1, duch.getY(), duch.getX());
                                duch.setY(duch.getY() - 1);
                                plansza.setValueAt(pacman, py, px);
                                pacman.setX(px);
                                pacman.setY(py);
                                pacman.odejmijZycie();
                                plansza.repaint();
                            }else {
                                if (losCzy==1){
                                    plansza.setValueAt(duch, duch.getY() - 1, duch.getX());
                                    plansza.setValueAt((int)(Math.random()*15+3), duch.getY(), duch.getX());
                                    losCzy=0;
                                }else {
                                    plansza.setValueAt(duch, duch.getY() - 1, duch.getX());
                                    plansza.setValueAt(2, duch.getY(), duch.getX());
                                }
                                duch.setY(duch.getY() - 1);
                                plansza.repaint();
                            }
                        }
                        break;
                    case 2:
                        if (!plansza.getValueAt(duch.getY(), duch.getX() - 1).equals(0) && !(plansza.getValueAt(duch.getY(), duch.getX()-1) instanceof Duch)){
                            if (plansza.getValueAt(duch.getY(), duch.getX() - 1).equals(pacman)){
                                plansza.setValueAt(duch, duch.getY(), duch.getX() - 1);
                                plansza.setValueAt(1, duch.getY(), duch.getX());
                                duch.setX(duch.getX() - 1);
                                plansza.setValueAt(pacman, py, px);
                                pacman.setX(px);
                                pacman.setY(py);
                                pacman.odejmijZycie();
                                plansza.repaint();
                            }else {
                                if (losCzy==1){
                                    plansza.setValueAt(duch, duch.getY(), duch.getX() - 1);
                                    plansza.setValueAt((int)(Math.random()*15+3), duch.getY(), duch.getX());
                                    losCzy=0;
                                }else {
                                    plansza.setValueAt(duch, duch.getY(), duch.getX() - 1);
                                    plansza.setValueAt(2, duch.getY(), duch.getX());
                                }
                                duch.setX(duch.getX() - 1);
                                plansza.repaint();
                            }
                        }
                        break;
                    case 3:
                        if (!plansza.getValueAt(duch.getY() + 1, duch.getX()).equals(0) && !(plansza.getValueAt(duch.getY()+1, duch.getX()) instanceof Duch)){
                            if (plansza.getValueAt(duch.getY() + 1, duch.getX()).equals(pacman)){
                                plansza.setValueAt(duch, duch.getY() + 1, duch.getX());
                                plansza.setValueAt(1, duch.getY(), duch.getX());
                                duch.setY(duch.getY() + 1);
                                plansza.setValueAt(pacman, py, px);
                                pacman.setX(px);
                                pacman.setY(py);
                                pacman.odejmijZycie();
                                plansza.repaint();
                            } else {
                                if (losCzy==1){
                                    plansza.setValueAt(duch, duch.getY() + 1, duch.getX());
                                    plansza.setValueAt((int)(Math.random()*15+3), duch.getY(), duch.getX());
                                    losCzy=0;
                                }else {
                                    plansza.setValueAt(duch, duch.getY() + 1, duch.getX());
                                    plansza.setValueAt(2, duch.getY(), duch.getX());
                                }
                                duch.setY(duch.getY() + 1);
                                plansza.repaint();
                            }
                        }
                        break;
                    case 4:
                        if (!plansza.getValueAt(duch.getY(), duch.getX()+1).equals(0) && !(plansza.getValueAt(duch.getY(), duch.getX()+1) instanceof Duch)){
                            if (plansza.getValueAt(duch.getY(), duch.getX()+1).equals(pacman)){
                                plansza.setValueAt(duch, duch.getY(), duch.getX()+1);
                                plansza.setValueAt(1, duch.getY(), duch.getX());
                                duch.setX(duch.getX() + 1);
                                plansza.setValueAt(pacman, py, px);
                                pacman.setX(px);
                                pacman.setY(py);
                                pacman.odejmijZycie();
                                plansza.repaint();
                            } else {
                                if (losCzy==1){
                                    losCzy=0;
                                    plansza.setValueAt(duch, duch.getY(), duch.getX()+1);
                                    plansza.setValueAt((int)(Math.random()*15+3), duch.getY(), duch.getX());
                                }else {
                                    plansza.setValueAt(duch, duch.getY(), duch.getX()+1);
                                    plansza.setValueAt(2, duch.getY(), duch.getX());
                                }
                                duch.setX(duch.getX() + 1);
                                plansza.repaint();
                            }
                        }
                        break;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void pozycjaPacman(Pacman pacman){
        do {
            py=(int)(Math.random()*plansza.getRowCount()-1);
            px=(int)(Math.random()*plansza.getColumnCount()-1);
        }while (plansza.getValueAt(py,px).equals(0) || plansza.getValueAt(py,px).equals(Duch.class) || plansza.getValueAt(px,py).equals(0) || px==0 || py==0);
        plansza.setValueAt(pacman, py,px);
        pacman.setX(px);
        pacman.setY(py);
    }
    public void generowanieDuchow(){
        for (int i=0; i<4; i++){
            Duch duch = new Duch();
            listaDuchow.add(duch);
        }
        for (int i=0; i<listaDuchow.size(); i++){
            int x;
            int y;
            do {
                x=(int)(Math.random()*plansza.getRowCount()-1);
                y=(int)(Math.random()*plansza.getColumnCount()-1);
            }while (plansza.getValueAt(x,y).equals(0) || plansza.getValueAt(x,y).equals(Duch.class) || plansza.getValueAt(y,x).equals(0) || x==0 || y==0);
            plansza.setValueAt(listaDuchow.get(i), y, x);
            listaDuchow.get(i).setX(x);
            listaDuchow.get(i).setY(y);
        }
    }
}