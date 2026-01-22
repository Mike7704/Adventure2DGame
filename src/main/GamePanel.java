package main;

import java.util.ArrayList;
import java.util.Collections;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends AnchorPane {

	// SCREEN SETTINGS
	private final int originalTileSize = 16; // 16 x 16 tile
	private final int scale = 3; // Scale tiles
	
	public final int tileSize = originalTileSize * scale; // 16 x 3 = 48 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow;
	public final int maxMap = 10;
	public int currentMap = 0;
	
	// FPS
	public final int TARGET_FPS = 60;
	private final double TARGET_TIME_BETWEEN_FRAMES = 1_000_000_000.0 / TARGET_FPS; // in nanoseconds
	
	// SETTINGS
	public boolean fullscreen = false;
	
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
    private Config config = new Config(this);
    private PathFinder pathFinder = new PathFinder(this);
    private EnvironmentManager environmentManager = new EnvironmentManager(this);
    private Map map = new Map(this);
    
    // Entity and Object
    private Player player = new Player(this, keyHandler);
    private Entity[][] obj = new Entity[maxMap][20];
    private Entity[][] npc = new Entity[maxMap][20];
    private Entity[][] monster = new Entity[maxMap][20];
    private InteractiveTile[][] interactiveTile = new InteractiveTile[maxMap][50];
    private ArrayList<Entity> entityList = new ArrayList<>();
    private Entity[][] projectileList = new Entity[maxMap][20];
    private ArrayList<Entity> particleList = new ArrayList<>();
    
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    
    Stage primaryStage;
    
	public GamePanel(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
		assetSetter.setInteractiveTile();
		environmentManager.setup();
		playMusic(0);
		
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
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			
			// MONSTER
			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
						monster[currentMap][i].update();
					}
					else if (!monster[currentMap][i].alive) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}
			
			// PROJECTILES
			for (int i = 0; i < projectileList[1].length; i++) {
				if (projectileList[currentMap][i] != null) {
					if (projectileList[currentMap][i].alive) {
						projectileList[currentMap][i].update();
					}
					else if (!projectileList[currentMap][i].alive) {
						projectileList[currentMap][i] = null;
					}
				}
			}

			// PARTICLES
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive) {
						particleList.get(i).update();
					}
					else if (!particleList.get(i).alive) {
						particleList.remove(i);
					}
				}
			}
			
			// INTERACTIVE TILES
			for (int i = 0; i < interactiveTile[1].length; i++) {
				if (interactiveTile[currentMap][i] != null) {
					interactiveTile[currentMap][i].update();
				}
			}
			
			// ENVIRONMENT
			environmentManager.update();
			
		}
		else if (gameState == pauseState) {
			// nothing yet
		}		
	}
		
	public void draw() {
		// DEBUG
		long drawStart = 0;
		if (keyHandler.showDebugText) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(gc);
			return;
		}
		
		// MAP SCREEN
		if (gameState == mapState) {
			map.drawFullMapScreen(gc);
			return;
		}
		
		// TILE
        tileManager.draw(gc);
        
        // INTERACTIVE TILES
		for (int i = 0; i < interactiveTile[1].length; i++) {
			if (interactiveTile[currentMap][i] != null) {
				interactiveTile[currentMap][i].draw(gc);
			}
		}
        
        // PLAYER ENTITY
        entityList.add(player);
        
        // NPC ENTITY
        for (int i = 0; i < npc[1].length; i++) {
			if (npc[currentMap][i] != null) {
				entityList.add(npc[currentMap][i]);
			}
		}
        
        // OBJECT ENTITY
        for (int i = 0; i < obj[1].length; i++) {
			if (obj[currentMap][i] != null) {
				entityList.add(obj[currentMap][i]);
			}
		}
        
        // MONSTER ENTITY
        for (int i = 0; i < monster[1].length; i++) {
			if (monster[currentMap][i] != null) {
				entityList.add(monster[currentMap][i]);
			}
		}
        
        // PROJECTILE ENTITY
        for (int i = 0; i < projectileList[1].length; i++) {
			if (projectileList[currentMap][i] != null) {
				entityList.add(projectileList[currentMap][i]);
			}
		}
        
        // PARTICLE ENTITY
        for (int i = 0; i < particleList.size(); i++) {
			if (particleList.get(i) != null) {
				entityList.add(particleList.get(i));
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
        
		// ENVIRONMENT
		environmentManager.draw(gc);
		
		// MINI MAP
		map.drawMiniMap(gc);
		
		// UI
        ui.draw(gc);
        
        // DEBUG
        if (keyHandler.showDebugText) {
	        long drawEnd = System.nanoTime();
	        long passed = drawEnd - drawStart;
	        System.out.println("Draw Time: " + passed);
	        System.out.println("WorldX: " + (int)player.worldX + ", WorldY: " + (int)player.worldY);
	        System.out.println("Column: " + ((int)(player.worldX + player.solidArea.getX()) / tileSize) + ", Row: " + ((int)(player.worldY + player.solidArea.getY()) / tileSize));
        }
    }
	
	public void toggleFullscreen() {
		fullscreen = !fullscreen;
        primaryStage.setFullScreen(fullscreen);
        
        if (fullscreen) {
            // Update canvas size to match full screen window size
            canvas.setWidth(primaryStage.getWidth());
            canvas.setHeight(primaryStage.getHeight());
            
            // Calculate the scale factors based on the current canvas size
            double scaleX = canvas.getWidth() / (double) (tileSize * maxScreenCol); // Scale relative to the original resolution width
            double scaleY = canvas.getHeight() / (double) (tileSize * maxScreenRow);

    	    // Apply the scaling to the GraphicsContext
    	    gc.save(); // Save the current state of the GC
    	    gc.scale(scaleX, scaleY); // Scale everything based on the canvas size
    	    
        } else {
            // Reset to default screen size
            canvas.setWidth(screenWidth);
            canvas.setHeight(screenHeight);
            
            gc.restore(); // Restore the default scale
        }
    }
			
	public void resetGame(boolean restart) {
		player.setDefaultPositions();
		player.restoreStatus();
		assetSetter.setNPC();
		assetSetter.setMonster();
				
		if (restart) {
			player.setDefaultValues();
			assetSetter.setObject();
			assetSetter.setInteractiveTile();
			environmentManager.lighting.resetDayNightCycle();
		}
		else {
			playMusic(0);
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
	
	public void setMusicVolume(float i) {
		music.setVolume(i);
	}
	
	public void setSoundVolume(float i) {
		soundEffect.setVolume(i);
	}
	
	public Sound getMusic() {
		return music;
	}
	
	public Sound getSoundEffect() {
		return soundEffect;
	}
	
	public KeyHandler getKeyHandler() {
	    return keyHandler;
	}
	
	public Canvas getCanvas() {
	    return canvas;
	}
	
	public TileManager getTileManager() {
	    return tileManager;
	}
	
	public CollisionChecker getCollisionChecker() {
		return collisionChecker;
	}
	
	public AssetSetter getAssetSetter() {
		return assetSetter;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Entity[][] getObject() {
		return obj;
	}
	
	public Entity[][] getNPC() {
		return npc;
	}
	
	public Entity[][] getMonster() {
		return monster;
	}
	
	public InteractiveTile[][] getInteractiveTile() {
		return interactiveTile;
	}
	
	public Entity[][] getProjectiles() {
		return projectileList;
	}
	
	public ArrayList<Entity> getParticles() {
		return particleList;
	}
	
	public UI getUI() {
		return ui;
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}
	
	public EnvironmentManager getEnvironmentManager() {
		return environmentManager;
	}
	
	public Map getMap() {
		return map;
	}
}
