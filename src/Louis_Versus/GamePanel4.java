package Louis_Versus;
import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

public class GamePanel4 extends JPanel {

    private BufferedImage bg1;
    private BufferedImage img;
    private BufferedImage[] defaultFrames, default2Frames;
    
    private GamePanel4Buttons sleepButton;
    private GamePanel4Buttons scareButton;
    private GamePanel4Buttons scratchButton;
  
    private Random rnd;

    private GamePanel4Play gameLogic;
    
    private JLabel resultLabel;
    
    private int spriteNum;
    private int spriteDelay;
   
    private int spriteX = 130;
    private int spriteY = 425;
    private int spriteWidth = 100;
    private int spriteHeight = 100;

    private int sprite2Num;
    private int sprite2X = 654; 
    private int sprite2Y = 425; 
    private int sprite2Width = 100;
    private int sprite2Height = 100;
    
    Timer tAnimate;
    
    public String state;
    
    public JLabel livesLabel;
    public JLabel scoreLabel;
    
    public int score;
    public int highScore;
    
    public GamePanel4(MainPanel mp) {
        this.setLayout(null);
        
        GamePanel4LoadingScreen  loadingScreen = new GamePanel4LoadingScreen (mp);
        
        gameLogic = new GamePanel4Play();
        state = "default";
        tAnimate = new Timer(1000/80, e -> animate());
        spriteDelay = 0;
        score = 0;
        highScore = 0;

        // Back button
        JButton backButton = new JButton();
        backButton.setBounds(20, 10, 110, 80);
        backButton.setIcon(resizeImages("/HomePage/back_button.png", 110, 80));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        addHoverEffect(backButton, 20, 10, 110, 80, 130, 100, "/HomePage/back_button.png");
        backButton.addActionListener(e -> mp.showScreen("LOADING4")); // Switch to GAME panel
        add(backButton);
        
        
        // Title labels
        JLabel titleLabel1 = new JLabel(new ImageIcon(loadImage("/gp4/smallTitle.png")));
        titleLabel1.setForeground(Color.MAGENTA);
        titleLabel1.setBounds(200, 20, 320, 50);
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel2 = new JLabel("Choose the right action");
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setFont(mp.arcade.deriveFont(Font.PLAIN, 18));
        titleLabel2.setBounds(130, 40, 450, 110);
        titleLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabel2line2 = new JLabel("to win your fish");
        titleLabel2line2.setForeground(Color.WHITE);
        titleLabel2line2.setFont(mp.arcade.deriveFont(Font.PLAIN, 18));
        titleLabel2line2.setBounds(130, 70, 450, 110);
        titleLabel2line2.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel instructionLabel = new JLabel("Directions:");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(mp.arcade.deriveFont(Font.PLAIN, 18));
        instructionLabel.setBounds(555, 25, 200, 50);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        JLabel instructionImg = new JLabel(new ImageIcon(loadImage("/gp4/rockpaperscissor.png")));
        instructionImg.setBounds(550, 40, 200, 120);
        
        JLabel fish = new JLabel(new ImageIcon(loadImage("/gp4/fish.png")));
        fish.setBounds(570, 165, 110, 80);
        
        JLabel heart = new JLabel(new ImageIcon(loadImage("/gp4/heart.png")));
        heart.setBounds(10, 90, 50, 50);
        
        scoreLabel = new JLabel(" X " + gameLogic.getScore());
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(mp.arcade.deriveFont(Font.BOLD, 20));
        scoreLabel.setBounds(657, 195, 100, 30);

        resultLabel = new JLabel();
        resultLabel.setBounds(125, 150, 400, 30);
        
        livesLabel = new JLabel(" X " + gameLogic.getLives());
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(mp.arcade.deriveFont(Font.BOLD, 16));
        livesLabel.setBounds(50, 100, 100, 30);
        
 
        rnd = new Random();

        sleepButton = new GamePanel4Buttons("/gp4/sleep_1.png", 36, 233, GamePanel4Play.ROCK, gameLogic, resultLabel, this, loadingScreen, mp);
        scareButton = new GamePanel4Buttons("/gp4/right_scare_1.png", 36, 306, GamePanel4Play.PAPER, gameLogic, resultLabel, this, loadingScreen, mp);
        scratchButton = new GamePanel4Buttons("/gp4/right_scratch_1.png", 36, 389, GamePanel4Play.SCISSORS, gameLogic, resultLabel, this, loadingScreen, mp);
        
        add(resultLabel);
        add(backButton);
        add(titleLabel1);
        add(titleLabel2);
        add(titleLabel2line2);
        add(livesLabel);
        add(scoreLabel);
        add(instructionLabel);
        add(instructionImg);
        add(fish);
        add(heart);
        add(sleepButton);
        add(scareButton);
        add(scratchButton);
        
        
        
        getImages();
    }

    public void startTimer() {
    	tAnimate.start();
    	repaint();
    }
    
    private void animate() {
        spriteDelay++;
        
    	if (spriteDelay > 30) {
    		spriteNum++;
    		sprite2Num++;
    		spriteDelay = 0;
    	}
        if (spriteNum >= defaultFrames.length) {
            spriteNum = 0;
        }
        
        if (sprite2Num >= default2Frames.length) {
        	sprite2Num = 0;
        }
        
        repaint();
    }
    
    private void getImages() {
    	bg1 = loadImage("/gp4/bgnofilter.jpg");
    	defaultFrames = new BufferedImage[17];
    	default2Frames = new BufferedImage[22];
    	
    	defaultFrames[0] = loadImage("/gp4/stand_1.png");
    	defaultFrames[1] = loadImage("/gp4/stand_1.png");
    	defaultFrames[2] = loadImage("/gp4/stand_1.png");
    	defaultFrames[3] = loadImage("/gp4/stand_1.png");
    	defaultFrames[4] = loadImage("/gp4/stand_1.png");
    	defaultFrames[5] = loadImage("/gp4/surprised_1.png");
    	defaultFrames[6] = loadImage("/gp4/kamot_1.png");
    	defaultFrames[7] = loadImage("/gp4/kamot_2.png");
    	defaultFrames[8] = loadImage("/gp4/kamot_3.png");
    	defaultFrames[9] = loadImage("/gp4/kamot_2.png");
    	defaultFrames[10] = loadImage("/gp4/kamot_3.png");
    	defaultFrames[11] = loadImage("/gp4/kamot_1.png");
    	defaultFrames[12] = loadImage("/gp4/stand_1.png");
    	defaultFrames[13] = loadImage("/gp4/stand_1.png");
    	defaultFrames[14] = loadImage("/gp4/stand_1.png");
    	defaultFrames[15] = loadImage("/gp4/stand_1.png");
    	defaultFrames[16] = loadImage("/gp4/stand_1.png");
    	
    	default2Frames[0] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[1] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[2] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[3] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[4] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[5] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[6] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[7] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[8] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[9] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[10] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[11] = loadImage("/gp4/surprised_1_blck.png");
    	default2Frames[12] = loadImage("/gp4/kamot_1_blck.png");
    	default2Frames[13] = loadImage("/gp4/kamot_2_blck.png");
    	default2Frames[14] = loadImage("/gp4/kamot_3_blck.png");
    	default2Frames[15] = loadImage("/gp4/kamot_2_blck.png");
    	default2Frames[16] = loadImage("/gp4/kamot_3_blck.png");
    	default2Frames[17] = loadImage("/gp4/kamot_1_blck.png");
    	default2Frames[18] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[19] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[20] = loadImage("/gp4/stand_1_blck.png");
    	default2Frames[21] = loadImage("/gp4/stand_1_blck.png");
    }
    
    private BufferedImage loadImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            System.err.println("IOException while loading image: " + imagePath);
            e.printStackTrace();
        }
        return image;
    }
    
    public void resetGame() {
        // Reset all game-related variables and components
        gameLogic.resetGame();
        spriteNum = 0;
        sprite2Num = 0;
        spriteDelay = 0;
        resultLabel.setIcon(null);;
        resultLabel.setText("");;
        livesLabel.setText("Lives: " + gameLogic.getLives());
        state = "default";
        startTimer();
        repaint();
    }
    
    private void drawSprites(Graphics g) {
    	BufferedImage img = null;
    	BufferedImage img2 = null;
    	
    	switch(state) {
            case "default":
                if (spriteNum >= 0 && spriteNum < defaultFrames.length) {
                    img = defaultFrames[spriteNum];
                }
                if (sprite2Num >= 0 && sprite2Num < default2Frames.length) {
                	img2 = default2Frames[sprite2Num];
                }
                break;
            
            case "newAnimation":
                if (spriteNum >= 0 && spriteNum < defaultFrames.length) { 
                    img = defaultFrames[spriteNum]; 
                }
                break;
    	}
    	
    	if (img != null) {
    		g.drawImage(img, spriteX, spriteY, spriteWidth, spriteHeight, null);
    	}
    	if (img2 != null) {
            g.drawImage(img2, sprite2X, sprite2Y, sprite2Width, sprite2Height, null);
        }
    }
    
    public void stopTimer() {
        if (tAnimate != null) {
            tAnimate.stop();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg1 != null) {
            g.drawImage(bg1, 0, 0, getWidth(), getHeight(), this);
        }
        drawSprites(g);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 400);
    }
public void addHoverEffect(JButton button, int x, int y, int normalWidth, int normalHeight, int hoverWidth, int hoverHeight, String imagePath) {
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBounds(x - (hoverWidth - normalWidth) / 2, y - (hoverHeight - normalHeight) / 2, hoverWidth, hoverHeight);
            button.setIcon(resizeImages(imagePath, hoverWidth, hoverHeight));
            button.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBounds(x, y, normalWidth, normalHeight);
            button.setIcon(resizeImages(imagePath, normalWidth, normalHeight));
            button.repaint();
        }
    });
}

// Method to resize ImageIcon
public ImageIcon resizeImages(String imagePath, int width, int height) {
    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
    Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(image);
	}

}
