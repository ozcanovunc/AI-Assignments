import java.io.IOException;

public class GoGame {

    public static void main(String[] args) throws IOException {
        
        GoBfs game = new GoBfs(3,4);

        System.in.read();
        
        GoNode resultNode = game.treeSearch();      
        System.out.println(resultNode);
    }
}