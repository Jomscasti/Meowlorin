package Ken_MemoryMatch;

import javax.imageio.ImageIO;
import javax.swing.*;
import Home.MainPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel1_Card extends JPanel {
    private final BufferedImage frontBackgroundImage;
    private final BufferedImage frontMainImage;
    private final BufferedImage backImage;
    private final String description;
    private boolean isFlipped;
    private int width = 59, height = 85;
    private boolean effectApplied;

    public static GamePanel1_Card firstFlippedCard = null; // Track the first flipped card
    public static GamePanel1_Card secondFlippedCard = null; // Track the second flipped card

    private String effectType; // "none", "powerup", "powerdown"
    private int effectValue; // Time value change
    private int currentEffectValue;
    
    public static int unmatched = 24;

    public GamePanel1_Card(String frontBackgroundImagePath, String frontMainImagePath, String backImagePath, String description, int x, int y, GamePanel1 gp1, MainPanel mp, String effectType, int effectValue, LoadingScreen1 ld1) {
        this.isFlipped = false;
        this.effectApplied = false;
        this.frontBackgroundImage = loadImages(frontBackgroundImagePath);
        this.frontMainImage = loadImages(frontMainImagePath);
        this.backImage = loadImages(backImagePath);
        this.description = description;
        this.effectType = effectType;
        this.effectValue = effectValue;
        this.currentEffectValue = effectValue;

        setLayout(new CardLayout());
        setBounds(x, y, width, height);
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        // Create panels for front and back images
        JPanel frontPanel = createFrontImagePanel(frontBackgroundImage, frontMainImage, description);
        JPanel backPanel = createImagePanel(backImage);

        // Add panels to the card layout
        add(frontPanel, "FRONT");
        add(backPanel, "BACK");

        resetCard();

        // Add mouse listener to handle card flipping
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (isFlipped && secondFlippedCard == null && firstFlippedCard == GamePanel1_Card.this) {
                    // If this card is the first flipped card and there's no second flipped card, flip it back
                    resetCard();
                    firstFlippedCard = null;
                } else if (!isFlipped && secondFlippedCard == null) { 
                    // If not flipped and no two cards already flipped
                    flipCard(gp1);
                    if (firstFlippedCard == null) {
                        firstFlippedCard = GamePanel1_Card.this;
                        mp.playSE(4);
                    } else {
                        secondFlippedCard = GamePanel1_Card.this;
                        mp.playSE(4);
                        checkMatch(mp, gp1, ld1);
                    }
                }
            }
        });
    }
    
    // Getters for effectType and effectValue
    public String getEffectType() {
        return effectType;
    }
    public int resetEffectValue() {
    	effectValue = 0;
    	effectValue += currentEffectValue;
    	
    	return effectValue;
    }
    
    public int getMatched() {
    	return unmatched;
    }
    
    public int getEffectValue() {
        return effectValue;
    }
    
 // Getter and setter for effectApplied
    public boolean isEffectApplied() {
        return effectApplied;
    }

    public void setEffectApplied(boolean effectApplied) {
        this.effectApplied = effectApplied;
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

    private JPanel createImagePanel(BufferedImage image) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        };
    }

    private JPanel createFrontImagePanel(BufferedImage backgroundImage, BufferedImage mainImage, String description) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                int imageWidth = 40;
                int imageHeight = 40;
                int x = (getWidth() - imageWidth) / 2;
                int y = (getHeight() - imageHeight) / 2 - 10; // Adjust to position above the description
                g.drawImage(mainImage, x, y, imageWidth, imageHeight, null);

                if (!description.isEmpty()) {
                    g.setColor(new Color(224, 40, 229));
                    g.setFont(new Font("Arial", Font.BOLD, 10));
                    FontMetrics fm = g.getFontMetrics();

                    // Split description into multiple lines
                    String[] lines = splitIntoLines(description, fm, getWidth() - 15);

                    // Draw each line of the description
                    for (int i = 0; i < lines.length; i++) {
                        int textX = (getWidth() - fm.stringWidth(lines[i])) / 2;
                        int textY = y + imageHeight + (fm.getAscent() + 2) * (i + 1);
                        g.drawString(lines[i], textX, textY);
                    }
                }
            }
        };
    }

    private String[] splitIntoLines(String text, FontMetrics fm, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            if (fm.stringWidth(line + word) < maxWidth) {
                line.append(word).append(" ");
            } else {
                lines.add(line.toString());
                line = new StringBuilder(word).append(" ");
            }
        }
        lines.add(line.toString()); // Add the last line

        return lines.toArray(new String[0]);
    }

    private void flipCard(GamePanel1 gp1) {
        isFlipped = !isFlipped; // Toggle flipped state

        CardLayout cl = (CardLayout) getLayout();
        if (isFlipped) {
            cl.show(this, "FRONT"); // Show front side
        } else {
            cl.show(this, "BACK"); // Show back side
        }
    }

    public void resetCard() {
        isFlipped = false;
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "BACK"); // Ensure back side is shown
        setPreferredSize(new Dimension(width, height)); // Reset preferred size
        revalidate(); // Ensure layout manager recognizes the new size
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    private static void checkMatch(MainPanel mp, GamePanel1 gamePanel1, LoadingScreen1 ld1) {
        if (firstFlippedCard != null && secondFlippedCard != null &&
                firstFlippedCard.description.equals(secondFlippedCard.description)) {
            // Cards match, perform match actions (e.g., remove them from play)
            mp.playSE(2);
            gamePanel1.handleMatchedCards(mp, ld1);     

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    SwingUtilities.invokeLater(() -> {
                        firstFlippedCard.setVisible(false);
                        secondFlippedCard.setVisible(false);
                        unmatched -= 2;
                        if (unmatched == 0) {
                            // All cards matched, generate new cards
                            gamePanel1.resetNewGame(mp, ld1);
                        }

                        // Update score in GamePanel1
                        gamePanel1.updateScoreLabel(50);

                        firstFlippedCard = null;
                        secondFlippedCard = null;
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            // Example delay before flipping back
            new Thread(() -> {
                try {
                    Thread.sleep(500); // Adjust delay time as needed
                    SwingUtilities.invokeLater(() -> {
                        firstFlippedCard.resetCard();
                        secondFlippedCard.resetCard();
                        firstFlippedCard = null;
                        secondFlippedCard = null;
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
