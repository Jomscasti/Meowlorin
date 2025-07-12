package Home;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HomePanel extends JPanel {
    BufferedImage bg1;

    // Title 
    BufferedImage title;

    // Buttons
    BufferedImage btn1, btn2, btn3, btn4, btn5;

    // Array to hold the buttons
    private HomePanel_Buttons[] buttons;
    private ArrayList<String[]> buttonList = new ArrayList<>();

    private JFrame mainFrame;
    private MainPanel mainPanel;
    private Sound sound;
    private boolean isMuted = false;
    private JLabel gifLabel; // Label to display the GIF

    public HomePanel(MainPanel mp, JFrame mainFrame) {
        this.mainPanel = mp;
        this.mainFrame = mainFrame;
        this.sound = new Sound();
        setLayout(null);
        getImages();
        drawButtons(mp);
        this.setBackground(new Color(141, 74, 100));

        // Initialize the GIF label
        gifLabel = new JLabel();
        gifLabel.setBounds(350, 50, 500, 700); // Initial position and size
        gifLabel.setVisible(false);
        add(gifLabel);

        // Automatically start the sound
        sound.setFile(6); // Set background music file
        sound.loop(); // Start playing in loop
    }

    // Initialize the drawing for button
    private void drawButtons(MainPanel mp) {
        String[][] buttonsAtt = {
            {"/HomePage/purrfectpair_title.png", "30", "132", "238", "50"},
            {"/HomePage/flappykitten_title.png", "30", "197", "238", "50"},
            {"/HomePage/purrfectleap_title.png", "30", "262", "238", "50"},
            {"/HomePage/fur-ociusshowdown_title.png", "30", "327", "238", "50"},
            {"/HomePage/catchthefood_title.png", "30", "392", "238", "50"}
        };
        Collections.addAll(buttonList, buttonsAtt);

        buttons = new HomePanel_Buttons[buttonList.size()];

        for (int i = 0; i < buttonList.size(); i++) {
            String[] button = buttonList.get(i);
            String imgPath = button[0];
            int x = Integer.parseInt(button[1]);
            int y = Integer.parseInt(button[2]);
            int width = Integer.parseInt(button[3]);
            int height = Integer.parseInt(button[4]);

            HomePanel_Buttons btn = new HomePanel_Buttons(imgPath, x, y, width, height, i, mp);
            buttons[i] = btn;
            add(btn);

            String gifPath;
            switch (i) {
                case 0:
                    gifPath = "/HomePage/cardcat.gif";
                    break;
                case 1:
                    gifPath = "/HomePage/hellicater.gif";
                    break;
                case 2:
                    gifPath = "/HomePage/catrun.gif";
                    break;
                case 3:
                    gifPath = "/HomePage/cathy.gif";
                    break;
                case 4:
                    gifPath = "/HomePage/eatingcat.gif"; 
                    break;
                default:
                    gifPath = "/HomePage/immunity.gif";
                    break;
            }

            final String gifPathFinal = gifPath;
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    gifLabel.setIcon(resizeGif(gifPathFinal, 420, 300)); 
                    gifLabel.setLocation(btn.getX() + btn.getWidth() + 10, getY());
                    gifLabel.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    gifLabel.setVisible(false);
                }
            });
        }

        // About Game Button
        JButton aboutGameButton = new JButton();
        aboutGameButton.setBounds(680, 470, 100, 75);
        aboutGameButton.setIcon(resizeImages("/HomePage/questionmark1_logo.png", 100, 75));
        aboutGameButton.setBorderPainted(false);
        aboutGameButton.setContentAreaFilled(false);
        aboutGameButton.setOpaque(false);
        addHoverEffect(aboutGameButton, 680, 470, 100, 70, 120, 95, "/HomePage/questionmark1_logo.png");
        aboutGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutGameFrame(mainFrame);
                mainFrame.setVisible(false);
            }
        });
        add(aboutGameButton);

        // Sound Button
        JButton soundButton = new JButton();
        soundButton.setBounds(680, 380, 100, 75);
        soundButton.setIcon(resizeImages("/HomePage/sound_on.png", 100, 75));
        soundButton.setBorderPainted(false);
        soundButton.setContentAreaFilled(false);
        soundButton.setOpaque(false);
        soundButton.setFocusPainted(false);
        addHoverEffect(soundButton, 680, 380, 100, 75, 120, 95, "/HomePage/sound_on.png");
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMuted) {
                    soundButton.setIcon(resizeImages("/HomePage/sound_on.png", 100, 75));
                    sound.setFile(6); 
                    sound.loop(); 
                } else {
                    soundButton.setIcon(resizeImages("/HomePage/sound_off.png", 100, 75));
                    sound.stop(); 
                }
                isMuted = !isMuted;
            }
        });
        add(soundButton);
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

    // Method to resize GIF ImageIcon
    public ImageIcon resizeGif(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }

    private Icon resizeImage(String string, int i, int j) {
        // TODO Auto-generated method stub
        return null;
    }

    private ImageIcon aboutGameButton(String string, String string2) {
        // TODO Auto-generated method stub
        return null;
    }

    // Get Images
    private void getImages() {
        bg1 = loadImages("/HomePage/background");
        title = loadImages("/HomePage/title_meowlorin");
    }

    // Load Images
    private BufferedImage loadImages(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(imagePath + ".png"));
        } catch (IOException e) {
            System.err.println("IOException while loading image: " + img);
            e.printStackTrace();
        }
        return img;
    }

    private void drawClouds(Graphics g) {}
    private void drawBackground(Graphics g) {
        if (bg1 != null) {
            g.drawImage(bg1, 0, 0, getWidth(), getHeight(), this); // Draw background image
        }
    }

    private void drawButtons(Graphics g) {}

    private void drawSprites(Graphics g, HomePanel_Buttons whtBtn) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }
}

class AboutGameFrame extends JFrame {
    private int currentSlide = 0;
    private JPanel slidePanel;
    private String[] slides = {
        "/HomePage/about_game",
        "/HomePage/about_game1",
        "/HomePage/about_game2",
        "/HomePage/about_game4",
        "/HomePage/about_game5"
    };

    private JFrame homeFrame;

    public AboutGameFrame(JFrame homeFrame) {
        this.homeFrame = homeFrame;
        setTitle("About Game");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton backButton = new JButton();
        backButton.setBounds(20, 10, 110, 80);
        backButton.setIcon(resizeImage("/HomePage/back_button.png", 110, 80));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        addHoverEffect(backButton, 20, 10, 110, 80, 130, 100, "/HomePage/back_button.png");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.setVisible(true);
                dispose();
            }
        });
        add(backButton);

        JButton nextButton = new JButton();
        nextButton.setBounds(431, 466, 100, 75); 
        nextButton.setIcon(resizeImage("/HomePage/next_button.png", 100, 75));
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setOpaque(false);
        addHoverEffect(nextButton, 431, 466, 100, 75, 120, 95, "/HomePage/next_button.png");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSlide < slides.length - 1) {
                    currentSlide++;
                    updateSlide();
                }
            }
        });
        add(nextButton);

        JButton previousButton = new JButton();
        previousButton.setBounds(304, 466, 100, 75);
        previousButton.setIcon(resizeImage("/HomePage/prev_button.png", 100, 75));
        previousButton.setBorderPainted(false);
        previousButton.setContentAreaFilled(false);
        previousButton.setOpaque(false);
        addHoverEffect(previousButton, 304, 466, 100, 75, 120, 95, "/HomePage/prev_button.png");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSlide > 0) {
                    currentSlide--;
                    updateSlide();
                }
            }
        });
        add(previousButton);

        slidePanel = new JPanel();
        slidePanel.setBounds(0, 0, 800, 600);
        slidePanel.setOpaque(false);
        add(slidePanel);

        updateSlide();
        setVisible(true);
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

    private void updateSlide() {
        slidePanel.removeAll();
        BufferedImage slideImage = loadImages(slides[currentSlide]);
        Image scaledSlideImage = slideImage.getScaledInstance(slidePanel.getWidth(), slidePanel.getHeight(), Image.SCALE_SMOOTH);
        JLabel slideLabel = new JLabel(new ImageIcon(scaledSlideImage));
        slidePanel.add(slideLabel);
        slidePanel.revalidate();
        slidePanel.repaint();
    }

    // Load Images method for AboutGameFrame
    private BufferedImage loadImages(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(imagePath + ".png"));
        } catch (IOException e) {
            System.err.println("IOException while loading image: " + img);
            e.printStackTrace();
        }
        return img;
    }
}
