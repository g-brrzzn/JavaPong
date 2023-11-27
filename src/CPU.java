import java.awt.*;

public class CPU {
    public void control(Paddle p, Ball b) {
        int halfScreen = 500;
        if (p.id == 1){
            if (b.getX() <= halfScreen * 0.75) p.setYDirection(b.getYDirection());
        }
        else {
            if (b.getX() >= halfScreen * 1.25) p.setYDirection(b.getYDirection());
        }
    }

    public void draw(Graphics g, boolean p1, boolean p2) {
        if (p1) g.drawString("CPU 1", (1000/2)-350, 50);
        if (p2) g.drawString("CPU 2", (1000/2)+200, 50);
    }
}
