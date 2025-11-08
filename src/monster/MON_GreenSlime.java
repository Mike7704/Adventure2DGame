package monster;

import java.util.Random;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity {
	
	public MON_GreenSlime(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Green Slime";
		type = type_monster;
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(gamePanel);
		
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
		
		Random random = new Random();
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) {	
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
			
			actionLockCounter = 0;
		}
		
		// Shoot projectile
		int p = random.nextInt(100)+1;
		if (p > 99 && !projectile.alive && shootCooldownCounter == 30) {
			projectile.set(worldX, worldY, direction, true, this);
			gamePanel.getProjectiles().add(projectile);
			shootCooldownCounter = 0;
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gamePanel.getPlayer().direction;
	}

}
