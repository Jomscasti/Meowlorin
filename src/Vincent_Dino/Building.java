package Vincent_Dino;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import Home.MainPanel;

public class Building extends JPanel {
    private BufferedImage imgDesign;
    private int x, y, width, height;

    public Building(MainPanel mp, BufferedImage imgDesign, int x, int y, int width, int height) {
        this.imgDesign = imgDesign;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setOpaque(false);
        requestFocusInWindow();
    }

    public void moveLeft() {
        x -= 3;
        if (x <= -width) {
            x = 900;
        }
        setLocation(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgDesign != null) {
            g.drawImage(imgDesign, 0, 0, width, height, this);
        }
    }
}
