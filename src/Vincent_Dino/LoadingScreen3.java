package Vincent_Dino;
import javax.swing.*;

import Home.MainPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadingScreen3 extends JPanel {

    private Image backgroundImage;

    public LoadingScreen3(MainPanel mp, GamePanel3 gp3) {
        setLayout(null);
        setBackground(Color.BLACK);

        JButton playButton = new JButton();
        playButton.setBounds(320, 400, 160, 43);
        playButton.setIcon(resizeImages("/start_button.png", 160, 43));
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);
        addHoverEffect(playButton, 320, 400, 160, 43, 180, 63, "/start_button.png");
        playButton.addActionListener(e -> {
        	gp3.startTimer();
        	mp.showScreen("GAME3");
        	}); // Switch to GAME panel
        add(playButton);
        
        JButton backButton = new JButton();
        backButton.setBounds(20, 10, 110, 80);
        backButton.setIcon(resizeImages("/HomePage/back_button.png", 110, 80));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        addHoverEffect(backButton, 20, 10, 110, 80, 130, 100, "/HomePage/back_button.png");
        backButton.addActionListener(e -> mp.showScreen("HOME")); // Switch to GAME panel
        add(backButton);
        
        
        // Load background image
        ImageIcon img1 = new ImageIcon(getClass().getResource("/LoadingScreen.png"));
        backgroundImage = img1.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
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
