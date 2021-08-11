import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    private static int WIDTH = 10;
    private static int HEIGHT = 10;
    private static final int BUTTON_SIZE = 50;
    private static boolean moveX0 = true;
    private List<JButton> buttons = new ArrayList<>();
    private int[][] field;// = new int[WIDTH][HEIGHT];







    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }

    public MainWindow() throws HeadlessException {

        setTitle("Крестики нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);
        fileMenu.addSeparator();
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(font);
        editMenu.addSeparator();
        editMenu.setVisible(true);
        editMenu.setEnabled(true);

        JMenuItem newGame = new JMenuItem("Новая игра!", new ImageIcon("new_game.png"));
        newGame.setFont(font);
        fileMenu.add(newGame);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               newGame();
//                newButton();
            }
        });
        JMenu field = new JMenu("Поле");
        editMenu.add(field);

        JMenuItem field10x10 = new JMenuItem("10 x 10", new ImageIcon("field.png"));
        field10x10.setFont(font);
        field.add(field10x10);
        field10x10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HEIGHT = 10;
                WIDTH = 10;
                setVisible(false);
                dispose();
                new MainWindow();
            }
        });
        JMenuItem field15x15 = new JMenuItem("15 x 15", new ImageIcon("field.png"));
        field15x15.setFont(font);
        field.add(field15x15);
        field15x15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HEIGHT = 15;
                WIDTH = 15;
                setVisible(false);
                dispose();
                new MainWindow();

            }
        });
        JMenuItem field20x20 = new JMenuItem("20 x 20", new ImageIcon("field.png"));
        field20x20.setFont(font);
        field.add(field20x20);
        field20x20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HEIGHT = 20;
                WIDTH = 20;
                setVisible(false);
                dispose();
                new MainWindow();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit", new ImageIcon("exit.png"));
        exitItem.setFont(font);
        fileMenu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        setSize(BUTTON_SIZE*WIDTH, BUTTON_SIZE*HEIGHT +32+25);
        newButton();

        setLayout(null);
        setLocation(20, 20);
        setVisible(true);
        setResizable(false);
        for (int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            buttons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    move(finalI);
                }
            });
        }

    }

    public void newButton(){
        field = new int[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++){
                JButton button = new JButton();
                Font font = new Font("Tahoma", Font.PLAIN, 20);
                button.setFont(font);
                button.setBounds(i*BUTTON_SIZE, j*BUTTON_SIZE,  BUTTON_SIZE, BUTTON_SIZE);
                button.setVisible(true);
                buttons.add(button);
                add(button);
                field[i][j] = 0;
            }
        }
    }

    public void newGame(){

        for(JButton button: buttons){
            button.setText("");
            button.setEnabled(true);
            button.setBackground(null);
        }
        for (int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j ++) {
                field[i][j] = 0;
            }
        }
    }
    public void move(int buttonId){
        int fieldX = buttonId / HEIGHT;
        int fieldY = buttonId % HEIGHT;
        String title;
        if (moveX0) {
            buttons.get(buttonId).setForeground(Color.GREEN);
            buttons.get(buttonId).setText("X");
            field[fieldX][fieldY] = 1;
        }
        else {
            buttons.get(buttonId).setForeground(Color.PINK);
            buttons.get(buttonId).setText("0");
            field[fieldX][fieldY] = -1;
        }
        buttons.get(buttonId).setEnabled(false);

        if (checkVictory(fieldX, fieldY)){
            String textVictory = String.format("Яркую победу одержал игрок, который ставил %s", field[fieldX][fieldY] == 1 ? "X" : "0");
            setTitle(textVictory);
            for(JButton button : buttons){
                button.setEnabled(false);
            }
            return;
        }

        moveX0 = !moveX0;
        title = String.format("Ходит %s игрок!", moveX0 ? "X" : "0");
        setTitle(title);
    }
    public boolean checkVictory(int x, int y){
        int sum = 0;
        int i = 0;

////////////////////////// горизонталь //////////////////////////////
        while(field[x + i][y] == field[x][y]) {
            sum++;

            i++;
            if ((x + i) >= WIDTH) break;
        }
        sum--;
        i = 0;
        while (field[x - i][y] == field[x][y]){
            sum++;
            i++;
            if ((x - i) < 0) break;
        }
        if (sum  >= 5) {
            i = 0;
            while(field[x + i][y] == field[x][y]) {
                int buttonId = (x + i) * HEIGHT + y;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((x + i) >= WIDTH) break;
            }
            i = 0;
            while (field[x - i][y] == field[x][y]){
                int buttonId = (x - i) * HEIGHT + y;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((x - i) < 0) break;
            }


            return true;
        }

        ////////////////////////// вертикаль //////////////////////////////
        sum=0;
        i=0;
        while(field[x][y+i] == field[x][y]) {
            sum++;

            i++;
            if ((y + i) >= HEIGHT) break;
        }
        sum--;
        i = 0;
        while (field[x][y - i] == field[x][y]){
            sum++;
            i++;
            if ((y - i) < 0) break;
        }
        if (sum  >= 5) {
            i = 0;
            while(field[x][y + i] == field[x][y]) {
                int buttonId = (x) * HEIGHT + y + i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((y + i) >= HEIGHT) break;
            }
            i = 0;
            while (field[x][y - i] == field[x][y]){
                int buttonId = (x) * HEIGHT + y - i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((y - i) < 0) break;
            }


            return true;
        }

        ////////////////////////// левая диагональ //////////////////////////////
        sum=0;
        i=0;
        while(field[x + i][y + i] == field[x][y]) {
            sum++;

            i++;
            if ((x + i) >= WIDTH) break;
            if ((y + i) >= HEIGHT) break;
        }
        sum--;
        i = 0;
        while (field[x - i][y - i] == field[x][y]){
            sum++;
            i++;
            if ((x - i) < 0) break;
            if ((y - i) < 0) break;
        }
        if (sum  >= 5) {
            i = 0;
            while(field[x + i][y + i] == field[x][y]) {
                int buttonId = (x + i) * HEIGHT + y + i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((x + i) >= WIDTH) break;
                if ((y + i) >= HEIGHT) break;
            }
            i = 0;
            while (field[x - i][y - i] == field[x][y]){
                int buttonId = (x - i) * HEIGHT + y - i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((x - i) < 0) break;
                if ((y - i) < 0) break;
            }


            return true;
        }

        ////////////////////////// правая диагональ //////////////////////////////
        sum=0;
        i=0;
        while(field[x + i][y - i] == field[x][y]) {
            sum++;

            i++;
            if ((x + i) >= WIDTH) break;
            if ((y - i) < 0) break;
        }
        sum--;
        i = 0;
        while (field[x - i][y + i] == field[x][y]){
            sum++;
            i++;
            if ((x - i) < 0) break;
            if ((y + i) >= HEIGHT) break;
        }
        if (sum  >= 5) {
            i = 0;
            while(field[x + i][y - i] == field[x][y]) {
                int buttonId = (x + i) * HEIGHT + y - i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((x + i) >= WIDTH) break;
                if ((y - i) < 0) break;
            }
            i = 0;
            while (field[x - i][y + i] == field[x][y]){
                int buttonId = (x - i) * HEIGHT + y + i;
                buttons.get(buttonId).setBackground(Color.BLUE);
                i++;
                if ((x - i) < 0) break;
                if ((y + i) >= HEIGHT) break;
            }


            return true;
        }

        return false;
    }
}
