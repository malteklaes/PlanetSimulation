import java.awt.*;
import javax.management.ValueExp;

public class Body {


    public static final double G = 6.6743e-11;
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Color color;
    private Vector3 force;


    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
    }

    /**
     * Returns the distance between this body and the specified 'body'.
     */
    public double distanceTo(Body body) {
        return this.position.distanceTo(body.position);
    }

    /**
     * Returns a vector representing the gravitational force exerted by 'body' on this body.
     * The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
     * interacting, r being the distance between the centers of the masses and G being the gravitational constant.
     * To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
     * calculated force
     */
    public void gravitationalForce(Body body) {
        Vector3 direction = body.position.minus(this.position); // minus(b2.getPosition(),b1.getPosition());
        double distance = direction.length();
        direction.normalize();
        double force_calc = G * this.mass * body.mass / (distance * distance);
        this.force = direction.times(force_calc);
    }


    /**
     * Merge zwei bodies, indem es den Mittelpunkt der Masse der Körper berechnet (centerofMass)
     * -> arbeitet mit OcTree zsm
     * Unifies two bodies by calculating the center of mass of these two bodies.
     * Returns a new Celestialbody with the center of mass as position.
     *
     * @param body
     * @return Body (neuen)
     */
    public Body merge(Body body) {
        double gesamtmasse = this.mass + body.mass;
        Vector3 g = position.centerOfMass(body.position, this.mass, body.mass, gesamtmasse);
        return new Body(name + body.name, gesamtmasse, (radius + body.radius) / 2, g, new Vector3(0, 0, 0), color);
    }


    /**
     * Returns a string with the information about this body including
     * name, mass, radius, position and current movement. Example:
     * "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
     */
    public String toString() {
        //String result = "" + name + ", " + mass + " kg, radius: [" + radius + "] m, position: " + position + " m, movement: " + currentMovement + " m/s";
        return name;
    }


    public void drawMovementDirection() {
        Vector3 new_bewegungs_vec = this.currentMovement.times(1e1);
        //this.setNew_bewegungs_vec(new_bewegungs_vec);
        new_bewegungs_vec.setX(this.position.getX() + new_bewegungs_vec.getX());
        new_bewegungs_vec.setY(this.position.getY() + new_bewegungs_vec.getY());
        new_bewegungs_vec.setZ(this.position.getZ() + new_bewegungs_vec.getZ());
        StdDraw.setPenColor(this.color);
        StdDraw.line(position.getX(), position.getY(), new_bewegungs_vec.getX(), new_bewegungs_vec.getY());
    }

    public void switchPosition(Body body) {
        Vector3 zwischen_position = this.position;
        this.position = body.position;
        body.position = zwischen_position;
    }



    /**
     * Getter und Setter
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setCurrentMovement(Vector3 currentMovement) {
        this.currentMovement = currentMovement;
    }


    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }

    public void setForce(Vector3 vector3) {
    }


    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getCurrentMovement() {
        return currentMovement;
    }

    public Color getColor() {
        return color;
    }

    public Vector3 getForce() {
        return force;
    }
}

