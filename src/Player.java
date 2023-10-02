import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Player {

    Settings s = new Settings();
    private float x;
    private float y;
    private int score;
    private boolean p1;

    public Player(boolean p1) {
        if (p1) {
            this.p1 = true;
            this.x = -0.9f;
            this.y = 0.0f;
        } else {
            this.p1 = false;
            this.x = 0.9f;
            this.y = 0;
        }
    }

    public float getX() {
        return x;
    }

    // Setter para x
    public void setX(float x) {
        this.x = x;
    }

    // Getter para y
    public float getY() {
        return y;
    }

    // Setter para y
    public void setY(float y) {
        this.y = y;
    }

    public void draw() {

        glBegin(GL_QUADS);
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex2f(this.x, this.y); // Canto superior esquerdo
        glVertex2f(this.x + 0.02f, this.y); // Canto superior direito
        glVertex2f(this.x + 0.02f, this.y + 0.25f); // Canto inferior direito
        glVertex2f(this.x, this.y + 0.25f); // Canto inferior esquerdo
        glEnd();
    }
}