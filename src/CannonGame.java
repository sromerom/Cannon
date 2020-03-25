import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ShadowEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CannonGame extends BasicGame {

    private UnicodeFont fontMarcador;
    private Landscape landscape;
    private Cannon cannon;
    private Target target;
    private Ball ball;
    private int score;
    private int intents = 5;
    private boolean shaEncertat = false;

    public enum Estat {
        MENU, JOC, FINAL
    }

    private Estat estatActual;

    public CannonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.landscape = new Landscape();
        this.cannon = new Cannon();
        this.target = new Target();
        this.fontMarcador = ResourceManager.getFont("WHITRABT.TTF", 20);
        this.score = 0;
        this.estatActual = Estat.MENU;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input mou = gameContainer.getInput();

        if (this.estatActual.equals(Estat.MENU)) {
            this.landscape.update();
            if (mou.isKeyPressed(Input.KEY_ENTER)) {
                System.out.println("Empezamos");
                this.estatActual = Estat.JOC;
            }
        } else if (this.estatActual.equals(Estat.JOC)) {
            this.landscape.update();
            this.cannon.update(gameContainer, i);
            this.target.update();

            if (this.ball == null) {
                if (mou.isKeyPressed(Input.KEY_SPACE)) {
                    System.out.println("Piiuum");
                    this.ball = cannon.fire();
                    this.ball.setTarget(this.target);
                }
            }

            if (this.ball != null) {
                this.ball.update();
                if (this.ball.hasFallen()) {
                    this.ball = null;
                    this.target.reset();
                    if (!shaEncertat && this.score > 0) {
                        System.out.println("Has perdido :(");
                        this.score -= 30;
                    }
                    shaEncertat = false;
                } else if (!this.ball.hasFallen()) {
                    if (this.ball.hit()) {
                        if (!shaEncertat) {
                            System.out.println("Has ganado!!");
                            shaEncertat = true;
                            this.score += 100;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

        if (this.estatActual.equals(Estat.MENU)) {
            this.landscape.render();
            UnicodeFont textMenu = ResourceManager.getFont("WHITRABT.TTF", 40);
            textMenu.drawString(300, 288, "Press ENTER to begin", Color.black);
        } else if (this.estatActual.equals(Estat.JOC)) {
            this.landscape.render();
            graphics.fillRect(0, 0, 1024, 70);
            Color blackTransparent = new Color(0, 0, 0, 75);
            graphics.setColor(blackTransparent);
            if (ball != null) {
                this.ball.render(graphics);
            }
            this.cannon.render();
            this.target.render(graphics);

            //Marcadors
            this.fontMarcador.drawString(20, 45, "Strenth: " + this.cannon.getStrength());
            this.fontMarcador.drawString(492, 45, "Angle: " + this.cannon.getRotation());
            this.fontMarcador.drawString(850, 45, "Score: " + parsePunts());
        }

    }

    private String parsePunts() {
        StringBuilder res = new StringBuilder("");
        int llargariaPunts = Integer.toString(this.score).length();
        for (int i = 0; i <= 5 - llargariaPunts; i++) {
            res.append("0");
        }

        res.append(this.score);
        return res.toString();
    }
}
