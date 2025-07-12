package Stephen_Flappy;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import Home.MainPanel;

public class GamePanel2Character extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 800;
    int frameHeight = 600;

    Image backgroundImg;
    Image catImg;
    Image topBuildingImg;
    Image bottomBuildingImg;
    Image powerUpImg;
    Image immunityImg; 

    int catX = frameWidth / 8;
    int catY = frameHeight / 2;
    int catWidth = 54;
    int catHeight = 44;

    JButton playButton, backButton;
    JButton playAgainButton;

    class Cat {
        int x = catX;
        int y = catY;
        int width = catWidth;
        int height = catHeight;
        Image img;
        Image immunityImg; 

        Cat(Image img, Image immunityImg) {
            this.img = img;
            this.immunityImg = immunityImg;
        }
    }

    class Building {
        int x = buildingX;
        int y = buildingY;
        int width = buildingWidth;
        int height = buildingHeight;
        Image img;
        boolean passed = false;

        Building(Image img) {
            this.img = img;
        }
    }

    class PowerUp {
        int x;
        int y;
        int width = 32;
        int height = 32;
        Image img;

        PowerUp(Image img, int x, int y) {
            this.img = img;
            this.x = x;
            this.y = y;
        }
    }

    int buildingX = frameWidth;
    int buildingY = 0;
    int buildingWidth = 64;
    int buildingHeight = 512;

    Cat cat;
    int velocityY = 0, velocityX = -4, gravity = 1;
    Timer placeBuildingsTimer;
    Timer gameLoop;
    Timer powerUpTimer;
    Timer immunityTimer;
    ArrayList<Building> buildings;
    PowerUp powerUp;
    Random random = new Random();

    boolean gameOver = false;
    boolean gameStarted = false;
    boolean isImmune = false;

    double score = 0;
    int immunityDuration = 0;
    int buildingGenerationInterval = 1500; 

    public GamePanel2Character(MainPanel mp) {
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setFocusable(true);
        addKeyListener(this);

        int delay = (10 + random.nextInt(30)) * 1000;
        powerUpTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePowerUp();
                powerUpTimer.stop();
            }
        });
        backgroundImg = new ImageIcon(getClass().getResource("/flappykittybg.png")).getImage();
        catImg = new ImageIcon(getClass().getResource("/meowlorin.png")).getImage();
        topBuildingImg = new ImageIcon(getClass().getResource("/topbuilding.png")).getImage();
        bottomBuildingImg = new ImageIcon(getClass().getResource("/bottombuilding.png")).getImage();
        powerUpImg = new ImageIcon(getClass().getResource("/powerup.png")).getImage();
        immunityImg = new ImageIcon(getClass().getResource("/immunity.gif")).getImage(); 

        cat = new Cat(catImg, immunityImg); 
        buildings = new ArrayList<Building>();

        placeBuildingsTimer = new Timer(buildingGenerationInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBuildings();
            }
        });

        gameLoop = new Timer(1000 / 60, this);
        
        JButton backButton = new JButton();
        backButton.setIcon(mp.resizeImage("/back_button.png", 104, 46));
	    
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
            resetGame();
            playButton.setVisible(true);
            placeBuildingsTimer.stop();
            gameLoop.stop();
        });
        
        playButton = new JButton();
        playButton.setIcon(mp.resizeImage("/start_button.png", 150, 100));
        
        // Remove default button border
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);
        
        mp.addHoverEffect(playButton, frameWidth / 2 - 100, frameHeight / 2 - 50, 150, 100, 170, 120, "/start_button.png");
        playButton.setBounds(frameWidth / 2 - 100, frameHeight / 2 - 50, 150, 100);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                playButton.setVisible(false);
                placeBuildingsTimer.start();
                gameLoop.start();
            }
        });

        playAgainButton = new JButton();
        playAgainButton.setIcon(mp.resizeImage("/start_button.png", 200, 100));
        playAgainButton.setFont(new Font("Pixel Sans Serif", Font.PLAIN, 32));
        
        // Remove default button border
        playAgainButton.setBorderPainted(false);
        playAgainButton.setContentAreaFilled(false);
        playAgainButton.setOpaque(false);
        
        mp.addHoverEffect(playAgainButton, 280, 320, 200, 100, 220, 120, "/start_button.png");
        playAgainButton.setBounds(280, 320, 200, 100);
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                
            }
        });

        setLayout(null);
        add(playButton);
        add(playAgainButton);
        add(backButton);
    }

    boolean hasReset = false;

    public void startGame() {
        gameStarted = true;
        placeBuildingsTimer.start();
        gameLoop.start();
        if (!hasReset) {
            startPowerUpTimer();
        }
    }

    public void resetGame() {
        cat.x = catX;
        cat.y = catY;
        velocityY = 0;
        score = 0;
        buildings.clear();
        gameOver = false;
        gameStarted = true;
        isImmune = false;
        powerUp = null;
        immunityDuration = 0;
        buildingGenerationInterval = 1500; 
        placeBuildingsTimer.setDelay(buildingGenerationInterval);
        playAgainButton.setVisible(false);
        placeBuildingsTimer.start();
        gameLoop.start();
        powerUpTimer.stop();
        startPowerUpTimer();
        hasReset = true; // Set the flag to true after resetting
    }


    public void startPowerUpTimer() {
        
        powerUpTimer.start();
    }

    public void generatePowerUp() {
        if (buildings.size() >= 2) {
            Building topBuilding = buildings.get(buildings.size() - 2);
            Building bottomBuilding = buildings.get(buildings.size() - 1);

            int freeSpaceStartY = topBuilding.y + buildingHeight;
            int freeSpaceEndY = bottomBuilding.y;

            int randomY = freeSpaceStartY + random.nextInt(freeSpaceEndY - freeSpaceStartY - 32);
            powerUp = new PowerUp(powerUpImg, frameWidth, randomY);
        }
    }

    public void placeBuildings() {
        int randomBuildingY = (int) (buildingY - buildingHeight / 4 - Math.random() * (buildingHeight / 2));
        int freeSpace = frameHeight / 3;

        Building topBuilding = new Building(topBuildingImg);
        topBuilding.y = randomBuildingY;
        buildings.add(topBuilding);

        Building bottomBuilding = new Building(bottomBuildingImg);
        bottomBuilding.y = topBuilding.y + buildingHeight + freeSpace;
        buildings.add(bottomBuilding);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, 800, 600, null);
        g.drawImage(cat.img, cat.x, cat.y, cat.width, cat.height, null);

        if (isImmune) {
            g.drawImage(cat.immunityImg, cat.x, cat.y, 64, 64 , null); 
        }

        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            g.drawImage(building.img, building.x, building.y, building.width, building.height, null);
        }

        if (powerUp != null) {
            g.drawImage(powerUp.img, powerUp.x, powerUp.y, powerUp.width, powerUp.height, null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Pixel Sans Serif", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over", 270, 250);
            g.drawString("Score: " + String.valueOf((int) score), frameWidth / 2 - 115, frameHeight / 2);
            playAgainButton.setVisible(true);
        } else {
            g.drawString(String.valueOf((int) score), 30, 95);
            if (isImmune) {
                g.drawString("Immunity: " + immunityDuration, 480, 40);
            }
        }
    }

    public void move() {
        velocityY += gravity;
        cat.y += velocityY;
        cat.y = Math.max(cat.y, 0);

        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            building.x += velocityX;

            if (!building.passed && cat.x > building.x + building.width) {
                building.passed = true;
                score += 0.5;
                if (score % 20 == 0 && placeBuildingsTimer.getDelay() > 300) { 
                    placeBuildingsTimer.setDelay(Math.max(300, placeBuildingsTimer.getDelay() - 100));
                }
            }
            if (!isImmune && collision(cat, building)) {
                gameOver = true;
            }
        }

        if (cat.y > frameHeight) {
            gameOver = true;
        }

        if (powerUp != null) {
            powerUp.x += velocityX;
            if (collision(cat, powerUp)) {
                isImmune = true;
                immunityDuration = 10;
                powerUp = null;

                immunityTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        immunityDuration--;
                        if (immunityDuration <= 0) {
                            isImmune = false;
                            immunityTimer.stop();
                            powerUpTimer.stop();
                            startPowerUpTimer();
                        }
                    }
                });
                immunityTimer.start();
            } else if (powerUp.x + powerUp.width < 0) {
                powerUp = null;
                powerUpTimer.stop();
                startPowerUpTimer();
                
            }
        }
    }

 

    public boolean collision(Cat a, Building b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    public boolean collision(Cat a, PowerUp p) {
        return a.x < p.x + p.width &&
                a.x + a.width > p.x &&
                a.y < p.y + p.height &&
                a.y + a.height > p.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            move();
            repaint();
            if (gameOver) {
                placeBuildingsTimer.stop();
                gameLoop.stop();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStarted) {
            velocityY = -9;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
