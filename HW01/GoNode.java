import java.util.*;

public class GoNode {
    
    private final GoButton.ButtonState[][] state;
    private GoNode parent;
    private final List<GoNode> children;
    private final int depth;
    private final int actionX;
    private final int actionY;

    @SuppressWarnings("LeakingThisInConstructor")
    public GoNode(GoButton.ButtonState[][] state, GoNode parent, int actionX, int actionY) {
        
        children = new ArrayList();
        this.state = state;
        this.actionX = actionX;
        this.actionY = actionY;
        
        if (parent != null) {
            this.depth = parent.getDepth() + 1;
            parent.addChild(this);
        }
        else {
            this.depth = 0;
        }
        
        // Make the move (act)
        state[actionX][actionY] = GoButton.ButtonState.BLACK_X;          
        updateState();
    }

    /**
     * Constructor for root node
     * @param state 
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public GoNode(GoButton.ButtonState[][] state) {
        
        children = new ArrayList();
        this.state = state;
        this.depth = 0;
        
        // There is no last action and parent as well
        this.actionX = -1;
        this.actionY = -1;
        this.parent = null;    
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
            
        if (actionX - 2 >= 0) // Up
            for (int i = actionX - 1; i >= 0; --i) {
                if (state[i][actionY] == GoButton.ButtonState.BLACK_X) {
                    fillGaps(actionX, actionY, i, actionY);
                    break;
                }
                if (state[i][actionY] != GoButton.ButtonState.EMPTY) {
                    break;
                }   
            }

        if (actionX + 2 < state.length) // Down
            for (int i = actionX + 1; i < state.length; ++i) {
                if (state[i][actionY] == GoButton.ButtonState.BLACK_X) {
                    fillGaps(actionX, actionY, i, actionY);
                    break;
                }
                if (state[i][actionY] != GoButton.ButtonState.EMPTY) {
                    break;
                } 
            }        
     
        if (actionY + 2 < state[0].length) // Right
            for (int i = actionY + 1; i < state[0].length; ++i) {
                if (state[actionX][i] == GoButton.ButtonState.BLACK_X) {
                    fillGaps(actionX, actionY, actionX, i);
                    break;
                }
                if (state[actionX][i] != GoButton.ButtonState.EMPTY) {
                    break;
                } 
            }   
        
        if (actionY - 2 >= 0) // Left
            for (int i = actionY - 1; i >= 0; --i) {
                if (state[actionX][i] == GoButton.ButtonState.BLACK_X) {
                    fillGaps(actionX, actionY, actionX, i);
                    break;
                }
                if (state[actionX][i] != GoButton.ButtonState.EMPTY) {
                    break;
                } 
            }
    }
    
    private void fillGaps(int x0, int y0, int x1, int y1) {
    
        if (x0 == x1 && y0 < y1) {
            for (int i = y0 + 1; i < y1; ++i) {
                state[x0][i] = GoButton.ButtonState.BLACK_O;      
            }
        }
        else if (x0 == x1 && y0 > y1) {
            for (int i = y1 + 1; i < y0; ++i) {
                state[x0][i] = GoButton.ButtonState.BLACK_O;      
            }   
        }
        else if (y0 == y1 && x0 < x1) {
            for (int i = x0 + 1; i < x1; ++i) {
                state[i][y0] = GoButton.ButtonState.BLACK_O;      
            }
        }
        else if (y0 == y1 && x0 > x1) {
            for (int i = x1 + 1; i < x0; ++i) {
                state[i][y0] = GoButton.ButtonState.BLACK_O;      
            }
        }
    }
}