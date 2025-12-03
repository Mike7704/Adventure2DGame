package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TileManager {

	private GamePanel gamePanel;
	public Tile[] tile;
	public int[][][] mapTileNum;
	public boolean drawPath = false;
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tile = new Tile[50];
		mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		
		getTileImage();
		loadMap("/Maps/worldV3.txt", 0);
		loadMap("/Maps/interior01.txt", 1);
	}
	
	// Tile texture images
	public void getTileImage() {
		// PLACEHOLDER (To make world map file neater)
        int indexes[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for(int i = 0; i < indexes.length; i++){
            setup(indexes[i], "grass00", false);
        }
        
        // TILES
        setup(11, "grass01", false);

        // WATER loop
        indexes = new int[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        for(int i = 0; i < indexes.length; i++){
            String waterIndex = "water" + String.format("%02d", i);
            setup(indexes[i], waterIndex, true);
        }

        // ROAD loop
        indexes = new int[]{26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
        for(int i = 0; i < indexes.length; i++){
            String roadIndex = "road" + String.format("%02d", i);
            setup(indexes[i], roadIndex, false);
        }

        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41,"tree", true);
        
        setup(42, "hut", false);
        setup(43, "floor01", false);
        setup(44,"table01", true);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		try {
			tile[index] = new Tile();
			tile[index].image = new Image(getClass().getResourceAsStream("/Tiles/" + imageName + ".png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
			tile[index].collision = collision;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Read map data from text file
	public void loadMap(String filePath, int map) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(filePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			int col = 0;
			int row = 0;

			while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
				String line = bufferedReader.readLine();

				// Store image number for each value on row
				while(col < gamePanel.maxWorldCol) {
					String[] numbers = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				// Next row
				if (col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Draw each tile on screen
	public void draw(GraphicsContext gc) {
		int worldCol = 0;
		int worldRow = 0;
	
		while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
			int tileNum = mapTileNum[gamePanel.currentMap][worldCol][worldRow]; // Get tile number/texture from map data
			
			// Set tile screen position relative to the player position
			int worldX = worldCol * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
			int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;
			
			// Draw tiles only visible on screen around the player
			if (worldX + gamePanel.tileSize > gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX &&
				worldX - gamePanel.tileSize < gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX &&
				worldY + gamePanel.tileSize > gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY &&
				worldY - gamePanel.tileSize < gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY)
			{
				gc.drawImage(tile[tileNum].image, screenX, screenY);
			}
			worldCol++;
			
			// Next row
			if (worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
		// Draw path finder (debug)
		if (drawPath) {
			gc.setFill(new Color(1, 1, 1, 0.2));
			
			for (int i = 0; i < gamePanel.getPathFinder().pathList.size(); i++) {
				int worldX = gamePanel.getPathFinder().pathList.get(i).col * gamePanel.tileSize;
				int worldY = gamePanel.getPathFinder().pathList.get(i).row * gamePanel.tileSize;
				int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
				int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;
				
				gc.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
			}
		}
	}
	
}
