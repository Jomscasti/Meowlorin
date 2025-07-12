package Ken_MemoryMatch;

import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadingScreen1 extends JPanel {
    GamePanel1 gp1;
    private BufferedImage bg1, title, buttonImage, card;
    
    int titleX = 170, titleY = 9;
    int btnX = 300, btnY = 350;
    int deckX = -70, deckY = 72;
    private BufferedImage deck;

    int duration = 0;
    boolean isDone = false;
    MainPanel mainPanel;
    
    Timer tGiveCard;
    Timer tDuration;

    public LoadingScreen1(MainPanel mp, GamePanel1 gp1) {
        setLayout(null);
        this.mainPanel = mp;
        getImages();
        setButton(mp, gp1);
        this.gp1 = gp1;
        tGiveCard = new Timer(1000 / 120, e -> animate(mp));
        tDuration = new Timer(1000, e -> checkDuration(mp));
        resetDefault(mp);
    }

    public void resetDefault(MainPanel mp) {
        titleX = 170;
        titleY = 9;
        btnX = 300;
        btnY = 350;
        deckX = -70;
        deckY = 72;
        duration = 0;
        isDone = false;
        tGiveCard.stop();
        tDuration.stop();
        addBackButton(mp);
        repaint();
        revalidate();
    }
    
    private void addBackButton(MainPanel mp) {

        // Create a JButton with the scaled image
		JButton backButton = new JButton();
		backButton.setIcon(gp1.resizeImage("/back_button.png", 104, 46));
	    
        // Set the bounds (position and size) of the button
        backButton.setBounds(20, 20, 104, 46);

        // Remove default button border (if desired)
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);

        // Add hover effect to the backButton
        mp.addHoverEffect(backButton, 20, 20, 104, 46, 124, 66, "/back_button.png");

        // Add action listener to handle button click
        backButton.addActionListener(e -> {
            mp.showScreen("HOME");
        });

        // Add the button to the panel
        add(backButton);
    }
    
    private void animate(MainPanel mp) {
        if (duration >= 0 && duration <= 1) {
            titleY -= 3; // Move the title vertically
        }
        if (duration > 1) {
            isDone = true;
            tGiveCard.stop(); // Stop the animation timer once done
            checkTransition(mp); // Check for transition after animation stops
        }
        repaint();
    }

    private void checkDuration(MainPanel mp) {
        duration += 1;
        checkTransition(mp); // Check for transition based on duration
        repaint();
    }

    private void checkTransition(MainPanel mp) {
        if (isDone) {
            resetDefault(mp);
            mainPanel.showScreen("GAME1");

            // Ensure GamePanel1 components are reset correctly
            GamePanel1 gamePanel1 = mainPanel.getGamePanel1();
            if (gamePanel1 != null) {
                gamePanel1.startTimer();
                gamePanel1.repaint();
                gamePanel1.resetScore();
            }

            // Stop all timers to ensure clean transition
            tGiveCard.stop();
            tDuration.stop();
        }
    }

    private void setButton(MainPanel mp, GamePanel1 gp1) {
        // Create button and set icon
        JButton playButton = new JButton();
        playButton.setIcon(gp1.resizeImage("/start_button.png", 190, 70));
        playButton.setBounds(btnX, btnY, 190, 70);

        // Remove default button border
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);

        gp1.addHoverEffect(playButton, btnX, btnY, 190, 70, 220, 90, "/start_button.png");
        playButton.addActionListener(e -> {
        	resetDefault(mp);
            // Start Animating the transition
            if (!tGiveCard.isRunning() && !tDuration.isRunning()) {
                tGiveCard.start();
                tDuration.start();
                // Sound effects
                mp.playSE(5);
            }
        });
        add(playButton);
    }

    private void getImages() {
        bg1 = loadImage("/gp1/bg1");
        title = loadImage("/gp1/title1");
        buttonImage = loadImage("/gp1/play");
    }

    private BufferedImage loadImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource(imagePath + ".png"));
        } catch (IOException e) {
            System.err.println("IOException while loading image: " + imagePath);
            e.printStackTrace();
        }
        return image;
    }

    private void drawBackground(Graphics g) {
        g.drawImage(bg1, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawTitle(Graphics g) {
        g.drawImage(title, titleX, titleY, 459, 332, null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawTitle(g);

        // Update the position of the button in paintComponent
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("backButton".equals(button.getName())) {
                    button.setBounds(20, 20, 104, 46);
                }
            }
        }
    }
}