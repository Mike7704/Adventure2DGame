package application;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class GamePanel extends AnchorPane {

	// SCREEN SETTINGS
	private final int originalTileSize = 16; // 16 x 16 tile
	private final int scale = 3; // Scale tiles
	
	private final int tileSize = originalTileSize * scale; // 16 x 3 = 48 tile
	private final int maxScreenCol = 16;
	private final int maxScreenRow = 12;
	private final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	private final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	private final int TARGET_FPS = 60;
	private final double TARGET_TIME_BETWEEN_FRAMES = 1_000_000_000.0 / TARGET_FPS; // in nanoseconds
	
	private Canvas canvas;
    private GraphicsContext gc;
	
    KeyHandler keyHandler = new KeyHandler();
    
    // Default player location
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
	public GamePanel() {
		this.setPrefSize(screenWidth, screenHeight);

        // Create canvas for drawing
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();

        this.getChildren().add(canvas);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	}
	
	public void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            private double accumulatedTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                long elapsedNanos = now - lastTime;
                accumulatedTime += elapsedNanos;
                lastTime = now;

                // Only update/draw when enough time has passed for 1 frame (60 FPS)
                while (accumulatedTime >= TARGET_TIME_BETWEEN_FRAMES) {
                    update();
                    draw();

                    accumulatedTime -= TARGET_TIME_BETWEEN_FRAMES;
                }
            }
        };

        timer.start();
    }
	
	public void update() {
		// Move player
		if (keyHandler.isUpPressed()) {
			playerY -= playerSpeed;
		}
		else if (keyHandler.isDownPressed()) {
			playerY += playerSpeed;
		}
		else if (keyHandler.isLeftPressed()) {
			playerX -= playerSpeed;
		}
		else if (keyHandler.isRightPressed()) {
			playerX += playerSpeed;
		}
	}
	
	public void draw() {
        // Clear screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // White square example
        gc.setFill(Color.WHITE);
        gc.fillRect(playerX, playerY, tileSize, tileSize);
    }
	
	public KeyHandler getKeyHandler() {
	    return keyHandler;
	}
}
