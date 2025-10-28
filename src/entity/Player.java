package entity;

import main.GamePanel;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {

	private KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	private int standCounter = 0;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		
		super(gamePanel);
		this.keyHandler = keyHandler;
		
		// Player position is fixed to the centre of the screen
		screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
		screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
		
		// Player collision
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		attackArea = new Rectangle(0, 0, 36, 36);
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	// Initial player start position
	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed= 4;
		direction = "down";
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
	}
	
	// Player sprite images
	public void getPlayerImage() {
		up1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_up_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_up_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_left_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_left_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_right_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_right_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_up_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackUp2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_up_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackDown1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_down_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackDown2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_down_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
		attackLeft1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_left_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		attackLeft2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_left_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		attackRight1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_right_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		attackRight2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_right_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
	}
	
	public void update() {
		
		if (attacking) {
			attacking();
		}
		
		// Move player
		else if (keyHandler.isKeyPressed() || keyHandler.enterPressed) {
			if (keyHandler.isUpPressed()) {
				direction = "up";
			}
			else if (keyHandler.isDownPressed()) {
				direction = "down";
			}
			else if (keyHandler.isLeftPressed()) {
				direction = "left";
			}
			else if (keyHandler.isRightPressed()) {
				direction = "right";
			}
			
			// Check tile collision
			collisionOn = false;
			gamePanel.getCollisionChecker().checkTile(this);
			
			// Check object collision
			int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
			pickUpObject(objectIndex);
			
			// Check NPC collision
			int npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPC());
			interactNPC(npcIndex);
			
			// Check monster collision
			int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
			contactMonster(monsterIndex);
			
			// Check event collision
			gamePanel.getEventHandler().checkEvent();
			
			// Player can move if no collision
			if (!collisionOn && !keyHandler.enterPressed) {
				switch(direction) {
					case "up": 		worldY -= speed; break;
					case "down": 	worldY += speed; break;
					case "left": 	worldX -= speed; break;
					case "right": 	worldX += speed; break;
				}
			}
			
			keyHandler.enterPressed = false; // Prevents multiple dialogues from opening
			
			// Walk animation
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
			standCounter++;
			if (standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		
		// Invincibility countdown
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void attacking() {
		spriteCounter++;
		
		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		else if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// Save current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = (int) solidArea.getWidth();
			int solidAreaHeight = (int) solidArea.getHeight();
			
			// Adjust player's worldX/Y for attack area
			switch(direction) {
				case "up": 		worldY -= attackArea.getHeight(); break;
				case "down": 	worldY += attackArea.getHeight(); break;
				case "left": 	worldX -= attackArea.getWidth(); break;
				case "right": 	worldX += attackArea.getWidth(); break;
			}
			
			// Adjust solid area to attack area
			solidArea.setWidth(attackArea.getWidth());
			solidArea.setHeight(attackArea.getHeight());
			
			// Check monster collision with updated worldX, worldY and attack area
			int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
			damageMonster(monsterIndex);
			
			// Restore original worldX, worldY, and solidArea
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.setWidth(solidAreaWidth);
			solidArea.setHeight(solidAreaHeight);
		}
		else if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int index) {
		if (index != 999) {
			
		}
	}
	
	public void interactNPC(int index) {
		
		if (gamePanel.getKeyHandler().enterPressed) {
			if (index != 999) {
				gamePanel.gameState = gamePanel.dialogueState;
				gamePanel.getNPC()[index].speak();
			}
			else {
				// No NPC in contact, so no dialogue opened
				gamePanel.playSoundEffect(7); // Swing weapon sound
				attacking = true;
			}
		}
	}

	
	public void contactMonster(int index) {
		if (index != 999) {
			// Damage player
			if (!invincible) {
				gamePanel.playSoundEffect(6); // Hurt sound
				life--;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int index) {
		if (index != 999) {
			Entity monster = gamePanel.getMonster()[index];
			if (!monster.invincible) {
				// Damage monster
				gamePanel.playSoundEffect(5); // Damage monster sound
				monster.life--;
				monster.invincible = true;
				monster.damageReaction();
				
				// Check if monster is dead
				if (monster.life <= 0) {
					monster.dying = true;
				}
			}
		}
	}
	
	// Draw player at updated position and image
	public void draw(GraphicsContext gc) {
		Image image = null;
		int offsetScreenX = screenX;
		int offsetScreenY = screenY;		
		
		if (attacking) {
			switch(direction) {
				case "up": 		image = (spriteNum == 1 ? attackUp1 : attackUp2); offsetScreenY = screenY - gamePanel.tileSize;		break;
				case "down": 	image = (spriteNum == 1 ? attackDown1 : attackDown2); 	break;
				case "left": 	image = (spriteNum == 1 ? attackLeft1 : attackLeft2); offsetScreenX = screenX - gamePanel.tileSize; 	break;
				case "right": 	image = (spriteNum == 1 ? attackRight1 : attackRight2); break;
				default: 		break;
			}
		}
		else {
			switch(direction) {
				case "up": 		image = (spriteNum == 1 ? up1 : up2); 		break;
				case "down": 	image = (spriteNum == 1 ? down1 : down2); 	break;
				case "left": 	image = (spriteNum == 1 ? left1 : left2); 	break;
				case "right": 	image = (spriteNum == 1 ? right1 : right2); break;
				default: 		break;
			}
		}
		
		// Invincibility effect
		if (invincible) {
			gc.setGlobalAlpha(0.4);
		}
		
		gc.drawImage(image, offsetScreenX, offsetScreenY);
		
		gc.setGlobalAlpha(1.0);	// Reset alpha
	}
}
