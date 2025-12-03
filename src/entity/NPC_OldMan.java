package entity;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Old Man";
		type = type_npc;
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		getNPCImage();
		setDialogue();
	}
	
	// NPC sprite images
	public void getNPCImage() {
		up1 = new Image(getClass().getResourceAsStream("/NPC/oldman_up_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/NPC/oldman_up_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/NPC/oldman_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/NPC/oldman_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/NPC/oldman_left_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/NPC/oldman_left_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/NPC/oldman_right_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/NPC/oldman_right_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}

	public void setDialogue() {
		dialogues[0] = "Hello, young adventurer!";
		dialogues[1] = "So you've come to this island to find the treasure?";
		dialogues[2] = "I used to be a great adventurer like you.";
		dialogues[3] = "But I made a mistake that cost me everything.";
		dialogues[4] = "I was too greedy and trusted the wrong people.";
		dialogues[5] = "Now I live here, alone and regretful.";
		dialogues[6] = "Take this map. It will help you on your journey.";
		dialogues[7] = "Remember, not all treasure is silver and gold.";
		dialogues[8] = "Good luck!";
	}
	
	// NPC behavior
	public void setAction() {
		
		if (onPath) {
			// Follow a path (walks to his house)
			int goalCol = 12;
			int goalRow = 9;
			
			searchPath(goalCol, goalRow);
		}
		else {
			// Random movement
			actionLockCounter++;
			
			if (actionLockCounter < 120) {
				return;
			}
			
			actionLockCounter = 0;
			
			Random random = new Random();
			int i = random.nextInt(100)+1; // 1-100
			if (i <= 25) {
				direction = "up";
			}
			else if (i > 25 && i <= 50) {
				direction = "down";
			}
			else if (i > 50 && i <= 75) {
				direction = "left";
			}
			else if (i > 75 && i <= 100) {
				direction = "right";
			}
		}
	}
	
	public void speak() {
		super.speak();
		
		onPath = true;
	}
}
