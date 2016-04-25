import java.util.*;

public class GoNode {
    
    private final GoButton.ButtonState[][] state;
    private GoNode parent;
    private final List<GoNode> children;
    private final int depth;
    private final int actionX;
    private final int actionY;
    private final boolean isMax;

    @SuppressWarnings("LeakingThisInConstructor")
    public GoNode(GoButton.ButtonState[][] state, GoNode parent, int actionX, 
            int actionY) {

        children = new ArrayList();
        this.state = state;
        this.actionX = actionX;
        this.actionY = actionY;

        if (parent != null) {
            this.depth = parent.getDepth() + 1;
            this.isMax = !parent.isMax;
            parent.addChild(this);
        }
        else {
            this.isMax = true;
            this.depth = 0;
        }
        
        // Make the move (act)
        if (this.isMax) {
            state[actionX][actionY]  = GoButton.ButtonState.UPPER_A;
        }
        else {
            state[actionX][actionY]  = GoButton.ButtonState.UPPER_B;
        }
        updateState();
    }

    /**
     * Constructor for root node
     * @param state 
     * @param isMax 
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public GoNode(GoButton.ButtonState[][] state, boolean isMax) {
        
        children = new ArrayList();
        this.state = state;
        this.depth = 0;
        
        // There is no last action and parent as well
        this.actionX = -1;
        this.actionY = -1;
        this.parent = null;
        this.isMax = isMax;
    }

    @Override
    public String toString() {
        
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        
        for (int ri = 0; ri < this.state.length; ++ri) {
            for (int ci = 0; ci < this.state[ri].length; ++ci) {
                result.append(state[ri][ci]);
                result.append(" ");
            }
            result.append(NEW_LINE);
        }
        
        return result.toString();  
    }

    public List<GoNode> getChildren() {
        return this.children;
    }
    
    public GoNode getParent() {
        return this.parent;
    }

    public int getDepth() {
        return this.depth;
    }
    
    public void addChild(GoNode child) {       
        child.parent = this;
        this.children.add(child);
    }

    public GoButton.ButtonState[][] getState() {
        return this.state;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    private void updateState() {

        // DIAGONAL

        if (actionX - 2 >= 0 && actionY + 2 < state[0].length) // Up right
            for (int i = actionX - 1, j = actionY + 1; 
                    i >= 0 && j < state[0].length; --i, ++j) {
                if (state[i][j] == GoButton.ButtonState.UPPER_A || 
                        state[i][j] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, i, j);
                    break;
                }
                if (state[i][j] != GoButton.ButtonState.EMPTY) {
                    break;
                }  
            }
          
        if (actionY - 2 >=0 && actionX + 2 < state.length) // Down left
            for (int i = actionX + 1, j = actionY - 1; 
                    i < state.length && j >= 0; ++i, --j) {
                if (state[i][j] == GoButton.ButtonState.UPPER_A || 
                        state[i][j] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, i, j);
                    break;
                }
                if (state[i][j] != GoButton.ButtonState.EMPTY) {
                    break;
                }
            }
                
        if (actionX - 2 >= 0 && actionY - 2 >= 0) // Up left   
            for (int i = actionX - 1, j = actionY - 1; i >= 0 && j >= 0; --i, --j) {
                if (state[i][j] == GoButton.ButtonState.UPPER_A || 
                        state[i][j] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, i, j);
                    break;
                }
                if (state[i][j] != GoButton.ButtonState.EMPTY) {
                    break;
                }  
            }
  
        if (actionX + 2 < state.length && actionY + 2 < state[0].length) // Down right
            for (int i = actionX + 1, j = actionY + 1; 
                    i < state.length && j < state[0].length; ++i, ++j) {
                if (state[i][j] == GoButton.ButtonState.UPPER_A || 
                        state[i][j] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, i, j);
                    break;
                }
                if (state[i][j] != GoButton.ButtonState.EMPTY) {
                    break;
                }
            }

        // LINEAR
        
        if (actionX - 2 >= 0) // Up
            for (int i = actionX - 1; i >= 0; --i) {
                if (state[i][actionY] == GoButton.ButtonState.UPPER_A || 
                        state[i][actionY] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, i, actionY);
                    break;
                }
                if (state[i][actionY] != GoButton.ButtonState.EMPTY) {
                    break;
                }   
            }

        if (actionX + 2 < state.length) // Down
            for (int i = actionX + 1; i < state.length; ++i) {
                if (state[i][actionY] == GoButton.ButtonState.UPPER_A || 
                        state[i][actionY] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, i, actionY);
                    break;
                }
                if (state[i][actionY] != GoButton.ButtonState.EMPTY) {
                    break;
                } 
            }        
     
        if (actionY + 2 < state[0].length) // Right
            for (int i = actionY + 1; i < state[0].length; ++i) {
                if (state[actionX][i] == GoButton.ButtonState.UPPER_A || 
                        state[actionX][i] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, actionX, i);
                    break;
                }
                if (state[actionX][i] != GoButton.ButtonState.EMPTY) {
                    break;
                } 
            }   

        if (actionY - 2 >= 0) // Left
            for (int i = actionY - 1; i >= 0; --i) {
                if (state[actionX][i] == GoButton.ButtonState.UPPER_A || 
                        state[actionX][i] == GoButton.ButtonState.UPPER_B) {
                    fillGaps(actionX, actionY, actionX, i);
                    break;
                }
                if (state[actionX][i] != GoButton.ButtonState.EMPTY) {
                    break;
                } 
            }
    }

    private void fillGaps(int x0, int y0, int x1, int y1) {
    
        // LINEAR
        
        if (x0 == x1 && y0 < y1) {
            for (int i = y0 + 1; i < y1; ++i) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
        else if (x0 == x1 && y0 > y1) {
            for (int i = y1 + 1; i < y0; ++i) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }   
        }
        else if (y0 == y1 && x0 < x1) {
            for (int i = x0 + 1; i < x1; ++i) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
        else if (y0 == y1 && x0 > x1) {
            for (int i = x1 + 1; i < x0; ++i) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
        
        // DIAGONAL
        
        else if (x0 > x1 && y0 < y1) { // Up right
            for (int i = x0 - 1, j = y0 + 1; i > x1; --i, ++j) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
        else if (x0 < x1 && y0 > y1) { // Down left
            for (int i = x0 + 1, j = y0 - 1; i < x1; ++i, --j) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
        else if (x0 < x1 && y0 < y1) { // Down right
            for (int i = x0 + 1, j = y0 + 1; i < x1; ++i, ++j) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
        else if (x0 > x1 && y0 > y1) { // Up left
            for (int i = x0 - 1, j = y0 - 1; i > x1; --i, --j) {
                if (this.isMax){
                    state[x0][i] = GoButton.ButtonState.LOWER_A;
                }
                else {
                    state[x0][i] = GoButton.ButtonState.LOWER_B;
                }
            }
        }
    }
}