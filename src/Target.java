import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Target {
    private Shape shape;
    private Image targetImage = ResourceManager.getImage("target.png");;
    private double positionX;
    private boolean hit;
    private int maxim = 890;
    private int minim = 225;
    private int range = maxim - minim + 1;
    private int rand = (int) (Math.random() * range) + minim;
    public void render() throws SlickException {
        this.targetImage.draw((float) this.positionX, 500);
    }

    public void update() {
       this.positionX = rand;
    }

    public boolean hit() {
        return true;
    }

    public void reset() {

    }

    public Shape getShape() {
        return shape;
    }

    public double getPositionX() {
        return positionX;
    }
}
