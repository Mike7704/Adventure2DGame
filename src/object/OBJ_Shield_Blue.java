package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

	public static final String objName = "Blue Shield";
	
	public OBJ_Shield_Blue(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_sheild;
		down1 = new Image(getClass().getResourceAsStream("/Object/shield_blue.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		defenseValue = 2;
		description = "[" + name + "]\nA blue shield.\n+" + defenseValue + " Defense";
		price = 100;
	}
}
