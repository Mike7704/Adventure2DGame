package application;

import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import tile.TileManager;

public class GamePanel extends AnchorPane {

	// SCREEN SETTINGS
	private final int originalTileSize = 16; // 16 x 16 tile
	private final int scale = 3; // Scale tiles
	
	public final int tileSize = originalTileSize * scale; // 16 x 3 = 48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	private final int TARGET_FPS = 60;
	private final double TARGET_TIME_BETWEEN_FRAMES = 1_000_000_000.0 / TARGET_FPS; // in nanoseconds
	
	private Canvas canvas;
    private GraphicsContext gc;
	
    private TileManager tileManager = new TileManager(this);
    
    private KeyHandler keyHandler = new KeyHandler();
    private Player player = new Player(this, keyHandler);
        
	public GamePanel() {
		this.setPrefSize(screenWidth, screenHeight);

        // Create canvas for drawing
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        
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
		player.update();
	}
	
	public void draw() {
        tileManager.draw(gc);
        player.draw(gc);
    }
	
	public KeyHandler getKeyHandler() {
	    return keyHandler;
	}
}
