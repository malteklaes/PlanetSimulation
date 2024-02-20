import java.awt.*;

public class Vector3 {

    // This class represents vectors in a 3D vector space.
    private double x;
    private double y;
    private double z;


    public Vector3() {
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the sum of this vector and vector 'v'.
     *
     * @param v (Vector3)
     * @return Vector3
     */
    public Vector3 plus(Vector3 v) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = this.x + v.getX();
        result.y = this.y + v.getY();
        result.z = this.z + v.getZ();

        return result;

    }

    /**
     * Returns the product of this vector and 'd'.
     *
     * @param d (Vector3)
     * @return Vector3
     */
    public Vector3 times(double d) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = this.x * d;
        result.y = this.y * d;
        result.z = this.z * d;

        return result;
    }

    /**
     * Returns the sum of this vector and -1*v.
     *
     * @param v (Vector3)
     * @return Vector3
     */
    public Vector3 minus(Vector3 v) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = this.x - v.getX();
        result.y = this.y - v.getY();
        result.z = this.z - v.getZ();

        return result;
    }

    /**
     * Returns the Euclidean distance of this vector to the specified vector 'v'.
     *
     * @param v (Vector3)
     * @return double
     */
    public double distanceTo(Vector3 v) {
        return Math.sqrt(Math.pow(this.x - v.getX(), 2) + Math.pow(this.y - v.getY(), 2) + Math.pow(this.z - v.getZ(), 2));
    }

    /**
     * Returns the length (norm) of this vector.
     *
     * @return double
     */
    public double length() {
        return distanceTo(new Vector3());
    }

    /**
     * Normalizes this vector: changes the length of this vector such that it becomes 1.
     * The direction and orientation of the vector is not affected.
     */
    public void normalize() {
        double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    /**
     * berechnet den Massenschwerpunkt zweier Körper
     * -> arbeitet mit Body zsm
     *
     * @param v           (bekommt die Position)
     * @param mbody1
     * @param mbody2
     * @param mbody1body2
     * @return Vector3
     */
    public Vector3 centerOfMass(Vector3 v, double mbody1, double mbody2, double mbody1body2) {
        double a = (this.x * mbody1 + v.x * mbody2) / mbody1body2;
        double b = (this.y * mbody1 + v.y * mbody2) / mbody1body2;
        double c = (this.z * mbody1 + v.z * mbody2) / mbody1body2;
        return new Vector3(a, b, c);
    }


    /**
     * Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
     * in the existing StdDraw canvas. The z-coordinate is not used.
     *
     * @param radius (double)
     * @param color  (Color)
     */
    public void drawAsDot(double radius, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(this.x, this.y, radius);
    }

    public void drawPositionSquare(double radius, int ebene){
        StdDraw.setPenColor(StdDraw.YELLOW);
        if(ebene != 0){
            StdDraw.setPenRadius(0.001 / ebene);
        }
        StdDraw.rectangle(this.x/2, this.y/2, radius,radius);
    }


    /**
     * Returns the coordinates of this vector in brackets as a string
     * in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
     *
     * @return String
     */
    public String toString() {
        return "[" + this.x + "," + this.y + "," + this.z + "]";
    }

    /**
     * Getter und Setter
     */
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}


