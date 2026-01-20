package monster;

import java.util.Random;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_Orc extends Entity {
		
	public MON_Orc(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Orc";
		type = type_monster;
		defaultSpeed = 1;
		speed= defaultSpeed;
		maxLife = 10;
		life = maxLife;
		attack = 8;
		defense = 2;
		knockBackPower = 5;
		exp = 5;
		attackMotion1Duration = 40;
		attackMotion2Duration = 85;
				
		solidArea = new Rectangle(4, 4, 40, 44);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		attackArea.setWidth(48);
		attackArea.setHeight(48);
		
		getMonsterImage();
		getAttackImage();
	}
		
	// NPC sprite images
	private void getMonsterImage() {
		up1 = new Image(getClass().getResourceAsStream("/Monster/orc_up_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/Monster/orc_up_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/Monster/orc_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/Monster/orc_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/Monster/orc_left_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/Monster/orc_left_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/Monster/orc_right_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/Monster/orc_right_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	private void getAttackImage() {
		attackUp1 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_up_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackUp2 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_up_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackDown1 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_down_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackDown2 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_down_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackLeft1 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_left_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		attackLeft2 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_left_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		attackRight1 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_right_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		attackRight2 = new Image(getClass().getResourceAsStream("/Monster/orc_attack_right_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
	}
	
	public void setAction() {
						
		if (onPath) {
			// Check if player is out of range
			checkStopChasing(gamePanel.getPlayer(), 15, 100);

			// Follow a path (attack the player)
			searchPath(getGoalCol(gamePanel.getPlayer()), getGoalRow(gamePanel.getPlayer()));
		}
		else {
			// Check if player is in range
			checkStartChasing(gamePanel.getPlayer(), 5, 100);
			
			// Random movement
			getRandomDirection();
		}
		
		// Check if it is time to attack
		if (!attacking) {
			checkAttack(30, gamePanel.tileSize*4, gamePanel.tileSize);
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
