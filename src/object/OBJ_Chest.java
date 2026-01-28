package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Chest extends Entity {
		
	public OBJ_Chest(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Chest";
		type = type_obstacle;
		image = new Image(getClass().getResourceAsStream("/Object/chest.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image2 = new Image(getClass().getResourceAsStream("/Object/chest_opened.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = image;
		collision = true;
		
		solidArea.setX(4);
		solidArea.setY(16);
		solidArea.setWidth(40);
		solidArea.setHeight(32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Your inventory is full. You can't open the chest.";
		
		dialogues[1][0] = "You opened the chest and found a " + loot.name + " !";

		dialogues[2][0] = "The chest is empty.";
	}
	
	public void setLoot(Entity loot) {
		this.loot = loot;
		setDialogue();
	}
	
	public void interact() {
		gamePanel.gameState = gamePanel.dialogueState;
		
		if (!isOpen) {
			if (!gamePanel.getPlayer().canObtainItem(loot)) {
				startDialogue(this, 0);
				return;
			}
			
			gamePanel.playSoundEffect(3); // Unlock sound
			startDialogue(this, 1);
			down1 = image2;
			isOpen = true;
		}
		else {
			startDialogue(this, 2);
		}
	}
}
