package Louis_Versus;
import java.util.Random;

public class GamePanel4Play {

    // Define constants for moves
    public static final int ROCK = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;

    // Random generator for opponent's move
    private Random random;

    // Game variables
    private int score;
    private int lives;

    public GamePanel4Play() {
        random = new Random();
        resetGame();
        
    }
    
    public void resetGame() {
        score = 0;
        lives = 8; // Reset lives to 8
    }
    
    // Method to get opponent's move
    public int getOpponentMove() {
        return random.nextInt(3); // Returns 0, 1, or 2 randomly for rock, paper, or scissors
    }

    // Method to determine the winner
    public String determineWinner(int playerMove, int opponentMove) {
        if (playerMove == opponentMove) {
            return "It's a tie!";
        } else if ((playerMove == ROCK && opponentMove == SCISSORS) ||
                   (playerMove == PAPER && opponentMove == ROCK) ||
                   (playerMove == SCISSORS && opponentMove == PAPER)) {
            score++; // Increment score on win
            return "You win!";
        } else {
            lives--; // Decrement lives on loss
            return "You lose!";
        }
    }

    // Method to check if the game is over
    public boolean isGameOver() {
        return lives <= 0; // Game is over if no lives left
    }

    // Getter for current score
    public int getScore() {
        return score;
    }

    // Getter for current lives count
    public int getLives() {
        return lives;
    }
}
