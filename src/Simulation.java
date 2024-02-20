import java.awt.*;
import java.util.Scanner;

public class Simulation {


    // gravitational constant
    public static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    private static int planetenAnzahl;
    private static double T_Wert;
    private static int scenario_option;
    private static Body[] bodies;
    private static int optionSpecialEffects;

    public static void main(String[] args) {

        // Einrichtung des Ausgabefensters
        StdDraw.setCanvasSize(1200, 1200);
        StdDraw.setXscale(-6 * AU, 6 * AU);
        StdDraw.setYscale(-6 * AU, 6 * AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);


        /**
         * Systemstart
         */
        // [1] Variablenbelegung
        interaction();
        bodies = new Body[planetenAnzahl];

        // [2] Szenariowahl
        scenario(scenario_option);

        // [3] Systemstart
        //initSystem(bodies);
        simulation();

    }


    private static void simulation() {
        // für Spezialeffekte, um Zeichnungsgeschwindigkeit, wenn gewollt, zu drosseln
        int counter = 0;


        while (true) {

            Octant octant = new Octant(0, 0, 0, AU * 12);
            OcTree octree = new OcTree(octant, T_Wert,0);

            for (int i = 0; i < bodies.length; i++) {
                if (octant.contains(bodies[i])) {
                    octree.addToTree(bodies[i]);
                }
            }
            //System.out.println(octree.numberOfBodiesAtLevel(2));
            System.out.println(octree.get;
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].setForce(new Vector3(0, 0, 0));
                octree.calculateForce(bodies[i]);
                octree.move(bodies[i]);
            }
            StdDraw.clear(StdDraw.BLACK);
            for (int i = 0; i < bodies.length; i++) {
                octree.draw(bodies[i]);
                // Bewegungsrichtungsvektoren
                if (optionSpecialEffects == 1) {
                    bodies[i].drawMovementDirection();
                }
                // schneller Tausch
                else if (optionSpecialEffects == 2) {
                    if ((i > 0)) {
                        bodies[i].switchPosition(bodies[(i - 1)]);
                    } else {
                        bodies[i].switchPosition(bodies[(i)]);
                    }
                }
                // langsamer Tausch
                else if (optionSpecialEffects == 3) {
                    if(counter % 10 == 0) {
                        if ((i > 0)) {
                            bodies[i].switchPosition(bodies[(i - 1)]);
                        } else {
                            bodies[i].switchPosition(bodies[(i)]);
                        }
                    }
                }
                // Bewegungsrichtungsvektoren und langsamer Tausch
                else if (optionSpecialEffects == 4) {
                    bodies[i].drawMovementDirection();
                    if(counter % 10 == 0) {
                        if (i > 0) {
                            bodies[i].switchPosition(bodies[(i - 1)]);
                        } else {
                            bodies[i].switchPosition(bodies[(i)]);
                        }
                    }
                }
                // Rechtecke um jeweilige Bodiesoktanten
                else if (optionSpecialEffects == 5) {
                    octree.drawSquare(bodies[i]);
                }
                // Rechtecke um jeweilige Bodiesoktanten mit Bewegungsrichtung
                else if (optionSpecialEffects == 6) {
                    octree.drawSquare(bodies[i]);
                    bodies[i].drawMovementDirection();
                }
                else{
                    continue;
                }

            }
            if(counter == 10){
                counter = 0;
            }

            counter++;
            StdDraw.show();
        }
    }

    public static void initSystem(Body[] bodies) {
        bodies[0] = new Body("Sol", 1.989e40, 696340e5, new Vector3(0, 0, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
        for (int i = 1; i < bodies.length; i++) {
            double angle = Math.random() * Math.PI * 2;
            bodies[i] = new Body("" + i, 1e25 * Math.random(), 1 + 1e3 * Math.random(),
                    new Vector3(Math.cos(angle) * 5 * AU * Math.random(), Math.sin(angle) * 5 * AU * Math.random(), AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                    new Vector3(0, 0, 0),
                    new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));

        }
    }

    public static void interaction() {
        // Planetenanzahl
        Scanner scanner_planetenAnzahl = new Scanner(System.in);
        System.out.println("[1] Wieviele Himmelskörper sollen erzeug werden? (Intervallempfehlung) [100,10000]");
        System.out.print(">> ");
        planetenAnzahl = scanner_planetenAnzahl.nextInt();

        // T-Wertbelegung
        /**
         * Constrain:
         *      - alle Werte größer 1 werden auf 1 gemapped
         *      - alle Werte kleiner 0 werden auf 0 gemapped
         */
        Scanner scanner_T_Wert = new Scanner(System.in);
        System.out.println("[2] Wie groß soll der T-Wert sein? (Komma-Angabe z.B. als '0,5') (Intervallempfehlung) [0,1]");
        System.out.print(">> ");
        T_Wert = scanner_T_Wert.nextDouble();

        // option in scenario
        Scanner scanner_option = new Scanner(System.in);
        System.out.println("[3] Welches Szenario? [0,3]");
        System.out.println("    (0) Szenario: System mit einer Sonne in der Mitte und zufällig verteilten Klasse-1/Klasse-2 Planeten (untersch. Radius und Masse)");
        System.out.println("    (1) Szenario: System ohne Sonne und zufällig verteilten Klasse-1/Klasse-2 Planeten (untersch. Radius und Masse)");
        System.out.println("    (2) Szenario: System mit acht konzentrisch angeordneten Sonnen und in zwei konzentrisch angeordneten Kreisbahnen von Planeten");
        System.out.println("    (3) Szenario: System mit zwei zufällig verteilten Schwarzen Löcher und zufällig verteilten Klasse-1/Klasse-2 Planeten (untersch. Radius und Masse)");
        System.out.println("    (4) Szenario: System mit einer Sonne in der Mitte und vertikal angeordneten, blauen Planeten (4 Klassen)");
        System.out.print(">> ");
        scenario_option = scanner_option.nextInt();

        //Specialeffects
        Scanner scanner_specialEffects = new Scanner(System.in);
        System.out.println("[4] Spezialeffekte");
        System.out.println("    (0) keine Effekte");
        System.out.println("    (1) Richtungsanzeige der Planetenbewegung");
        System.out.println("    (2) Zufälliger Planetentausch (schnell)");
        System.out.println("    (3) Zufälliger Planetentausch (langsam)");
        System.out.println("    (4) Richtungsanzeige der Planetenbewegung mit zufälligem Planetentausch");
        System.out.println("    (5) Rechtecke um die jeweiligen Planetenoktanten");
        System.out.println("    (6) Rechtecke um die jeweiligen Planetenoktanten mit Richtungsanzeige");
        System.out.print(">> ");
        optionSpecialEffects = scanner_specialEffects.nextInt();
    }

    public static void scenario(int option) {
        if (option == 0) {
            /** System mit einer Sonne in der Mitte und zufällig verteilten Klasse-1/Klasse-2 Planeten (untersch. Radius und Masse)
             */
            for (int i = 0; i < bodies.length; i++) {
                if (i % 1000 == 0) {
                    bodies[i] = new Body("" + i, 1.899e40 * Math.random(), 1 + 1e4 * Math.random(),
                            new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(0, 0, 0),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                } else {
                    bodies[i] = new Body("" + i, 1.899e30 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(0, 0, 0),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                }
            }
            bodies[0] = new Body("Sol", 1.9e40, 600e8, new Vector3(0, 0, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
        } else if (option == 1) {
            /** System ohne Sonne und zufällig verteilten Klasse-1/Klasse-2 Planeten (untersch. Radius und Masse)
             */
            for (int i = 0; i < bodies.length; i++) {
                if (i % 1000 == 0) {
                    bodies[i] = new Body("" + i, 1.899e40 * Math.random(), 1 + 1e4 * Math.random(),
                            new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(0, 0, 0),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                } else {
                    bodies[i] = new Body("" + i, 1.899e30 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(0, 0, 0),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                }
            }
        } else if (option == 2) {
            /** System mit acht konzentrisch angeordneten Sonnen und in zwei konzentrisch angeordneten Kreisbahnen von Planeten
             */
            bodies[0] = new Body("Sol1", 1.e40, 1e7, new Vector3(0, 0, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[1] = new Body("Sol2", 1.e35, 1e5, new Vector3(4 * AU, -4 * AU, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[2] = new Body("Sol3", 1.e35, 1e5, new Vector3(-4 * AU, 4 * AU, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[3] = new Body("Sol4", 1.e35, 1e5, new Vector3(-4 * AU, -4 * AU, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[4] = new Body("Sol5", 1.e35, 1e5, new Vector3(4 * AU, 4 * AU, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[5] = new Body("Sol6", 1.e40, 1e7, new Vector3(5 * AU, 0, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[6] = new Body("Sol7", 1.e40, 1e7, new Vector3(-5 * AU, 0, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[7] = new Body("Sol8", 1.e40, 1e7, new Vector3(0, 5 * AU, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            bodies[8] = new Body("Sol9", 1.e40, 1e7, new Vector3(0, -5 * AU, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
            double speed = 1e8;
            for (int i = 9; i < bodies.length; i++) {
                if (i % 10 == 8) {
                    double angle = Math.random() * Math.PI * 2;
                    bodies[i] = new Body("" + i, 1e25 * Math.random(), 1 + 1e4 * Math.random(),
                            new Vector3(Math.cos(angle) * 4 * AU, Math.sin(angle) * 4 * AU, 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(speed * Math.random() * (Math.random() < 0.5 ? 1 : -1), speed * Math.random() * (Math.random() < 0.5 ? 1 : -1), speed * Math.random() * (Math.random() < 0.5 ? 1 : -1)),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                } else {
                    double angle = Math.random() * Math.PI * 2;
                    bodies[i] = new Body("" + i, 1e20 * Math.random(), 1 + 1e4 * Math.random(),
                            new Vector3(Math.cos(angle) * 2 * AU, Math.sin(angle) * 2 * AU, 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(speed * Math.random() * (Math.random() < 0.5 ? 1 : -1), speed * Math.random() * (Math.random() < 0.5 ? 1 : -1), speed * Math.random() * (Math.random() < 0.5 ? 1 : -1)),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                }
            }
        } else if (option == 3) {
            /** System mit zwei zufällig verteilten Schwarzen Löcher und zufällig verteilten Klasse-1/Klasse-2 Planeten (untersch. Radius und Masse)
             */
            bodies[0] = new Body("Black_Hole_1", 1.e43, 1e15,
                    new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                    new Vector3(0, 0, 0), StdDraw.BLACK);
            bodies[1] = new Body("Black_Hole_2", 1.e43, 1e15,
                    new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                    new Vector3(0, 0, 0), StdDraw.BLACK);
            for (int i = 2; i < bodies.length; i++) {
                if (i % 1000 == 0) {
                    bodies[i] = new Body("" + i, 1.899e40 * Math.random(), 1 + 1e4 * Math.random(),
                            new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(0, 0, 0),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                } else {
                    bodies[i] = new Body("" + i, 1.899e30 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1), 6 * AU * Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(0, 0, 0),
                            new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random())));
                }
            }


        } else {
            /** System mit einer Sonne in der Mitte und vertikal angeordneten, blauen Planeten (4 Klassen)
             */
            for (int i = 1; i < bodies.length; i++) {
                if (i % 4 == 0) {
                    bodies[i] = new Body("" + i, 1e25 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(8e9 * Math.random(), 200e9 * Math.random(), Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1)),
                            new Color(0, 0, (int) (Math.random() * 250)));
                } else if (i % 4 == 1) {
                    bodies[i] = new Body("" + i, 1e25 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(8e9 * Math.random(), 200e9 * Math.random() * -1, Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1)),
                            new Color(0, 0, (int) (Math.random() * 250)));
                } else if (i % 4 == 2) {
                    bodies[i] = new Body("" + i, 1e25 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(8e9 * Math.random() * -1, 200e9 * Math.random(), Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1)),
                            new Color(0, 0, (int) (Math.random() * 250)));
                } else {
                    bodies[i] = new Body("" + i, 1e25 * Math.random(), 1 + 1e3 * Math.random(),
                            new Vector3(8e9 * Math.random() * -1, 200e9 * Math.random() * -1, Math.random() * (Math.random() < 0.5 ? -1 : 1)),
                            new Vector3(1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1), 1e8 * Math.random() * (Math.random() < 0.5 ? 1 : -1)),
                            new Color(0, 0, (int) (Math.random() * 250)));
                }
            }
            bodies[0] = new Body("Sol", 1.9e40, 6e10, new Vector3(0, 0, 0), new Vector3(0, 0, 0), StdDraw.YELLOW);
        }
    }


}


