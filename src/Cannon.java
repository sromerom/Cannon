import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Cannon {
    private double rotation = 0;
    private double strength = 0;

    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }

    public void update(GameContainer gameContainer, int i) {
        Input mou = gameContainer.getInput();
        if (mou.isKeyPressed(Input.KEY_LEFT)) {
            updateRotation(-0.3);
            //System.out.println(this.rotation);
        } else if (mou.isKeyPressed(Input.KEY_RIGHT)) {
            updateRotation(0.3);
            System.out.println(this.rotation);
        }

        this.rotation = 0;
    }

    public void fire() {

    }

    public void updateRotation(double deltaRotation) {
        this.rotation = this.rotation + deltaRotation;
    }

    public void updateStrength(double deltaStrength) {

    }

    public double getRotation() {
        return rotation;
    }

    public double getStrength() {
        return strength;
    }
}
