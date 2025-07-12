package Louis_Versus;
import javax.imageio.ImageIO;
import javax.swing.*;

import Home.MainPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel4Buttons extends JButton {

    private BufferedImage normalImage;
    private BufferedImage pressedImage;
   
    public GamePanel4Buttons(String imageName, int x, int y, int playerMove, GamePanel4Play gameLogic, JLabel resultLabel, GamePanel4 gp4, GamePanel4LoadingScreen loadingScreen, MainPanel mp) {

        this.normalImage = loadImage(imageName);
        this.pressedImage = createPressedImage(normalImage);
       

        this.setIcon(new ImageIcon(normalImage));
        this.setBounds(x, y, 50, 50);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon(pressedImage));
                setBounds(getX(), getY(), 45, 45);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon(normalImage));
                setBounds(getX(), getY(), 50, 50);
                // Call the playGame method in GamePanel4
                playGame(playerMove, gameLogic, resultLabel);

                // Update livesLabel immediately after a loss
                if (gameLogic.isGameOver()) {
                    GamePanel4TryAgain tryAgainFrame = new GamePanel4TryAgain(loadingScreen, gp4, mp, gameLogic);
                    mp.add(tryAgainFrame, "GAMEOVERPANEL4");
                    mp.showScreen("GAMEOVERPANEL4");
                    tryAgainFrame.setVisible(true);
                    
                } else {
                    // Update livesLabel if the game is not over
                    gp4.livesLabel.setText(" X " + gameLogic.getLives());
                    gp4.scoreLabel.setText(" X " + gameLogic.getScore());
                }

                
               
            }
        });

    }

    private BufferedImage loadImage(String filename) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filename);
            if (inputStream != null) {
                return ImageIO.read(inputStream);
            } else {
                System.err.println("Image not found: " + filename);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage createPressedImage(BufferedImage original) {
        BufferedImage pressed = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = pressed.createGraphics();
        g2d.drawImage(original, 0, 0, original.getWidth() - 5, original.getHeight() - 5, null);
        g2d.dispose();
        return pressed;
    }

    private void playGame(int playerMove, GamePanel4Play gameLogic, JLabel resultLabel) {
        // Get opponent's move
        int opponentMove = gameLogic.getOpponentMove();

        // Determine the winner
        String result = gameLogic.determineWinner(playerMove, opponentMove);

        // Display the result

        // Set the corresponding result image based on player and opponent moves
        String imageName = getImageName(playerMove, opponentMove);
        if (imageName != null) {
            BufferedImage resultImage = loadImage(imageName);
            if (resultImage != null) {
                resultLabel.setIcon(new ImageIcon(resultImage));
                resultLabel.setBounds(resultLabel.getX(), resultLabel.getY(), resultImage.getWidth(), resultImage.getHeight());
            }
        }

        // Debug
        System.out.println("Player chose: " + moveToString(playerMove));
        System.out.println("Opponent chose: " + moveToString(opponentMove));
        System.out.println(result);
    }

    private String getImageName(int playerMove, int opponentMove) {
        switch (playerMove) {
            case GamePanel4Play.ROCK:
                switch (opponentMove) {
                    case GamePanel4Play.ROCK:
                        return "/gp4/rockvrock.png"; // Replace with actual path
                    case GamePanel4Play.PAPER:
                        return "/gp4/rockvpaper.png"; // Replace with actual path
                    case GamePanel4Play.SCISSORS:
                        return "/gp4/rockvscissors.png"; // Replace with actual path
                }
                break;
            case GamePanel4Play.PAPER:
                switch (opponentMove) {
                    case GamePanel4Play.ROCK:
                        return "/gp4/papervrock.png"; // Replace with actual path
                    case GamePanel4Play.PAPER:
                        return "/gp4/papervpaper.png"; // Replace with actual path
                    case GamePanel4Play.SCISSORS:
                        return "/gp4/papervscissors.png"; // Replace with actual path
                }
                break;
            case GamePanel4Play.SCISSORS:
                switch (opponentMove) {
                    case GamePanel4Play.ROCK:
                        return "/gp4/scissorsvrock.png"; // Replace with actual path
                    case GamePanel4Play.PAPER:
                        return "/gp4/scissorsvpaper.png"; // Replace with actual path
                    case GamePanel4Play.SCISSORS:
                        return "/gp4/scissorsvscissors.png"; // Replace with actual path
                }
                break;
        }
        return null; // Handle if no image path matches
    }

    private String moveToString(int move) {
        switch (move) {
            case GamePanel4Play.ROCK:
                return "Rock (Sleep)";
            case GamePanel4Play.PAPER:
                return "Paper (Scare)";
            case GamePanel4Play.SCISSORS:
                return "Scissors (Scratch)";
            default:
                return "Unknown move";
        }
    }
}
