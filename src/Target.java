import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Target {
    private Image targetImage = ResourceManager.getImage("target.png");
    private Shape colisioTarget;
    private double positionX;
    private double positionY = 500;
    private final int MAXIM = 890;
    private final int MINIM = 225;
    private int range = MAXIM - MINIM + 1;
    private int rand = (int) (Math.random() * range) + MINIM;
    private boolean dyng = false;
    //private SpriteSheet sprite;
    //private Animation anim;

    public Target() {
        this.positionX = rand;
        colisioTarget = new Rectangle((float) this.positionX, (float) positionY, targetImage.getWidth(), targetImage.getHeight());
    }

    public void render(Graphics g) throws SlickException {
        this.targetImage.draw((float) this.positionX, (float) positionY);
        g.drawRect(colisioTarget.getX(), colisioTarget.getY(), colisioTarget.getWidth(), colisioTarget.getHeight());
    }

    public void update() {
        if (dyng) {
            this.positionY+= 5;
        }
    }

    public void reset() {
        this.dyng = false;
        this.positionY = 500;
        System.out.println("Reiniciamos");
        this.rand = (int) (Math.random() * range) + MINIM;
        this.positionX = this.rand;
        this.colisioTarget.setX((float) this.positionX);
    }

    public Shape getShape() {
        return colisioTarget;
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
