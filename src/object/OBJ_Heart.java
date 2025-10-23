package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	public OBJ_Heart(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Heart";
		image = new Image(getClass().getResourceAsStream("/Object/heart_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image2 = new Image(getClass().getResourceAsStream("/Object/heart_half.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image3 = new Image(getClass().getResourceAsStream("/Object/heart_blank.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
}
