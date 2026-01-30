package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Tent extends Entity{

	public static final String objName = "Tent";
	
	public OBJ_Tent(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_consumable;
		down1 = new Image(getClass().getResourceAsStream("/Object/tent.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\nA tent for sleeping.";
		price = 50;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		// Only allow sleeping at night
		if (gamePanel.getEnvironmentManager().lighting.dayState != gamePanel.getEnvironmentManager().lighting.day) {
			gamePanel.playSoundEffect(14); // Sleep sound effect
			gamePanel.gameState = gamePanel.sleepState;
			gamePanel.getPlayer().life = gamePanel.getPlayer().maxLife;
			gamePanel.getPlayer().mana = gamePanel.getPlayer().maxMana;
			gamePanel.getPlayer().getSleepingImage(down1);
		}
		else {
			gamePanel.gameState = gamePanel.dialogueState;
			gamePanel.getUI().currentDialogue = "You can only sleep at night.";
		}
		
		return false; // false = don't remove from inventory
	}
}
