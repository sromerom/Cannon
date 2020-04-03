import org.newdawn.slick.*;
import org.newdawn.slick.geom.BasicTriangulator;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CannonGame extends BasicGame {

    private UnicodeFont scoreboardFont;
    private Image heartImage;
    private Image addScoreImage;
    private Image gameoverImage;
    private boolean selectorGameOver = true;

    private Landscape landscape;
    private Cannon cannon;
    private Target target;
    private Ball ball;
    private int score;
    private int attempts = 5;
    private int shotsInARow = 0;
    private boolean shotTarget = false;
    private String name = "";

    public enum State {
        MENU, GAME, ADDSCORE, SCOREBOARD, GAMEOVER
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
        this.addScoreImage = ResourceManager.getImage("stateScoreImage.jpg");
        this.gameoverImage = ResourceManager.getImage("gameOver.png");
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input move = gameContainer.getInput();

        if (move.isKeyPressed(Input.KEY_ESCAPE)) {
            System.exit(1);
        }
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
                //this.actualState = State.FINAL;
                this.actualState = State.ADDSCORE;
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
                    if (!shotTarget && this.score > 20) {
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
        } else if (this.actualState.equals(State.ADDSCORE)) {
            if (move.isKeyPressed(Input.KEY_A)) {
                this.name = this.name + "A";
            }
            if (move.isKeyPressed(Input.KEY_B)) {
                this.name = this.name + "B";
            }
            if (move.isKeyPressed(Input.KEY_C)) {
                this.name = this.name + "C";
            }
            if (move.isKeyPressed(Input.KEY_D)) {
                this.name = this.name + "D";
            }
            if (move.isKeyPressed(Input.KEY_E)) {
                this.name = this.name + "E";
            }
            if (move.isKeyPressed(Input.KEY_F)) {
                this.name = this.name + "F";
            }
            if (move.isKeyPressed(Input.KEY_G)) {
                this.name = this.name + "G";
            }
            if (move.isKeyPressed(Input.KEY_H)) {
                this.name = this.name + "H";
            }
            if (move.isKeyPressed(Input.KEY_I)) {
                this.name = this.name + "I";
            }
            if (move.isKeyPressed(Input.KEY_J)) {
                this.name = this.name + "J";
            }
            if (move.isKeyPressed(Input.KEY_K)) {
                this.name = this.name + "K";
            }
            if (move.isKeyPressed(Input.KEY_L)) {
                this.name = this.name + "L";
            }
            if (move.isKeyPressed(Input.KEY_M)) {
                this.name = this.name + "M";
            }
            if (move.isKeyPressed(Input.KEY_N)) {
                this.name = this.name + "N";
            }
            if (move.isKeyPressed(Input.KEY_O)) {
                this.name = this.name + "O";
            }
            if (move.isKeyPressed(Input.KEY_P)) {
                this.name = this.name + "P";
            }
            if (move.isKeyPressed(Input.KEY_Q)) {
                this.name = this.name + "Q";
            }
            if (move.isKeyPressed(Input.KEY_R)) {
                this.name = this.name + "R";
            }
            if (move.isKeyPressed(Input.KEY_S)) {
                this.name = this.name + "S";
            }
            if (move.isKeyPressed(Input.KEY_T)) {
                this.name = this.name + "T";
            }
            if (move.isKeyPressed(Input.KEY_U)) {
                this.name = this.name + "U";
            }
            if (move.isKeyPressed(Input.KEY_V)) {
                this.name = this.name + "V";
            }
            if (move.isKeyPressed(Input.KEY_W)) {
                this.name = this.name + "W";
            }
            if (move.isKeyPressed(Input.KEY_X)) {
                this.name = this.name + "X";
            }
            if (move.isKeyPressed(Input.KEY_Y)) {
                this.name = this.name + "Y";
            }
            if (move.isKeyPressed(Input.KEY_Z)) {
                this.name = this.name + "Z";
            }
            if (move.isKeyPressed(Input.KEY_DELETE)) {
                String newString = "";
                if (this.name.length() != 0) {
                    for (int j = 0; j < this.name.length() - 1; j++) {
                        newString = newString + Character.toString(this.name.charAt(j));
                    }
                }

                this.name = newString;
            }

            if (this.name.length() == 3) {
                this.actualState = State.SCOREBOARD;
                try {
                    addData(parsePunts() + "-" + this.name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (this.actualState.equals(State.SCOREBOARD)) {
            if (move.isKeyPressed(Input.KEY_ENTER)) {

                //Reiniciam atributs
                this.score = 0;
                this.attempts = 5;
                this.shotsInARow = 0;
                this.ball = null;
                this.shotTarget = false;
                this.name = "";
                this.actualState = State.GAMEOVER;
            }
        } else if (this.actualState.equals(State.GAMEOVER)) {
            if (move.isKeyPressed(Input.KEY_RIGHT)) {
                this.selectorGameOver = false;
            } else if (move.isKeyPressed(Input.KEY_LEFT)) {
                this.selectorGameOver = true;
            }

            if (move.isKeyPressed(Input.KEY_ENTER)) {
                if (this.selectorGameOver) {
                    this.actualState = State.MENU;
                } else {
                    System.exit(1);
                }
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

        } else if (this.actualState.equals(State.ADDSCORE)) {
            this.addScoreImage.draw(0, 0);
            UnicodeFont scoreboardFontBig = ResourceManager.getFont("WHITRABT.TTF", 70);
            this.scoreboardFont.drawString(200, 150, "Congratulations! You have one score in the top 10 scores!");
            this.scoreboardFont.drawString(450, 200, "Add your name");
            Shape rec1 = new Rectangle(380, 350, 75, 5);
            Shape rec2 = new Rectangle(480, 350, 75, 5);
            Shape rec3 = new Rectangle(580, 350, 75, 5);

            graphics.fill(rec1);
            graphics.fill(rec2);
            graphics.fill(rec3);


            if (this.name.length() == 1) {
                scoreboardFontBig.drawString(395, 290, Character.toString(this.name.charAt(0)));
            } else if (this.name.length() == 2) {
                scoreboardFontBig.drawString(395, 290, Character.toString(this.name.charAt(0)));
                scoreboardFontBig.drawString(495, 290, Character.toString(this.name.charAt(1)));
            } else if (this.name.length() == 3) {
                scoreboardFontBig.drawString(395, 290, Character.toString(this.name.charAt(0)));
                scoreboardFontBig.drawString(495, 290, Character.toString(this.name.charAt(1)));
                scoreboardFontBig.drawString(595, 290, Character.toString(this.name.charAt(2)));
            }
            //this.scoreboardFont.drawString(450, 370, this.name);
        } else if (this.actualState.equals(State.SCOREBOARD)) {


            this.scoreboardFont.drawString(450, 45, "HIGH SCORES", Color.white);
            this.scoreboardFont.drawString(180, 100, "RANK", Color.white);
            this.scoreboardFont.drawString(480, 100, "SCORE", Color.white);
            this.scoreboardFont.drawString(780, 100, "NAME", Color.white);

            this.scoreboardFont.drawString(180, 130, "1ST", Color.red);
            this.scoreboardFont.drawString(180, 160, "2ND", Color.orange);
            this.scoreboardFont.drawString(180, 190, "3RD", Color.yellow);
            this.scoreboardFont.drawString(180, 220, "4TH", Color.green);
            this.scoreboardFont.drawString(180, 250, "5TH", Color.blue);
            this.scoreboardFont.drawString(180, 280, "6TH", Color.cyan);
            this.scoreboardFont.drawString(180, 310, "7TH", Color.magenta);
            this.scoreboardFont.drawString(180, 340, "8TH", Color.pink);
            this.scoreboardFont.drawString(180, 370, "9TH", Color.gray);
            this.scoreboardFont.drawString(180, 400, "10TH", Color.darkGray);

            this.scoreboardFont.drawString(350, 450, "Press ENTER to continue the game", Color.white);


            try {
                String[] data = getData();
                int posicio = 130;
                Color[] colors = new Color[]{Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.cyan, Color.magenta, Color.pink, Color.gray, Color.darkGray};
                for (int i = 0; i < data.length; i++) {
                    String[] parts = data[i].split("-");
                    String actualScore = parts[0];
                    String actualName = parts[1];

                    this.scoreboardFont.drawString(480, posicio, actualScore, colors[i]);
                    this.scoreboardFont.drawString(780, posicio, actualName, colors[i]);
                    posicio += 30;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.actualState.equals(State.GAMEOVER)) {
            this.gameoverImage.draw(0, 0);

            if (this.selectorGameOver) {
                graphics.drawRect(435, 405, 50, 25);
            } else {
                graphics.drawRect(543, 405, 50, 25);
            }


            graphics.setColor(Color.red);
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

    private void addData(String score) throws IOException {
        String[] partsActualScore = score.split("-");
        int nouScore = Integer.parseInt(partsActualScore[0]);
        boolean afegit = false;
        File archive = new File("maxScore.txt");
        BufferedWriter bw;

        if (archive.exists()) {
            String[] data = getData();
            bw = new BufferedWriter(new FileWriter(archive));
            for (int i = 0; i < data.length; i++) {
                String[] parts = data[i].split("-");
                int actualScore = Integer.parseInt(parts[0]);


                if (nouScore >= actualScore && !afegit) {

                    if (data.length >= 10) {
                        bw.write(score);
                        bw.newLine();
                    } else {
                        bw.write(score);
                        bw.newLine();
                        bw.write(data[i]);
                        bw.newLine();
                    }

                    afegit = true;
                } else {
                    bw.write(data[i]);
                    bw.newLine();
                }
            }

            if (!afegit && data.length < 10) {
                bw.write(score);
            }


        } else {
            bw = new BufferedWriter(new FileWriter(archive));
            bw.write(score);
        }
        bw.close();
    }

    private String[] getData() throws FileNotFoundException, IOException {
        List<String> resultList = new ArrayList<String>();
        String line;
        FileReader file = new FileReader("maxScore.txt");
        BufferedReader buffer = new BufferedReader(file);

        int i = 0;
        while ((line = buffer.readLine()) != null) {
            resultList.add(line);
            i++;
        }
        buffer.close();

        String[] result = new String[resultList.size()];
        resultList.toArray(result);
        return result;
    }
}
