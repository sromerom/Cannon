import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CannonGame extends BasicGame {

    private UnicodeFont fontMarcador;
    private Landscape landscape;
    private Cannon cannon;
    private Target target;
    private Ball ball;

    public CannonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.landscape = new Landscape();
        this.cannon = new Cannon();
        this.target = new Target();
        this.ball = new Ball();
        this.fontMarcador = ResourceManager.getFont("WHITRABT.TTF", 20);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        this.landscape.update();
        this.cannon.update(gameContainer, i);
        this.target.update();
        this.ball.update(this.cannon.getRotation(), this.cannon.getStrength(), i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

        this.landscape.render();
        this.ball.render();
        this.cannon.render();
        this.target.render();

        //Marcadors
        this.fontMarcador.drawString(20, 45, "Strenth: " + this.cannon.getStrength());
        this.fontMarcador.drawString(492, 45, "Angle: " + -(this.cannon.getRotation()));
        this.fontMarcador.drawString(850, 45, "Score: ");

    }
}
