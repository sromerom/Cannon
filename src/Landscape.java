import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Landscape {
    private Image landscapeImage = ResourceManager.getImage("landscape.jpg");
    private Image cloudImage = ResourceManager.getImage("cloud.png");
    private double cloudX = 0;
    private boolean enrere = false;
    public void update() {
        if (!enrere) {
            cloudX += 0.3;
        } else if (enrere) {
            cloudX -= 0.3;
        }
    }

    public void render() {
        this.landscapeImage.draw(0, 0);
        this.cloudImage.draw((float)this.cloudX, 100);

        if (this.cloudX > 1024 - 200) {
           this.enrere = true;
        }

        if (this.cloudX < 0) {
            this.enrere = false;
        }
    }
}
