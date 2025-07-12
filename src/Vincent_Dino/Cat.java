package Vincent_Dino;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.List;

public class Cat extends JPanel {
    private List<BufferedImage> runImages;
    private List<BufferedImage> jumpImages;
    private int currentFrame;
    private int frameCount;
    private int x, y, width, height;
    private boolean jumping = false;
    private int jumpVelocity = 0;
    private final int gravity = 10;
    private final int groundY;
    private int frameDelay;
    private int frameDelayCounter;

    public Cat(List<BufferedImage> runImages, List<BufferedImage> jumpImages, int x, int y, int width, int height, int frameDelay) {
        this.runImages = runImages;
        this.jumpImages = jumpImages;
        this.frameCount = runImages.size();
        this.currentFrame = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.groundY = y; // Initial ground position
        this.frameDelay = frameDelay;
        this.frameDelayCounter = 1;
        this.setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setOpaque(false);
        requestFocusInWindow();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    jump();
                }
            }
        });
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            jumpVelocity = -15; // Initial jump velocity
            currentFrame = 0; // Reset frame to start jump animation
        }
    }

    public void update() {
        if (jumping) {
            y += jumpVelocity;
            jumpVelocity += gravity;
            if (y >= groundY) {
                y = groundY;
                jumping = false;
                jumpVelocity = 0;
                currentFrame = 0; // Reset frame to start running animation
            }
        }
        frameDelayCounter++;
        if (frameDelayCounter >= frameDelay) {
            if (jumping) {
                currentFrame = (currentFrame + 1) % jumpImages.size();
            } else {
                currentFrame = (currentFrame + 1) % runImages.size();
            }
            frameDelayCounter = 0;
        }
        setLocation(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (jumping && !jumpImages.isEmpty()) {
            g.drawImage(jumpImages.get(currentFrame), 0, 0, width, height, this);
        } else if (!runImages.isEmpty()) {
            g.drawImage(runImages.get(currentFrame), 0, 0, width, height, this);
        }
    }
}
