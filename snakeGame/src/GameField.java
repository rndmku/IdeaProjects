import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 100;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int [ALL_DOTS];
    private int[] y = new int [ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame= true;
    private boolean isWin = false;
    private int score;





    public GameField(){
        setBackground(Color.CYAN);
        loadImages();
        initGame();
        addKeyListener((new FieldKeyListener()));
        setFocusable(true);

    }

    public void createNewGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 96 - i * DOT_SIZE;
            y[i] = 96;
        }
        createApple();
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 96 - i * DOT_SIZE;
            y[i] = 96;
        }
        timer = new Timer(500 , this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(10)*DOT_SIZE;
        appleY = new Random().nextInt(10) * DOT_SIZE;
        for (int i = dots; i>0; i--){
            if (x[i] == appleX && y[i] == appleY)
                createApple();
        }

    }




    public void loadImages(){
//        JLabel icon = new JLabel(new ImageIcon(String.valueOf(getClass().getResourceAsStream("images//apple.png"))));
        ImageIcon iia = new ImageIcon("images//apple.png");
//        apple = icon.createImage(32, 32);
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("images/dot.png");
        dot = iid.getImage();
//        JLabel appleI = new JLabel(new javax.swing.ImageIcon(getClass().getResource("images//apple.png")));
//        apple = appleI.createImage(32,32);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < getWidth(); x+=DOT_SIZE) {
            g.drawLine(x,0, x, getHeight());
        }
        for(int y = 0; y < getHeight(); y+=DOT_SIZE){
            g.drawLine(0, y, getWidth(), y);
        }

        if (inGame){
            g.drawImage(apple, appleX, appleY, this);
            g.drawString("x = "+x[0], 10, 32);
            g.drawString("y = "+y[0], 10, 64);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i], this);
            }
        } else  if (isWin){
            String str = "You Win!";
            Font f = new Font("Times New Roman", Font.BOLD, 35);
            g.setColor(Color.GRAY);
            g.setFont(f);
            g.drawString(str, SIZE/2 - 2*35, SIZE / 2);
        } else {
            String str = "Game Over";
            Font f = new Font("Times New Roman", Font.BOLD, 35);
            g.setColor(Color.RED);
            g.setFont(f);
            g.drawString(str, SIZE/2 - 2*35, SIZE / 2);
        }
    }

    public void move(){
        for (int i = dots; i >0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right){
            x[0] += DOT_SIZE;
        }
        if (up){
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] ==appleY){
            dots++;
            score+=5;
            createApple();
            if (score > 25) {
                isWin = true;
                inGame = false;
            }
        }
    }
    public void checkCollisions(){
        for (int i = dots; i > 0  ; i--) {
            if (i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }

        }

        if (x[0] >= SIZE || x[0] < 0 || y[0] >= SIZE || y[0] <0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up=false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up=false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_ENTER && !inGame){
                createNewGame();
                score = 0;
                inGame = true;
                isWin = false;
            }
        }
    }
}
