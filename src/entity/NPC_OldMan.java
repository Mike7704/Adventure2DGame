package entity;

import java.util.Random;

import javafx.scene.image.Image;
import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gamePanel) {
		super(gamePanel);
		
		direction = "down";
		speed = 1;
		
		getNPCImage();
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

	// NPC behavior
	public void setAction() {
		
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
