package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Boots extends Entity {
	
	public OBJ_Boots(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Boots";
		down1 = new Image(getClass().getResourceAsStream("/Object/boots.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\nIncreases speed.";
	}
}
