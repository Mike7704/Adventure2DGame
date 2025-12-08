package entity;

import java.util.ArrayList;

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
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int shootCooldownCounter = 0;
	public boolean onPath = false;
	public boolean knockBack = false;
	public int knockBackCounter = 0;
	
	// CHARACTER STATUS
	public String name;
	public int defaultSpeed;
	public int speed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int ammo;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	// ITEM ATTRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 16;
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	public int price;
	public int knockBackPower = 0;
	
	// TYPE
	public int type;
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_sheild = 5;
	public final int type_consumable = 6;
	public final int type_pickup = 7;
	public final int type_obstacle = 8;
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setAction() {
		// Overridden in subclasses
	}
	
	public void damageReaction() {
		// Overridden in subclasses
	}
	
	public void damagePlayer(int attack) {
		// Contact with player
		if (!gamePanel.getPlayer().invincible) {
			// Damage the player
			gamePanel.playSoundEffect(6); // Hurt sound
			int damage = attack - gamePanel.getPlayer().defense;
			if (damage < 0) {
				damage = 0;
			}
			gamePanel.getPlayer().life -= damage;
			gamePanel.getPlayer().invincible = true;
		}
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
	
	public void interact() {
		// Overridden in subclasses
	}
	
	public boolean use(Entity entity) {
		// Overridden in subclasses
		return false;
	}
	
	public void checkDrop() {
		// Overridden in subclasses
	}
	
	public void dropItem(Entity droppedItem) {
		for (int i = 0; i < gamePanel.getObject()[1].length; i++) {
			// Entity is dead, replace it with a dropped item
			if (gamePanel.getObject()[gamePanel.currentMap][i] == null) {
				gamePanel.getObject()[gamePanel.currentMap][i] = droppedItem;
				gamePanel.getObject()[gamePanel.currentMap][i].worldX = worldX;
				gamePanel.getObject()[gamePanel.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	
	public int getDetected(Entity user, Entity[][] target, String targetName) {
		int index = 999;
		
		// Check the surrounding object
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		
		switch (user.direction) {
			case "up": nextWorldY = user.getTopY() - user.speed - 1; break;
			case "down": nextWorldY = user.getBottomY() + user.speed; break;
			case "left": nextWorldX = user.getLeftX() - user.speed; break;
			case "right": nextWorldX = user.getRightX() + user.speed + 1; break;
		}
		
		int col = nextWorldX / gamePanel.tileSize;
		int row = nextWorldY / gamePanel.tileSize;
		
		for (int i = 0; i < target[1].length; i++) {
			Entity object = target[gamePanel.currentMap][i];
			
			if (object != null) {
				// Check if we have found the target object
				if (object.getCol() == col && object.getRow() == row && object.name.equals(targetName)) {
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	public Color getParticleColor() {
		// Overridden in subclasses
		return null;
	}
	
	public int getParticleSize() {
		// Overridden in subclasses
		return 0;
	}
	
	public int getParticleSpeed() {
		// Overridden in subclasses
		return 0;
	}
	
	public int getParticleMaxLife() {
		// Overridden in subclasses
		return 0;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();		
		
		Particle p1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
		Particle p3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
		Particle p4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);
		
		gamePanel.getParticles().add(p1);
		gamePanel.getParticles().add(p2);
		gamePanel.getParticles().add(p3);
		gamePanel.getParticles().add(p4);
	}
	
	public void checkCollision() {
		// Check tile collision
		collisionOn = false;
		gamePanel.getCollisionChecker().checkTile(this);
		gamePanel.getCollisionChecker().checkObject(this, false);
		gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPC());
		gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
		gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getInteractiveTile());
		boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);
		
		if (this.type == type_monster && contactPlayer) {
			damagePlayer(attack);
		}
	}
	
	public int getLeftX() {
		return (int) (worldX + solidArea.getX());
	}
	
	public int getRightX() {
		return (int) (worldX + solidArea.getX() + solidArea.getWidth());
	}
	
	public int getTopY() {
		return (int) (worldY + solidArea.getY());
	}
	
	public int getBottomY() {
		return (int) (worldY + solidArea.getY() + solidArea.getHeight());
	}
	
	public int getCol() {
		return (int) ((worldX + solidArea.getX()) / gamePanel.tileSize);
	}
	
	public int getRow() {
		return (int) ((worldY + solidArea.getY()) / gamePanel.tileSize);
	}
	
	
	public void update() {
		
		if (knockBack) {
			checkCollision();
			
			if (collisionOn) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			else {
				switch(gamePanel.getPlayer().direction) {
					case "up": 		worldY -= speed; break;
					case "down": 	worldY += speed; break;
					case "left": 	worldX -= speed; break;
					case "right": 	worldX += speed; break;
					default: 		break;
				}
			}
			
			knockBackCounter++;
			if (knockBackCounter >= 10) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			
		}
		else {
			setAction();
			checkCollision();
			
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
		}
		
		spriteCounter++;
		if (spriteCounter > 24) {
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
		
		// Shoot cooldown
		if (shootCooldownCounter < 30) {
			shootCooldownCounter++;
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
			alive = false;
		}
	}
	
	public void searchPath(int goalCol, int goalRow) {
		// Current position
		int startCol = (int) ((worldX + solidArea.getX()) / gamePanel.tileSize);
		int startRow = (int) ((worldY + solidArea.getY()) / gamePanel.tileSize);
		
		gamePanel.getPathFinder().setNodes(startCol, startRow, goalCol, goalRow);
		
		// Check if a path has been found
		if (gamePanel.getPathFinder().search()) {
			// Next worldX and worldY
			int nextX = gamePanel.getPathFinder().pathList.get(0).col * gamePanel.tileSize;
			int nextY = gamePanel.getPathFinder().pathList.get(0).row * gamePanel.tileSize;
			
			// Entity solid area position
			int entLeftX = (int) (worldX + solidArea.getX());
			int entRightX = (int) (worldX + solidArea.getX() + solidArea.getWidth());
			int entTopY = (int) (worldY + solidArea.getY());
			int entBottomY = (int) (worldY + solidArea.getY() + solidArea.getHeight());
			
			if (entTopY > nextY && entLeftX >= nextX && entRightX < nextX + gamePanel.tileSize) {
				direction = "up";
			}
			else if (entTopY < nextY && entLeftX >= nextX && entRightX < nextX + gamePanel.tileSize) {
				direction = "down";
			}
			else if (entTopY >= nextY && entLeftX > nextX && entBottomY < nextY + gamePanel.tileSize) {
				direction = "left";
			}
			else if (entTopY >= nextY && entLeftX < nextX && entBottomY < nextY + gamePanel.tileSize) {
				direction = "right";
			}
			
			else if (entTopY > nextY && entLeftX > nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn) {
					direction = "left";
				}
			}
			else if (entTopY > nextY && entLeftX < nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn) {
					direction = "right";
				}
			}
			else if (entTopY < nextY && entLeftX > nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) {
					direction = "left";
				}
			}
			else if (entTopY < nextY && entLeftX < nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) {
					direction = "right";
				}
			}
			
			// Check if the goal has been reached
			int nextCol = gamePanel.getPathFinder().pathList.get(0).col;
			int nextRow = gamePanel.getPathFinder().pathList.get(0).row;
			
			if (nextCol == goalCol && nextRow == goalRow) {
				onPath = false;
			}
		}
	}
	
}
