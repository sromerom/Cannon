import org.newdawn.slick.*;


public class CannonGame extends BasicGame {

    private UnicodeFont fontMarcador;
    private Image heart;
    private Landscape landscape;
    private Cannon cannon;
    private Target target;
    private Ball ball;
    private int score;
    private int intents = 42;
    private int disparsSeguits = 0;
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
        this.heart = ResourceManager.getImage("heart.png");
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

            if (this.intents == 0) {
                this.estatActual = Estat.FINAL;
            }

            if (this.disparsSeguits == 3) {
                this.intents++;
                this.disparsSeguits = 0;
            }

            if (this.ball == null) {
                if (mou.isKeyPressed(Input.KEY_SPACE)) {
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
                        this.disparsSeguits = 0;
                        this.intents--;
                    } else if (!shaEncertat) {
                        this.intents--;
                    }
                    shaEncertat = false;
                } else if (!this.ball.hasFallen()) {
                    if (this.ball.hit()) {
                        if (!shaEncertat) {
                            System.out.println("Has ganado!!");
                            shaEncertat = true;
                            this.score += 100;
                            this.disparsSeguits++;
                            this.target.setDyng(true);
                        }
                    }
                }
            }
        } else if (this.estatActual.equals(Estat.FINAL)) {
            if (mou.isKeyPressed(Input.KEY_ENTER)) {
                System.out.println("Empezamos");
                this.estatActual = Estat.MENU;
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        //System.out.println("Intents restants: " + intents);
        //System.out.println("Numero de dispars seguits: " + disparsSeguits);
        UnicodeFont textMenu = ResourceManager.getFont("WHITRABT.TTF", 40);
        if (this.estatActual.equals(Estat.MENU)) {
            this.landscape.render();
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

            //Intents
            dibuixaIntents();
        } else if (this.estatActual.equals(Estat.FINAL)) {
            this.fontMarcador.drawString(20, 45, "Actual Score: " + parsePunts(), Color.white);
            this.fontMarcador.drawString(750, 45, "Global Score: 0000", Color.white);
            textMenu.drawString(400, 288, "Game Over", Color.white);
            this.fontMarcador.drawString(400, 388, "Press ENTER to restart", Color.white);

            //Reiniciam atributs
            this.score = 0;
            this.intents = 5;
            this.ball = null;
            this.shaEncertat = false;
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

    private void dibuixaIntents() {
        int inicialX = 990;
        int inicialY = 5;
        UnicodeFont mesVides = ResourceManager.getFont("WHITRABT.TTF", 20);

        if (this.intents >= 36) {
            for (int i = 0; i < 36; i++) {
                this.heart.draw(inicialX, inicialY, 30, 30);
                inicialX -= 27;
            }
            this.fontMarcador.drawString(5, 13, "+" + (this.intents - 36));
        } else {
            for (int i = 0; i < this.intents; i++) {
                this.heart.draw(inicialX, inicialY, 30, 30);
                inicialX -= 27;
            }
        }
    }
}
