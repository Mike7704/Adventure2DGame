package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Boots extends Entity {
	
	public static final String objName = "Boots";
	
	public OBJ_Boots(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_consumable;
		down1 = new Image(getClass().getResourceAsStream("/Object/boots.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\nIncreases speed.";
		price = 100;
	}
}
