import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GoGame {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        
        int rows, cols, depth, eval; 
        String user;
        AdversarialGo.Player player;
        Object[] userRange = {"MAX = A", "MIN = B"};
        Object[] evalRange = {1, 2};
        Object[] boardRange = {1, 2, 3, 4, 5, 6};
                
        rows =      (int)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Number of rows",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    boardRange,
                    null);
    
        cols =     (int)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Number of columns",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    boardRange,
                    null);        
        
        depth =     (int)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Maximum depth",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    boardRange,
                    null);
        
        user =      (String)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Play as max or min?",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    userRange,
                    null); 

        /**
         * There are two evaluation functions implemented in this project,
         * first one is quite better.
         */
        eval =      (int)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Evaluation function",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    evalRange,
                    null);
        
        if (user.equalsIgnoreCase("MAX = A")) {
            player = AdversarialGo.Player.PLAYER_MAX;
        }
        else {
            player = AdversarialGo.Player.PLAYER_MIN;
        }
        
        new AdversarialGo(rows, cols, player, depth, eval == 1);
    }
}