package Janna_Eating;
import javax.swing.*;
import java.awt.*;

public class GamePanel5_Character extends JPanel {
    private int cat1X = 0;
    private int cat1Y = 0;
    private String cat1Direction = "still";
    
    private ImageIcon icnCat1, icnCat2, icnCat3;
    private JLabel lblSprite;

    public GamePanel5_Character() {
        this.setOpaque(false);
        this.setLayout(null);

        //Cat Images
        icnCat1 = new ImageIcon(getClass().getResource("/cat1.png"));
        icnCat2 = new ImageIcon(getClass().getResource("/cat2.png"));
        icnCat3 = new ImageIcon(getClass().getResource("/cat3.png"));

        lblSprite = new JLabel();
        lblSprite.setBounds(cat1X, cat1Y, 100, 150); 
        lblSprite.setIcon(icnCat2);
        add(lblSprite);

        Timer t = new Timer(1000 / 2, e -> {
            switch (cat1Direction) {
                case "right":
                case "left":
                    break;
                case "still":
                    lblSprite.setIcon(icnCat1);
                    break;
            }
            repaint();
        });
        t.start();
    }

    public Rectangle getMouthBounds() {
        int mouthX = this.getX() + 50; 
        int mouthY = this.getY() + 70; 
        int mouthWidth = 20; 
        int mouthHeight = 10; 
        return new Rectangle(mouthX, mouthY, mouthWidth, mouthHeight);
    }
    
    public void switchToEatingAnimation() {
        lblSprite.setIcon(icnCat2);
    }

    public void switchToIdleAnimation() {
        lblSprite.setIcon(icnCat3);
    }
}
