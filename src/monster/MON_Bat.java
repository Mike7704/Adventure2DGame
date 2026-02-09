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

public class MON_Bat extends Entity {
	public MON_Bat(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Bat";
		type = type_monster;
		defaultSpeed = 4;
		speed= defaultSpeed;
		maxLife = 3;
		life = maxLife;
		attack = 4;
		defense = 0;
		exp = 4;
		
		solidArea = new Rectangle(3, 15, 42, 21);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		getMonsterImage();
	}
		
	// NPC sprite images
	private void getMonsterImage() {
		up1 = new Image(getClass().getResourceAsStream("/Monster/bat_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/Monster/bat_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/Monster/bat_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/Monster/bat_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/Monster/bat_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/Monster/bat_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/Monster/bat_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/Monster/bat_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
		
	public void setAction() {
						
		if (onPath) {
			// Check if player is out of range
			//checkStopChasing(gamePanel.getPlayer(), 15, 100);

			// Follow a path (attack the player)
			//searchPath(getGoalCol(gamePanel.getPlayer()), getGoalRow(gamePanel.getPlayer()));
			
			// Shoot projectile
			//checkShootProjectile(200, 30);
		}
		else {
			// Check if player is in range
			//checkStartChasing(gamePanel.getPlayer(), 5, 100);
			
			// Random movement
			getRandomDirection(60);
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		//onPath = true;
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