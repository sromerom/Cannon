import org.newdawn.slick.Image;

public class Landscape {
    private Image landscapeImage = ResourceManager.getImage("landscape.jpg");
    private Image cloudImage = ResourceManager.getImage("cloud.png");
    private double positionX = 0;
    private boolean turnBack = false;
    public void update() {
        if (!turnBack) {
            positionX += 0.3;
        } else if (turnBack) {
            positionX -= 0.3;
        }
    }

    public void render() {
        this.landscapeImage.draw(0, 0);
        this.cloudImage.draw((float)this.positionX, 100);

        if (this.positionX > 1024 - 200) {
           this.turnBack = true;
        }

        if (this.positionX < 0) {
            this.turnBack = false;
        }
    }
}
