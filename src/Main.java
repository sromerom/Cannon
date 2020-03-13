import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    public static void main(String[] args) {
        try {
            CannonGame cg = new CannonGame("Provant");
            AppGameContainer app = new AppGameContainer(cg);
            app.setTargetFrameRate(60);
            app.setShowFPS(true);
            app.setDisplayMode(1024, 576, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}
