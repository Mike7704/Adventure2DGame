package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

	public OBJ_Sword_Normal(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Normal Sword";
		down1 = new Image(getClass().getResourceAsStream("/Object/sword_normal.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		attackValue = 1;
	}
}
