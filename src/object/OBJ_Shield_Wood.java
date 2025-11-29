package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

	public OBJ_Shield_Wood(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Wood Shield";
		type = type_sheild;
		down1 = new Image(getClass().getResourceAsStream("/Object/shield_wood.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		defenseValue = 1;
		description = "[" + name + "]\nA wooden shield.\n+" + defenseValue + " Defense";
		price = 50;
	}
}
