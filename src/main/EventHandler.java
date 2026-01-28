package main;

import entity.Entity;
import entity.Player;

public class EventHandler {

	private GamePanel gamePanel;
	private EventRect eventRect[][][];
	private Entity eventEntity;
	
	// Track previous event position
	public int previousEventX, previousEventY;
	public int tempMap, tempCol, tempRow;
	private boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		eventEntity = new Entity(gamePanel);
		
		// Event rectangles across the world
		eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while (map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
			eventRect[map][col][row] = new EventRect(23, 23, 2, 2);
			eventRect[map][col][row].eventRectDefaultX = (int) eventRect[map][col][row].getX();
			eventRect[map][col][row].eventRectDefaultY = (int) eventRect[map][col][row].getY();
			col++;
			if (col == gamePanel.maxWorldCol) {
				col = 0;
				row++;
				
				if (row == gamePanel.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}		
		
		setDialogue();
	}
	
	public void setDialogue() {
		eventEntity.dialogues[0][0] = "You fall into a pit!";

		eventEntity.dialogues[1][0] = "You drink the water.\nYour life and mana are fully restored!\nThe game has been saved.";
	}
	
	// Check for events
	public void checkEvent() {
		// Check if player is far enough from last event
		int xDistance = Math.abs(gamePanel.getPlayer().worldX - previousEventX);
		int yDistance = Math.abs(gamePanel.getPlayer().worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if (distance > gamePanel.tileSize) {
			canTouchEvent = true;
		}
		
		if (canTouchEvent) {
			// Damage pit
			if (hit(0, 27, 16, "right")) {
				damagePit(gamePanel.dialogueState);
			}
			
			// Healing pool
			else if (hit(0, 23, 12, "up")) {
				healingPool(gamePanel.dialogueState);
			}
			
			// Hut enter teleport
			else if (hit(0, 10, 39, "any")) {
				teleport(1, 12, 13);
			}
			
			// Hut exit teleport
			else if (hit(1, 12, 13, "any")) {
				teleport(0, 10, 39);
			}
			
			// Hut merchant speak
			else if (hit(1, 12, 9, "up")) {
				speak(gamePanel.getNPC()[1][0]);
			}
		}
	}
	
	// Check if player hits event rectangle
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		if (map != gamePanel.currentMap) {
			return false;
		}
		
		boolean hit = false;
		Player player = gamePanel.getPlayer();
		
		// Set player solid area position
		player.solidArea.setX(player.worldX + player.solidArea.getX());
		player.solidArea.setY(player.worldY + player.solidArea.getY());
		
		// Set event rectangle position
		eventRect[map][col][row].setX(col * gamePanel.tileSize + eventRect[map][col][row].getX());
		eventRect[map][col][row].setY(row * gamePanel.tileSize + eventRect[map][col][row].getY());
		
		// Check intersection and player direction
		if (player.solidArea.getBoundsInParent().intersects(eventRect[map][col][row].getBoundsInParent()) && !eventRect[map][col][row].eventDone) {
			if (reqDirection.equals(player.direction) || reqDirection.equals("any")) {
				hit = true;
				
				previousEventX = player.worldX;
				previousEventY = player.worldY;
			}
		}
		
		// Reset player solid area position
		player.solidArea.setX(player.solidAreaDefaultX);
		player.solidArea.setY(player.solidAreaDefaultY);
		eventRect[map][col][row].setX(eventRect[map][col][row].eventRectDefaultX);
		eventRect[map][col][row].setY(eventRect[map][col][row].eventRectDefaultY);
		
		return hit;
	}
	
	// Event: Teleport
	public void teleport(int map, int col, int row) {
		gamePanel.gameState = gamePanel.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gamePanel.playSoundEffect(13); // Stairs sound effect
	}
	
	// Event: Damage pit
	public void damagePit(int gameState) {
		gamePanel.gameState = gameState;
		eventEntity.startDialogue(eventEntity, 0);
		gamePanel.getPlayer().life -= 1;
		canTouchEvent = false;
	}
	
	// Event: Healing pool
	public void healingPool(int gameState) {
		if (gamePanel.getKeyHandler().enterPressed) {
			gamePanel.gameState = gameState;
			eventEntity.startDialogue(eventEntity, 1);
			gamePanel.getPlayer().life = gamePanel.getPlayer().maxLife;
			gamePanel.getPlayer().mana = gamePanel.getPlayer().maxMana;
			gamePanel.getPlayer().attackCanceled = true;
			
			// Respawn monsters
			gamePanel.getAssetSetter().setMonster();
			
			// Save the game
			gamePanel.getSaveLoad().save();
		}
	}
	
	// Event: Speak to NPC
	public void speak(Entity entity) {
		if (gamePanel.getKeyHandler().enterPressed) {
			gamePanel.gameState = gamePanel.dialogueState;
			gamePanel.getPlayer().attackCanceled = true;
			entity.speak();
		}
	}
}
