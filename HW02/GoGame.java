import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GoGame {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        
        new AdversarialGo(3, 3, AdversarialGo.PlayerType.PLAYER_MAX);
       
    }
}