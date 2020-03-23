import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Target {
    private Shape shape;
    private Image targetImage = ResourceManager.getImage("target.png");;
    private double positionX;
    private boolean hit;
    private final int MAXIM = 890;
    private final int MINIM = 225;
    private int range = MAXIM - MINIM + 1;
    private int rand = (int) (Math.random() * range) + MINIM;

    public void render() throws SlickException {
        this.targetImage.draw((float) this.positionX, 500);
    }

    public void update() {
       this.positionX = rand;
    }

    public boolean hit() {
        return false;
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
