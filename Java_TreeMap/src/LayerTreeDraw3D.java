public class LayerTreeDraw3D {

    private static final int LEAF_WIDTH = 10;
    private static final int LEAF_HEIGHT = 10;
    private static int count =0;

    public static void layoutTree(Node node, int xPosition, int yPosition){
        relativeBounds(node, xPosition, yPosition);
        absoluteBounds(node, xPosition, yPosition);
    }
    public static void relativeBounds(Node node, int xPosition, int yPosition) {
        
        count++;
        if (node == null) return;
        
        if (node.getChildren().isEmpty()) {
            node.setBound(new Rectangle(new Point(xPosition, yPosition), LEAF_WIDTH, LEAF_HEIGHT));

        } else {
            int maxSize = 0;
            int offset = 0;

            for (Node child : node.getChildren()) {
                if(node.getDepth()%2 == 0){
                    relativeBounds(child, xPosition + offset, yPosition);
                    offset += child.getBound().getWidth();
                    maxSize = Math.max(maxSize, (int)child.getBound().getHeight());
                }
                else{
                    relativeBounds(child, xPosition, yPosition + offset);
                    offset += child.getBound().getHeight();
                    maxSize = Math.max(maxSize, (int)child.getBound().getWidth());
                }
            }

            if(node.getDepth()%2 == 0){
                double parentX = (offset-LEAF_WIDTH)/2;
                node.setBound(new Rectangle(new Point(parentX, yPosition), offset, maxSize));
            }
            else{
                double parentY = (offset-LEAF_HEIGHT)/2;
                node.setBound(new Rectangle(new Point(xPosition, parentY), maxSize, offset));
            }
                
        }
    }

    private static void absoluteBounds(Node node, int xPosition, int yPosition){
        count++;
        node.setBound(new Rectangle(new Point(xPosition, yPosition), node.getBound().getWidth(), node.getBound().getHeight()));
    
        if(!node.getChildren().isEmpty()){
            int offset = 0;
            for(Node child : node.getChildren()){
    
                if(node.getDepth()%2 == 0){
                    absoluteBounds(child, xPosition + offset, yPosition + (int)(node.getBound().getHeight() - child.getBound().getHeight()) / 2);
                    offset += child.getBound().getWidth();
                } else {
                    absoluteBounds(child, xPosition + (int)(node.getBound().getWidth() - child.getBound().getWidth()) / 2, yPosition + offset);
                    offset += child.getBound().getHeight();
                }
            }
        }
    }
    
    public static int getCount(){
        return count;
    }
}
