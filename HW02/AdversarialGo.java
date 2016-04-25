import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class AdversarialGo extends JFrame {

    JPanel panel;
    GoButton[][] buttons;
    GoButton.ButtonState[][] boardState;
    int rows;
    int cols;

    public static enum PlayerType {
        PLAYER_MIN, PLAYER_MAX
    }

    @SuppressWarnings({"Convert2Lambda", "OverridableMethodCallInConstructor"})
    AdversarialGo(int rows, int cols, PlayerType type) {

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

            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: Handle min moves
                
            }    
        });

        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                initBoardState();

                for (int ri = 0; ri < rows; ++ri) {
                    for (int ci = 0; ci < cols; ++ci) {
                        if (type == PlayerType.PLAYER_MAX) {
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[1]);
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[1]);
                        }
                        else {
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[0]);
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[1]);
                        }
                    }
                }
                
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

    private static boolean goalTest(GoButton.ButtonState[][] config) {
    
        boolean isSolution = true;
        
        for (int ri = 0; ri < config.length; ++ri) {
            for (int ci = 0; ci < config[ri].length; ++ci) {
                if (config[ri][ci] == GoButton.ButtonState.EMPTY)
                    isSolution = false;
            }
        }
        return isSolution;
    }

    private static GoButton.ButtonState[][] copyStateArray(
            GoButton.ButtonState[][] array) {
        
        GoButton.ButtonState[][] newArray = 
                new GoButton.ButtonState[array.length][array[0].length]; 

        for (int ri = 0; ri < array.length; ++ri)
            for (int ci = 0; ci < array[ri].length; ++ci)
                newArray[ri][ci] = array[ri][ci];

        return newArray;  
    }
    
    /**
     * Evaluation function
     * @param array
     * @return number of pieces of the player
     */
    private static int eval(GoButton.ButtonState[][] array, PlayerType type)
    {
        int evalValue = 0;
        
        for (int ri = 0; ri < array.length; ++ri)
            for (int ci = 0; ci < array[ri].length; ++ci) {
                if ((type == PlayerType.PLAYER_MIN) && 
                        (array[ri][ci] == GoButton.ButtonState.UPPER_B 
                        || array[ri][ci] == GoButton.ButtonState.LOWER_B))
                    ++evalValue;
                else if ((type == PlayerType.PLAYER_MAX) &&
                        (array[ri][ci] == GoButton.ButtonState.UPPER_A 
                        || array[ri][ci] == GoButton.ButtonState.LOWER_A))
                    ++evalValue;
            } 
        
        return evalValue;
    }
}