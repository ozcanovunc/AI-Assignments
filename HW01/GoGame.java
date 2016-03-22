import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GoGame {

    public static void main(String[] args) throws IOException {
        
        AbstractGo game;
        String method;
        int rows, columns;        
        Object[] searchOptions = {"BFS", "DFS"};
        Object[] boardRange = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        
        method =    (String)JOptionPane.showInputDialog(
                    new JPanel(),
                    "BFS or DFS",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    searchOptions,
                    null);
        
        rows =      (int)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Number of rows",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    boardRange,
                    null);
    
        columns =   (int)JOptionPane.showInputDialog(
                    new JPanel(),
                    "Number of columns",
                    "HW01",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    boardRange,
                    null);        
        
        if (method.equalsIgnoreCase("BFS")) {
            game = new GoBfs(rows, columns);
        }
        else {
            game = new GoDfs(rows, columns);
        }
    }
}