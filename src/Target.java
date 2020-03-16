import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Target {
    private Shape shape;
    private double positionX;
    private boolean hit;
    private int maxim = 1000;
    private int minim = 225;
    private int range = maxim - minim + 1;
    private int rand = (int) (Math.random() * range) + minim;
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.shape = new Rectangle(rand, 500, 150, 59);

    }

    public void update(GameContainer gameContainer, int i) {
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
