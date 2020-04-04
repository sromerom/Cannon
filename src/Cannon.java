import org.newdawn.slick.*;

//La classe cannon ens permet carregar tots els components que formen el cano del joc (la base del cano i el cano) i poder moure i disparar aquest.
public class Cannon {
    private Image cannonImage = ResourceManager.getImage("cannon.png");
    private Image cannonBaseImage = ResourceManager.getImage("cannon_base.png");
    private double rotation = 0;
    private double strength = 0;

    //En el metode update de Cannon, farem que es vagi canviant l'atribut rotation segons si es pren una tecla o un altre (Right and Left keys)
    public void update(GameContainer gameContainer, int i) {

        //Per poder detectar la tecla que es pren s'haura de utilitzar el metode getInput(), que ens proporciona el gameContainer. Es per això que com
        // a parametre hem de rebre un objecte GameContainer.
        Input mou = gameContainer.getInput();

        //Si es pren la tecla esquerra, doncs diminuirem la rotacio i si es pren la tecla dreta augmentarem. Farem el mateix amb les tecles adalt i abaix, segons
        // que tecla de les dues es prengui variara la força
        if (mou.isKeyDown(Input.KEY_LEFT)) {
            updateRotation(-0.5);
        } else if (mou.isKeyDown(Input.KEY_RIGHT)) {
            updateRotation(0.5);
        } else if (mou.isKeyDown(Input.KEY_UP)) {
            updateStrength(1);
        } else if (mou.isKeyDown(Input.KEY_DOWN)) {
            updateStrength(-1);
        }
    }


    //Al metode render renderitzarem les dues parts que formen el cano, especificarem quina sera el centre de rotacio d'aquest i el rotarem amb l'actual rotation
    public void render() {
        this.cannonImage.setCenterOfRotation((this.cannonImage.getWidth() / 2) / 2, this.cannonImage.getHeight() / 2);

        //Amb el metode rotate, podem rotar la imatge simulant que el cano es mou.
        this.cannonImage.rotate((float) this.rotation);

        //Una vegada s'ha rotat amb l'actual rotacio del moment, el dibuixam en les coordenades que l'hi corresponen.
        this.cannonImage.draw(42, 470);
        this.cannonBaseImage.draw(42, 490);

        //Per tal de que el cano no començi a fer rotacions cada vegada que es crida render(), doncs hem de fer que es quedi aturat. Ho farem tornant a restar la rotacio
        // que hem fet abans
        this.cannonImage.rotate((float) -(this.rotation));
    }

    //El metode fire, es el metode que ens permet disparar les pilotes. Aquest metode retornara un objecte de tipus Ball amb una força i una rotacio determinada.
    public Ball fire() {
        return new Ball(this.rotation, this.strength);
    }

    //El metode updateRotation ens permet actualitzar l'atribut rotation. Aquest metode es cridara cada vegada que l'usuari presioni una tecla de les que hem dit abans.
    public void updateRotation(double deltaRotation) {

        //Simplement suma (o resta si el delta es negativa) el delta qui l'hi passem.
        this.rotation = this.rotation + deltaRotation;

        //Posam que el graus del cano siguin d'un maxim de 90 i d'un minim de 0. Aixi el cano estara limitat.
        if (this.rotation < -90) {
            this.rotation = -90;
        }

        if (this.rotation > 0) {
            this.rotation = 0;
        }
    }

    //Metode updateStrength que ens permet actualitzar el actual strength passant com a parametre el delta que volem canviar.
    public void updateStrength(double deltaStrength) {
        this.strength = this.strength + deltaStrength;

        // En aquest cas, el maxim de força sera d'un 100% i el minim sera d'un 20%.
        if (this.strength < 20) {
            this.strength = 20;
        }

        if (this.strength > 100) {
            this.strength = 100;
        }
    }

    //Getters and setters
    public double getRotation() {
        return rotation;
    }

    public double getStrength() {
        return strength;
    }

}
