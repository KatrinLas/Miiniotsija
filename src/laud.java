import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                buttons[a][b].addActionListener(this); //tegevus siduda nuppudega
                grid.add(buttons[a][b]);
            }
        }
        frame.add(grid, BorderLayout.CENTER); //nupud pannakse ekraanile
        createRandomMiinid();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //miinide tekitamine lauale juhuslikult, miinide arv 10

    public void createRandomMiinid() {
        ArrayList<Integer> list = new ArrayList<Integer>();
            for (int x = 0; x < counts.length; x++) {
                for (int y = 0; y < counts[0].length; y++) {
                    list.add(x * 100 + y);
                }
            }
        //nullimine ja tähistab miinid ning eemaldab need nuppude nimekirjast
        counts = new int[9][9];
        for (int a = 0; a < 10; a++) {
            int choice = (int) (Math.random() * list.size());
            counts[list.get(choice) / 100][list.get(choice) % 100] = MIIN;
            list.remove(choice);
        }
        // mitu miini on läheduses
        for (int x = 0; x < counts.length; x++) {
            for (int y = 0; y < counts[0].length; y++) {
                if (counts[x][y] != MIIN) {
                    int neighborcount = 0;
                    if (x > 0 && y > 0 && counts[x - 1][y - 1] == MIIN) { //vasak nurk
                        int i = neighborcount++;
                    }
                    if (y > 0 && counts[x][y - 1] == MIIN) { //ülemine keskel
                        int i=neighborcount++;

                    }

                    counts[x][y] = neighborcount;
                }

            }

        }
    }

    //kuidas avada kõiki üksteise kõrval olevaid null-ruute
    public void avada_nullid(ArrayList < Integer> toClear){
        if (toClear.size() == 0) {
            return;
        }
        else {
            int x = toClear.get(0) / 100;
            int y = toClear.get(0) % 100;
            toClear.remove(0);

            if (x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()) { //yleval vasakul
                buttons[x - 1][y - 1].setText(counts[x - 1][y - 1] + "");
                buttons[x - 1][y - 1].setEnabled(false);
                if (counts[x - 1][y - 1] == 0) {
                    toClear.add((x - 1) * 100 + (y - 1));

                }
            }
            if (y > 0 && buttons[x][y-1].isEnabled()) { //yleval
                buttons[x][y - 1].setText(counts[x][y - 1] + "");
                buttons[x][y - 1].setEnabled(false);
                if (counts[x][y - 1] == 0) {
                    toClear.add(x * 100 + (y - 1));
                }

            }
            if (x < counts.length - 1 && buttons[x+1][y-1].isEnabled()) { //yleval paremal
                buttons[x + 1][y- 1].setText(counts[x + 1][y-1] + "");
                buttons[x + 1][y -1].setEnabled(false);
                if (counts[x + 1][y - 1] == 0) {
                    toClear.add((x + 1) * 100 + (y-1) );
                }
            }
            if (x > 0 && buttons[x-1][y].isEnabled()) {//vasakul
                buttons[x - 1][y].setText(counts[x - 1][y] + "");
                buttons[x - 1][y].setEnabled(false);
                if (counts[x - 1][y] == 0) {
                    toClear.add((x - 1) * 100 + y);
                }
            }
            if (x < counts.length - 1 && buttons[x+1][y].isEnabled()) { //paremal
                buttons[x + 1][y].setText(counts[x + 1][y] + "");
                buttons[x + 1][y].setEnabled(false);
                if (counts[x + 1][y] == 0) {
                    toClear.add((x + 1) * 100 + y);
                }

            }

            if (x > 0 && y < counts[0].length-1 && buttons[x-1][y+1].isEnabled()) { //all vasakul
                buttons[x - 1][y + 1].setText(counts[x - 1][y + 1] + "");
                buttons[x - 1][y + 1].setEnabled(false);
                if (counts[x - 1][y + 1] == 0) {
                    toClear.add((x + 1) * 100 + (y + 1));
                }
            }
            if (y < counts[0].length-1 && buttons[x][y+1].isEnabled()) { //all
                buttons[x][y + 1].setText(counts[x][y + 1] + "");
                buttons[x][y + 1].setEnabled(false);
                if (counts[x][y + 1] == 0) {
                    toClear.add(x * 100 + (y + 1));
                }
            }
            if (x < counts.length - 1 && y < counts[0].length-1 && buttons[x+1][y+1].isEnabled()) { //all paremal
                buttons[x + 1][y + 1].setText(counts[x + 1][y + 1] + "");
                buttons[x + 1][y + 1].setEnabled(false);
                if (counts[x + 1][y + 1] == 0) {
                    toClear.add((x + 1) * 100 + (y + 1));
                }
            }
            avada_nullid(toClear);
        }

        Miin m = new Miin();
        Miin m2 = new Miin();
        Miin m3 = new Miin();
        Miin m4 = new Miin();
        m.kasSainPihta(4, 5);

    }

    public void kaotamine() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[0].length; y++) {
                if (buttons[x][y].isEnabled()) {
                    if (counts[x][y] != MIIN) {
                        buttons[x][y].setText(counts[x][y] + "");
                        buttons[x][y].setEnabled(false);//kui ei ole miin, siis peab olema number
                    }
                    else { //kui ei ole ei miin, ega number
                        buttons[x][y].setText("MIIN");
                        buttons[x][y].setEnabled(false);
                    }
                }

            }
        }
    }

    public void kontrollim2ngu(laud laud){
        boolean võit=true;
        for (int x=0; x<counts.length;x++){
            for (int y=0; y<counts[0].length; y++){
                if (counts[x][y]!=MIIN && buttons[x][y].isEnabled()==true){
                    võit=false;
                }
            }
        }
        if (võit==true){
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Sina võitsid!");

        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(reset)) {
            for (int x=0; x<buttons.length; x++){
                for(int y=0;y<buttons[0].length;y++){
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                    //määratakse ära mida tehakse reset nupu korral: luuakse nupud, ilma
                }                                    //kirjata ja võimaldatakse neid uuesti avada
            }                                        //luuakse juhuslikult miinid
            createRandomMiinid();
        }
        else {
            for (int x = 0; x < buttons.length; x++) { //mängu jooksul toimuvad tegevused: loeb miinid ja tekitab arvud
                for (int y = 0; y < buttons[0].length; y++) {
                    if (event.getSource().equals(buttons[x][y])) {
                        if (counts[x][y] == MIIN) {
                            //kui juhtud miiniga ruudule
                            buttons[x][y].setForeground(Color.red); //kuvad ruudu punasena
                            buttons[x][y].setText("MIIN");
                            kaotamine();

                        } else if (counts[x][y] == 0) {
                            buttons[x][y].setText(counts[x][y] + "");
                            buttons[x][y].setEnabled(false);
                            ArrayList<Integer> toClear = new ArrayList<Integer>();
                            toClear.add(x * 100 + y);
                            avada_nullid(toClear);

                        } else {
                            buttons[x][y].setText(counts[x][y] + "");
                            buttons[x][y].setEnabled(false);



                        }
                    }


                }
            }
        }
    }

}