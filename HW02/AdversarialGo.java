import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class AdversarialGo extends JFrame {

    private final JPanel panel;
    private GoButton[][] buttons;
    private GoButton.ButtonState[][] boardState;
    private final int rows;
    private final int cols;
    
    private int nodesExpanded;

    /**
     * Last move of the player
     */
    private int lastXMove;
    private int lastYMove;
    
    /**
     * Type of the player
     */
    private Player player;
    
    public static enum Player {
        PLAYER_MIN, PLAYER_MAX
    }

    @SuppressWarnings({"Convert2Lambda", "OverridableMethodCallInConstructor"})
    AdversarialGo(int rows, int cols, Player player, int depth, boolean firstUtilityUsed) {

        panel = new JPanel();
        buttons = new GoButton[rows][cols];
        this.boardState = new GoButton.ButtonState[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.player = player;
        this.nodesExpanded = 0;

        setSize(cols * 100, (rows + 1) * 100);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(0, cols));

        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                
                buttons[ri][ci] = new GoButton();
                buttons[ri][ci].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object source = e.getSource();
                        for (int ri = 0; ri < rows; ++ri) {
                            for (int ci = 0; ci < cols; ++ci) {
                                    if(buttons[ri][ci] == source) {
                                        lastXMove = ri;
                                        lastYMove = ci;
                                    }
                                }
                            }
                    }
                });
                panel.add(buttons[ri][ci]);
            }
        }

        JButton startButton = new JButton("START");
        JButton nextButton = new JButton("NEXT");      
        nextButton.setVisible(false);
        
        /**
         * Action listener for opponent to make their move
         */
        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                GoNode player1, player2;
                
                updateBoardState();
                
                /**
                 * Check if the game is over
                 */
                if (goalTest(boardState)) {
                                        
                    if (getWinner(boardState) == Player.PLAYER_MAX){
                        JOptionPane.showMessageDialog(null, 
                                "The winner is: Player A", "Result", 
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, 
                                "The winner is: Player B", "Result", 
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    return;
                }
                
                player1 = new GoNode(boardState, null, lastXMove, lastYMove, player);
                boardState = copyStateArray(player1.getState());
                
                /**
                 * Get the appropriate action for the computer (minimax algorithm)
                 * and calculate expanded nodes for each move
                 */
                nodesExpanded = 0;
                Point action = getAction(boardState, 
                        MinimaxDecision(player1, depth, firstUtilityUsed));

                /**
                 * Show the number of nodes expanded
                 */
                JOptionPane.showMessageDialog(null, "Expanded nodes: " +  
                        nodesExpanded, "Result", JOptionPane.INFORMATION_MESSAGE);
                
                if (player == Player.PLAYER_MAX) {
                    boardState[action.x][action.y] = GoButton.ButtonState.UPPER_B;
                    player2 = new GoNode(boardState, player1, action.x, 
                            action.y, Player.PLAYER_MIN);
                }
                else {
                    boardState[action.x][action.y] = GoButton.ButtonState.UPPER_A;
                    player2 = new GoNode(boardState, player1, action.x, 
                            action.y, Player.PLAYER_MAX);
                }

                boardState = copyStateArray(player2.getState());
                updateBoardButtons();
            }    
        });

        /**
         * Start the game
         */
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                updateBoardState();

                for (int ri = 0; ri < rows; ++ri) {
                    for (int ci = 0; ci < cols; ++ci) {
                        if (player == Player.PLAYER_MAX) {
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[2]);
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[2]);
                        }
                        else {
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[1]);
                            buttons[ri][ci].removeActionListener(
                                    buttons[ri][ci].getActionListeners()[2]);
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

    private GoNode MinimaxDecision(GoNode node, int depth, boolean firstUtilityUsed) {
 
        List<GoNode> successors = expand(node);
        GoNode temp;
        
        nodesExpanded += successors.size();
                
        if (successors.isEmpty()) {
            return node;
        }
        
        if (depth == 1) {
            
            temp = successors.get(0);
            
            if (node.getPlayerType() == Player.PLAYER_MIN) {
                for (int i = 1; i < successors.size(); ++i) {
                    /**
                     * Get the node with highest utility for MAX
                     */
                    if (evaluationFunction1(successors.get(i).getState()) 
                            > evaluationFunction1(temp.getState()) 
                            && firstUtilityUsed) {
                        temp = successors.get(i);
                    }
                    else if (evaluationFunction2(successors.get(i).getState()) 
                            > evaluationFunction2(temp.getState()) 
                            && !firstUtilityUsed) {
                        temp = successors.get(i);
                    }
                }
            }
            else {
                for (int i = 1; i < successors.size(); ++i) {
                    /**
                     * Get the node with highest utility for MIN
                     */
                    if (evaluationFunction1(successors.get(i).getState()) 
                            < evaluationFunction1(temp.getState()) 
                            && firstUtilityUsed) {
                        temp = successors.get(i);
                    }
                    else if (evaluationFunction2(successors.get(i).getState()) 
                            < evaluationFunction2(temp.getState()) 
                            && !firstUtilityUsed) {
                        temp = successors.get(i);
                    }    
                }
            }
            
            return temp;
        }
        else {
            
            temp = MinimaxDecision(successors.get(0), depth - 1, firstUtilityUsed);
        
            if (node.getPlayerType() == Player.PLAYER_MIN) {
                
                for (int i = 0; i < successors.size(); ++i) {
                    GoNode temp2 = MinimaxDecision(successors.get(i), depth - 1, 
                            firstUtilityUsed);
                    if (evaluationFunction1(temp2.getState()) > 
                            evaluationFunction1(temp.getState()) 
                            && firstUtilityUsed) {
                        temp = temp2;
                    }
                    else if (evaluationFunction2(temp2.getState()) > 
                            evaluationFunction2(temp.getState()) 
                            && !firstUtilityUsed) {
                        temp = temp2;
                    }
                }
            }
            else {
                
                for (int i = 0; i < successors.size(); ++i) {
                    GoNode temp2 = MinimaxDecision(successors.get(i), depth - 1, 
                            firstUtilityUsed);
                    if (evaluationFunction1(temp2.getState()) < 
                            evaluationFunction1(temp.getState()) 
                            && firstUtilityUsed) {
                        temp = temp2;
                    }
                    else if (evaluationFunction2(temp2.getState()) < 
                            evaluationFunction2(temp.getState()) 
                            && !firstUtilityUsed) {
                        temp = temp2;
                    }
                }
            }
            
            return temp;
        }
    }
 
    private Point getAction(GoButton.ButtonState[][] config, GoNode node) {
    
        GoNode temp = node;
        
        while (true) {
            
            if (compareStateArray(temp.getParent().getState(), config))
                break;
            
            temp = temp.getParent();
        }
        
        return temp.getLastAction();        
    }
    
    private void updateBoardState() {
    
        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                boardState[ri][ci] = buttons[ri][ci].getState();
            }
        }
    }

    private void updateBoardButtons() {
        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                buttons[ri][ci].setState(boardState[ri][ci]);
            }
        }    
    }

    /**
     * Check if the board is full or not
     * @param config
     * @return 
     */
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

    private static boolean compareStateArray(GoButton.ButtonState[][] rhs, 
            GoButton.ButtonState[][] lhs) {
      
        boolean equals = true;
        
        for (int ri = 0; ri < rhs.length; ++ri) {
            for (int ci = 0; ci < rhs[0].length; ++ci) {
                if (rhs[ri][ci] != lhs[ri][ci]){
                    equals = false;
                }
            }
        }
        
        return equals;
    }
    
    private static GoButton.ButtonState[][] copyStateArray(
            GoButton.ButtonState[][] config) {
        
        GoButton.ButtonState[][] newArray = 
                new GoButton.ButtonState[config.length][config[0].length]; 

        for (int ri = 0; ri < config.length; ++ri)
            for (int ci = 0; ci < config[ri].length; ++ci)
                newArray[ri][ci] = config[ri][ci];

        return newArray;  
    }
    
    private int evaluationFunction1(GoButton.ButtonState[][] config)
    {
        int evalValue = 0;
        
        for (int ri = 0; ri < config.length; ++ri)
            for (int ci = 0; ci < config[ri].length; ++ci) {
                if (config[ri][ci] == GoButton.ButtonState.UPPER_B 
                        || config[ri][ci] == GoButton.ButtonState.LOWER_B)
                    --evalValue;
                else if ((config[ri][ci] == GoButton.ButtonState.UPPER_A 
                        || config[ri][ci] == GoButton.ButtonState.LOWER_A))
                    ++evalValue;
            } 
        
        return evalValue;
    }

    private int evaluationFunction2(GoButton.ButtonState[][] config)
    {
        int evalValueA = 0, evalValueB = 0;
        
        for (int ri = 0; ri < config.length; ++ri)
            for (int ci = 0; ci < config[ri].length; ++ci) {
                if (config[ri][ci] == GoButton.ButtonState.UPPER_B 
                        || config[ri][ci] == GoButton.ButtonState.LOWER_B)
                    ++evalValueB;
                else if ((config[ri][ci] == GoButton.ButtonState.UPPER_A 
                        || config[ri][ci] == GoButton.ButtonState.LOWER_A))
                    ++evalValueA;
            } 
        
        return this.player == Player.PLAYER_MAX ? evalValueB : evalValueA;
    }
    
    private static Player getWinner(GoButton.ButtonState[][] config) {

        int numOfA = 0, numOfB = 0;
        
        for (int ri = 0; ri < config.length; ++ri) {
            for (int ci = 0; ci < config[0].length; ++ci) {
                if (config[ri][ci] == GoButton.ButtonState.LOWER_A || 
                        config[ri][ci] == GoButton.ButtonState.UPPER_A)
                    ++numOfA;
                if (config[ri][ci] == GoButton.ButtonState.LOWER_B || 
                        config[ri][ci] == GoButton.ButtonState.UPPER_B)
                    ++numOfB;
            }
        }
        
        return numOfA > numOfB ? Player.PLAYER_MAX : Player.PLAYER_MIN;
    }
    
    public List<GoNode> expand(GoNode parent) {
        
        List<GoNode> successors = new ArrayList();
        GoButton.ButtonState[][] parentState = parent.getState();
        
        /**
         * Type of the successor nodes
         */
        Player p = Player.PLAYER_MAX == parent.getPlayerType() ? 
                Player.PLAYER_MIN : Player.PLAYER_MAX;
        
        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                if (parentState[ri][ci] == GoButton.ButtonState.EMPTY) {
                    successors.add(new GoNode(
                            copyStateArray(parent.getState()), 
                            parent, ri, ci, p));
                }   
            }
        }
        return successors; 
    }
}