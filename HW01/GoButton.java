import java.awt.event.*;
import javax.swing.*;

public class GoButton extends JButton implements ActionListener {

    private static final String BLACK_X_ICON = "blackx.png";
    private static final String RED_X_ICON = "redx.png";
    private static final String BLACK_O_ICON = "blacko.png";
    private static ImageIcon blackX, redX, blackO;
    
    private ButtonState state;
    
    public enum ButtonState {
        BLACK_X, RED_X, BLACK_O, EMPTY
    }

    public GoButton() {
        blackX = new ImageIcon(this.getClass().getResource(BLACK_X_ICON));
        redX = new ImageIcon(this.getClass().getResource(RED_X_ICON));
        blackO = new ImageIcon(this.getClass().getResource(BLACK_O_ICON));
        setState(ButtonState.EMPTY);
        this.addActionListener(this);
    }

    public void setState(ButtonState state) {

        this.state = state;

        switch(state) {
            case BLACK_X:
                setIcon(blackX);
                break;            
            case RED_X:
                setIcon(redX);
                break;
            case BLACK_O:
                setIcon(blackO);
                break;
            case EMPTY:
                setIcon(null);
                break;
        }
    }

    public ButtonState getState() {
        return this.state;
    }
    
    public void actionPerformed(ActionEvent e) {
        setState(ButtonState.RED_X);
    }
}