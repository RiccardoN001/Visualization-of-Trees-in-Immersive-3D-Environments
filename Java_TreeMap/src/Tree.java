import java.util.LinkedList;

public class Tree {
    private Node root;
    private LinkedList<Node> treeNodes;

    public Tree(){
        this.root = null;
        treeNodes = new LinkedList<Node>();
    }

    public Node getRoot(){
        return root;
    }
    public void setRoot(Node n){
        root = n;
    }

    public void addNode(Node n){
        treeNodes.add(n);
    }

    public LinkedList<Node> getNodes(){
        return treeNodes;
    }

    public void setTreeParentsRecursive(Node n){
        if(n == root){
            n.setParent(null);
        }
        
        if(!n.getChildren().isEmpty()){
            for(int i=0;i<n.getChildren().size(); i++){
                n.getChildren().get(i).setParent(n);
                setTreeParentsRecursive(n.getChildren().get(i));
            }
        }
    }

    public void calculateLeafDepthRecursive(Node n, int currentDepth){
        
        n.setDepth(currentDepth);
        
        if(!n.getChildren().isEmpty()){
            for(int i=0; i<n.getChildren().size(); i++){
                calculateLeafDepthRecursive(n.getChildren().get(i), currentDepth + 1);
            }
        }

    }

    public int calculateSubtreeSizeRecursive(Node n){
        int size = 1;

        if(n.getChildren().isEmpty()){
            n.setData(size);
        }
        else{
            for(Node node : n.getChildren()){
            size += calculateSubtreeSizeRecursive(node);
            n.setData(size);
            }
        }

        return size;
    }
}
