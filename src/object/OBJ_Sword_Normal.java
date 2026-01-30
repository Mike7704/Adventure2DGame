package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

	public static final String objName = "Normal Sword";
	
	public OBJ_Sword_Normal(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_sword;
		down1 = new Image(getClass().getResourceAsStream("/Object/sword_normal.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		attackValue = 1;
		knockBackPower = 2;
		attackMotion1Duration = 5;
		attackMotion2Duration = 25;
		attackArea.setWidth(36);
		attackArea.setHeight(36);
		
		description = "[" + name + "]\nAn old sword.\n+" + attackValue + " Attack";
		price = 50;
	}
}
