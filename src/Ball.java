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
    private boolean hit = false;
    private boolean animacio = false;

    public Ball(double angle, double strength) {
        this.angle = angle;
        this.strength = strength;
        colisioBall = new Circle((float) inicialX, (float) inicialY, ballImage.getWidth() / 2);
    }

    public void render(Graphics g) {
        this.ballImage.draw((float) this.posicioX, (float) this.posicioY);
        g.draw(colisioBall);
        //System.out.println(colisioBall.getX() + "," + colisioBall.getY());
    }

    public void update() {
        if (!hit) {
            calculParabolic(this.angle, this.strength, this.inicialX, this.inicialY);
        } else {
            //System.out.println("Posicion actual inicial: " + this.posicioX + "," + this.posicioY);
            if (!animacio) {
                this.inicialX = this.posicioX;
                this.inicialY = this.posicioY;
                this.posicioX = 0;
                this.posicioY = 0;
                this.frame = 0;
                this.animacio = true;
            }
            calculParabolic(this.angle, this.strength/2, this.inicialX, this.inicialY);
        }

        //Actualitzam el shape de colisions
        this.colisioBall.setX((float) this.posicioX);
        this.colisioBall.setY((float) this.posicioY);
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
            this.hit = true;
            return true;
        }
        return false;
    }

    private void calculParabolic(double angle, double strength, double xInicial, double yInicial) {
        System.out.println(this.posicioX + "," + this.posicioY);
        double angleActual = angle * -1 * Math.PI / 180f;
        double grav = (-9.8) * -1;
        //Calculam la velocitat tant per la part vertical com la horizontal
        double vx = strength * Math.cos(angleActual);
        double vy = (strength * Math.sin(angleActual)) * -1;

        //System.out.println(frame);

        this.posicioX = xInicial + vx * frame;
        this.posicioY = yInicial + vy * frame + grav * frame * frame / 2f;

        this.frame += 0.2;
        //1 fps =	0.3048	m/s
        //frame = 1 / 60
        //this.x += 2;

    }

    public void setTarget(Target target) {
        this.target = target;
    }

}
