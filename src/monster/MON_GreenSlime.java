package monster;

import java.util.Random;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
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
	
	public void update() {
		super.update();
		
		// Check distance between player
		int xDistance = Math.abs(worldX - gamePanel.getPlayer().worldX);
		int yDistance = Math.abs(worldY - gamePanel.getPlayer().worldY);
		int tileDistance = (xDistance + yDistance) / gamePanel.tileSize;
		
		if (!onPath && tileDistance < 5) {
			int i = new Random().nextInt(100)+1;
			if (i > 50) {
				onPath = true;
			}
		}
		else if (onPath && tileDistance > 10) {
			// Player out of range
			onPath = false;
		}
	}
	
	public void setAction() {
		
		Random random = new Random();
		
		if (onPath) {
			// Follow a path (attack the player)
			int goalCol = (int)(gamePanel.getPlayer().worldX + gamePanel.getPlayer().solidArea.getX()) / gamePanel.tileSize;
			int goalRow = (int)(gamePanel.getPlayer().worldY + gamePanel.getPlayer().solidArea.getY()) / gamePanel.tileSize;
			
			searchPath(goalCol, goalRow);
			
			// Shoot projectile
			int p = random.nextInt(100)+1;
			if (p > 99 && !projectile.alive && shootCooldownCounter == 30) {
				projectile.set(worldX, worldY, direction, true, this);
				gamePanel.getProjectiles().add(projectile);
				shootCooldownCounter = 0;
			}
		}
		else {
			// Random movement
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
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
	}
	
	public void checkDrop() {
		// Called when monster dies
		int i = new Random().nextInt(100)+1;
		
		if (i <= 50) {
			dropItem(new OBJ_Coin_Bronze(gamePanel));
		}
		else if (i <= 75) {
			dropItem(new OBJ_Heart(gamePanel));
		}
		else {
			dropItem(new OBJ_ManaCrystal(gamePanel));
		}
	}

}
