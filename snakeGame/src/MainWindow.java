import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("MedvedSnake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(321,358);
        setLocation(200,200);
        add(new GameField());
        setVisible(true);
        setResizable(false);
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
