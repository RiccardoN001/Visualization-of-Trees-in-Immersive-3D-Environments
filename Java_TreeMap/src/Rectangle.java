
public class Rectangle {
    private double width;
    private double height;
    private Point vertex;

    public Rectangle(Point v, double w, double h){
        this.vertex = v;
        this.width = w;
        this.height = h;
    }

    public Point center(){
        double x = vertex.getX() + width/2;
        double y = vertex.getY() + height/2;
        Point center = new Point(x, y);
        return center;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public void setWidth(double w){
        width = w;
    }

    public void setHeight(double h){
        height = h;
    }


    public Point getVertex(){
        return vertex;
    }

    public void remove(Rectangle r){

        if((this.vertex.getX() == r.getVertex().getX() || this.vertex.getY() == r.getVertex().getY()) && (this.height == r.getHeight() || this.width == r.getWidth())){

            if(height == r.getHeight()){
                if(vertex.getX() == r.getVertex().getX()){
                    vertex.setX(vertex.getX()+ r.getWidth());  
                }
                
                width -= r.getWidth();
            }
            else if(width == r.getWidth()){
                if(vertex.getY() == r.getVertex().getY()){
                    vertex.setY(vertex.getY() + r.getHeight());
                }
                
                height -= r.getHeight();
            }
        }
        else{
            System.out.println("Errore nel posizionamento del vertice o nelle dimensioni");
        }
    }

    public String toString(){
        return("x: "+ vertex.getX() +", y: "+ vertex.getY() +", Width: "+ width +", Height: "+ height);
    }

    public double aspectRatio(){

        if(this.width>this.height){
            return this.width/this.height;
        }
        else{
            return this.height/this.width;
        } 
    }
}
