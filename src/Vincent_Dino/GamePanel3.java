package Vincent_Dino;
import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.awt.image.BufferedImage;

public class GamePanel3 extends JPanel implements KeyListener {

    private BufferedImage bgcat, trashImage, trash2Image, trash3Image, benchImage, chairImage, houseImage;
    private String direction = "right";
    private int x = 100;
    private int y = 450;
    private int obstacleX = 900, obstacleX1 = 1400, obstacleX2 = 1900; // Initial positions set to -500 to start off screen
    private int benchX = 800, chairX = 1500, houseX = 2000;
    private int counter = 1;
    private int spriteDelay;
    private int score = 0; // Initialize score
    private boolean goingUp = false;
    private boolean isLanded = true;
    private JLabel lblSprite;
    private ImageIcon icnRightRun1, icnRightRun2, icnRightJump, icnRightLand;
    
    Timer t;
    CollisionChecker collisionChecker;
    JButton restartButton;

    public GamePanel3(MainPanel mp) {
    	
    	JButton back1Button = new JButton("Play");
        back1Button.setBounds(340, 260, 10, 10);
        back1Button.setBackground(new Color (255,105,180));
        back1Button.addActionListener(e -> mp.showScreen("LOADING3")); // Switch LoadingScreen
        add(back1Button);
        
        setOpaque(false);
        setLayout(null);
        setFocusable(true); // Ensure the panel can receive focus
        addKeyListener(this); // Register the KeyListener
        
        JButton backButton = new JButton();
        backButton.setBounds(20, 10, 110, 80);
        backButton.setIcon(resizeImages("/HomePage/back_button.png", 110, 80));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        addHoverEffect(backButton, 20, 10, 110, 80, 130, 100, "/HomePage/back_button.png");
        backButton.addActionListener(e -> mp.showScreen("LOADING3")); // Switch to GAME panel
        add(backButton);
        
        lblSprite = new JLabel();
        lblSprite.setBounds(x, y, 50, 50);
        add(lblSprite);

        URL urlRightRun1 = getClass().getResource("/right_run_1.png");
        icnRightRun1 = new ImageIcon(urlRightRun1);
        URL urlRightRun2 = getClass().getResource("/right_run_2.png");
        icnRightRun2 = new ImageIcon(urlRightRun2);
        URL urlRightJump = getClass().getResource("/right_jump_2.png");
        icnRightJump = new ImageIcon(urlRightJump);
        URL urlRightLand = getClass().getResource("/right_land_1.png");
        icnRightLand = new ImageIcon(urlRightLand);

        showBg(); // Load background and trash images

        collisionChecker = new CollisionChecker(this); // Initialize the collision checker

        restartButton = new JButton("Restart");
        restartButton.setBounds(320, 400, 160, 43);
        restartButton.setVisible(false);
        restartButton.setBorderPainted(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setOpaque(false);
        restartButton.setIcon(resizeImages("/start_button.png", 160, 43));
        addHoverEffect(restartButton, 320, 400, 160, 43, 180, 63, "/start_button.png");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        add(restartButton);

        t = new Timer(1000 / 20, e -> {
            if (goingUp) {
                y -= 10;
                lblSprite.setIcon(icnRightJump);
                if (y <= 370) {
                    goingUp = false;
                }
            } else if (!goingUp && y < 450) {
                y += 10;
                lblSprite.setIcon(icnRightLand);
            } else {
                isLanded = true;
                if (counter == 1)
                    lblSprite.setIcon(icnRightRun1);
                else if (counter == 2)
                    lblSprite.setIcon(icnRightRun2);
                else if (counter == 3)
                    lblSprite.setIcon(icnRightRun1);
                else if (counter == 4)
                    lblSprite.setIcon(icnRightRun2);
                spriteDelay ++;
                if (spriteDelay > 2) {
                	counter += 1;
                	spriteDelay = 0;
                }
                if (counter > 5)
                    counter = 1;
            }

            // Update obstacle positions and reset if necessary
            if (obstacleX <= -150) {
                obstacleX = obstacleX2 + 500 + new Random().nextInt(200); // Reset with a minimum distance of 500 units
                if (obstacleX <= obstacleX2 + 500) {
                    obstacleX += 500;
                }
            }

            if (obstacleX1 <= -150) {
                obstacleX1 = obstacleX + 500 + new Random().nextInt(200); // Reset with a minimum distance of 500 units
                if (obstacleX1 <= obstacleX + 500) {
                    obstacleX1 += 500;
                }
            }

            if (obstacleX2 <= -150) {
                obstacleX2 = obstacleX1 + 500 + new Random().nextInt(200); // Reset with a minimum distance of 500 units
                if (obstacleX2 <= obstacleX1 + 500) {
                    obstacleX2 += 500;
                }
            }

            // Update decoration positions and reset if necessary
            if (benchX <= -150) {
                benchX = 1000;
            }
            if (chairX <= -150) {
                chairX = 1100;
            } 
            if (houseX <= -150) {
                houseX = 2000;
            }

            obstacleX -= 10;
            obstacleX1 -= 10;
            obstacleX2 -= 10;
            benchX -= 10;
            chairX -= 10;
            houseX -= 10;

            // Check if cat passed over the obstacle
            if (y <= 500 && (obstacleX <= x + 50 && x <= obstacleX + 45)) {
                score += 1; // Increase score when cat passes over an obstacle
            }
            if (y <= 500 && (obstacleX1 <= x + 50 && x <= obstacleX1 + 45)) {
                score += 1; // Increase score when cat passes over an obstacle
            }
            if (y <= 500 && (obstacleX2 <= x + 50 && x <= obstacleX2 + 45)) {
                score += 1; // Increase score when cat passes over an obstacle
            }

            lblSprite.setBounds(x, y, 50, 50);
            repaint();

            if (collisionChecker.isGameOver()) {
                ((Timer) e.getSource()).stop(); // Stop the timer
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                restartButton.setVisible(true); // Show the restart button
            }
        });

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

    private void restartGame() {
        x = 100;
        y = 450;
        obstacleX = 900;
        obstacleX1 = 1400;
        obstacleX2 = 1900;
        benchX = 800;
        chairX = 1500;
        houseX = 2000;
        goingUp = false;
        isLanded = true;
        counter = 1;
        score = 0;
        lblSprite.setIcon(icnRightRun1);
        restartButton.setVisible(false);
        
        Timer t = new Timer(1000 / 20, e -> {
            if (goingUp) {
                y -= 10;
                lblSprite.setIcon(icnRightJump);
                if (y <= 370) {
                    goingUp = false;
                }
            } else if (!goingUp && y < 450) {
                y += 10;
                lblSprite.setIcon(icnRightLand);
            } else {
                isLanded = true;
                if (counter == 1)
                    lblSprite.setIcon(icnRightRun1);
                else if (counter == 2)
                    lblSprite.setIcon(icnRightRun2);
                else if (counter == 3)
                    lblSprite.setIcon(icnRightRun1);
                else if (counter == 4)
                    lblSprite.setIcon(icnRightRun2);
                spriteDelay ++;
                if (spriteDelay > 2) {
                	counter += 1;
                	spriteDelay = 0;
                }
                if (counter > 5)
                    counter = 1;
            }

            // Update obstacle positions and reset if necessary
            if (obstacleX <= -150) {
                obstacleX = obstacleX2 + 500 + new Random().nextInt(200); // Reset with a minimum distance of 500 units
                if (obstacleX <= obstacleX2 + 500) {
                    obstacleX += 500;
                }
            }

            if (obstacleX1 <= -150) {
                obstacleX1 = obstacleX + 500 + new Random().nextInt(200); // Reset with a minimum distance of 500 units
                if (obstacleX1 <= obstacleX + 500) {
                    obstacleX1 += 500;
                }
            }

            if (obstacleX2 <= -150) {
                obstacleX2 = obstacleX1 + 500 + new Random().nextInt(200); // Reset with a minimum distance of 500 units
                if (obstacleX2 <= obstacleX1 + 500) {
                    obstacleX2 += 500;
                }
            }

            // Update decoration positions and reset if necessary
            if (benchX <= -150) {
                benchX = 1000;
            }
            if (chairX <= -150) {
                chairX = 1100;
            }
            if (houseX <= -150) {
                houseX = 1800;
            }

            obstacleX -= 10;
            obstacleX1 -= 10;
            obstacleX2 -= 10;
            benchX -= 10;
            chairX -= 10;
            houseX -= 10;
            
         // Check if cat passed over the obstacle
            if (y <= 500 && (obstacleX <= x + 50 && x <= obstacleX + 45)) {
                score += 1; // Increase score when cat passes over an obstacle
            }
            if (y <= 500 && (obstacleX1 <= x + 50 && x <= obstacleX1 + 45)) {
                score += 1; // Increase score when cat passes over an obstacle
            }
            if (y <= 500 && (obstacleX2 <= x + 50 && x <= obstacleX2 + 45)) {
                score += 1; // Increase score when cat passes over an obstacle
            }

            lblSprite.setBounds(x, y, 50, 50);
            repaint();

            if (collisionChecker.isGameOver()) {
                ((Timer) e.getSource()).stop(); // Stop the timer
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                restartButton.setVisible(true); // Show the restart button
            }
        });

        t.start();
    }
    
    private void showBg() {
        try {
            bgcat = ImageIO.read(getClass().getResource("/bgcat.png"));
            trashImage = ImageIO.read(getClass().getResource("/Trash 2.png"));
            trash2Image = ImageIO.read(getClass().getResource("/Trash 2.1.png"));
            trash3Image = ImageIO.read(getClass().getResource("/Trash 2.2.png"));
            benchImage = ImageIO.read(getClass().getResource("/bench.png"));
            chairImage = ImageIO.read(getClass().getResource("/chair.png"));
            houseImage = ImageIO.read(getClass().getResource("/house.png"));
        } catch (IOException e) {
            System.err.println("IOException while loading images");
            e.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgcat != null) {
            g.drawImage(bgcat, 0, 0, getWidth(), getHeight(), this);
        }
        if (benchImage != null) {
            g.drawImage(benchImage, benchX, 405, 120, 90, this); // Draw bench image
        }
        if (chairImage != null) {
            g.drawImage(chairImage, chairX, 405, 120, 90, this); // Draw chair image
        }
        if (houseImage != null) {
            g.drawImage(houseImage, houseX, 350, 150, 140, this); // Draw house image
        }
        if (trashImage != null) {
            g.drawImage(trashImage, obstacleX, 450, 45, 45, this); // Draw trash image
        }
        if (trash2Image != null) {
            g.drawImage(trash2Image, obstacleX1, 450, 45, 45, this); // Draw trash image
        }
        if (trash3Image != null) {
            g.drawImage(trash3Image, obstacleX2, 450, 45, 45, this); // Draw trash image
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, getWidth() - 100, 30);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600); // Adjust as needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && isLanded) {
            isLanded = false;
            goingUp = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this context 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this context
    }

    // Getter methods for collision detection
    public int getCatX() {
        return x;
    }

    public int getCatY() {
        return y;
    }

    public int[] getObstacleXPositions() {
        return new int[]{obstacleX, obstacleX1, obstacleX2};
    }

    public Rectangle getCatBounds() {
        return new Rectangle(x, y, lblSprite.getWidth(), lblSprite.getHeight());
    }

    public Rectangle[] getObstacleBounds() {
        return new Rectangle[]{
            new Rectangle(obstacleX, 480, 45, 45),
            new Rectangle(obstacleX1, 480, 45, 45),
            new Rectangle(obstacleX2, 480, 45, 45)
        };
    }
    
    public void startTimer() {
    	t.start();
    }
}
