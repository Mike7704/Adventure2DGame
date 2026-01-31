package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TileManager {

	private GamePanel gamePanel;
	public Tile[] tile;
	public int[][][] mapTileNum;
	public boolean drawPath = false;
	private ArrayList<String> fileNames = new ArrayList<>();
	private ArrayList<String> collisionStatus = new ArrayList<>();
	
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		// Read tile data file
		InputStream inputStream = getClass().getResourceAsStream("/Maps/tiledata.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		// Get tile names and collision status
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				fileNames.add(line);
				collisionStatus.add(bufferedReader.readLine());
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Initialise tile array
		tile = new Tile[fileNames.size()];
		getTileImage();
		
		// Get the max row and column for map array
		inputStream = getClass().getResourceAsStream("/Maps/worldmap.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String line2 = bufferedReader.readLine();
			String[] maxTiles = line2.split(" ");
			
			gamePanel.maxWorldCol = maxTiles.length;
			gamePanel.maxWorldRow = maxTiles.length;
			
			mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		loadMap("/Maps/worldmap.txt", 0);
		loadMap("/Maps/indoor01.txt", 1);
		loadMap("/Maps/dungeon01.txt", 2);
		loadMap("/Maps/dungeon02.txt", 3);
		
	}
	
	// Tile texture images
	public void getTileImage() {
		for (int i = 0; i < fileNames.size(); i++) {
			String imageName = fileNames.get(i);
			boolean collision = Boolean.parseBoolean(collisionStatus.get(i));
			setup(i, imageName, collision);
		}
	}
	
	public void setup(int index, String imageName, boolean collision) {
		try {
			tile[index] = new Tile();
			tile[index].image = new Image(getClass().getResourceAsStream("/Tiles/" + imageName), gamePanel.tileSize, gamePanel.tileSize, true, false);
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
