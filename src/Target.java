import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Target {
    private Image targetImage = ResourceManager.getImage("target.png");
    private Shape collisionTarget;
    private double positionX;
    private double positionY = 500;
    private final int MAX = 890;
    private final int MIN = 225;
    private int range = MAX - MIN + 1;
    private int random = (int) (Math.random() * range) + MIN;
    private boolean dyng = false;

    public Target() {
        this.positionX = random;
        collisionTarget = new Rectangle((float) this.positionX, (float) positionY, targetImage.getWidth(), targetImage.getHeight());
    }

    public void render(Graphics g) throws SlickException {
        this.targetImage.draw((float) this.positionX, (float) positionY);
    }

    public void update() {
        if (this.dyng) {
            this.positionY += 5;
        }
    }

    public void reset() {
        this.dyng = false;
        this.positionY = 500;
        this.random = (int) (Math.random() * range) + MIN;
        this.positionX = this.random;
        this.collisionTarget.setX((float) this.positionX);
    }

    public Shape getShape() {
        return collisionTarget;
    }

    public double getPositionX() {
        return positionX;
    }

    public boolean isDyng() {
        return dyng;
    }

    public void setDyng(boolean dyng) {
        this.dyng = dyng;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}
