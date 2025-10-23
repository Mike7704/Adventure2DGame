package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Key extends Entity {

	public OBJ_Key(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Key";
		down1 = new Image(getClass().getResourceAsStream("/Object/key.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
}
