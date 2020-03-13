import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CannonGame extends BasicGame {
    private Image landscapeImage;
    private Image cannonBaseImage;
    private Image cannonImage;
    private Image targetImage;
    private Image cloudImage;
    private Image ballImage;
    private Cannon cannon = new Cannon();


    public CannonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.landscapeImage = ResourceManager.getImage("landscape.jpg");
        this.cannonBaseImage = ResourceManager.getImage("cannon_base.png");
        this.cannonImage = ResourceManager.getImage("cannon.png");
        this.targetImage = ResourceManager.getImage("target.png");
        this.cloudImage = ResourceManager.getImage("cloud.png");
        this.ballImage = ResourceManager.getImage("ball.png");
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        cannon.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.landscapeImage.draw(0, 0);

        //this.cannonImage.setCenterOfRotation(this.cannonImage.getWidth() * 1 / 2, this.cannonImage.getHeight() * 1 / 2);
        this.cannonImage.rotate((float)cannon.getRotation());
        System.out.println(cannon.getRotation());
        this.cannonImage.draw(42, 470);
        this.cannonBaseImage.draw(42, 490);
        this.cloudImage.draw(570, 70);
        this.targetImage.draw(780, 500);
    }
}
