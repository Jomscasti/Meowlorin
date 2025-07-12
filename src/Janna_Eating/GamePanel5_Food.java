package Janna_Eating;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GamePanel5_Food extends JPanel {

    private int speed = 5; 
    
    private static final String[] foodTypes = {
        "milk", "hotdog", "bread", "fish", "catFood", "mice", "ball", "can", "choco", "grapes", "mug", "spicy"
    };
    private ImageIcon[] foodIcons = new ImageIcon[foodTypes.length];

    public GamePanel5_Food(String food) {
        this.setOpaque(false);
        
        JLabel lblSprite = new JLabel();
        lblSprite.setBounds(0, 0, 25, 25);
        add(lblSprite);

        // URLs for food
        foodIcons[0] = new ImageIcon(GamePanel5_Food.class.getResource("/milk.png"));
        foodIcons[1] = new ImageIcon(GamePanel5_Food.class.getResource("/hotdog.png"));
        foodIcons[2] = new ImageIcon(GamePanel5_Food.class.getResource("/bread.png"));
        foodIcons[3] = new ImageIcon(GamePanel5_Food.class.getResource("/fish.png"));
        foodIcons[4] = new ImageIcon(GamePanel5_Food.class.getResource("/food.png"));
        foodIcons[5] = new ImageIcon(GamePanel5_Food.class.getResource("/mice.png"));
        
        // URLs for objects
        foodIcons[6] = new ImageIcon(GamePanel5_Food.class.getResource("/ball.png"));
        foodIcons[7] = new ImageIcon(GamePanel5_Food.class.getResource("/can.png"));
        foodIcons[8] = new ImageIcon(GamePanel5_Food.class.getResource("/Choco.png"));
        foodIcons[9] = new ImageIcon(GamePanel5_Food.class.getResource("/grapes.png"));
        foodIcons[10] = new ImageIcon(GamePanel5_Food.class.getResource("/mug.png"));
        foodIcons[11] = new ImageIcon(GamePanel5_Food.class.getResource("/spicey.png"));

        for (int i = 0; i < foodTypes.length; i++) {
            if (foodTypes[i].equals(food)) {
                lblSprite.setIcon(foodIcons[i]);
                break;
            }
        }
    }

    public void hideFood() {
        this.setBounds(-50, -50, 1, 1);
    }
}
