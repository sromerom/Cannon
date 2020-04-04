import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Ball {
    private Image ballImage = ResourceManager.getImage("ball.png");
    private Target target;
    private Shape collisionBall;

    //Atributs que ens permetran fer el calcul parabolic del ball
    private double angle;
    private double strength;
    private double initialPositionX = 42;
    private double initialPositionY = 490;
    private double actualPositionX = 0;
    private double actualPositionY = 0;
    private double frame = 0;

    //Atributs que ens permet fer l'animacio quan l'usuari encerta en el target
    private boolean hit = false;
    private boolean animation = false;

    //Constructor Ball on l'hi passarem per parametre el angulo i la força en la que ha sigut disparada la pilota en questio
    public Ball(double angle, double strength) {
        this.angle = angle;
        this.strength = strength;
        //Com que la pilota te un shape de colisions a igual que el target, doncs en aquest constructor l'ha crearem. En aquest cas, el shape sera un cercle
        this.collisionBall = new Circle((float) initialPositionX, (float) initialPositionY, ballImage.getWidth() / 2);
    }

    //El metode update ens permet anar actualizant les posicions X i Y segons el resultat que ens dona la formula del tir parabolic
    public void update() {

        //Si encara no s'ha encertat el tir, cridam al metode calculParabolic que ens calcula quina es la posicio X i Y utilitzant la formula del calcula parabolic. A mes
        // de calcular-los, tambe actualitza els atributs actualPositionX i actualPositionY.
        if (!hit) {
            calculParabolic(this.angle, this.strength, this.initialPositionX, this.initialPositionY);
        } else {
            // Si hit es true, voldra dir que s'ha encertat y que ja es pot fer l'animacio. Aquesta animacio torna a ser un moviment parabolic

            //La primera vegada i nomes la primera, canviam el valor dels atributs
            if (!animation) {
                this.initialPositionX = this.actualPositionX;
                this.initialPositionY = this.actualPositionY;
                this.actualPositionX = 0;
                this.actualPositionY = 0;
                this.frame = 0;
                this.animation = true;
            }
            // I es crida a calculParabolic amb la diferencia que la força sempre sera la meitat
            calculParabolic(this.angle, this.strength / 2, this.initialPositionX, this.initialPositionY);
        }

        //Important actualitzar el shape de colisions
        this.collisionBall.setX((float) this.actualPositionX);
        this.collisionBall.setY((float) this.actualPositionY);
    }

    //Metode que ens permet dibuixar en tot moment la trajectoria de la pilota. Gracies a aquest metode podem veure el tir parobolic
    public void render(Graphics g) {
        this.ballImage.draw((float) this.actualPositionX, (float) this.actualPositionY);
    }

    //Metode que ens permet saber si el tir del jugador ha sigut certer o si al contrari ha fallat. Considedarem que el jugador ha fallat
    // si la pilota es troba per davall del limit de la pantalla (> 576)
    public boolean hasFallen() {

        if (this.actualPositionY > 576) {
            return true;
        }
        return false;
    }

    //Metode que ens permet saber si la pilota i el target estan colisionant. Gracies al metode intersects que trobam en l'objecte Shape ho podem fer
    public boolean hit() {

        if (this.collisionBall.intersects(this.target.getShape())) {
            this.hit = true;
            return true;
        }
        return false;
    }

    //Metode que ens permet calcular quines serien les posicions segons en angle i la força en un tir parabolic.
    private void calculParabolic(double angle, double strength, double xInicial, double yInicial) {
        double angleActual = angle * -1 * Math.PI / 180f;
        double grav = (-9.8) * -1;

        //Calculam la velocitat tant per la part vertical com la horizontal
        double vx = strength * Math.cos(angleActual);
        double vy = (strength * Math.sin(angleActual)) * -1;

        //El resultat que ens doni ho assignarem directament al atribut de la posicio (tant per X i Y)
        this.actualPositionX = xInicial + vx * frame;
        this.actualPositionY = yInicial + vy * frame + grav * frame * frame / 2f;

        //1 fps =	0.3048	m/s
        this.frame += 0.2;

    }

    //Getters and Setters
    public void setTarget(Target target) {
        this.target = target;
    }

}
