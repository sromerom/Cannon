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
        if (mou.isKeyDown(Input.KEY_LEFT)) {
            updateRotation(-0.5);
            //System.out.println(this.rotation);
            System.out.println("Augmentamos angulo");
        } else if (mou.isKeyDown(Input.KEY_RIGHT)) {
            updateRotation(0.5);
            System.out.println("Disminuimos angulo");
            //System.out.println(this.rotation);
        } else if (mou.isKeyDown(Input.KEY_UP)) {
            updateStrength(0.5);
        } else if (mou.isKeyDown(Input.KEY_DOWN)) {
            updateStrength(-0.5);
        } else if(mou.isKeyPressed(Input.KEY_SPACE)) {
            System.out.println("Piiuum");
        }

        //Posam que el graus maxim sempre siguin 90 i el menor sigui 0
        if (this.rotation < -90) {
            this.rotation = -90;
        }

        if (this.rotation > 0) {
            this.rotation = 0;
        }

        //Posam que la força pugui ser un maxim de 100 i un minim de 0
        if (this.strength < 10) {
            this.strength = 0;
        }

        if (this.strength > 100) {
            this.strength = 100;
        }

    }

    public void fire() {

    }

    public void updateRotation(double deltaRotation) {
        //this.rotation +=delta/10;
        //this.rotation += deltaRotation / 10;
        this.rotation = this.rotation + deltaRotation;
    }

    public void updateStrength(double deltaStrength) {
        this.strength = this.strength + deltaStrength;
    }

    public double getRotation() {
        return rotation;
    }

    public double getStrength() {
        return strength;
    }

}
