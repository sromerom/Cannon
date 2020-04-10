import org.newdawn.slick.*;
import org.newdawn.slick.geom.BasicTriangulator;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Classe a on juntam tot les classes creades per fer el joc (Implementacio). Aquesta sera la classe base a on es carregara tot.
// Aquesta classe, per tal de que funcioni el joc haura d'heretar de BasicGame.
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

    //Constructor a on l'hi passem per parametre el nom del joc. Aquest String ho passarem a la classe superior amb super
    public CannonGame(String title) {
        super(title);
    }

    //Com que aquesta classe es heretada de BasicGame, farem override dels metodes init, update i render

    //El metode init ens permet carregar fonts, imatges (recursos en general) a la memoria. En aquest carregam inicialitzam tots els objectes necessaris per joc, la font i algunes imatges.
    //Tambe inicialitzem l'estat actual del Game, en aquest cas el joc sempre començara pel MENU i el Score a 0.
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

    //Al metode update es on carregarem tots els update que hem creat en totes les altres classes. També assignarem tots els controls de cada pantalla.
    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input move = gameContainer.getInput();

        //Si es pren la tecla ESC, doncs sortirem del joc. Es podra sortir del joc en tot moment amb la tecla ESC.
        if (move.isKeyPressed(Input.KEY_ESCAPE)) {
            System.exit(0);
        }

        //Si ens trobam en l'estat MENU, doncs nomes carregarem el update de landscape (el fons i el nigul)
        if (this.actualState.equals(State.MENU)) {
            this.landscape.update();

            //I en el moment que es presioni la tecla ENTER, voldra dir haurem de canviar d'estat al GAME.
            if (move.isKeyPressed(Input.KEY_ENTER)) {
                this.actualState = State.GAME;
            }
            //Si l'estat actual es el GAME, doncs carregarem el update de landscape, cannon i target.
        } else if (this.actualState.equals(State.GAME)) {
            this.landscape.update();
            this.cannon.update(gameContainer, i);
            this.target.update();

            //Nomes podem disparar una pilota quan no hi hagui cap disparada.
            if (this.ball == null) {
                //Dispararem la pilota amb la tecla SPACE. Una vegada es presiona, cridarem al metode fire de cannon i inicialitzarem la pilota. També hem de assignar el target
                // el target actual al target de la classe Ball.
                if (move.isKeyPressed(Input.KEY_SPACE)) {
                    this.ball = cannon.fire();
                    this.ball.setTarget(this.target);
                }
            }

            //Sempre i quan la pilota no sigui null, podrem carregar el update de la pilota
            if (this.ball != null) {
                this.ball.update();

                //Condicions per determinar quan el jugador guanya i quan el jugador perd i quan l'hem de restar intents i quan hem de sumar o restar puntuacio
                //Si s'ha fallat, doncs eliminarem la anterior pilota (basta que l'ha possem com a null) i reiniciem el target (amb el metode reset).
                if (this.ball.hasFallen()) {
                    this.ball = null;
                    this.target.reset();

                    //Nomes llevarem punts sempre i quan el score actual sigui major a 30. Si aixo no es compleix simplement llevarem intents
                    if (!this.shotTarget && this.score > 30) {
                        this.score -= 30;
                        this.shotsInARow = 0;
                        this.attempts--;
                    } else if (!this.shotTarget) {
                        this.attempts--;
                        this.shotsInARow = 0;
                    }
                    this.shotTarget = false;

                    //Si no ha fallat encara, i el jugador ha encertat, doncs sumarem puntuacio al Score i posarem com true el boolea Dyng de la classe target.
                } else {
                    if (this.ball.hit() && !shotTarget) {
                        this.shotTarget = true;
                        this.score += 100;
                        this.shotsInARow++;
                        this.target.setDyng(true);
                    }
                }
            }

            //Nomes canviarem d'estat quan el intents o vides sigui igual a 0
            if (this.attempts == 0) {
                this.actualState = State.ADDSCORE;
            }

            //Si el jugador encerta mes de 3 vegades seguides, doncs l'hi sumam un intent mes
            if (this.shotsInARow == 3) {
                this.attempts++;
                this.shotsInARow = 0;
            }
            //Com que en l'estat ADDSCORE fa falta que el jugador introduiexi el seu nom, doncs hem de habilitar el input de totes les lletres del teclat
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
            //A mes de les tecles, també hem de permetre al jugador esborrar les lletres introduides. Aixo ho farem amb la tecla retroceso
            if (move.isKeyPressed(Input.KEY_BACK)) {
                StringBuilder newString = new StringBuilder();
                for (int j = 0; j < this.name.length() - 1; j++) {
                    newString.append(Character.toString(this.name.charAt(j)));
                }

                this.name = newString.toString();

            }

            //Nomes avançarem cap al seguent estat sempre i quan l'usuari presioni la tecla ENTER a més de que els camps del nom estiguin complets.
            if (move.isKeyPressed(Input.KEY_ENTER)) {
                if (this.name.length() == 3) {
                    this.actualState = State.SCOREBOARD;
                    try {
                        //A més, guardarem l'actual resultat en el scoreboard gracies al metode addData
                        addData(parsePunts() + "-" + this.name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //Una vegada en el scoreboard, simplement habilitarem la tecla ENTER per poder avançar al seguent estat.
        } else if (this.actualState.equals(State.SCOREBOARD)) {
            if (move.isKeyPressed(Input.KEY_ENTER)) {
                this.actualState = State.GAMEOVER;
            }
            // En l'estat GAMEOVER, decidirem si l'usuari vol seguir jugant o si al contrari vol tancar el joc.
        } else if (this.actualState.equals(State.GAMEOVER)) {
            if (move.isKeyPressed(Input.KEY_RIGHT)) {
                this.selectorGameOver = false;
            } else if (move.isKeyPressed(Input.KEY_LEFT)) {
                this.selectorGameOver = true;
            }

            //Si al presionar la tecla ENTER, l'usuari a seleccionat continuar, doncs reinciarem tots els atributs del joc com pot ser el SCORE o el intents.
            if (move.isKeyPressed(Input.KEY_ENTER)) {
                if (this.selectorGameOver) {
                    //Reiniciam atributs
                    this.score = 0;
                    this.attempts = 5;
                    this.shotsInARow = 0;
                    this.ball = null;
                    this.shotTarget = false;
                    this.name = "";

                    this.actualState = State.MENU;

                    //Si no vol seguir jugant, simplementar tancarem el joc
                } else {
                    System.exit(0);
                }
            }
        }

    }

    //El metode render farem practicament el mateix que en el update. Cridarem a tots el render de tots els objectes. A més, segons en l'estat que es trobi el joc,
    // dibuixarem una cosa o un altre
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        UnicodeFont textMenu = ResourceManager.getFont("WHITRABT.TTF", 40);

        //Si ens trobam en el menu, doncs nomes dibuixarem el fons, el nigul (el render de lanscape) i un text descriptiu.
        if (this.actualState.equals(State.MENU)) {
            this.landscape.render();
            textMenu.drawString(300, 288, "Press ENTER to begin", Color.black);

            //Si ens trobam en el GAME, haurem de dibuixar tots els renders dels objectes, el marcador indicant la força i el l'angle actual i les vides que te
        } else if (this.actualState.equals(State.GAME)) {
            this.landscape.render();

            // Si hi ha un pilota, es pot començar a dibuixar
            if (ball != null) {
                this.ball.render(graphics);
            }
            this.cannon.render();
            this.target.render(graphics);

            //Fons transparent del marcador
            graphics.fillRect(0, 0, 1024, 70);
            Color transparentBlack = new Color(0, 0, 0, 75);
            graphics.setColor(transparentBlack);

            //Marcadors
            this.scoreboardFont.drawString(20, 45, "Strenth: " + this.cannon.getStrength());
            this.scoreboardFont.drawString(492, 45, "Angle: " + Math.abs(this.cannon.getRotation()));
            this.scoreboardFont.drawString(850, 45, "Score: " + parsePunts());

            //Dibuixam les vides gracies al metode dibuixaIntents
            dibuixaIntents();

            //En l'estat ADDSCORE, simplement dibuixarem unes quantes frases explicatives i les lletres que introduiex el jugador
        } else if (this.actualState.equals(State.ADDSCORE)) {

            //Carregam una imatge de fons i carregam una font nova amb un tamany de 70
            this.addScoreImage.draw(0, 0);
            UnicodeFont scoreboardFontBig = ResourceManager.getFont("WHITRABT.TTF", 70);

            //Renderitzam les frases corresponents i tres lines indicant a on estaran les lletres que introduiexqui l'usuari
            this.scoreboardFont.drawString(300, 150, "Congratulations! You have a new score!");
            this.scoreboardFont.drawString(450, 200, "Add your name");
            this.scoreboardFont.drawString(370, 470, "Press ENTER to continue", Color.white);
            Shape rec1 = new Rectangle(380, 350, 75, 5);
            Shape rec2 = new Rectangle(480, 350, 75, 5);
            Shape rec3 = new Rectangle(580, 350, 75, 5);
            graphics.fill(rec1);
            graphics.fill(rec2);
            graphics.fill(rec3);

            //Segons quantes vegades l'usuari ja ha presionat les tecles necessaries per escriure el nom, entrara en un condicional per renderitzar.
            //Si el jugador ha introduit dos lletres, doncs nomes es dibuixaran dos lletres
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


            //I en l'estat SCOREBOARD simplement dibuixam el SCORE actual.
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
            this.scoreboardFont.drawString(390, 450, "Press ENTER to continue", Color.white);

            try {
                //S'ha aconsegueix tots els scores i noms gracies al metode getData, que ens permet aconseguir tota la informacio del txt
                String[] data = getData();
                int posicio = 130;
                Color[] colors = new Color[]{Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.cyan, Color.magenta, Color.pink, Color.gray, Color.darkGray, Color.darkGray};

                //I recorrem el array de puntuacions, separant-los gracies al metode split.
                for (int i = 0; i < data.length; i++) {
                    String[] parts = data[i].split("-");
                    String actualScore = parts[0];
                    String actualName = parts[1];

                    //I despres de fer el split, afegim l'actual score i l'actual name a la pantalla. Cada vegada augmentarem la posicio Y en 30
                    this.scoreboardFont.drawString(480, posicio, actualScore, colors[i]);
                    this.scoreboardFont.drawString(780, posicio, actualName, colors[i]);
                    posicio += 30;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //I en l'ultim estat, GAMEOVER, dibuixarem el fons i dibuixarem el rectangle de seleccio segons l'usuari seleccioni segui jugant o sortir (YES o NO)
        } else if (this.actualState.equals(State.GAMEOVER)) {
            this.gameoverImage.draw(0, 0);

            //Si ha presionat la tecla esquerra, voldra dir que voldra seguir i que dibuixarem el rectangle de seleccio alrededor del YES
            if (this.selectorGameOver) {
                graphics.drawRect(435, 405, 50, 25);

                //Al contrari, dibuixarem el rectangle de seleccio alrededor del NO
            } else {
                graphics.drawRect(543, 405, 50, 25);
            }
            graphics.setColor(Color.red);
        }

    }

    //Metode que ens permet tenir SCORE en format joc (amb zeros davant). 00012, 00539, 09165...
    private String parsePunts() {
        StringBuilder result = new StringBuilder("");
        int scoreLength = Integer.toString(this.score).length();

        for (int i = 0; i <= 5 - scoreLength; i++) {
            result.append("0");
        }

        result.append(this.score);
        return result.toString();
    }

    //Metode que ens permet dibuixa per pantalla les cors indicant el numero d'intents que l'hi queden al jugador.
    private void dibuixaIntents() {
        int initialX = 990;
        int initialY = 5;


        //Si el intents es major a 36, voldra dir que ja no hi caben mes cors, doncs haurem de dibuixar un numero auxiliar.
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

    //Metode que ens permet escrire el resultat en un fitxer txt, per despres llegir-lo i poder carregar tots els scores. Aixo ens permet guardar tots els scores una vegada
    // el jugador ha tancat el joc.
    private void addData(String score) throws IOException {
        int nouScore = Integer.parseInt(score.split("-")[0]);
        boolean afegit = false;
        File archive = new File("maxScore.txt");
        BufferedWriter bw;

        //Si el fitxer existeix, haurem de recuperar les anterior dades.
        if (archive.exists()) {
            String[] data = getData();
            bw = new BufferedWriter(new FileWriter(archive));
            for (int i = 0; i < data.length; i++) {
                int actualScore = Integer.parseInt(data[i].split("-")[0]);

                if (nouScore > actualScore && !afegit) {
                    bw.write(score);
                    bw.newLine();
                    bw.write(data[i]);
                    bw.newLine();
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

    //Metode que ens permet recuperar les dades d'un fitxer txt. Aquestes dades posteriorment les utilitzarem per carregar en la pantalla de scoreboard
    private String[] getData() throws FileNotFoundException, IOException {
        List<String> resultList = new ArrayList<String>();
        String line;
        FileReader file = new FileReader("maxScore.txt");
        BufferedReader buffer = new BufferedReader(file);

        while ((line = buffer.readLine()) != null) {
            resultList.add(line);
        }

        //Eliminiam l'ultim registre innecessari
        if (resultList.size() > 10) {
            resultList.remove(resultList.get(resultList.size() - 1));
        }
        buffer.close();

        String[] result = new String[resultList.size()];
        resultList.toArray(result);
        return result;
    }
}
