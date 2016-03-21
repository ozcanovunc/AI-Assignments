import java.util.*;
import java.util.List;

public class GoDfs extends AbstractGo {
 
    private Stack<GoNode> fringe;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    GoDfs(int rows, int cols) {
        
        fringe = new Stack();
        boardState = new GoButton.ButtonState[rows][cols];
        this.rows = rows;
        this.cols = cols;
        initBoard();
    }
    
    @Override
    GoNode treeSearch() {

        // Initialize the search tree
        initBoardState();
        GoNode root = new GoNode(boardState);
        GoNode headNode;
        List<GoNode> expandedNodes;
        
        fringe.push(root);
        
        while (true) {
            // If fringe is empty then return failure
            if (fringe.isEmpty())
                return null;
            
            headNode = fringe.pop();

            // If the node contains a goal state then return it
            if (goalTest(headNode.getState()))
                return headNode;
            
            // Expand the node and add the resulting nodes to the search tree     
            expandedNodes = expand(headNode);
            if (expandedNodes != null) {
                fringe.addAll(expandedNodes);
            }
        } 
    }

    @Override
    List<GoNode> expand(GoNode parent) {
        
        List<GoNode> successors = new ArrayList();
        GoButton.ButtonState[][] parentState = parent.getState();
        
        for (int ri = 0; ri < rows; ++ri) {
            for (int ci = 0; ci < cols; ++ci) {
                if (parentState[ri][ci] == GoButton.ButtonState.EMPTY) {
                    // Add new child
                    successors.add(new GoNode(
                            copyStateArray(parent.getState()), 
                            parent, ri, ci));
                }   
            }
        }
        return successors; 
    }
}
