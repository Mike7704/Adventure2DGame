package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Axe extends Entity {

	public static final String objName = "Woodcutter's Axe";
	
	public OBJ_Axe(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_axe;
		down1 = new Image(getClass().getResourceAsStream("/Object/axe.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		attackValue = 2;
		knockBackPower = 10;
		attackMotion1Duration = 20;
		attackMotion2Duration = 40;
		attackArea.setWidth(30);
		attackArea.setHeight(30);
		
		description = "[" + name + "]\nA trusty axe.\n+" + attackValue + " Attack";
		price = 75;
	}
}
