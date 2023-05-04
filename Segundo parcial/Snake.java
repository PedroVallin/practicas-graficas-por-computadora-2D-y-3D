
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int CELL_SIZE = 10;
    private static final int DELAY = 100;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color SNAKE_COLOR = Color.GREEN;
    private static final Color FOOD_COLOR = Color.RED;
    private static final int INITIAL_SNAKE_LENGTH = 5;

    private List<Point> snake;
    private Point food;
    private int direction;
    private int score;

    public Snake() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(this);
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<Point>();
        direction = KeyEvent.VK_RIGHT;
        score = 0;

        // Initialize snake
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            Point point = new Point(WIDTH / 2 - i * CELL_SIZE, HEIGHT / 2);
            snake.add(point);
        }

        // Initialize food
        spawnFood();

        // Start game loop
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                move();
                repaint();
            }
        }).start();
    }

    private void move() {
        Point head = new Point(snake.get(0));
        switch (direction) {
            case KeyEvent.VK_LEFT:
                head.x -= CELL_SIZE;
                break;
            case KeyEvent.VK_RIGHT:
                head.x += CELL_SIZE;
                break;
            case KeyEvent.VK_UP:
                head.y -= CELL_SIZE;
                break;
            case KeyEvent.VK_DOWN:
                head.y += CELL_SIZE;
                break;
        }

        // Check for collision with walls
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver();
            return;
        }

        // Check for collision with snake
        if (snake.contains(head)) {
            gameOver();
            return;
        }

        snake.add(0, head);
        if (!head.equals(food)) {
            snake.remove(snake.size() - 1);
        } else {
            score++;
            spawnFood();
        }
    }

    private void spawnFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH / CELL_SIZE) * CELL_SIZE;
        int y = random.nextInt(HEIGHT / CELL_SIZE) * CELL_SIZE;
        food = new Point(x, y);
    }

    private void gameOver() {
        System.out.println("Game over! Score: " + score);
        initGame();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(food.x, food.y, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x, p.y, CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        Snake snakeGame = new Snake();
        frame.add(snakeGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // snakeGame.initGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) ) {
            
            direction = KeyEvent.VK_LEFT;
        }

        if ((key == KeyEvent.VK_RIGHT) ) {
            direction = KeyEvent.VK_RIGHT;
        }

        if ((key == KeyEvent.VK_UP)) {
            direction = KeyEvent.VK_UP;
        }

        if ((key == KeyEvent.VK_DOWN)) {
            direction = KeyEvent.VK_DOWN;
        }
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
      
    }
    
}