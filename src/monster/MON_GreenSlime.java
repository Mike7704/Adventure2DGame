package monster;

import java.util.Random;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class MON_GreenSlime extends Entity {
	
	public MON_GreenSlime(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Green Slime";
		type = 2;
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		solidArea = new Rectangle(3, 18, 42, 30);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		getMonsterImage();
	}
		
	// NPC sprite images
	private void getMonsterImage() {
		up1 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/Monster/redslime_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
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
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gamePanel.getPlayer().direction;
	}

}
