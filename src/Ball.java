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

    public double x = 42;
    public double y =  470;

    public void render(){
        this.ballImage.draw(130, 490);
    }

    public void update() {
        this.x += 1;
    }

    public boolean hasFallen() {
        return false;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

}
