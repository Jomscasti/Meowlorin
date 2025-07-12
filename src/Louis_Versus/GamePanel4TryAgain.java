package Louis_Versus;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;

public class GamePanel4TryAgain extends JPanel {

    private GamePanel4LoadingScreen loadingScreen;
    private GamePanel4 gp4;
    private Image backgroundImage;

    public static Color clrFont = new Color(174,78,204,255);
    
    public GamePanel4TryAgain(GamePanel4LoadingScreen loadingScreen, GamePanel4 gp4, MainPanel mp, GamePanel4Play gameLogic) {

        ImageIcon img1 = new ImageIcon(getClass().getResource("/gp4/loadingScreen.jpg"));
        backgroundImage = img1.getImage();

        this.loadingScreen = loadingScreen;
        this.gp4 = gp4;

        setSize(800, 600);
        setOpaque(false); // Make the panel transparent
        setLayout(null); 

        JLabel tryAgainLabel = new JLabel("Congrats! You Gain:");
        tryAgainLabel.setForeground(clrFont);
        tryAgainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tryAgainLabel.setFont(mp.arcade.deriveFont(Font.BOLD, 20));
        tryAgainLabel.setOpaque(false); 
        tryAgainLabel.setBounds(145, 20, 500, 30); // Set position and size

        JLabel scoreLabel = new JLabel("Fishes = " + gameLogic.getScore());
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(mp.arcade.deriveFont(Font.BOLD, 20));
        scoreLabel.setOpaque(false); 
        scoreLabel.setBounds(285, 70, 200, 30); // Set position and size

        JLabel win = new JLabel(new ImageIcon(loadImage("/gp4/win.png")));
        win.setHorizontalAlignment(SwingConstants.CENTER);
        win.setOpaque(false); 
        win.setBounds(190, 100, 400, 200);
        
        JButton backButton = new JButton(new ImageIcon(loadImage("/gp4/back_button.png")));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false); 
        backButton.setBounds(315, 450, 150, 75); // Set position and size
        backButton.addActionListener(e -> {
            mp.showScreen("LOADING4");
            loadingScreen.resetGame(gp4); // Reset the game on the loading screen
            loadingScreen.setVisible(true); // Show the loading screen
        });

        add(tryAgainLabel);
        add(scoreLabel);
        add(win);
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private BufferedImage loadImage(String filename) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filename);
            if (inputStream != null) {
                return ImageIO.read(inputStream);
            } else {
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + filename);
            e.printStackTrace();
            return null;
        }
    }
}
