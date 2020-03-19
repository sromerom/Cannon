import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Landscape {
    private Image landscapeImage = ResourceManager.getImage("landscape.jpg");

    public void update() {

    }

    public void render() {
        this.landscapeImage.draw(0, 0);
    }
}
