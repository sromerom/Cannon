import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

//La classe Target ens permetra carregar la imatge del target i donar-li totes les seves respectives funcionalitats
public class Target {
    private Image targetImage = ResourceManager.getImage("target.png");
    private Shape collisionTarget;
    private double positionX;

    //Com que el meu joc el target nomes varia sobre l'eix X, doncs l'eix Y sempre sera 500. Si volguessim que el target pugui apareixer ens els dos eixos,
    // aquesta posicioY no podria ser fixa.
    private double positionY = 500;

    //Tamany maxim i minim en el que el target pot apareixer.
    private final int MAX = 890;
    private final int MIN = 225;

    //El random estara entre els maxims i minims que hem especificat
    private int range = MAX - MIN + 1;
    private int random = (int) (Math.random() * range) + MIN;
    private boolean dyng = false;

    //Constructor a on especificarem quina es la posicio del target ja amb el random calculat. Obviament cada vegada que es crea un target aquest random varia.
    //Al mateix temps, tambe es crea i s'actualitza el shape, el seu rang de colisio. La forma d'aquest shape sera un rectangle
    public Target() {
        this.positionX = random;
        this.collisionTarget = new Rectangle((float) this.positionX, (float) positionY, targetImage.getWidth(), targetImage.getHeight());
    }

    //Com que el target en un objecte fixe del joc, en un principi no hem d'anar actualizant res. Només actualizarem la seva posicio quan el jugador encerti el dispar.
    public void update() {

        //Quan l'usuari encerta en el target, aquest es començara a moure sobre l'eix Y (cap avall). Aixo ho aconseguirem amb el boolea dyng. Mentres dyng sigui false,
        // no es realitzara aquesta animacio
        if (isDyng()) {
            this.positionY += 5;
        }
    }


    //Al render dibuixarem el target amb la posicio random ja calculada
    public void render(Graphics g) throws SlickException {
        this.targetImage.draw((float) this.positionX, (float) positionY);
    }

    //El metode reset ens permetra "reiniciar" el target. Al reiniciar posarem el boolea dyng en false (per que no faci l'animacio), i tornarem a crear una nova posicio X aleatoriament
    // Sempre que modifiquem la posicio del target, tambe haurem de modificar el el shape de colisions, per tant tambe canviarem de posicio el aquest shape.
    public void reset() {
        this.dyng = false;
        this.positionY = 500;
        this.random = (int) (Math.random() * range) + MIN;
        this.positionX = this.random;
        this.collisionTarget.setX((float) this.positionX);
    }

    //Getters and Setters
    public Shape getShape() {
        return collisionTarget;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public boolean isDyng() {
        return dyng;
    }

    public void setDyng(boolean dyng) {
        this.dyng = dyng;
    }

}
