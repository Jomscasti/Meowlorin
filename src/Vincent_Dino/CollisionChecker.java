package Vincent_Dino;
import java.awt.*;

class CollisionChecker {
    private GamePanel3 gamePanel;

    public CollisionChecker(GamePanel3 gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean isGameOver() {
        Rectangle catBounds = gamePanel.getCatBounds();
        for (Rectangle obstacle : gamePanel.getObstacleBounds()) {
            if (catBounds.intersects(obstacle)) {
                return true;
            }
        }
        return false;
    }
}