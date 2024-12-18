import javax.swing.table.AbstractTableModel;
import java.util.*;

public class Plansza extends AbstractTableModel {

    private int x;
    private int y;
    private Object[][] plansza;

    public Plansza(int x, int y) {
        this.x=x;
        this.y=y;
        plansza= new Object[x][y];
        generateMap();
        for (int i = 1; i < plansza.length-1; i++) {
            for (int j = 1; j < plansza[i].length-1; j++) {
                if (i == plansza.length/2) {
                    plansza[i][j] = 2;
                }
                if (j == plansza.length/2) {
                    plansza[i][j] = 2;
                }
//                if (i == 0 || i == plansza.length-1 || j == 0 || j == plansza[0].length-1)
//                    plansza[i][j] = 0;
            }
        }
    }

    @Override
    public int getRowCount() {
        return x;
    }

    @Override
    public int getColumnCount() {
        return y;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return plansza[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        plansza[rowIndex][columnIndex]=aValue;
    }
    public void generateMap() {
        for (int i = 0; i < plansza.length; i++) {
            for (int j = 0; j < plansza[0].length; j++) {
                plansza[i][j] = 0;
            }
        }

        plansza[1][1] = 2;

        ArrayList<int[]> sasiedzi = new ArrayList<>();
        ArrayList<int[]> pom = new ArrayList<>();
        pom.add(new int[]{1, 1});

        while (pom.size() != 0) {
            int[] aktualna = pom.get(pom.size()-1);
            int wiersz = aktualna[0];
            int kolumna = aktualna[1];

            if (kolumna > 2 && plansza[wiersz][kolumna - 2].equals(0) ) {
                sasiedzi.add(new int[]{wiersz, kolumna - 2});
            }
            if (wiersz > 2 && plansza[wiersz - 2][kolumna].equals(0) ) {
                sasiedzi.add(new int[]{wiersz - 2, kolumna});
            }
            if (kolumna < plansza[0].length - 2 && plansza[wiersz][kolumna + 2].equals(0) && kolumna + 2 != plansza[0].length - 1) {
                sasiedzi.add(new int[]{wiersz, kolumna + 2});
            }
            if (wiersz < plansza.length - 2 && plansza[wiersz + 2][kolumna].equals(0) && wiersz + 2 != plansza.length - 1) {
                sasiedzi.add(new int[]{wiersz + 2, kolumna});
            }

            Collections.shuffle(sasiedzi);
            if (!sasiedzi.isEmpty()) {
                int[] nastepna = sasiedzi.get(0);
                int kolejnyWiersz = nastepna[0];
                int kolejnaKolumna = nastepna[1];
                plansza[kolejnyWiersz][kolejnaKolumna] = 2;
                plansza[(wiersz + kolejnyWiersz) / 2][(kolumna + kolejnaKolumna) / 2] = 2;
                pom.add(nastepna);
            } else {
                pom.remove(pom.size()-1);
            }
            sasiedzi.clear();
        }
    }
}
