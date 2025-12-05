package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

	public OBJ_Sword_Normal(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Normal Sword";
		type = type_sword;
		down1 = new Image(getClass().getResourceAsStream("/Object/sword_normal.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		attackValue = 1;
		knockBackPower = 2;
		attackArea.setWidth(36);
		attackArea.setHeight(36);
		
		description = "[" + name + "]\nAn old sword.\n+" + attackValue + " Attack";
		price = 50;
	}
}
