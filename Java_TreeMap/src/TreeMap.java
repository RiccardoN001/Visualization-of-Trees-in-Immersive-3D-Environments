import java.util.LinkedList;
import java.util.Stack;
import java.lang.Math;
import java.util.Collections;
import java.util.Comparator;

public class TreeMap {
    private static int test = 0;
    public static int getCount(){
        return test;
    } 
    //SLICE AND DICE ----------------------------------------------------------------------------------------------------------------
    public static void treeMapBoundsSliceNDice(Tree t, Rectangle availableSpace){
        Node root = t.getRoot();
        root.setBound(availableSpace);
        LinkedList<Node> nodes = root.getChildren();
        layoutNodes(nodes, availableSpace);
    }

    private static void layoutNodes(LinkedList<Node> nodes, Rectangle availableSpace){
        int count = 0;
        for(int i=0; i<nodes.size(); i++){
            Rectangle bound = sliceAndDice(nodes.get(i), availableSpace, count);
            nodes.get(i).setBound(bound);
            availableSpace.remove(nodes.get(i).getBound());
            count+= nodes.get(i).getData();
            
            if(!nodes.get(i).getChildren().isEmpty()){
                Rectangle parentAvailableSpace = new Rectangle(new Point(nodes.get(i).getBound().getVertex().getX(), nodes.get(i).getBound().getVertex().getY()), nodes.get(i).getBound().getWidth(), nodes.get(i).getBound().getHeight());
                layoutNodes(nodes.get(i).getChildren(), parentAvailableSpace);
            }
        }
    }

    private static Rectangle sliceAndDice(Node n, Rectangle space, int size){
        double tot = (double)n.getParent().getData() - 1 - size;

        if(n.getDepth()%2 != 0){
            Rectangle bound = new Rectangle(space.getVertex(), space.getWidth() * (double)(n.getData()/tot), space.getHeight());
            return bound;
        }
        else{
            Rectangle bound = new Rectangle(space.getVertex(), space.getWidth(), space.getHeight() * (n.getData()/tot));
            return bound;
        }
    }
    //SQUARIFIED-----------------------------------------------------------------------------------------------------------------------
    public static void treeMapBoundsSquarified(Tree t, Rectangle availableSpace){
        Node root = t.getRoot();
        root.setBound(availableSpace);
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.addAll(root.getChildren());
        squarified(nodes, availableSpace);
    }
    public static void squarified(LinkedList<Node> nodes, Rectangle space){
        LinkedList<Node> nodesForRecursion = new LinkedList<>();
        nodesForRecursion.addAll(nodes);
        double parentTot = Math.sqrt(currentParentNodesData(nodes)+1);
            double aspectRatioNew = 10;
            double aspectRatioOld = 11;
            Stack<Node> currentNodes = new Stack<Node>();
            Stack<Rectangle> backUp = new Stack<Rectangle>();
            int iter=0;
            while(aspectRatioNew<aspectRatioOld){
                backUp.removeAllElements();
                backUp = backUpBound(currentNodes);

                aspectRatioOld = aspectRatioNew;
                currentNodes.push(nodes.get(iter));
                int totDataNodes = currentNodesData(currentNodes);
                if(space.getWidth() >= space.getHeight()){
                    Rectangle tempSpace = new Rectangle(space.getVertex(), ((totDataNodes/parentTot)*space.getWidth())/parentTot, space.getHeight());
                    for(Node n : currentNodes){
                        double width = (totDataNodes/parentTot);
                        //System.out.println("Print width:" + width);
                        double height = (((double)n.getData()/totDataNodes)*parentTot);
                        //System.out.println("Height: "+ height);
                        Rectangle bound = new Rectangle(tempSpace.getVertex(), (width*space.getWidth())/parentTot, (height*space.getHeight())/parentTot);
                        System.out.println(bound);
                        n.setBound(bound);
                        tempSpace.remove(bound);
                    }
                    aspectRatioNew = worstAspectRatio(currentNodes);
                    iter++;
                }
                else{
                    for(Node n : currentNodes){
                        double height = (totDataNodes/parentTot);
                        double width = ((double)n.getData()/totDataNodes)*parentTot;
                        Rectangle bound = new Rectangle(space.getVertex(), (width*space.getWidth())/parentTot, (height*space.getHeight())/parentTot);
                        n.setBound(bound);
                    }
                    aspectRatioNew = worstAspectRatio(currentNodes);
                    iter++;
                }
                System.out.println("Numero di Nodi in CurrentNodes: "+currentNodes.size());
            }
            currentNodes.pop();
            for(int i=0;i<currentNodes.size();i++){
                    currentNodes.get(i).setBound(backUp.get(i));
                    System.out.println(backUp.get(i).toString());
                }
            System.out.println("Numero di Nodi in backup: "+backUp.firstElement().toString());
            System.out.println("Total: "+ totalBound(backUp, space).toString());
            //space.remove(totalBound(backUp, space));

            for(int k=0; k<=iter; k++){//rimuovo i nodi già analizzati
                nodes.removeFirst();
            }
            if(nodes.size()==1){
                nodes.getFirst().setBound(space);
            }
            else{
              squarified(nodes, space);  
            }

        for(Node n : nodesForRecursion){
            if(!n.getChildren().isEmpty()){
                Rectangle parentAvailableSpace = new Rectangle(n.getBound().getVertex(), n.getBound().getWidth(), n.getBound().getHeight());
                LinkedList<Node> childrenNodes = new LinkedList<Node>();
                childrenNodes.addAll(n.getChildren());
                squarified(childrenNodes, parentAvailableSpace);
            }
        }
        
    }
    public static Rectangle totalBound(Stack<Rectangle> rect, Rectangle ASpace){
        if(rect.size()==1){
            System.out.println("First element");
            return rect.firstElement();
        }
        else{
            if(rect.get(0).getWidth()==rect.get(1).getWidth()){
                System.out.println("Stessa width");
                return new Rectangle(ASpace.getVertex(), rect.firstElement().getWidth(), ASpace.getHeight());
            }
            else{
                System.out.println("Stessa height");
                return new Rectangle(ASpace.getVertex(), ASpace.getWidth(), rect.firstElement().getHeight());
            }
        }
    }
    private static double worstAspectRatio(Stack<Node> nodes){
        double[] worst = new double[nodes.size()];
        double ris = 0;
        for(Node n : nodes){
            double height = n.getBound().getHeight();
            double width = n.getBound().getWidth();

            if(width>height){
                worst[nodes.indexOf(n)] = width/height;
            }
            else{
                worst[nodes.indexOf(n)] = height/width;
            }
        }
        for(int i=0; i<worst.length;i++){
            if(worst[i]>ris){
                ris = worst[i];
            }
        }
        return ris;
    }
    private static int currentNodesData(Stack<Node> currentNodes){
        int data = 0;
        for(Node element : currentNodes){
            data += element.getData();
        }
        return data;
    }
    private static int currentParentNodesData(LinkedList<Node> currentNodes){
        int data = 0;
        for(Node element : currentNodes){
            data += element.getData();
        }
        return data;
    }
    private static Stack<Rectangle> backUpBound(Stack<Node> nodes){
        Stack<Rectangle> backUp = new Stack<Rectangle>();
        for(Node n : nodes){
            backUp.add(n.getBound());
        }
        //Collections.reverse(backUp);
        return backUp;
    }
    //SPLIT----------------------------------------------------------------------------------------------------------------------------
    public static void treeMapBoundsSplit(Tree t, Rectangle availableSpace){
        Node root = t.getRoot();
        root.setBound(availableSpace);
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.addAll(root.getChildren());
        split(nodes, availableSpace);
    }
    
    private static void split(LinkedList<Node> nodes, Rectangle space){
        //count++;
        if(nodes.size() == 0){
            return;
        }

        if(nodes.size() == 1){
            Rectangle bound = new Rectangle(new Point(space.getVertex().getX(), space.getVertex().getY()), space.getWidth(), space.getHeight());
            nodes.getFirst().setBound(bound);

           if (!nodes.getFirst().getChildren().isEmpty()) {
                Rectangle parentAvailebleSpace = new Rectangle(new Point(bound.getVertex().getX(), bound.getVertex().getY()), bound.getWidth(), bound.getHeight());
                split(nodes.getFirst().getChildren(), parentAvailebleSpace);
            }
        }
        else{
            LinkedList<LinkedList<Node>> generateGroups = groups(nodes);
            double tot = 0;
            double firstGroupData = 0;
            for(Node n : nodes){
                tot += n.getData();
                if(generateGroups.getFirst().contains(n)){
                    firstGroupData += n.getData();
                }
            }
            Rectangle bound;
            if(space.getWidth() >= space.getHeight()){
                bound = new Rectangle(new Point(space.getVertex().getX(), space.getVertex().getY()), space.getWidth() * (firstGroupData / tot), space.getHeight());
            } else {
                bound = new Rectangle(new Point(space.getVertex().getX(), space.getVertex().getY()), space.getWidth(), space.getHeight() * (firstGroupData / tot));
            }
            space.remove(bound);
            split(generateGroups.getFirst(), bound);
            split(generateGroups.getLast(), space);
        }
    }
    
    private static LinkedList<LinkedList<Node>> groups(LinkedList<Node> nodes) {
        LinkedList<Node> group1 = new LinkedList<>();
        LinkedList<Node> group2 = new LinkedList<>();
        double sum1 = 0, sum2 = 0;

        for (Node node : nodes) {
            // Assegna il nodo al gruppo con la somma minore
            if (sum1 <= sum2) {
                group1.add(node);
                sum1 += node.getData();
            } else {
                group2.add(node);
                sum2 += node.getData();
            }
        }

        LinkedList<LinkedList<Node>> result = new LinkedList<>();
        result.add(group1);
        result.add(group2);
        return result;
    }

    public static double mediumAspectRatio(Tree t){
        int totNodes = t.getNodes().size();
        double totAspect = 0;

        for(Node n : t.getNodes()){
            totAspect += n.getBound().aspectRatio();
        }
        return totAspect/totNodes;
    }

    public static void calculated3DCordinates(Tree tree){

        Collections.sort(tree.getNodes(), new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return Integer.compare(n1.getId(), n2.getId());
            }
        });
        int maxDepth = 0;
        for(int i=0; i<tree.getNodes().size(); i++){
            if(tree.getNodes().get(i).getDepth()>maxDepth){
                maxDepth = tree.getNodes().get(i).getDepth();
            }
        }
        calculated3DCordinatesRec(tree.getRoot(), maxDepth);
    }
    public static void calculated3DCordinatesRec(Node n, int maxDepth){
        int initialZ = 10*(maxDepth);
        n.setCoordinates(new Point3D(n.getBound().center().getX(), n.getBound().center().getY(), initialZ-(n.getDepth())*10));
        
        if(!n.getChildren().isEmpty()){
            for(Node child : n.getChildren()){
                calculated3DCordinatesRec(child, maxDepth);
            }
        }
    }
}