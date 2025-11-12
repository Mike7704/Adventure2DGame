package tile_interactive;

import javafx.scene.image.Image;
import main.GamePanel;

public class IT_Trunk  extends InteractiveTile {

	public IT_Trunk(GamePanel gamePanel, int col, int row) {
		super(gamePanel, col, row);
		
		this.worldX = gamePanel.tileSize * col;
		this.worldY = gamePanel.tileSize * row;
		
		down1 = new Image(getClass().getResourceAsStream("/Tiles_Interactive/trunk.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		
		// Remove solid area so there is no collision
		solidArea.setX(0);
		solidArea.setY(0);
		solidArea.setWidth(0);
		solidArea.setHeight(0);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
	}

}
