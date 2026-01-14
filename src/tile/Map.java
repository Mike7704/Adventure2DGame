package tile;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import main.GamePanel;

public class Map extends TileManager {

	private GamePanel gamePanel;
	private Image worldMap[];
	public boolean miniMapOn = false;
	
	public Map(GamePanel gamePanel) {
		super(gamePanel);
		this.gamePanel = gamePanel;
		createWorldMap();
	}
	
	private void createWorldMap() {
		worldMap = new Image[gamePanel.maxMap];
		int worldmapWidth = gamePanel.tileSize * gamePanel.maxWorldCol;
		int worldmapHeight = gamePanel.tileSize * gamePanel.maxWorldRow;
		
		for (int i = 0; i < gamePanel.maxMap; i++) {
			Canvas canvas2 = new Canvas(worldmapWidth, worldmapHeight);
			GraphicsContext gc2 = canvas2.getGraphicsContext2D();
			
			int col = 0;
			int row = 0;
			
			while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
				int tileNum = mapTileNum[i][col][row];
				int x = gamePanel.tileSize * col;
				int y = gamePanel.tileSize * row;
				
				// Draw tile
				gc2.drawImage(tile[tileNum].image, x, y);
				
				col++;
				if (col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			
			// Convert the Canvas content to a WritableImage
	        WritableImage mapImage = new WritableImage(worldmapWidth, worldmapHeight);
	        canvas2.snapshot(null, mapImage); // Take a snapshot of the Canvas and store it in mapImage

	        // Store the created map image in worldMap
	        worldMap[i] = mapImage;
		}
	}
	
	public void drawFullMapScreen(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		
		// Draw map
		int width = 500;
		int height = 500;
		int x = gamePanel.screenWidth / 2 - width / 2;
		int y = gamePanel.screenHeight / 2 - height / 2;
		gc.drawImage(worldMap[gamePanel.currentMap], x, y, width, height);
		
		// Draw player
		double scale = (double) (gamePanel.tileSize * gamePanel.maxWorldCol)/width;
		int playerX = (int) (x + gamePanel.getPlayer().worldX / scale);
		int playerY = (int) (y + gamePanel.getPlayer().worldY / scale);
		int playerSize = (int) (gamePanel.tileSize / scale);
		gc.drawImage(gamePanel.getPlayer().down1, playerX, playerY, playerSize, playerSize);
		
		// Close message
		gc.setFont(gamePanel.getUI().font_very_small);
		gc.setTextAlign(TextAlignment.RIGHT);
		gamePanel.getUI().drawTextWithShadow("Press 'M' to close", gamePanel.screenWidth - 20, gamePanel.screenHeight - 20);
	}

	public void drawMiniMap(GraphicsContext gc) {
		if (miniMapOn) {
			// Draw map
			int width = 200;
			int height = 200;
			int x = gamePanel.screenWidth - width - 20;
			int y = gamePanel.screenHeight - height - 50;
			gc.drawImage(worldMap[gamePanel.currentMap], x, y, width, height);
			
			// Draw player
			double scale = (double) (gamePanel.tileSize * gamePanel.maxWorldCol)/width;
			int playerX = (int) (x + gamePanel.getPlayer().worldX / scale);
			int playerY = (int) (y + gamePanel.getPlayer().worldY / scale);
			int playerSize = (int) (gamePanel.tileSize / scale) + 10;
			gc.drawImage(gamePanel.getPlayer().down1, playerX - 5, playerY - 5, playerSize, playerSize);
		}
	}
}
