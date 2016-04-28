import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class GoButton extends JButton implements ActionListener, Serializable {

    private static final String UPPER_A_ICON = "upper_case_a.jpg";
    private static final String UPPER_B_ICON = "upper_case_b.jpg";
    private static final String LOWER_A_ICON = "lower_case_a.jpg";
    private static final String LOWER_B_ICON = "lower_case_b.jpg";
    private static final String LOWER_X_ICON = "lower_case_x.jpg";
    private static ImageIcon upperA, upperB, lowerA, lowerB, lowerX;
    
    private ButtonState state;
    
    public static enum ButtonState {
        UPPER_A, UPPER_B, LOWER_A, LOWER_B, LOWER_X, EMPTY
    }

    @SuppressWarnings({"LeakingThisInConstructor", 
        "OverridableMethodCallInConstructor", "Convert2Lambda"})
    public GoButton() {
        
        upperA = new ImageIcon(this.getClass().getResource(UPPER_A_ICON));
        upperB = new ImageIcon(this.getClass().getResource(UPPER_B_ICON));
        lowerA = new ImageIcon(this.getClass().getResource(LOWER_A_ICON));
        lowerB = new ImageIcon(this.getClass().getResource(LOWER_B_ICON));
        lowerX = new ImageIcon(this.getClass().getResource(LOWER_X_ICON));

        this.setState(ButtonState.EMPTY);
        this.addActionListener(this);
        
        /**
         * Action listener for player MIN (Represented as B)
         */
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(ButtonState.UPPER_B);
            }
        });
        
        /**
         * Action listener for player MAX (Represented as A)
         */
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(ButtonState.UPPER_A);
            }
        });
    }
    
    public void setState(ButtonState state) {

        this.state = state;

        switch(state) {
            case UPPER_A:
                setIcon(upperA);
                break;            
            case UPPER_B:
                setIcon(upperB);
                break;
            case LOWER_A:
                setIcon(lowerA);
                break;            
            case LOWER_B:
                setIcon(lowerB);
                break;
            case LOWER_X:
                setIcon(lowerX);
                break;
            case EMPTY:
                setIcon(null);
                break;
        }
    }

    public ButtonState getState() {
        return this.state;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        setState(ButtonState.LOWER_X);
    }
}