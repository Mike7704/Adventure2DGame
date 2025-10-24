package main;

import java.util.ArrayList;
import java.util.Collections;

import entity.Entity;
import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// FPS
	public final int TARGET_FPS = 60;
	private final double TARGET_TIME_BETWEEN_FRAMES = 1_000_000_000.0 / TARGET_FPS; // in nanoseconds
	
	private Canvas canvas;
    private GraphicsContext gc;
	
    private AnimationTimer gameLoop;
    private TileManager tileManager = new TileManager(this);
    private KeyHandler keyHandler = new KeyHandler(this);
    private Sound music = new Sound();
    private Sound soundEffect = new Sound();
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private UI ui = new UI(this);
    private EventHandler eventHandler = new EventHandler(this);
    
    // Entity and Object
    private Player player = new Player(this, keyHandler);
    private Entity[] obj = new Entity[10];
    private Entity[] npc = new Entity[10];
    private Entity[] monster = new Entity[20];
    private ArrayList<Entity> entityList = new ArrayList<>();
    
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    
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
		assetSetter.setNPC();
		assetSetter.setMonster();
		
		//playMusic(0);
		
		gameState = titleState;
	}
	
	public void startGameLoop() {
        gameLoop = new AnimationTimer() {
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
        gameLoop.start();
    }
	
	public void stopGameLoop() {
		gameLoop.stop();
    }
	
	public void update() {
		if (gameState == playState) {
			// PLAYER
			player.update();
			
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			
			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					monster[i].update();
				}
			}

		}
		else if (gameState == pauseState) {
			// nothing yet
		}		
	}
	
	public void draw() {
		// DEBUG
		long drawStart = 0;
		if (keyHandler.checkDrawTime) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(gc);
			return;
		}
		
		// TILE
        tileManager.draw(gc);
        
        // PLAYER ENTITY
        entityList.add(player);
        
        // NPC ENTITY
        for (int i = 0; i < npc.length; i++) {
			if (npc[i] != null) {
				entityList.add(npc[i]);
			}
		}
        
        // OBJECT ENTITY
        for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				entityList.add(obj[i]);
			}
		}
        
        // MONSTER ENTITY
        for (int i = 0; i < monster.length; i++) {
			if (monster[i] != null) {
				entityList.add(monster[i]);
			}
		}
        
        // SORT ENTITIES BY WORLD Y POSITION
        Collections.sort(entityList, (e1, e2) -> {
			return Integer.compare(e1.worldY, e2.worldY);
		});
        
        // DRAW ENTITIES
		for (Entity entity : entityList) {
			entity.draw(gc);
		}
		entityList.clear();
                
       // UI
        ui.draw(gc);
        
        // DEBUG
        if (keyHandler.checkDrawTime) {
	        long drawEnd = System.nanoTime();
	        long passed = drawEnd - drawStart;
	        System.out.println("Draw Time: " + passed);
        }
    }
	
	public void playMusic(int i) {
		music.setFile(i);
		music.loop();
		music.play();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSoundEffect(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
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
	
	public Entity[] getObject() {
		return obj;
	}
	
	public Entity[] getNPC() {
		return npc;
	}
	
	public Entity[] getMonster() {
		return monster;
	}
	
	public UI getUI() {
		return ui;
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
}
