package newCrossAndZerro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainClass extends JFrame {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int BUTTON_SIZE = 50;


    private static boolean turn = true;
    private static int[][] field = new int[WIDTH][HEIGHT];
    private List<JButton> buttons = new ArrayList<>();





    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
    }



    public MainClass() throws HeadlessException {

        setTitle("Крестики нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(BUTTON_SIZE*WIDTH, BUTTON_SIZE*HEIGHT+32+5);
        newButton();

        setLayout(null);
        setLocation(200, 200);
        setVisible(true);
        setResizable(false);
        for (int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            buttons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    setTitle("Нажата кнопка - " + finalI);
                    game(finalI);
                }
            });
        }

    }

    private void newButton(){

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++){
                JButton button = new JButton();
                button.setBounds(i*BUTTON_SIZE, j*BUTTON_SIZE,  BUTTON_SIZE, BUTTON_SIZE);
                button.setVisible(true);
                buttons.add(button);
                add(button);
                field[i][j] = 0;
            }
        }
    }

    private void game(int buttonId){
        int fieldX = buttonId / HEIGHT;
        int fieldY = buttonId % HEIGHT;

        if (turn) {
            buttons.get(buttonId).setText("X");
            field[fieldX][fieldY] = 1;
        } else {
            buttons.get(buttonId).setText("0");
            field[fieldX][fieldY] = 2;
        }
        turn = !turn;
        String title = String.format("Ходит %s игрок!", turn? "X" : "0");

        setTitle(title);
        if (checkVictory(fieldX,fieldY)) {
            title = String.format("Убедительную победу одержал игрок %s!", field[fieldX][fieldY] == 1? "X" : "0");
            setTitle(title);
            for(JButton button : buttons){
                button.setEnabled(false);
            }
        }

        buttons.get(buttonId).setEnabled(false);
    }






    public boolean checkVictory(int x, int y){
        /////////////////////////////// горизонталь ///////////////////////////
        int i = 0;
        int sum = 0;
        while (field[x + i][y] == field[x][y]){
            sum++;
            i++;
            if (x + i >= WIDTH) break;
        }
        sum--;
        i = 0;
        while (field[x - i][y] == field[x][y]){
            sum++;
            i++;
            if (x - i < 0) break;
        }
        if (sum >= 5) {
            i = 0;
            while (field[x + i][y] == field[x][y]){
                int buttonId = (x + i) * HEIGHT + y;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (x + i >= WIDTH) break;
            }
            i = 0;
            while (field[x - i][y] == field[x][y]){
                int buttonId = (x - i) * HEIGHT + y;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (x - i < 0) break;
            }


            return true;
        }

        /////////////////// Вертикаль //////////////////
        i = 0;
        sum = 0;
        while (field[x][y + i] == field[x][y]){
            sum++;
            i++;
            if (y + i >= HEIGHT) break;
        }
        sum--;
        i = 0;
        while (field[x][y - i] == field[x][y]){
            sum++;
            i++;
            if (y - i < 0) break;
        }
        if (sum >= 5) {
            i = 0;
            while (field[x ][y + i] == field[x][y]){
                int buttonId = (x) * HEIGHT + y + i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (y + i >= HEIGHT) break;
            }
            i = 0;
            while (field[x ][y - i] == field[x][y]){
                int buttonId = (x) * HEIGHT + y - i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (y - i < 0) break;
            }
            return true;
        }
        ////////////////// Левая Диагональ /////////////
        i = 0;
        sum = 0;
        while (field[x + i][y + i] == field[x][y]){
            sum++;
            i++;
            if (x + i >= WIDTH) break;
            if (y + i >= HEIGHT) break;
        }
        sum--;
        i = 0;
        while (field[x - i][y - i] == field[x][y]){
            sum++;
            i++;
            if (x - i < 0) break;
            if (y - i < 0) break;
        }
        if (sum >= 5) {
            i = 0;
            while (field[x + i][y + i] == field[x][y]){
                int buttonId = (x + i) * HEIGHT + y + i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (x + i >= WIDTH) break;
                if (y + i >= HEIGHT) break;
            }
            i = 0;
            while (field[x - i][y - i] == field[x][y]){
                int buttonId = (x - i) * HEIGHT + y - i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (x - i < 0) break;
                if (y - i < 0) break;
            }
            return true;
        }
        //////////////// Правая Диагональ ////////////////////////
        i = 0;
        sum = 0;
        while (field[x + i][y - i] == field[x][y]){
            sum++;
            i++;
            if (x + i >= WIDTH) break;
            if (y - i < 0) break;
        }
        sum--;
        i = 0;
        while (field[x - i][y + i] == field[x][y]){
            sum++;
            i++;
            if (x - i < 0) break;
            if (y + i >= HEIGHT) break;
        }
        if (sum >= 5) {
            i = 0;
            while (field[x + i][y - i] == field[x][y]){
                int buttonId = (x + i) * HEIGHT + y - i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (x + i >= WIDTH) break;
                if (y - i < 0) break;
            }
            i = 0;
            while (field[x - i][y + i] == field[x][y]){
                int buttonId = (x - i) * HEIGHT + y + i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if (x - i < 0) break;
                if (y + i > HEIGHT) break;
            }
            return true;
        }




        return false;
    }

}
