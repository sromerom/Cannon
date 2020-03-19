import org.newdawn.slick.*;

public class Cannon {
    private Image cannonImage = ResourceManager.getImage("cannon.png");
    private Image cannonBaseImage = ResourceManager.getImage("cannon_base.png");

    private double rotation = 0;
    private double strength = 0;

    public void render() {
        this.cannonImage.setCenterOfRotation((this.cannonImage.getWidth() / 2) / 2, this.cannonImage.getHeight() / 2);
        this.cannonImage.rotate((float) this.rotation);
        this.cannonImage.draw(42, 470);
        this.cannonBaseImage.draw(42, 490);
        this.cannonImage.rotate((float) -(this.rotation));
    }

    public void update(GameContainer gameContainer, int i) {
        Input mou = gameContainer.getInput();
        if (mou.isKeyDown(Input.KEY_LEFT)) {
            updateRotation(-0.5);
            System.out.println("Augmentamos angulo");
        } else if (mou.isKeyDown(Input.KEY_RIGHT)) {
            updateRotation(0.5);
            System.out.println("Disminuimos angulo");
        } else if (mou.isKeyDown(Input.KEY_UP)) {
            System.out.println("Augmentamos fuerza");
            updateStrength(0.5);
        } else if (mou.isKeyDown(Input.KEY_DOWN)) {
            System.out.println("Disminuimos fuerza");
            updateStrength(-0.5);
        } else if (mou.isKeyPressed(Input.KEY_SPACE)) {
            System.out.println("Piiuum");
            fire();
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
            this.strength = 10;
        }


        if (this.strength > 100) {
            this.strength = 100;
        }

    }

    public Ball fire() {
        return new Ball();
    }

    public void updateRotation(double deltaRotation) {
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
