package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import application.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileManager {

	private GamePanel gamePanel;
	private Tile[] tile;
	private int mapTileNum[][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tile = new Tile[10];
		mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
		
		getTileImage();
		loadMap("/Maps/map01.txt");
	}
	
	// Tile texture images
	public void getTileImage() {
		tile[0] = new Tile();
		tile[0].image = new Image(getClass().getResourceAsStream("/Tiles/grass00.png"));
		
		tile[1] = new Tile();
		tile[1].image = new Image(getClass().getResourceAsStream("/Tiles/wall.png"));
		
		tile[2] = new Tile();
		tile[2].image = new Image(getClass().getResourceAsStream("/Tiles/water00.png"));
	}
	
	// Read map data from text file
	public void loadMap(String filePath) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(filePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			int col = 0;
			int row = 0;

			while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
				String line = bufferedReader.readLine();

				// Store image number for each value on row
				while(col < gamePanel.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				// Next row
				if (col == gamePanel.maxScreenCol) {
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
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
	
		while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
			int tileNum = mapTileNum[col][row]; // Get tile number/texture from map data
			
			gc.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize);
			col++;
			x += gamePanel.tileSize;
			
			// Next row
			if (col == gamePanel.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gamePanel.tileSize;
			}
		}
	}
	
}
