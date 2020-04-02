import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Ball {
    private Image ballImage = ResourceManager.getImage("ball.png");
    private Target target;
    private Shape collisionBall;
    private double angle;
    private double strength;
    private double initialPositionX = 42;
    private double initialPositionY = 490;
    private double actualPositionX = 0;
    private double actualPositionY = 0;
    private double frame = 0;
    private boolean hit = false;
    private boolean animation = false;

    public Ball(double angle, double strength) {
        this.angle = angle;
        this.strength = strength;
        collisionBall = new Circle((float) initialPositionX, (float) initialPositionY, ballImage.getWidth() / 2);
    }

    public void render(Graphics g) {
        this.ballImage.draw((float) this.actualPositionX, (float) this.actualPositionY);
        g.draw(collisionBall);
    }

    public void update() {
        if (!hit) {
            calculParabolic(this.angle, this.strength, this.initialPositionX, this.initialPositionY);
        } else {
            if (!animation) {
                this.initialPositionX = this.actualPositionX;
                this.initialPositionY = this.actualPositionY;
                this.actualPositionX = 0;
                this.actualPositionY = 0;
                this.frame = 0;
                this.animation = true;
            }
            calculParabolic(this.angle, this.strength/2, this.initialPositionX, this.initialPositionY);
        }

        //Actualitzam el shape de colisions
        this.collisionBall.setX((float) this.actualPositionX);
        this.collisionBall.setY((float) this.actualPositionY);
    }

    public boolean hasFallen() {
        if (this.actualPositionY > 576) {
            return true;
        }
        return false;
    }

    public boolean hit() {
        if (this.collisionBall.intersects(this.target.getShape())) {
            this.hit = true;
            return true;
        }
        return false;
    }

    private void calculParabolic(double angle, double strength, double xInicial, double yInicial) {
        double angleActual = angle * -1 * Math.PI / 180f;
        double grav = (-9.8) * -1;

        //Calculam la velocitat tant per la part vertical com la horizontal
        double vx = strength * Math.cos(angleActual);
        double vy = (strength * Math.sin(angleActual)) * -1;

        this.actualPositionX = xInicial + vx * frame;
        this.actualPositionY = yInicial + vy * frame + grav * frame * frame / 2f;

        //1 fps =	0.3048	m/s
        this.frame += 0.2;

    }

    public void setTarget(Target target) {
        this.target = target;
    }

}
