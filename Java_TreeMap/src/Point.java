public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double X){
        this.x = X;
    }

    public void setY(double Y){
        this.y = Y;
    }

    public boolean equals(Point p){
        if(this.x == p.getX() && this.y == p.getY()){
            return true;
        }
        else{
            return false;
        }
    }

    public String toString(){

        return "[x: "+ x +", y: "+y+"]";
    }
}
