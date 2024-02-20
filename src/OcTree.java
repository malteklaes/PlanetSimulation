import java.awt.*;

public class OcTree {

    private OcTree[] children;
    private Body body;
    private Octant octant;
    private double T;
    private int ebene = 0;
    private static int deepestLevel = 0;



    public OcTree(Octant octant, double T, int ebene) {
        this.octant = octant;
        // Sicherstellen, dass T im Intervall [0,1] liegt
        if (T > 1) {
            this.T = 1;
        } else if (T < 0) {
            this.T = 0;
        } else {
            this.T = T;
        }
        body = null;
        children = new OcTree[8];
        this.ebene = ebene;
        this.ebene += 1;
    }

    /**
     * Fügt Körper zu passendem Octant hinzu und erstellt nötige Eltern-Kind-Struktur
     *
     * @param body (Body)
     * @return boolean
     */
    public boolean addToTree(Body body) {
        if (this.body == null) {
            this.body = body;
            return true;
        }
        if (recognizeLeave() == false) {
            this.body = this.body.merge(body);
            addToOctant(body);
            return true;
        } else {
            for (int i = 0; i <= 7; i++) {
                children[i] = new OcTree(octant.initOctant(i), this.T, ebene); //hier neue Unterbäume mit der jeweiligen root = children[i] erstellen
            }
            addToOctant(this.body);
            addToOctant(body); // Kinder einfügen
            this.body = this.body.merge(body);
            return true;
        }
    }

    /**
     * Fügt Körper zu dem vorgesehenen Octanten innerhalb eines gesamten Octrees hinzu
     *
     * @param body
     * @return boolean
     */
    public void addToOctant(Body body) {
        for (int i = 0; i <= 7; i++) {
            if (octant.initOctant(i).contains(body)) {
                children[i].addToTree(body);
                break;
            }
        }
    }

    /**
     * Überprüft, falls this ein Blattknoten ist und gibt true zurück, falls das zutrifft
     *
     * @return boolean
     */
    public boolean recognizeLeave() {
        for (int i = 0; i <= 7; i++) {
            if (children[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Berechnet rekursiv die Kraft, die auf einen Körper wirkt.
     *
     * @param body (braucht Typ CelestialBody)
     */
    public void calculateForce(Body body) {
        if (this.body == null || this.body.equals(body)) {
            return;
        }
        if (recognizeLeave()) {
            body.gravitationalForce(this.body);
        } else {
            double r = this.body.distanceTo(body) * 10; // *10, um die Sichtbarkeit zwischen 0.0 und 1.0 zu erhöhen
            double d = octant.getLength();
            if ((d / r) < T) {
                body.gravitationalForce(this.body);
            } else {
                for (int i = 0; i <= 7; i++) {
                    children[i].calculateForce(body);
                }
            }
        }
    }


    public void move(Body body_input) {
        Vector3 newCurrentMovement = body_input.getCurrentMovement().plus(body_input.getForce().times(1 / body_input.getMass()));
        Vector3 newPosition = body_input.getPosition().plus(newCurrentMovement);
        body_input.setCurrentMovement(newCurrentMovement);
        body_input.setPosition(newPosition);
    }

    public void draw(Body body_input) {
        body_input.getPosition().drawAsDot((5.e8 * Math.log10(body_input.getRadius())), body_input.getColor());
    }

    public void drawSquare(Body body_input) {
        body_input.getPosition().drawPositionSquare((5.e9 * Math.log10(body_input.getRadius())), this.ebene);
        for (int i = 0; i < children.length; i++) {
            StdDraw.setPenColor(StdDraw.YELLOW);
            if(this.ebene != 0){
                StdDraw.setPenRadius(0.001 / (this.ebene*5));
            }
            if(children[i] != null && children[i].getBody() != null && body_input != null) {
                StdDraw.line(children[i].getBody().getX(), children[i].getBody().getY(), body_input.getX(), body_input.getY());
            }
        }
    }


    public Octant getOctant() {
        return octant;
    }

    public Body getBody() {
        return body;
    }

    public void printDeepestLevel(){
        deepestLevel++;
        int counter =+ 1;
        for (int i = 0; i < children.length; i++) {
            if(octant != null && octant.initOctant(i).contains(this.body)){
                    if(children[i] != null ){
                        children[i].printDeepestLevel();
                    }
            }
        }
        //return counter;
    }

    public static int getDeepestLevel() {
        //printDeepestLevel();
        return deepestLevel;
    }

    public boolean numberOfBodiesAtLevel(int level){
        System.out.println(this.ebene);
        for (int i = 0; i < children.length; i++) {
            if(octant != null && octant.initOctant(i).contains(this.body)){
                if(level != 0) {
                    if(children[i] != null ){
                        children[i].numberOfBodiesAtLevel(level--);
                    }
                }
                else{
                    System.out.println(this.ebene);
                }
            }
        }
        return true;
        /*int count =0;
        if(level > 10){
            return 0;
        }
        *//*for (int i = 0; i < level; i++) {
            if()
        }*//*



        if(level != ebene){
            this.numberOfBodiesAtLevel(ebene++);
        }
        else if(level == ebene){
            for (OcTree t:children) {
                count++;
            }
        }
        return count;*/
    }
}
