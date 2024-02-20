
public class Octant {

    private Vector3 mid_vector;
    private double length;


    public Octant(double mid_x, double mid_y, double mid_z, double length) {
        this.mid_vector = new Vector3(mid_x, mid_y, mid_z);
        this.length = length;
    }


    /**
     * Überpfüt und gibt zurück, ob der jeweilige body im Octant liegt
     *
     * @param body
     * @return boolean
     */
    public boolean contains(Body body) {
        double half = length / 2;
        boolean x_pos_seite = (body.getX() <= mid_vector.getX() + half);
        boolean x_neg_seite = (body.getX() >= mid_vector.getX() - half);
        boolean y_pos_seite = (body.getY() <= mid_vector.getY() + half);
        boolean y_neg_seite = (body.getY() >= mid_vector.getY() - half);
        boolean z_pos_seite = (body.getZ() <= mid_vector.getZ() + half);
        boolean z_neg_seite = (body.getZ() >= mid_vector.getZ() - half);

        return (x_pos_seite && x_neg_seite && y_pos_seite && y_neg_seite && z_pos_seite && z_neg_seite);
    }


    /**
     * Einzelne Octante erzeugen
     * Einteilung des Cubes:
     * (0 0 0) meint (bspw.) (north/south west/east back/front)
     * (1 1 1) = (north west back)
     * (0 0 0) = (south east front)
     * <p>
     * Einteilung nach 3-Bit:
     * 1 = (1 1 1) (north west back)  = nwb
     * 2 = (1 1 0) (north west front) = nwf
     * 3 = (1 0 1) (north east back)  = neb
     * 4 = (1 0 0) (north east front) = nef
     * 5 = (0 1 1) (south west back)  = swb
     * 6 = (0 1 0) (south west front) = swf
     * 7 = (0 0 1) (south east back)  = seb
     * 8 = (0 0 0) (south east front) = sef
     *
     * @param place
     * @return
     */
    public Octant initOctant(int place) {
        if (place == 0) {
            double x = mid_vector.getX() - length / 4.0; // west
            double y = mid_vector.getY() + length / 4.0; // north
            double z = mid_vector.getZ() + length / 4.0; // back
            double len = length / 2.0;
            Octant nwb = new Octant(x, y, z, len);
            return nwb;
        } else if (place == 1) {
            double x = mid_vector.getX() - length / 4.0; // west
            double y = mid_vector.getY()  + length / 4.0; // north
            double z = mid_vector.getZ() - length / 4.0; // front
            double len = length / 2.0;
            Octant nwf = new Octant(x, y, z, len);
            return nwf;
        } else if (place == 2) {
            double x = mid_vector.getX() + length / 4.0; // east
            double y = mid_vector.getY()  + length / 4.0; // north
            double z = mid_vector.getZ() + length / 4.0; // back
            double len = length / 2.0;
            Octant neb = new Octant(x, y, z, len);
            return neb;
        } else if (place == 3) {
            double x = mid_vector.getX() + length / 4.0; // east
            double y = mid_vector.getY()  + length / 4.0; // north
            double z = mid_vector.getZ() - length / 4.0; // front
            double len = length / 2.0;
            Octant nef = new Octant(x, y, z, len);
            return nef;
        } else if (place == 4) {
            double x = mid_vector.getX() - length / 4.0; // west
            double y = mid_vector.getY()  - length / 4.0; // south
            double z = mid_vector.getZ() + length / 4.0; // back
            double len = length / 2.0;
            Octant swb = new Octant(x, y, z, len);
            return swb;
        } else if (place == 5) {
            double x = mid_vector.getX() - length / 4.0; // west
            double y = mid_vector.getY()  - length / 4.0; // south
            double z = mid_vector.getZ() - length / 4.0; // front
            double len = length / 2.0;
            Octant swf = new Octant(x, y, z, len);
            return swf;
        } else if (place == 6) {
            double x = mid_vector.getX() + length / 4.0; // east
            double y = mid_vector.getY()  - length / 4.0; // south
            double z = mid_vector.getZ() + length / 4.0; // back
            double len = length / 2.0;
            Octant seb = new Octant(x, y, z, len);
            return seb;
        } else if (place == 7) {
            double x = mid_vector.getX() + length / 4.0; // east
            double y = mid_vector.getY() - length / 4.0; // south
            double z = mid_vector.getZ() - length / 4.0; // front
            double len = length / 2.0;
            Octant sef = new Octant(x, y, z, len);
            return sef;
        } else return null;
    }


    /**
     * Getter
     */
    public double getLength() {
        return length;
    }

    public double getMid_X(){
        return this.mid_vector.getX();
    }

    public double getMid_Y(){
        return this.mid_vector.getY();
    }


}
