import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Ball {
    private Image ballImage = ResourceManager.getImage("ball.png");
    private Target target;
    private Shape colisioBall;

    private double inicialX = 42;
    private double inicialY = 490;
    private double posicioX = 0;
    private double posicioY = 0;
    private double frame = 0;
    private double angle;
    private double strength;

    public Ball(double angle, double strength) {
        this.angle = angle;
        this.strength = strength;
        colisioBall = new Circle((float) inicialX, (float) inicialY, ballImage.getWidth() / 2);
    }

    public void render(Graphics g) {
        this.ballImage.draw((float) this.posicioX, (float) this.posicioY);
        g.draw(colisioBall);
    }

    public void update() {
        //System.out.println(this.posicioX + "," + this.posicioY);
        double angleActual = this.angle * -1 * Math.PI / 180f;
        double grav = (-9.8) * -1;
        //Calculam la velocitat tant per la part vertical com la horizontal
        double vx = this.strength * Math.cos(angleActual);
        double vy = (this.strength * Math.sin(angleActual)) * -1;

        //System.out.println(frame);

        this.posicioX = inicialX + vx * frame;
        this.posicioY = inicialY + vy * frame + grav * frame * frame / 2f;

        //Actualitzam el shape de colisions
        this.colisioBall.setX((float) this.posicioX);
        this.colisioBall.setY((float) this.posicioY);
        frame += 0.2;
        //1 fps =	0.3048	m/s
        //frame = 1 / 60
        //this.x += 2;
    }

    public boolean hasFallen() {
        //System.out.println("Target X position: " + target.getPositionX());
        if (this.posicioY > 576) {
            System.out.println("Ha llegado al suelo");
            return true;
        }
        return false;
    }

    public boolean hit() {
        if (this.colisioBall.intersects(this.target.getShape())) {
            return true;
        }
        return false;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

}
