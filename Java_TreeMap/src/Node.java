import java.util.LinkedList;

public class Node{ //Classe che definisce un nodo
        private int data;
        private Node parent;
        private LinkedList<Node> children;
        private int depth;
        private Rectangle bound;
        private int id;
        private Point3D coordinates;

        public Node(){
            this.id = 0;
            this.coordinates = new Point3D(0,0,0);
            this.data = 0;
            this.children = new LinkedList<Node>();
            this.bound = new Rectangle(new Point(0, 0), 0, 0);
        }

        public int getId(){
            return id;
        }

        public void setId(int i){
            id = i;
        }
        public Point3D getCoordinates(){
            return coordinates;
        }
        public void setCoordinates(Point3D point){
            coordinates.setX(point.getX());
            coordinates.setY(point.getY()); 
            coordinates.setZ(point.getZ()); 
        }
        public void setCoordinatesX(double x){
            coordinates.setX(x);
        }
        public void setCoordinatesY(double y){
            coordinates.setY(y);
        }
        public void setCoordinatesZ(double z){
            coordinates.setZ(z);
        }
        
        public int getData(){
            return data;
        }
        public void setData(int d){
            data = d;
        }

        public int getDepth(){
            return depth;
        }

        public void setDepth(int de){
            depth = de;
        }

        public Node getParent(){
            return parent;
        }

        public void setParent(Node p){
            parent = p;
        }

        public LinkedList<Node> getChildren(){
            return children;
        }

        public void addChild(Node n){
            children.add(n);
        }

        public Rectangle getBound(){
            return bound;
        }

        public void setBound(Rectangle b){
            this.bound.getVertex().setX(b.getVertex().getX());
            this.bound.getVertex().setY(b.getVertex().getY());
            this.bound.setWidth(b.getWidth());
            this.bound.setHeight(b.getHeight());
        }
    }