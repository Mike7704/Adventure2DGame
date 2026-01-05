package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import tile_interactive.InteractiveTile;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {

	private KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	private int standCounter = 0;
	
	public boolean attackCanceled = false;
	public boolean lightUpdated = false;
	
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
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	// Initial player start position
	public void setDefaultValues() {
		name = "Player";
		type = type_player;
		setDefaultPositions();
		defaultSpeed = 4;
		speed= defaultSpeed;
		
		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 1; // Damage
		dexterity = 1; // Defense
		exp = 0;
		nextLevelExp = 5;
		coin = 200;
		currentWeapon = new OBJ_Sword_Normal(gamePanel);
		currentShield = new OBJ_Shield_Wood(gamePanel);
		projectile = new OBJ_Fireball(gamePanel);
		attack = getAttack(); // Calculate attack value based on weapon and strength
		defense = getDefense(); // Calculate defense value based on shield and dexterity
	}
	
	public void setDefaultPositions() {
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		direction = "down";
	}
	
	public void restoreLifeAndMana() {
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}
	
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		
		return strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return dexterity * currentShield.defenseValue;
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
	
	public void getSleepingImage(Image sleepImage) {
		up1 = sleepImage;
		up2 = sleepImage;
		down1 = sleepImage;
		down2 = sleepImage;
		left1 = sleepImage;
		left2 = sleepImage;
		right1 = sleepImage;
		right2 = sleepImage;
	}
	
	public void getPlayerAttackImage() {
		if (currentWeapon.type == type_sword) {
			attackUp1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_up_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackUp2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_up_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackDown1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_down_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackDown2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_down_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackLeft1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_left_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
			attackLeft2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_left_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
			attackRight1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_right_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
			attackRight2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_attack_right_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		}
		else if (currentWeapon.type == type_axe) {
			attackUp1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_up_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackUp2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_up_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackDown1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_down_1.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackDown2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_down_2.png"), gamePanel.tileSize, gamePanel.tileSize*2, true, false);
			attackLeft1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_left_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
			attackLeft2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_left_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
			attackRight1 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_right_1.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
			attackRight2 = new Image(getClass().getResourceAsStream("/Player/Attacking sprites/boy_axe_right_2.png"), gamePanel.tileSize*2, gamePanel.tileSize, true, false);
		}
	}
	
	public void update() {
		
		if (attacking) {
			attacking();
		}
		
		// Move player
		else if (keyHandler.isKeyPressed() || keyHandler.enterPressed) {
			if (keyHandler.upPressed) {
				direction = "up";
			}
			else if (keyHandler.downPressed) {
				direction = "down";
			}
			else if (keyHandler.leftPressed) {
				direction = "left";
			}
			else if (keyHandler.rightPressed) {
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
			
			// Check interactive tile collision
			gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getInteractiveTile());
			
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
			
			// Player attack
			if (keyHandler.enterPressed && !attackCanceled) {
				gamePanel.playSoundEffect(7); // Sword sound
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
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
		
		// Shoot projectile
		if (gamePanel.getKeyHandler().shootKeyPressed && !projectile.alive && shootCooldownCounter == 30 && mana >= projectile.useCost) {
			projectile.set(worldX, worldY, direction, true, this);
			
			mana -= projectile.useCost;
			
			for (int i = 0; i < gamePanel.getProjectiles()[1].length; i++) {
				if (gamePanel.getProjectiles()[gamePanel.currentMap][i] == null) {
					gamePanel.getProjectiles()[gamePanel.currentMap][i] = projectile;
					break;
				}
			}
			
			shootCooldownCounter = 0;
			
			gamePanel.playSoundEffect(10);
		}
		
		// Shoot cooldown
		if (shootCooldownCounter < 30) {
			shootCooldownCounter++;
		}
		
		// Invincibility countdown
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if (life > maxLife) {
			life = maxLife;
		}
		
		if (mana > maxMana) {
			mana = maxMana;
		}
		
		if (life <= 0) {
			gamePanel.stopMusic();
			gamePanel.getUI().commandNum = -1; // Prevent accidentally selecting an option
			gamePanel.gameState = gamePanel.gameOverState;
			gamePanel.playSoundEffect(12); // Game over sound
			
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
			damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
			
			// Check interactive tile collision
			int interactiveTileIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getInteractiveTile());
			damageInteractiveTile(interactiveTileIndex);
			
			// Check projectile collision
			int projectileIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getProjectiles());
			damageProjectile(projectileIndex);
			
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
			Entity object = gamePanel.getObject()[gamePanel.currentMap][index];
			
			// Pick up only items
			if (object.type == type_pickup) {
				object.use(this);
				gamePanel.getObject()[gamePanel.currentMap][index] = null;
			}
			// Obstacles
			else if (object.type == type_obstacle) {
				if (gamePanel.getKeyHandler().enterPressed) {
					attackCanceled = true;
					object.interact();
				}
			}
			// Inventory items
			else {
				if (canObtainItem(object)) {
					// Pick up item
					gamePanel.playSoundEffect(1); // Item pickup sound
					gamePanel.getUI().addMessage("Picked up " + object.name + "!");
					gamePanel.getObject()[gamePanel.currentMap][index] = null;
				}
				else {
					// Inventory full
					gamePanel.getUI().addMessage("Cannot pick up " + object.name + ". Inventory full!");
				}
			}
		}
	}
	
	public void interactNPC(int index) {
		
		if (gamePanel.getKeyHandler().enterPressed) {
			if (index != 999) {
				attackCanceled = true;
				gamePanel.gameState = gamePanel.dialogueState;
				gamePanel.getNPC()[gamePanel.currentMap][index].speak();
			}
		}
	}

	
	public void contactMonster(int index) {
		if (index != 999) {
			// Damage player
			if (!invincible && !gamePanel.getMonster()[gamePanel.currentMap][index].dying) {
				gamePanel.playSoundEffect(6); // Hurt sound
				int damage = gamePanel.getMonster()[gamePanel.currentMap][index].attack - defense;
				life -= damage;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int index, int attack, int knockBackPower) {
		if (index != 999) {
			Entity monster = gamePanel.getMonster()[gamePanel.currentMap][index];
			if (!monster.invincible) {
				// Knock back monster
				if (knockBackPower > 0) {
					knockBack(monster, knockBackPower);
				}
				// Damage monster
				gamePanel.playSoundEffect(5); // Damage monster sound
				int damage = attack - monster.defense;
				if (damage < 0) {
					damage = 0;
				}
				monster.life -= damage;
				monster.invincible = true;
				monster.damageReaction();
				gamePanel.getUI().addMessage(damage + " damage!");
				
				// Check if monster is dead
				if (monster.life <= 0) {
					monster.dying = true;
					gamePanel.getUI().addMessage("Killed the " + monster.name + "!");
					gamePanel.getUI().addMessage("+" + monster.exp + " Exp");
					exp += monster.exp;
					checkLevelUp();
				}
			}
		}
	}
	
	private void knockBack(Entity entity, int knockBackPower) {
		entity.direction = direction;
		entity.speed += knockBackPower;
		entity.knockBack = true;
	}
	
	private void damageInteractiveTile(int index) {
		if (index != 999) {
			InteractiveTile interactiveTile = gamePanel.getInteractiveTile()[gamePanel.currentMap][index];
			if (interactiveTile.destructible && interactiveTile.isCorrectItem(this) && !interactiveTile.invincible) {
				// Hit tile
				interactiveTile.playSoundEffect();
				interactiveTile.life--;
				interactiveTile.invincible = true; // Cool down
				
				generateParticle(interactiveTile, interactiveTile);
				
				if (interactiveTile.life == 0) {
					gamePanel.getInteractiveTile()[gamePanel.currentMap][index] = interactiveTile.getDestroyedForm();
				}
			}
		}
	}
	
	private void damageProjectile(int index) {
		if (index != 999) {
			Entity projectile = gamePanel.getProjectiles()[gamePanel.currentMap][index];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}
	
	private void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp * 2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			life = maxLife;
			
			gamePanel.playSoundEffect(8); // Level up sound
			gamePanel.gameState = gamePanel.dialogueState;
			gamePanel.getUI().currentDialogue = "Leveled up to " + level + "!";
		}
	}
	
	public void selectItem() {
		// Get the selected item index from the UI
		int itemIndex = gamePanel.getUI().getItemIndexOnSlot(gamePanel.getUI().playerSlotCol, gamePanel.getUI().playerSlotRow);
		
		if (itemIndex < inventory.size()) {
			// Equip or use the selected item
			Entity selectedItem = inventory.get(itemIndex);
			
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			else if (selectedItem.type == type_sheild) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			else if (selectedItem.type == type_consumable) {
				if (selectedItem.use(this)) {
					if (selectedItem.stackAmount > 1) {
						selectedItem.stackAmount--;
					}
					else {
						inventory.remove(itemIndex);
					}
				}
			}
			else if (selectedItem.type == type_light) {
				if (currentLightSource == selectedItem) {
					// Turn off light
					currentLightSource = null;
				}
				else {
					// Equip new light
					currentLightSource = selectedItem;
				}
				lightUpdated = true;
			}	
		}
	}
	
	// Get item index by name
	public int searchItemInInventory(String itemName) {
		int itemIndex = 999;
		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	
	// Check space in our inventory
	public boolean canObtainItem(Entity item) {
		boolean canObtain = false;
		
		// Check stackable items
		if (item.stackable) {
			int index = searchItemInInventory(item.name);
			
			if (index != 999) {
				inventory.get(index).stackAmount++;
				canObtain = true;				
			}
			else {
				// New item
				if (inventory.size() != maxInventorySize) {
					inventory.add(item);
					canObtain = true;
				}
			}			
		}
		else {
			if (inventory.size() != maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
		}
		
		return canObtain;
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
