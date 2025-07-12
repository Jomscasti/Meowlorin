package Janna_Eating;
import javax.swing.*;


import Home.MainPanel;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GamePanel5_LoadingPage extends JPanel {
    private JButton playButton, backButton;
    Image loadingPageBg;

    public GamePanel5_LoadingPage(MainPanel mp, GamePanel5_Settings gp5) {
        setLayout(null); 

        URL urlLoadingPageBg = GamePanel5_Settings.class.getResource("/lp_bgg.png");
        loadingPageBg = new ImageIcon(urlLoadingPageBg).getImage().getScaledInstance(800, 700, Image.SCALE_SMOOTH);

        playButton = new JButton();
        playButton.setIcon(mp.resizeImage("/start_button.png", 160, 43));
        playButton.setFont(new Font("Pixel Sans Serif", Font.PLAIN, 32));
        
        // Remove default button border
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);
        
        mp.addHoverEffect(playButton, 300, 400, 160, 43, 180, 63, "/start_button.png");
        playButton.setBounds(300, 400, 160, 43); 
        playButton.setFocusPainted(false);
        this.add(playButton);
        
        playButton.addActionListener( e ->{
        	mp.showScreen("GAME5");
            gp5.startGame();
        });

        JButton backButton = new JButton();
        backButton.setBounds(20, 10, 110, 80);
        backButton.setIcon(resizeImages("/HomePage/back_button.png", 110, 80));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        addHoverEffect(backButton, 20, 10, 110, 80, 130, 100, "/HomePage/back_button.png");
        backButton.addActionListener(e -> mp.showScreen("HOME")); // Switch to GAME panel
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


    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(loadingPageBg, 0, 0, 800, 650, this);
    }
}