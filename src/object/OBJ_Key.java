package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Key extends Entity {

	public OBJ_Key(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Key";
		type = type_consumable;
		down1 = new Image(getClass().getResourceAsStream("/Object/key.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\nOpens a door.";
		price = 50;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		gamePanel.gameState = gamePanel.dialogueState;
		
		int objectIndex = getDetected(entity, gamePanel.getObject(), "Door");
		
		if (objectIndex != 999) {
			gamePanel.getUI().currentDialogue = "You used the " + name + " and opened the door.";
			gamePanel.playSoundEffect(3); // Unlock sound
			gamePanel.getObject()[gamePanel.currentMap][objectIndex] = null;
			return true;
		}
		else {
			gamePanel.getUI().currentDialogue = "You can't use this right now.";
			return false;
		}
	}
}
