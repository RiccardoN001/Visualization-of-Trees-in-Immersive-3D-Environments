public class Point3D {
    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double getZ(){
        return this.z;
    }

    public void setX(double X){
        this.x = X;
    }

    public void setY(double Y){
        this.y = Y;
    }

    public void setZ(double Z){
        this.z = Z;
    }

    public boolean equals(Point3D p){
        if(this.x == p.getX() && this.y == p.getY() && this.z == p.getZ()){
            return true;
        }
        else{
            return false;
        }
    }

    public String toString(){

        return "[x: "+ x +", y: "+y+", z: "+z+"]";
    }
}
