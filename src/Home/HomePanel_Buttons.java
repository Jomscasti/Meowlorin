package Home;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HomePanel_Buttons extends JButton {
    private final BufferedImage img;
    private static final int hoverWidth = 268; // Adjust as per your hover effect size
    private static final int hoverHeight = 70; // Adjust as per your hover effect size

    public HomePanel_Buttons(String imgPath, int x, int y, int width, int height, int i, MainPanel mp) {
        this.img = loadImages(imgPath);
        
        // Set initial bounds and size
        setBounds(x, y + 55, width, height);
        setPreferredSize(new Dimension(width, height));
        
        // Remove default button border and make it transparent
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);

        addHoverEffect(x, y, width, height, hoverWidth, hoverHeight);
    
        // Assign action listeners based on index i
        addActionListener(e -> {
            switch (i) {
                case 0:
                    // Action for button 1
                    mp.showScreen("LOADING1");
                    break;
                case 1:
                    // Action for button 2
                	mp.showScreen("GAME2");
                    break;
                case 2:
                    // Action for button 2
                	mp.showScreen("LOADING3");
                    break;
                case 3:
                    // Action for button 2
                	mp.showScreen("LOADING4");
                    break;
                case 4:
                    // Action for button 2
                    mp.showScreen("LOADING5");
                    break;
                // Add more cases as needed for additional buttons
                default:
                    break;
            }
        });
    }
    
    private void addHoverEffect(int x, int y, int normalWidth, int normalHeight, int hoverWidth, int hoverHeight) {
        // Add mouse listener for hover animation
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBounds(x - (hoverWidth - normalWidth) / 2, 55 + y - (hoverHeight - normalHeight) / 2, hoverWidth, hoverHeight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBounds(x, 55 + y, normalWidth, normalHeight);
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
    }

    private BufferedImage loadImages(String imgPath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(imgPath));
        } catch (IOException e) {
            System.err.println("IOException while loading image: " + imgPath);
            e.printStackTrace();
        }
        return img;
    }
}