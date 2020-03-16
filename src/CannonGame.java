import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CannonGame extends BasicGame {
    private Image landscapeImage;
    private Image cannonBaseImage;
    private Image cannonImage;
    private Image targetImage;
    private Image cloudImage;

    private UnicodeFont fontMarcador;

    private Image ballImage;
    private Cannon cannon = new Cannon();
    private Target target = new Target();

    public CannonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.landscapeImage = ResourceManager.getImage("landscape.jpg");
        this.cannonBaseImage = ResourceManager.getImage("cannon_base.png");
        this.cannonImage = ResourceManager.getImage("cannon.png");
        this.cannonImage.setCenterOfRotation((this.cannonImage.getWidth() / 2) / 2, this.cannonImage.getHeight() / 2);
        this.targetImage = ResourceManager.getImage("target.png");
        this.cloudImage = ResourceManager.getImage("cloud.png");
        this.ballImage = ResourceManager.getImage("ball.png");

        this.fontMarcador = ResourceManager.getFont("WHITRABT.TTF", 20);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        this.cannon.update(gameContainer, i);
        this.target.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.target.render(gameContainer, graphics);
        //this.landscapeImage.draw(0, 0);

        //System.out.println(cannon.getRotation());
        //this.cannonImage.rotate((float) cannon.getRotation());

        //this.cannonImage.draw(42, 470);
        //this.cannonBaseImage.draw(42, 490);
        //this.cloudImage.draw(570, 70);
        //this.targetImage.draw(780, 500);
        //this.targetImage.draw((float)target.getPositionX(), 500);

        //Marcadors
        this.fontMarcador.drawString(20, 45, "Strenth: " + this.cannon.getStrength());
        this.fontMarcador.drawString(492, 45, "Angle: " + this.cannon.getRotation());
        this.fontMarcador.drawString(850, 45, "Score: ");

        //Reiniciam la rotacio actual
        this.cannonImage.rotate((float) -cannon.getRotation());
        //cannon.setRotation(0);
        //this.cannonImage.rotate(0);
    }
}
