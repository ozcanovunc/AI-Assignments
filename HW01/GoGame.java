import java.awt.*;
import javax.swing.*;

public class GoGame extends JFrame {
    
    private JPanel panel;
    private GoButton[][] buttons;

    public static void main(String[] args) {
        new GoGame();
    }
    
    public GoGame() {
        super("GoGame");
        panel = new JPanel();
        initBoard(3, 4);
    }
    
    private void initBoard(int rows, int cols) {
  
        buttons = new GoButton[rows][cols];
        
        setSize(cols * 100, rows * 100);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(rows, cols));

        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                buttons[ri][ci] = new GoButton();
                panel.add(buttons[ri][ci]);
            }
        }
        add(panel);
        setVisible(true);
    }
}