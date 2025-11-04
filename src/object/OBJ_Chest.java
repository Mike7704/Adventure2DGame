package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Chest extends Entity {
	
	public OBJ_Chest(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Chest";
		type = type_consumable;
		down1 = new Image(getClass().getResourceAsStream("/Object/chest.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
}
