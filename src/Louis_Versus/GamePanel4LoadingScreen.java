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

public class GamePanel4LoadingScreen extends JPanel {
	
	private Image backgroundImage;
	public static Color clrFont = new Color(237, 34, 157);

    public GamePanel4LoadingScreen (MainPanel mainPanel) {
    	
    	ImageIcon img1 = new ImageIcon(getClass().getResource("/gp4/loadingScreen.jpg"));
    	backgroundImage = img1.getImage();
        setLayout(null);
        
        JButton playButton =  new JButton(new ImageIcon(loadImage("/gp4/start_button.png")));
        playButton.setIcon(resizeImages("/gp4/start_button.png", 250, 125));
        playButton.setBorderPainted(false);
    	playButton.setContentAreaFilled(false);
    	playButton.setFocusPainted(false);
    	playButton.setOpaque(false);
        playButton.setBounds(270, 300, 250, 125);
        addHoverEffect(playButton, 270, 300, 250, 125, 270, 145, "/gp4/start_button.png");
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                playButton.setBounds(playButton.getX(), playButton.getY(), 240, 115);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                playButton.setBounds(playButton.getX(), playButton.getY(), 250, 125); 
                mainPanel.showScreen("GAME4");
                GamePanel4 gamePanel4 = mainPanel.getGamePanel4();
                if (gamePanel4 != null) {
                    gamePanel4.startTimer();
                }
            }
        });
        
        JButton backButton = new JButton();
        backButton.setBounds(20, 10, 110, 80);
        backButton.setIcon(resizeImages("/HomePage/back_button.png", 110, 80));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        addHoverEffect(backButton, 20, 10, 110, 80, 130, 100, "/HomePage/back_button.png");
        backButton.addActionListener(e -> mainPanel.showScreen("HOME")); // Switch to GAME panel
        add(backButton);


        JLabel titleLabel1 = new JLabel(new ImageIcon(loadImage("/gp4/Title.png")));
        titleLabel1.setForeground(clrFont);
        titleLabel1.setBounds(150, 55, 500, 300);
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(titleLabel1);
        add(playButton);
        add(backButton);
        
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

	public void resetGame(GamePanel4 gp4) {
		gp4.resetGame();
		
	}

 }