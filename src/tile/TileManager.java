package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileManager {

	private GamePanel gamePanel;
	public Tile[] tile;
	public int[][] mapTileNum;
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tile = new Tile[10];
		mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		
		getTileImage();
		loadMap("/Maps/world01.txt");
	}
	
	// Tile texture images
	public void getTileImage() {
		tile[0] = new Tile();
		tile[0].image = new Image(getClass().getResourceAsStream("/Tiles/grass00.png"));
		
		tile[1] = new Tile();
		tile[1].image = new Image(getClass().getResourceAsStream("/Tiles/wall.png"));
		tile[1].collision = true;
		
		tile[2] = new Tile();
		tile[2].image = new Image(getClass().getResourceAsStream("/Tiles/water00.png"));
		tile[2].collision = true;
		
		tile[3] = new Tile();
		tile[3].image = new Image(getClass().getResourceAsStream("/Tiles/earth.png"));
		
		tile[4] = new Tile();
		tile[4].image = new Image(getClass().getResourceAsStream("/Tiles/tree.png"));
		tile[4].collision = true;
		
		tile[5] = new Tile();
		tile[5].image = new Image(getClass().getResourceAsStream("/Tiles/road00.png"));
	}
	
	// Read map data from text file
	public void loadMap(String filePath) {
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
					
					mapTileNum[col][row] = num;
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
			int tileNum = mapTileNum[worldCol][worldRow]; // Get tile number/texture from map data
			
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
				gc.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
			}
			worldCol++;
			
			// Next row
			if (worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
}
