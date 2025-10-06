package application;

import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import object.OBJ_Key;
import object.SuperObject;
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
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS
	private final int TARGET_FPS = 60;
	private final double TARGET_TIME_BETWEEN_FRAMES = 1_000_000_000.0 / TARGET_FPS; // in nanoseconds
	
	private Canvas canvas;
    private GraphicsContext gc;
	
    private TileManager tileManager = new TileManager(this);
    
    private KeyHandler keyHandler = new KeyHandler();
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private Player player = new Player(this, keyHandler);
    private SuperObject[] obj = new SuperObject[10];
    
	public GamePanel() {
		this.setPrefSize(screenWidth, screenHeight);

        // Create canvas for drawing
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        
        this.getChildren().add(canvas);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	}
	
	public void setupGame() {
		assetSetter.setObject();
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
		// TILE
        tileManager.draw(gc);
        
        // OBJECT
        for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(gc, this);
			}
		}
        
        // PLAYER
        player.draw(gc);
    }
	
	public KeyHandler getKeyHandler() {
	    return keyHandler;
	}
	
	public TileManager getTileManager() {
	    return tileManager;
	}
	
	public CollisionChecker getCollisionChecker() {
		return collisionChecker;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public SuperObject[] getObject() {
		return obj;
	}
}
