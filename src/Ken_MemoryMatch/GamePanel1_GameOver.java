package Ken_MemoryMatch;

import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel1_GameOver extends JPanel {
    private int score;
    BufferedImage bg1;
    private int highscore;
    private JLabel scoreLabel;
    private JLabel highscoreLabel;


    public GamePanel1_GameOver(MainPanel mp, int score, int highscore, GamePanel1 gamePanel, LoadingScreen1 ld1) {
        this.score = score;
        this.highscore = highscore; 
        setLayout(null); // Use null layout for absolute positioning

        // Create a label to display the score
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(gamePanel.arcade.deriveFont(24f));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);

        // Create a label to display the highscore
        highscoreLabel = new JLabel("Highscore: " + highscore);
        highscoreLabel.setFont(gamePanel.arcade.deriveFont(30f));
        highscoreLabel.setForeground(Color.YELLOW);
        add(highscoreLabel);

        setBackground(Color.BLACK); // Set background color
        setButton(mp, gamePanel);
        // Center labels initially
        updateAndCenterLabels();
    }
    
    private void setButton(MainPanel mp, GamePanel1 gp1) {
        // Create button and set icon
        JButton playButton = new JButton();
        playButton.setIcon(gp1.resizeImage("/gp1/play.png", 200, 100));
        playButton.setBounds(300, 300, 200, 100);

        // Remove default button border
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);

        gp1.addHoverEffect(playButton, 300, 300, 200, 100, 220, 120, "/gp1/play.png");
        playButton.addActionListener(e -> {
        	gp1.resetScore();
            gp1.startTimer();
            mp.showScreen("GAME1");
            mp.playSE(5);
        });
        add(playButton);
    }

    private void updateAndCenterLabels() {
        String scoreText = "Score: " + score;
        String highscoreText = "Highscore: " + highscore;

        scoreLabel.setText(scoreText);
        highscoreLabel.setText(highscoreText);

        // Calculate the position to center the labels
        Dimension scoreLabelSize = scoreLabel.getPreferredSize();
        Dimension highscoreLabelSize = highscoreLabel.getPreferredSize();
        int panelWidth = 800;

        int scoreX = (panelWidth - scoreLabelSize.width) / 2;
        int scoreY = 200; // Adjust Y position as needed
        int highscoreX = (panelWidth - highscoreLabelSize.width) / 2;
        int highscoreY = 100; // Adjust Y position as needed

        scoreLabel.setBounds(scoreX, scoreY, scoreLabelSize.width, scoreLabelSize.height);
        highscoreLabel.setBounds(highscoreX, highscoreY, highscoreLabelSize.width + 300, highscoreLabelSize.height);

        revalidate();
        repaint();
    }
    

    private void drawBg(Graphics g) {
        try {
            bg1 = ImageIO.read(getClass().getResource("/gp1/gameoverbg.png"));
        } catch (IOException e) {
            System.err.println("IOException while loading image:");
            e.printStackTrace();
        }
        
        g.drawImage(bg1,0,0,800,600, null);

    	
    }


    
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	drawBg(g);
    	repaint();
    	
    }
}