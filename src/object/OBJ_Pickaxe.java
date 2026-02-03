package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

	public static final String objName = "Pickaxe";
	
	public OBJ_Pickaxe(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_pickaxe;
		down1 = new Image(getClass().getResourceAsStream("/Object/pickaxe.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		attackValue = 2;
		knockBackPower = 10;
		attackMotion1Duration = 20;
		attackMotion2Duration = 40;
		attackArea.setWidth(30);
		attackArea.setHeight(30);
		
		description = "[" + name + "]\nA trusty pickaxe.\n+" + attackValue + " Attack";
		price = 75;
	}
}
