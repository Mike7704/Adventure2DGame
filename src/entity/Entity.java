package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class Entity {
	
	protected GamePanel gamePanel;
	
		
	public Image up1, up2, down1, down2, left1, left2, right1, right2;
	public Image attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public Image image, image2, image3;
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	public int actionLockCounter = 0;
	protected String dialogues[] = new String[20];
	
	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	protected int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	
	// CHARACTER STATUS
	public String name;
	public int type; // 0 = player, 1 = NPC, 2 = monster
	public int speed;
	public int maxLife;
	public int life;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setAction() {
		// Overridden in subclasses
	}
	
	public void damageReaction() {
		// Overridden in subclasses
	}
	
	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gamePanel.getUI().currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gamePanel.getPlayer().direction) {
			case "up": 		direction = "down"; break;
			case "down": 	direction = "up"; break;
			case "left": 	direction = "right"; break;
			case "right": 	direction = "left"; break;
		}
	}
	
	public void update() {
		setAction();
		
		// Check tile collision
		collisionOn = false;
		gamePanel.getCollisionChecker().checkTile(this);
		gamePanel.getCollisionChecker().checkObject(this, false);
		gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPC());
		gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
		boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);
		
		if (this.type == 2 && contactPlayer) {
			// Contact with player
			if (!gamePanel.getPlayer().invincible) {
				// Damage the player
				gamePanel.playSoundEffect(6); // Hurt sound
				gamePanel.getPlayer().life -= 1;
				gamePanel.getPlayer().invincible = true;
			}
			
		}
		
		// If no collision, NPC can move
		if (!collisionOn) {
			switch(direction) {
				case "up": 		worldY -= speed; break;
				case "down": 	worldY += speed; break;
				case "left": 	worldX -= speed; break;
				case "right": 	worldX += speed; break;
			default: 		break;
			}
		}
		
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
		
		// Invincibility countdown
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(GraphicsContext gc) {
		Image image = null;
		int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
		int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;
		
		// Draw object only visible on screen around the player
		if (worldX + gamePanel.tileSize > gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX &&
			worldX - gamePanel.tileSize < gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX &&
			worldY + gamePanel.tileSize > gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY &&
			worldY - gamePanel.tileSize < gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY)
		{
			switch(direction) {
				case "up": 		image = (spriteNum == 1 ? up1 : up2); 		break;
				case "down": 	image = (spriteNum == 1 ? down1 : down2); 	break;
				case "left": 	image = (spriteNum == 1 ? left1 : left2); 	break;
				case "right": 	image = (spriteNum == 1 ? right1 : right2); break;
				default: 		break;
			}
			
			// Monster health bar displays when attacked
			if (type == 2 && hpBarOn) {
				double oneScale = (double)gamePanel.tileSize / maxLife;
				double hpBarValue = oneScale * life;
				
				gc.setFill(Color.DIMGRAY);
				gc.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);
				
				gc.setFill(Color.RED);
				gc.fillRect(screenX, screenY - 15, hpBarValue, 10);
				
				hpBarCounter++;
				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			// Invincibility effect
			if (invincible) {
				hpBarOn = true;
				hpBarCounter = 0;
				gc.setGlobalAlpha(0.4);
			}
			
			if (dying) {
				dyingAnimation(gc);
			}
			
			gc.drawImage(image, screenX, screenY);
			
			gc.setGlobalAlpha(1.0);	// Reset alpha
		}
	}
	
	private void dyingAnimation(GraphicsContext gc) {
		dyingCounter++;
		if (dyingCounter <= 40) {
			if (dyingCounter % 5 != 0) {
				gc.setGlobalAlpha(0);
			}
			else {
				gc.setGlobalAlpha(1);
			}
		}
		else {
			dying = false;
			alive = false;
		}
	}
	
}
