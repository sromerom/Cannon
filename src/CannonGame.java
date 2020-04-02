import org.newdawn.slick.*;


public class CannonGame extends BasicGame {

    private UnicodeFont scoreboardFont;
    private Image heartImage;
    private Landscape landscape;
    private Cannon cannon;
    private Target target;
    private Ball ball;
    private int score;
    private int attempts = 5;
    private int shotsInARow = 0;
    private boolean shotTarget = false;

    public enum State {
        MENU, GAME, FINAL
    }

    private State actualState;

    public CannonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.landscape = new Landscape();
        this.cannon = new Cannon();
        this.target = new Target();
        this.scoreboardFont = ResourceManager.getFont("WHITRABT.TTF", 20);
        this.score = 0;
        this.actualState = State.MENU;
        this.heartImage = ResourceManager.getImage("heart.png");
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input move = gameContainer.getInput();

        if (this.actualState.equals(State.MENU)) {
            this.landscape.update();
            if (move.isKeyPressed(Input.KEY_ENTER)) {
                this.actualState = State.GAME;
            }
        } else if (this.actualState.equals(State.GAME)) {
            this.landscape.update();
            this.cannon.update(gameContainer, i);
            this.target.update();

            if (this.attempts == 0) {
                this.actualState = State.FINAL;
            }

            if (this.shotsInARow == 3) {
                this.attempts++;
                this.shotsInARow = 0;
            }

            if (this.ball == null) {
                if (move.isKeyPressed(Input.KEY_SPACE)) {
                    this.ball = cannon.fire();
                    this.ball.setTarget(this.target);
                }
            }

            if (this.ball != null) {
                this.ball.update();
                if (this.ball.hasFallen()) {
                    this.ball = null;
                    this.target.reset();
                    if (!shotTarget && this.score > 0) {
                        this.score -= 30;
                        this.shotsInARow = 0;
                        this.attempts--;
                    } else if (!shotTarget) {
                        this.attempts--;
                    }
                    shotTarget = false;
                } else if (!this.ball.hasFallen()) {
                    if (this.ball.hit()) {
                        if (!shotTarget) {
                            shotTarget = true;
                            this.score += 100;
                            this.shotsInARow++;
                            this.target.setDyng(true);
                        }
                    }
                }
            }
        } else if (this.actualState.equals(State.FINAL)) {
            if (move.isKeyPressed(Input.KEY_ENTER)) {
                //Reiniciam atributs
                this.score = 0;
                this.attempts = 5;
                this.ball = null;
                this.shotTarget = false;

                this.actualState = State.MENU;
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        UnicodeFont textMenu = ResourceManager.getFont("WHITRABT.TTF", 40);
        if (this.actualState.equals(State.MENU)) {
            this.landscape.render();
            textMenu.drawString(300, 288, "Press ENTER to begin", Color.black);
        } else if (this.actualState.equals(State.GAME)) {
            this.landscape.render();

            //Fons del marcador
            graphics.fillRect(0, 0, 1024, 70);
            Color transparentBlack = new Color(0, 0, 0, 75);
            graphics.setColor(transparentBlack);

            if (ball != null) {
                this.ball.render(graphics);
            }
            this.cannon.render();
            this.target.render(graphics);

            //Marcadors
            this.scoreboardFont.drawString(20, 45, "Strenth: " + this.cannon.getStrength());
            this.scoreboardFont.drawString(492, 45, "Angle: " + this.cannon.getRotation() * -1);
            this.scoreboardFont.drawString(850, 45, "Score: " + parsePunts());

            //Intents
            dibuixaIntents();

        } else if (this.actualState.equals(State.FINAL)) {
            this.scoreboardFont.drawString(20, 45, "Actual Score: " + parsePunts(), Color.white);
            this.scoreboardFont.drawString(750, 45, "Global Score: 0000", Color.white);
            textMenu.drawString(400, 288, "Game Over", Color.white);
            this.scoreboardFont.drawString(400, 388, "Press ENTER to restart", Color.white);
        }

    }

    private String parsePunts() {
        StringBuilder result = new StringBuilder("");
        int scoreLength = Integer.toString(this.score).length();
        for (int i = 0; i <= 5 - scoreLength; i++) {
            result.append("0");
        }

        result.append(this.score);
        return result.toString();
    }

    private void dibuixaIntents() {
        int initialX = 990;
        int initialY = 5;

        if (this.attempts >= 36) {
            for (int i = 0; i < 36; i++) {
                this.heartImage.draw(initialX, initialY, 30, 30);
                initialX -= 27;
            }
            this.scoreboardFont.drawString(5, 13, "+" + (this.attempts - 36));
        } else {
            for (int i = 0; i < this.attempts; i++) {
                this.heartImage.draw(initialX, initialY, 30, 30);
                initialX -= 27;
            }
        }
    }
}
