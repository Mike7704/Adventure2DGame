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
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You used the " + name + " and opened the door.";
		
		dialogues[1][0] = "You can't use this right now.";
	}
	
	public boolean use(Entity entity) {	
		int objectIndex = getDetected(entity, gamePanel.getObject(), "Door");
		
		if (objectIndex != 999) {
			startDialogue(this, 0);
			gamePanel.playSoundEffect(3); // Unlock sound
			gamePanel.getObject()[gamePanel.currentMap][objectIndex] = null;
			return true;
		}
		else {
			startDialogue(this, 1);
			return false;
		}
	}
}
