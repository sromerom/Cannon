import org.newdawn.slick.Image;

//Classe Landscape carregarem i assignarem la funcionalitat de cada una de les parts que formen el fons del joc. En aquest cas
// en el fons de joc hi trobarem l'imatge de fons i el nigul.
public class Landscape {
    private Image landscapeImage = ResourceManager.getImage("landscape.jpg");
    private Image cloudImage = ResourceManager.getImage("cloud.png");
    private double positionX = 0;
    private boolean turnBack = false;

    //Metode update que anira actualizant la posicio del nigul sobre l'eix X. Amb aquest contingut, ens permetra moure el nigul de dreta a esquerra.
    public void update() {

        //El boolea turnBack ens permetra saber si el nigul ha arribat al final de la pantalla i ha de tornar cap enrere.
        //Si el boolea es false augmentarem X en 0.3, i en cas contrari, per tornar enrere disminuirem X en 0.3
        if (!turnBack) {
            positionX += 0.3;
        } else {
            positionX -= 0.3;
        }

        //Assignarem el boolea turnBack com true, quan la posicio actual de X sigui major al tamany maxim de la pantalla (width) menys el el tamany del nigul
        if (this.positionX > landscapeImage.getWidth() - cloudImage.getWidth()) {
            this.turnBack = true;
        }

        //I si la posicio X es menor a 0 doncs tornarem a posar a false el boolea.
        if (this.positionX < 0) {
            this.turnBack = false;
        }
    }


    //Metode render a on dibuixarem el fons del joc i la posicio actual del nigul.
    public void render() {
        this.landscapeImage.draw(0, 0);

        //Com que en el update anem actualizant la posicio del nigul, l'anirem dibuixant en una posicio diferent cada vegada. Aixo es possible gracies al atribut positionX
        this.cloudImage.draw((float) this.positionX, 100);
    }
}
