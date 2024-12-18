import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Pacman implements Serializable {
    private String nazwa;
    private int punkty=0;
    private int zycia=3;
    private int x;
    private int y;
    private ImageIcon icon;

    private static ArrayList<Pacman> pacmany = new ArrayList<>();
    public Pacman() {
        icon= new ImageIcon("src/pacman_right.png");
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("ranking.txt"))) {
            while (true) {
                pacmany = (ArrayList<Pacman>) inputStream.readObject();
            }
        } catch (ClassNotFoundException | IOException ex) {
            if (!(ex instanceof EOFException)) ex.printStackTrace();
        }
    }

    public void dodajPunkty(){
        punkty++;
    }

    public void dodajPunkty20(){
        punkty+=20;
    }

    public void odejmijZycie(){
        zycia--;
    }

    public void dodajZycie(){
        zycia++;
    }

    public void setZycia(int zycia) {
        this.zycia = zycia;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPunkty() {
        return punkty;
    }

    public int getZycia() {
        return zycia;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public static ArrayList<Pacman> getPacmany() {
        return pacmany;
    }

    @Override
    public String toString() {
        return nazwa+" "+punkty;
    }
}
