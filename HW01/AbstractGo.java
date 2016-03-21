import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public abstract class AbstractGo extends JFrame {

    JPanel panel;
    GoButton[][] buttons;
    GoButton.ButtonState[][] boardState;
    Queue<GoNode> fringe;
    int rows;
    int cols;
    
    abstract GoNode treeSearch();
    abstract List<GoNode> expand(GoNode parent);

    void initBoard() {
  
        panel = new JPanel();
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

    void initBoardState() {
    
        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                boardState[ri][ci] = buttons[ri][ci].getState();
            }
        }
    }   
    
    static boolean goalTest(GoButton.ButtonState[][] config) {
    
        boolean isSolution = true;
        
        for (int ri = 0; ri < config.length; ++ri) {
            for (int ci = 0; ci < config[ri].length; ++ci) {
                if (config[ri][ci] == GoButton.ButtonState.EMPTY)
                    isSolution = false;
            }
        }
        return isSolution;
    }
    
    static GoButton.ButtonState[][] copyStateArray(
            GoButton.ButtonState[][] array) {
        
        GoButton.ButtonState[][] newArray = 
                new GoButton.ButtonState[array.length][array[0].length]; 

        for (int ri = 0; ri < array.length; ++ri)
            for (int ci = 0; ci < array[ri].length; ++ci)
                newArray[ri][ci] = array[ri][ci];

        return newArray;  
    }    
}