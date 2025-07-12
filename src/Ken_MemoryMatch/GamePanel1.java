package Ken_MemoryMatch;
import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;
import Home.Sound;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;

public class GamePanel1 extends JPanel {
    private BufferedImage bg1;
    private GamePanel1_Card[] cards; // Array to hold all cards
    Font arcade;
    private JLabel timerLabel; // Label to display the timer
    private JLabel scoreLabel; // Label to display the score
    private JLabel effectLabel;
    private Timer timer; // Swing Timer for updating the timer label
    private int time = 60;
    private int score = 0; // Initialize score
    private ArrayList<String[]> cardList = new ArrayList<>();
    
    private int spriteNum;
    private BufferedImage[] play1;
    private BufferedImage[] play2;
    public String state;
    private int spriteDelay;
    private int highScore = 0; 
    Timer tAnimateMeow;
    
    public GamePanel1(MainPanel mp) {
    	this.setDoubleBuffered(true);
    	LoadingScreen1 ld1 = new LoadingScreen1(mp, this);
    	//Declare font
    	InputStream is = getClass().getResourceAsStream("/Fonts/ka1.ttf");
    	try {
			arcade = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        setPreferredSize(new Dimension(800, 600)); // Set preferred size of the panel
        setLayout(null); // Use null layout for absolute positioning
        repaint();
        setUpGame(mp, ld1);
    }

    //Set up the Game
    public void setUpGame(MainPanel mp, LoadingScreen1 ld1) {
    	
    	getImages(); // Load background image and initialize cards
        displayCards(mp, ld1);
        initializeTimer(mp, ld1);
        initializeScore();
        addBackButton(mp, ld1);
        initializeEffect();
        tAnimateMeow = new Timer(1000/60, e -> animate());
        state = "playing";
        
    }
 
    private void animate() {
    	spriteDelay++;
    	if(spriteDelay > 5) {
    		spriteNum++;
    		spriteDelay = 0;
    	}
    	
    	switch(state) {
    	case "playing":
    		if(spriteNum >= play1.length) {
        		spriteNum = 0;
        	}
    	case "playing2":
    		if(spriteNum >= play2.length) {
        		spriteNum = 0;
        	}
    	}
    	
    	repaint();
    }
    
    //Display Card Method
    private void displayCards(MainPanel mp, LoadingScreen1 ld1) {
        // Algorithm variables for positioning cards
        int xAxis = 24;
        int yAxis = 72;
        int panelWidth = 59;
        int panelHeight = 85;
        int xSpacing = 40;
        int ySpacing = 26;

        // Example data for cards
        String[][] Cards = {
            {"frontcard.png", "backcard.png", "Fish Bone", "fishbone.png", "powerdown", "2"},
            {"frontcard.png", "backcard.png", "Fish Bone", "fishbone.png", "powerdown", "2"},
            {"frontcard.png", "backcard.png", "Cat Kibble", "catfood.png", "powerup", "4"},
            {"frontcard.png", "backcard.png", "Cat Kibble", "catfood.png", "powerup", "4"},
            {"frontcard.png", "backcard.png", "Bitten Apple", "bittenapple.png", "powerdown", "2"},
            {"frontcard.png", "backcard.png", "Bitten Apple", "bittenapple.png", "powerdown", "2"},
            {"frontcard.png", "backcard.png", "Cold Milk", "coldmilk.png", "powerup", "6"},
            {"frontcard.png", "backcard.png", "Cold Milk", "coldmilk.png", "powerup", "6"},
            {"frontcard.png", "backcard.png", "Hot Milk", "hotmilk.png", "powerup", "5"},
            {"frontcard.png", "backcard.png", "Hot Milk", "hotmilk.png", "powerup", "5"},
            {"frontcard.png", "backcard.png", "Steak", "steak.png", "powerup", "3"},
            {"frontcard.png", "backcard.png", "Steak", "steak.png", "powerup", "3"},
            {"frontcard.png", "backcard.png", "Empty Can", "can.png", "none", "0"},
            {"frontcard.png", "backcard.png", "Empty Can", "can.png", "none", "0"},
            {"frontcard.png", "backcard.png", "Drum Stick", "chickenlegs.png", "powerup", "3"},
            {"frontcard.png", "backcard.png", "Drum Stick", "chickenlegs.png", "powerup", "3"},
            {"frontcard.png", "backcard.png", "Catnip", "catnip.png", "powerup", "4"},
            {"frontcard.png", "backcard.png", "Catnip", "catnip.png", "powerup", "4"},
            {"frontcard.png", "backcard.png", "Cat Food", "cat food.png", "powerup", "4"},
            {"frontcard.png", "backcard.png", "Cat Food", "cat food.png", "powerup", "4"},
            {"frontcard.png", "backcard.png", "Fish", "fish.png", "powerup", "5"},
            {"frontcard.png", "backcard.png", "Fish", "fish.png", "powerup", "5"},
            {"frontcard.png", "backcard.png", "Cat Treat", "cattreat.png", "powerup", "5"},
            {"frontcard.png", "backcard.png", "Cat Treat", "cattreat.png", "powerup", "5"},
        };

        // Shuffle the array of card data
        Collections.addAll(cardList, Cards);
        Collections.shuffle(cardList);

        cards = new GamePanel1_Card[cardList.size()]; // Initialize array to hold cards

        for (int i = 0; i < cardList.size(); i++) {
            String[] Card = cardList.get(i);
            String frontImagePath = "/gp1/" + Card[0];
            String backImagePath = "/gp1/" + Card[1];
            String mainImagePath = "/gp1/" + Card[3];
            String description = "" + Card[2];
            String effectType = "" + Card[4];
            int value = Integer.parseInt(Card[5]);

            // Create a new GamePanel1_Card instance
            GamePanel1_Card card = new GamePanel1_Card(frontImagePath, mainImagePath, backImagePath, description, xAxis, yAxis, this, mp, effectType, value, ld1);
            cards[i] = card; // Add card to array

            add(card); // Add card to the panel

            // Update position for the next card
            xAxis += panelWidth + xSpacing;
            if (xAxis + panelWidth > 800) {
                xAxis = 24;
                yAxis += panelHeight + ySpacing;
            }
        }
    }    
    
    //Reset Game Method
    public void resetNewGame(MainPanel mp, LoadingScreen1 ld1) {
    	removeAll(); // Remove all components from the panel
        cardList.clear(); // Clear the card list
        displayCards(mp, ld1); // Redisplay shuffled cards
        initializeTimer(mp, ld1);
        initializeScore();
        initializeEffect();
        addBackButton(mp, ld1);
        state = "playing";
        GamePanel1_Card.unmatched = 24;
        GamePanel1_Card.firstFlippedCard = null;
        GamePanel1_Card.secondFlippedCard = null;
        revalidate(); // Revalidate the panel to reflect changes
        repaint(); // Repaint the panel to update the display
    }
    
    public void stopTimer() {
    	timer.stop();
        tAnimateMeow.stop();
    }
    
    //Method to switch to gameover screen
    private void switchToGameOverPanel(MainPanel mp, LoadingScreen1 ld1) {
    	if (score > highScore) {
            highScore = score;
        }
    	
    	GamePanel1_GameOver gpO1 = new GamePanel1_GameOver(mp, score, highScore, this, ld1);
        mp.add(gpO1, "GAMEOVERPANEL1");
        mp.showScreen("GAMEOVERPANEL1");
    }
    
    //Method to show the gameover screen
    private void showGameOver(MainPanel mp, LoadingScreen1 ld1) {
        stopTimer();
        switchToGameOverPanel(mp, ld1);
    }
    public void resetScore() {
    	score = 0;
    	scoreLabel.setText("Score: " + score);
    	repaint();
    }
    
    //Initialize the Effect Method
    private void initializeEffect() {
        if (effectLabel == null) {
            effectLabel = new JLabel("", SwingConstants.CENTER);
            effectLabel.setBounds(690, 50, 200, 30); // Initial bounds
            effectLabel.setFont(arcade.deriveFont(Font.PLAIN, 20f));
            effectLabel.setForeground(Color.YELLOW);
        }
        effectLabel.setVisible(false); // Initially hidden
        add(effectLabel);
    }
    
  //Update the Effect Method
    public void updateEffect(String effectText, int x, int y, Color color) {
            effectLabel.setText(effectText); // Update the label text
            effectLabel.setBounds(x, y, 50, 40); // Update the position of the label
            effectLabel.setForeground(color); // Update the color of the text
            effectLabel.setVisible(true); // Make the label visible

            // Set a transparent background color for better visibility
            effectLabel.setBackground(new Color(0, 0, 0, 150)); // Black with 150 (out of 255) alpha transparency
            effectLabel.setOpaque(true); // Make the background color visible
            Timer effectTimer = new Timer(2000, new ActionListener() { // Timer to hide the effect after 2 second
                @Override
                public void actionPerformed(ActionEvent e) {
                    effectLabel.setVisible(false); // Hide the label after the timer finishes
                    effectLabel.repaint(); // Repaint the label to update its visibility
                }
            });
            effectTimer.setRepeats(false);
            effectTimer.start(); // Start the timer
        repaint(); // Repaint the panel to reflect changes
    }
    
    private void initializeScore() {
        // Create the score label and set its properties
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(arcade.deriveFont(24f));

        // Calculate the position to center horizontally
        int labelWidth = scoreLabel.getPreferredSize().width + 10;
        int labelHeight = scoreLabel.getPreferredSize().height;
        int x = (800 - labelWidth) / 2; // Center horizontally
        int y = 30; // Adjust Y position as needed
        // Update score in GamePanel1
        updateScoreLabel(0);

        // Set bounds for the score label
        scoreLabel.setBounds(x, y, labelWidth, labelHeight);
        
        // Add the score label to the panel
        add(scoreLabel);
    }

    public void handleMatchedCards(MainPanel mp, LoadingScreen1 ld1) {
        for (GamePanel1_Card card : cards) {
            if (!card.isEffectApplied() && card.isFlipped()) {
                applyCardEffect(card);
                if (card.getEffectType().equals("powerup")) {
                    updateEffect("" + card.getEffectValue(), 350, 520, new Color(0, 255, 0));
                } else if (card.getEffectType().equals("powerdown")) {
                    updateEffect("" + card.getEffectValue(), 350, 520, new Color(255, 0, 0));
                }
                // Mark the card as having its effect applied
                card.setEffectApplied(true);
            }
        }
        // Toggle between "playing" and "playing2"
        switch (state) {
            case "playing":
                state = "playing2";
                break;
            case "playing2":
                state = "playing";
                break;
        }
    }

    
    private void applyCardEffect(GamePanel1_Card card) {
        // Ensure the effect is applied only once
        if (!card.isEffectApplied()) {
            switch (card.getEffectType()) {
                case "powerup":
                    increaseTimer(card.getEffectValue() - (card.getEffectValue() / 2));
                    break;
                case "powerdown":
                    decreaseTimer(card.getEffectValue() - (card.getEffectValue() / 2));
                    break;
                // Handle other effects as needed
                default:
                    // No effect case
                    break;
            }
            // Mark the card as having its effect applied
            card.setEffectApplied(true);
        }
    }

    private void increaseTimer(int seconds) {
        time += seconds;
        timerLabel.setText("Time: " + time + " seconds");
    }

    private void decreaseTimer(int seconds) {
        time -= seconds;
        if (time < 0) {
            time = 0; // Ensure timer doesn't go negative
        }
        timerLabel.setText("Time: " + time + " seconds");
    }
    
    // Method to update the score label text and adjust its position
    public void updateScoreLabel(int points) {
        score += points; // Update the score
        scoreLabel.setText("Score: " + score); // Update the score label text
        
        // Adjust the position of the score label to keep it centered horizontally
        int labelWidth = scoreLabel.getPreferredSize().width + 10;
        scoreLabel.setBounds((getWidth() - labelWidth) / 2, 30, labelWidth, 30);
        
        repaint(); // Ensure the panel repaints to reflect changes
    }
    
    public void addHoverEffect(JButton button, int x, int y, int normalWidth, int normalHeight, int hoverWidth, int hoverHeight, String imagePath) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBounds(x - (hoverWidth - normalWidth) / 2, y - (hoverHeight - normalHeight) / 2, hoverWidth, hoverHeight);
                button.setIcon(resizeImage(imagePath, hoverWidth, hoverHeight));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBounds(x, y, normalWidth, normalHeight);
                button.setIcon(resizeImage(imagePath, normalWidth, normalHeight));
                button.repaint();
            }
        });
        
    }
    
    // Method to resize ImageIcon
    public ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
    private void addBackButton(MainPanel mp, LoadingScreen1 ld1) {
        // Load the image for the button
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/back_button.png"));

        // Resize the image to fit within 104x46 dimensions while maintaining aspect ratio
        Image scaledImage = backIcon.getImage().getScaledInstance(104, 46, Image.SCALE_SMOOTH);
        ImageIcon scaledBackIcon = new ImageIcon(scaledImage);

        // Create a JButton with the scaled image
        JButton backButton = new JButton(scaledBackIcon);

        // Set the bounds (position and size) of the button
        backButton.setBounds(10, 10, 104, 46);

        // Remove default button border (if desired)
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);

        // Add hover effect to the backButton
        addHoverEffect(backButton, 10, 10, 104, 46, 114, 56, "/back_button.png");

        // Add action listener to handle button click
        backButton.addActionListener(e -> {
            mp.showScreen("LOADING1");
            stopTimer();
            resetNewGame(mp, ld1);
        });

        // Add the button to the panel
        add(backButton);
    }
    
    //Initialize/Declare Timer
    private void initializeTimer(MainPanel mp, LoadingScreen1 ld1) {
        // Initialize the timer label
        timerLabel = new JLabel("Time: " + time + " seconds");
        timerLabel.setFont(arcade.deriveFont(Font.PLAIN, 20));
        timerLabel.setForeground(new Color(224, 40, 229));
        timerLabel.setBounds(530, 520, 260, 30); // Position the timer at the upper right corner
        
        // Set transparent background
        timerLabel.setOpaque(false); // Make the label transparent
        timerLabel.setBackground(new Color(0, 0, 0, 0)); // Set the background color with alpha (transparent)
        
        add(timerLabel);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
                timerLabel.setText("Time: " + time + " seconds");
                if (time <= 0) {
                    stopTimer();
                    resetNewGame(mp, ld1);
                    showGameOver(mp, ld1);
                }
            }
        });
    }

    
    //Start the Timer
    public void startTimer() {
        time = 60; // Reset the time
        timerLabel.setText("Time: " + time + " seconds"); // Update the label
        timer.start(); // Start the timer
        tAnimateMeow.start();
    }
    
    private void getImages() {
    	bg1 = loadImage("bg1");
    	
    	play1 = new BufferedImage[6];
    	
    	//Cat is playing
    	play1[0] = loadImage("meowlorin_play1");
    	play1[1] = loadImage("meowlorin_play2");
    	play1[2] = loadImage("meowlorin_play1");
    	play1[3] = loadImage("meowlorin_play2");
    	play1[4] = loadImage("meowlorin_play1");
    	play1[5] = loadImage("meowlorin_play1");

    	play2 = new BufferedImage[6];
    	
    	play2[0] = loadImage("meowlorin_play3");
    	play2[1] = loadImage("meowlorin_play4");
    	play2[2] = loadImage("meowlorin_play3");
    	play2[3] = loadImage("meowlorin_play4");
    	play2[4] = loadImage("meowlorin_play3");
    	play2[5] = loadImage("meowlorin_play3");
    	
    	
    }
    
    //Method for getting images to reduce redundancy
    private BufferedImage loadImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/gp1/" +imagePath + ".png"));
        } catch (IOException e) {
            System.err.println("IOException while loading image: " + imagePath);
            e.printStackTrace();
        }
        return image;
    }

    //Draw meowlorin
    private void drawSprites(Graphics g) {
    	BufferedImage img = null;
    	
    	switch(state) {
    	case "playing":
    		if (spriteNum >= 0 && spriteNum < play1.length) {
    			img = play1[spriteNum];
    		}
    		break;
    	case "playing2":
    		if (spriteNum >= 0 && spriteNum < play2.length) {
    			img = play2[spriteNum];
    		}
    		break;
    		
    	}
    	
    	g.drawImage(img, 342, 384, 116, 114, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg1 != null) {
            g.drawImage(bg1, 0, 0, getWidth(), getHeight(), this); // Draw background image
        }
        // Set transparency for the rectangle
        g.setColor(new Color(0, 0, 0, 230)); 
        g.fillRect(530, 520, 240, 30); // Position and size of the rectangle below the timer label
        drawSprites(g);
    }
}