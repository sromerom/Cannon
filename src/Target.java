import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Target {
    private Image targetImage = ResourceManager.getImage("target.png");
    ;
    private Shape colisioTarget;
    private double positionX;
    private boolean hit;
    private final int MAXIM = 890;
    private final int MINIM = 225;
    private int range = MAXIM - MINIM + 1;
    private int rand = (int) (Math.random() * range) + MINIM;

    public Target() {
        this.positionX = rand;
        colisioTarget = new Rectangle((float) this.positionX, 500, targetImage.getWidth(), targetImage.getHeight());
    }

    public void render(Graphics g) throws SlickException {
        this.targetImage.draw((float) this.positionX, 500);
        g.drawRect(colisioTarget.getX(), colisioTarget.getY(), colisioTarget.getWidth(), colisioTarget.getHeight());
    }

    public void update() {
    }

    public boolean hit() {
        return false;
    }

    public void reset() {
        System.out.println("Reiniciamos");
        this.rand = (int) (Math.random() * range) + MINIM;
       this.positionX = this.rand;
    }

    public Shape getShape() {
        return colisioTarget;
    }

    public double getPositionX() {
        return positionX;
    }
}
