import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Ball {
    private Image ballImage = ResourceManager.getImage("ball.png");;
    private Target target;
    private Shape shape;

    private static final double INICIALX = 42;
    private static final double INICIALY = 490;
    private double posicioX = 0;
    private double posicioY = 0;
    private double frame = 0;
    public void render(){
        this.ballImage.draw((float)this.posicioX, (float)this.posicioY);
    }

    public void update(double angle, double strength, int i) {
        //V0X = V * cosinus;
        //V0Y = V * sinus;

        double strengthProv = 100;
        double angleProv = 80f * Math.PI / 180f;
        double grav = (-9.8) * -1;
        //angle = Math.toRadians(angle);
        //angleProv = Math.toRadians(angleProv);

        //Calculam la velocitat tant per la part vertical com la horizontal
        double vx = strengthProv * Math.cos(angleProv);
        double vy = (strengthProv * Math.sin(angleProv)) * -1;

        //System.out.println(frame);
        this.posicioX = INICIALX + vx * frame;
        this.posicioY = INICIALY + vy * frame + grav * frame * frame/2f;
        System.out.println(posicioX +","+ posicioY);
        frame += 0.3048;
        //1 fps =	0.3048	m/s
        //frame = 1 / 60
        //this.x += 2;
    }

    public boolean hasFallen() {
        return false;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

}
