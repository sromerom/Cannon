import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    public static void main(String[] args) {
        try {

            //Cream una instancia de CannonGame per poder carregar tot el joc que hem creat. Als parametres del constructor haurem de passar-li un nom pel joc (Title name).
            CannonGame cg = new CannonGame("Cannon Game");

            //I aquest BasicGame ho passarem a un AppGameContainer a on canviarem una serie de opcions com pot ser el tamany de la pantalla de joc,
            // llevar que mostri els FPS per pantalla, entre altres.
            AppGameContainer app = new AppGameContainer(cg);
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.setDisplayMode(1024, 576, false);

            //El metode mes important i el que ens permet que el joc realment funcioni es el metode start(), sense ell, el joc ni arrancaria.
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}
