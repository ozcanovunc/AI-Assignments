import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.util.List;

public abstract class AbstractGo extends JFrame {

    JPanel panel;
    GoButton[][] buttons;
    GoButton.ButtonState[][] boardState;
    Queue<GoNode> fringe;
    Stack<GoNode> result;
    int rows;
    int cols;
    
    int expandedNodeCount;
    int moveCount;
    int fringeSize;
    
    abstract GoNode treeSearch();
    abstract List<GoNode> expand(GoNode parent);
    
    void initBoard() {

        panel = new JPanel();
        buttons = new GoButton[rows][cols];
        
        setSize(cols * 100, (rows + 1) * 100);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(0, cols));

        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                buttons[ri][ci] = new GoButton();
                panel.add(buttons[ri][ci]);
            }
        }
        
        JButton startButton = new JButton("START");
        JButton nextButton = new JButton("NEXT");      
        nextButton.setVisible(false);
        
        nextButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            
                if (!result.isEmpty()) {
                    GoNode step = result.pop();
                    for (int ri = 0; ri < rows; ++ri) {
                        for (int ci = 0; ci < cols; ++ci) { 
                            buttons[ri][ci].setState(step.getState()[ri][ci]);
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Expanded nodes: " + 
                            expandedNodeCount + "\nFringe list size: " + 
                            fringeSize + "\nGame over, total of " + moveCount +
                            " moves", "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }    
        });
        
        startButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                initBoardState();
                GoNode node = treeSearch();

                // Store the whole path
                while (node != null) {
                    result.push(node);
                    node = node.getParent();
                }
                if (!result.isEmpty())
                    result.pop();
                
                startButton.setVisible(false);
                nextButton.setVisible(true);
            }
        });
        
        panel.add(startButton);
        panel.add(nextButton);
        
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