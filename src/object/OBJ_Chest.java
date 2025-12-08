package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Chest extends Entity {
	
	Entity loot;
	boolean isOpen = false;
	
	public OBJ_Chest(GamePanel gamePanel, Entity loot) {
		super(gamePanel);
		this.loot = loot;
		
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
	
	public void interact() {
		gamePanel.gameState = gamePanel.dialogueState;
		
		if (!isOpen) {
			if (gamePanel.getPlayer().inventory.size() >= gamePanel.getPlayer().maxInventorySize) {
				gamePanel.getUI().currentDialogue = "Your inventory is full. You can't open the chest.";
				return;
			}
			
			gamePanel.playSoundEffect(3); // Unlock sound
			gamePanel.getUI().currentDialogue = "You opened the chest and found a " + loot.name + " !";
			gamePanel.getPlayer().inventory.add(loot);
			down1 = image2;
			isOpen = true;
		}
		else {
			gamePanel.getUI().currentDialogue = "The chest is empty.";
		}
	}
}
