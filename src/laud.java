import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;



/**
 * Created by Lenovo on 27.01.2017.
 */
public class laud implements ActionListener {
    JFrame frame = new JFrame("Miiniotsija");
    JButton reset = new JButton("Proovi uuesti");
    JButton[][] buttons = new JButton[9][9]; // nuppude kogus
    int[][] counts = new int[9][9];
    Container grid = new Container();
    final int MIIN = 10;

    //konstruktor
    public laud() {
        frame.setSize(600, 600); //kogu raam, mis avaneb
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        reset.addActionListener(this); //mida teeb see neetud reset nupp
            //nuppude ruudustik
        grid.setLayout(new GridLayout(9, 9));
        for (int a = 0; a < buttons.length; a++) {
            for (int b = 0; b < buttons.length; b++) {
                buttons[a][b] = new JButton();
                buttons[a][b].addActionListener(this);
                buttons[a][b].addMouseListener(new MouseListKK());  //tegevus siduda nuppudega
                grid.add(buttons[a][b]);
            }
        }
        frame.add(grid, BorderLayout.CENTER); //nupud pannakse ekraanile
        createRandomMiinid();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //miinide tekitamine lauale juhuslikult, miinide arv 10, paneb need miinide nimekirja

    public void createRandomMiinid() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int row = 0; row < counts.length; row++) {
            for (int column = 0; column < counts[0].length; column++) {
                list.add(row * 100 + column);
            }
        }
        //nullimine ja tähistab miinid ning eemaldab need nuppude nimekirjast
        counts = new int[9][9];
        for (int a = 0; a < 10; a++) {
            int choice = (int) (Math.random() * list.size());
            int row = list.get(choice) / 100;
            int col = list.get(choice) % 100;
            counts[row][col] = MIIN;
            list.remove(choice);
        }
        // mitu miini on läheduses
        for (int row = 0; row < counts.length; row++) {
            for (int column = 0; column < counts[0].length; column++) {
                if (counts[row][column] != MIIN) {
                    counts[row][column] = getSquareNumber(row, column);
                }

            }

        }
    }

    private int getSquareNumber(int row, int column) {
        int neighborcount = 0;
        if (row > 0 && column > 0 && counts[row - 1][column - 1] == MIIN) { //vasak nurk
            neighborcount++;
        }
        if (row > 0 && counts[row - 1][column] == MIIN) { //ylemine keskel
            neighborcount++;
        }
        if (row > 0 && column < counts.length - 1 && counts[row - 1][column + 1] == MIIN) { //parem nurk
            neighborcount++;
        }
        if (column > 0 && counts[row][column - 1] == MIIN) { //keskmine rida, vasak
            neighborcount++;
        }
        if (column < counts.length - 1 && counts[row][column + 1] == MIIN) { //keskmine rida, parem
            neighborcount++;
        }
        if (row < counts.length - 1 && column > 0 && counts[row + 1][column - 1] == MIIN) { //alumine vasak nurk
            neighborcount++;
        }
        if (row < counts.length - 1 && counts[row + 1][column] == MIIN) { //alumine keskmine
            neighborcount++;
        }
        if (row < counts.length - 1 && column < counts.length - 1 && counts[row + 1][column + 1] == MIIN) { //alumine parem nurk
            neighborcount++;
        }
        return neighborcount;
    }

    //kuidas avada kõiki üksteise kõrval olevaid null-ruute
    public void avada_nullid(ArrayList < Integer> toClear){
        if (toClear.size() == 0) {
            return;
        }
        else {
            int row = toClear.get(0) / 100;
            int column = toClear.get(0) % 100;
            toClear.remove(0);

            if (row > 0 && column > 0 && buttons[row-1][column-1].isEnabled() && counts[row - 1][column - 1] != MIIN) { //yleval vasakul
                buttons[row - 1][column - 1].setText(counts[row - 1][column - 1] + "");
                buttons[row - 1][column - 1].setEnabled(false);
                if (counts[row - 1][column - 1] == 0) {
                    toClear.add((row - 1) * 100 + (column - 1));

                }
            }
            if (column > 0 && buttons[row][column-1].isEnabled() && counts[row][column - 1] != MIIN) { //yleval keskel
                buttons[row][column - 1].setText(counts[row][column - 1] + "");
                buttons[row][column - 1].setEnabled(false);
                if (counts[row][column - 1] == 0) {
                    toClear.add(row * 100 + (column - 1));
                }

            }
            if (row < counts.length - 1 && buttons[row+1][column-1].isEnabled() && counts[row + 1][column - 1] != MIIN) { //yleval paremal
                buttons[row + 1][column- 1].setText(counts[row + 1][column-1] + "");
                buttons[row + 1][column -1].setEnabled(false);
                if (counts[row + 1][column - 1] == 0) {
                    toClear.add((row + 1) * 100 + (column-1) );
                }
            }
            if (row > 0 && buttons[row-1][column].isEnabled() && counts[row - 1][column] != MIIN) {//vasakul
                buttons[row - 1][column].setText(counts[row - 1][column] + "");
                buttons[row - 1][column].setEnabled(false);
                if (counts[row - 1][column] == 0) {
                    toClear.add((row - 1) * 100 + column);
                }
            }
            if (row < counts.length - 1 && buttons[row+1][column].isEnabled() && counts[row + 1][column] != MIIN) { //paremal
                buttons[row + 1][column].setText(counts[row + 1][column] + "");
                buttons[row + 1][column].setEnabled(false);
                if (counts[row + 1][column] == 0) {
                    toClear.add((row + 1) * 100 + column);
                }

            }

            if (row > 0 && column < counts[0].length-1 && buttons[row-1][column+1].isEnabled() && counts[row - 1][column + 1] != MIIN) { //all vasakul
                buttons[row - 1][column + 1].setText(counts[row - 1][column + 1] + "");
                buttons[row - 1][column + 1].setEnabled(false);
                if (counts[row - 1][column + 1] == 0) {
                    toClear.add((row + 1) * 100 + (column + 1));
                }
            }
            if (column < counts[0].length-1 && buttons[row][column+1].isEnabled() && counts[row][column + 1] != MIIN) { //all
                buttons[row][column + 1].setText(counts[row][column + 1] + "");
                buttons[row][column + 1].setEnabled(false);
                if (counts[row][column + 1] == 0) {
                    toClear.add(row * 100 + (column + 1));
                }
            }
            if (row < counts.length - 1 && column < counts[0].length-1 && buttons[row+1][column+1].isEnabled() && counts[row + 1][column + 1] != MIIN) { //all paremal
                buttons[row + 1][column + 1].setText(counts[row + 1][column + 1] + "");
                buttons[row + 1][column + 1].setEnabled(false);
                if (counts[row + 1][column + 1] == 0) {
                    toClear.add((row + 1) * 100 + (column + 1));
                }
            }
            avada_nullid(toClear);
        }

    }

    public void kaotamine() {
        boolean lost=false;
        for (int row = 0; row < buttons.length; row++) {
            int column;
            for (column = 0; column < buttons[0].length; column++) {
                if (buttons[row][column].isEnabled()) {             //avamata ruudud
                    if (counts[row][column] != MIIN) {
                        buttons[row][column].setText(counts[row][column] + "");
                        buttons[row][column].setEnabled(false);         //kui ei ole miin, siis peab olema number ja ava ruut
                    } else { //siis on miin
                        buttons[row][column].setText("MIIN");   //kirjuta et on miin ja ava ruut
                        buttons[row][column].setEnabled(false);
                        lost=true;
                    }
                }

            }

        }if (lost == true) {
            JOptionPane.showMessageDialog(frame, "Kaotasid");
        }
    }

    public void kontrollim2ngu(laud laud){
        boolean win=true;
        boolean lost=false;
        //int openedBoxCount = 0;
        for (int row=0; row<counts.length;row++){
            for (int column=0; column<counts[0].length; column++){
                if (counts[row][column] != MIIN && buttons[row][column].isEnabled() == true){ //ei ole miin ja ruudud avamata
                    win = false;
                    //openedBoxCount++;

                }if (counts[row][column] == MIIN);
                lost=true;
            }
        }
        if (win == true && lost==false){
            JOptionPane.showMessageDialog(frame, "Võitsid");
        //if (openedBoxCount == 71) {
            //Component frame = null;

        }
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(reset)) {
            for (int row=0; row<buttons.length; row++){
                for(int column=0;column<buttons[0].length;column++){
                    buttons[row][column].setEnabled(true);
                    buttons[row][column].setText("");
                    buttons[row][column].setBackground(null);
                    //määratakse ära mida tehakse reset nupu korral: luuakse nupud, ilma
                }                                    //kirjata ja võimaldatakse neid uuesti avada
            }                                        //luuakse juhuslikult miinid
            createRandomMiinid();

        }
        else {
            for (int row = 0; row < buttons.length; row++) {        //mängu jooksul toimuvad tegevused: loeb miinid ja tekitab arvud
                for (int column = 0; column < buttons[0].length; column++) {
                    if (event.getSource().equals(buttons[row][column])) {
                        if (counts[row][column] == MIIN) {
                            //kui juhtud miiniga ruudule
                            buttons[row][column].setBackground(Color.red); //kuvad ruudu punasena
                            buttons[row][column].setText("MIIN");
                            kaotamine();


                        } else if (counts[row][column] == 0) {
                            buttons[row][column].setText(counts[row][column] + "");
                            buttons[row][column].setEnabled(false);
                            ArrayList<Integer> toClear = new ArrayList<Integer>();
                            toClear.add(row * 100 + column);
                            avada_nullid(toClear);



                        } else {
                            buttons[row][column].setText(counts[row][column] + "");
                            buttons[row][column].setEnabled(false);



                        }
                        kontrollim2ngu(this);
                    }


                }
            }
        }
    }


    private class MouseListKK implements MouseListener {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e) || e.isControlDown()) {

                JButton myButton = (JButton)e.getSource();
                if (myButton.getBackground()==Color.orange) {
                    myButton.setBackground(null);
                }
                else {
                    myButton.setBackground(Color.orange);

                }

            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

        }

    }
}

