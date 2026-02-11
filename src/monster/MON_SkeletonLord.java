package monster;

import java.util.Random;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity {

	public static final String monName = "Skeleton Lord";
	private int size = gamePanel.tileSize*5; // 240
	
	public MON_SkeletonLord(GamePanel gamePanel) {
		super(gamePanel);
		
		name = monName;
		type = type_monster;
		boss = true;
		defaultSpeed = 1;
		speed= defaultSpeed;
		maxLife = 50;
		life = maxLife;
		attack = 10;
		defense = 2;
		knockBackPower = 5;
		exp = 50;
		attackMotion1Duration = 25;
		attackMotion2Duration = 50;

		solidArea = new Rectangle(48, 48, size - 48 * 2, size - 48);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		attackArea.setWidth(170);
		attackArea.setHeight(170);
		
		getMonsterImage();
		getAttackImage();
	}
		
	// NPC sprite images
	private void getMonsterImage() {
		if (!inRage) {
			up1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_up_1.png"), size, size, true, false);
			up2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_up_2.png"), size, size, true, false);
			down1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_down_1.png"), size, size, true, false);
			down2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_down_2.png"), size, size, true, false);
			left1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_left_1.png"), size, size, true, false);
			left2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_left_2.png"), size, size, true, false);
			right1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_right_1.png"), size, size, true, false);
			right2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_right_2.png"), size, size, true, false);
		}
		else {
			up1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_up_1.png"), size, size, true, false);
			up2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_up_2.png"), size, size, true, false);
			down1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_down_1.png"), size, size, true, false);
			down2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_down_2.png"), size, size, true, false);
			left1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_left_1.png"), size, size, true, false);
			left2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_left_2.png"), size, size, true, false);
			right1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_right_1.png"), size, size, true, false);
			right2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_right_2.png"), size, size, true, false);
		}
	}
	
	private void getAttackImage() {
		if (!inRage) {
			attackUp1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_up_1.png"), size, size*2, true, false);
			attackUp2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_up_2.png"), size, size*2, true, false);
			attackDown1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_down_1.png"), size, size*2, true, false);
			attackDown2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_down_2.png"), size, size*2, true, false);
			attackLeft1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_left_1.png"), size*2, size, true, false);
			attackLeft2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_left_2.png"), size*2, size, true, false);
			attackRight1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_right_1.png"), size*2, size, true, false);
			attackRight2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_attack_right_2.png"), size*2, size, true, false);
		}
		else {
			attackUp1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_up_1.png"), size, size*2, true, false);
			attackUp2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_up_2.png"), size, size*2, true, false);
			attackDown1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_down_1.png"), size, size*2, true, false);
			attackDown2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_down_2.png"), size, size*2, true, false);
			attackLeft1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_left_1.png"), size*2, size, true, false);
			attackLeft2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_left_2.png"), size*2, size, true, false);
			attackRight1 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_right_1.png"), size*2, size, true, false);
			attackRight2 = new Image(getClass().getResourceAsStream("/Monster/skeletonlord_phase2_attack_right_2.png"), size*2, size, true, false);
		}
	}
	
	public void setAction() {
		
		if (!inRage && life < maxLife / 2) {
			inRage = true;
			getMonsterImage();
			getAttackImage();
			defaultSpeed++;
			speed = defaultSpeed;
			attack++;
		}
		
		if (getTileDistance(gamePanel.getPlayer()) < 10) {
			moveTowardPlayer(60);
		}
		else {
			// Random movement
			getRandomDirection(120);
		}
		
		// Check if it is time to attack
		if (!attacking) {
			checkAttack(60, gamePanel.tileSize*7, gamePanel.tileSize*5);
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
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
