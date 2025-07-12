package Home;

import javax.swing.*;

import Janna_Eating.GamePanel5_LoadingPage;
import Janna_Eating.GamePanel5_Settings;
import Ken_MemoryMatch.GamePanel1;
import Ken_MemoryMatch.LoadingScreen1;
import Louis_Versus.GamePanel4;
import Louis_Versus.GamePanel4LoadingScreen;
import Stephen_Flappy.GamePanel2Character;
import Vincent_Dino.GamePanel3;
import Vincent_Dino.LoadingScreen3;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    
    //GamePanels
    private GamePanel1 gp1;
    private GamePanel2Character gp2;
    private GamePanel4 gp4;
    private	GamePanel5_Settings gp5;
    private HomePanel pnlHome;
    private GamePanel5_LoadingPage loadingPage;
    private GamePanel5_Settings gameSettings;
    
    public Font arcade;

    Sound sound = new Sound();
    Sound se = new Sound();
    
    public MainPanel(JFrame mainFrame) {
    	//Font
        InputStream is = getClass().getResourceAsStream("/Fonts/ka1.ttf");
        try {
            arcade = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        gp5 = new GamePanel5_Settings(this);
        // Import HomePanel class panel
        pnlHome = new HomePanel(this, mainFrame);
        loadingPage = new GamePanel5_LoadingPage(this, gp5);
        
        // Import Games and loading screen
        gp1 = new GamePanel1(this);
        LoadingScreen1 loadingScreen = new LoadingScreen1(this, gp1);
        gp2 = new GamePanel2Character(this);
        GamePanel4LoadingScreen  load4 = new GamePanel4LoadingScreen (this);
        gp4 = new GamePanel4(this);
        GamePanel3 pnlGame = new GamePanel3(this); // Initialize GamePanel3 here
        LoadingScreen3 pnlMenu = new LoadingScreen3(this, pnlGame);

        
        add(pnlHome, "HOME");
        add(loadingScreen, "LOADING1");
        add(gp1, "GAME1");
        add(gp2, "GAME2");
        add(pnlMenu, "LOADING3");
        add(pnlGame, "GAME3");
        add(load4, "LOADING4");
        add(gp4, "GAME4");
        add(loadingPage, "LOADING5");
        add(gp5, "GAME5");

    }

    public void showScreen(String name) {
        cardLayout.show(this, name);
        repaint();
        revalidate();
    }
    
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    
    public void stopMusic() {
        sound.stop();
    }
    
    public void playSE(int i) {
        se.setFile(i);
        se.play();
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
    
    
    public GamePanel1 getGamePanel1() {
        return gp1;
        
    }
    
    public GamePanel5_Settings getGamePanel2() {
        return gp5;
    }
    
    public GamePanel4 getGamePanel4() {
        return gp4;
    }
    public HomePanel getHomePanel() {
        return pnlHome;
    }
}
