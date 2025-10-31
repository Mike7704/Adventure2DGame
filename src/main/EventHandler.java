package main;

import entity.Player;

public class EventHandler {

	private GamePanel gamePanel;
	private EventRect eventRect[][];
	
	// Track previous event position
	private int previousEventX, previousEventY;
	private boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		// Event rectangles across the world
		eventRect = new EventRect[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
			eventRect[col][row] = new EventRect(23, 23, 2, 2);
			eventRect[col][row].eventRectDefaultX = (int) eventRect[col][row].getX();
			eventRect[col][row].eventRectDefaultY = (int) eventRect[col][row].getY();
			col++;
			if (col == gamePanel.maxWorldCol) {
				col = 0;
				row++;
			}
		}		
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
			// Teleport
			if (hit(23, 7, "any")) {
				teleport(27, 7, gamePanel.dialogueState);
			}
			
			// Damage pit
			if (hit(27, 16, "right")) {
				damagePit(27, 16, gamePanel.dialogueState);
			}
			
			// Healing pool
			if (hit(23, 12, "up")) {
				healingPool(23, 12, gamePanel.dialogueState);
			}
		}
	}
	
	// Check if player hits event rectangle
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		Player player = gamePanel.getPlayer();
		
		// Set player solid area position
		player.solidArea.setX(player.worldX + player.solidArea.getX());
		player.solidArea.setY(player.worldY + player.solidArea.getY());
		
		// Set event rectangle position
		eventRect[col][row].setX(col * gamePanel.tileSize + eventRect[col][row].getX());
		eventRect[col][row].setY(row * gamePanel.tileSize + eventRect[col][row].getY());
		
		// Check intersection and player direction
		if (player.solidArea.getBoundsInParent().intersects(eventRect[col][row].getBoundsInParent()) && !eventRect[col][row].eventDone) {
			if (reqDirection.equals(player.direction) || reqDirection.equals("any")) {
				hit = true;
				
				previousEventX = player.worldX;
				previousEventY = player.worldY;
			}
		}
		
		// Reset player solid area position
		player.solidArea.setX(player.solidAreaDefaultX);
		player.solidArea.setY(player.solidAreaDefaultY);
		eventRect[col][row].setX(eventRect[col][row].eventRectDefaultX);
		eventRect[col][row].setY(eventRect[col][row].eventRectDefaultY);
		
		return hit;
	}
	
	// Event: Teleport
	public void teleport(int col, int row, int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.getUI().currentDialogue = "Teleport!";
		gamePanel.getPlayer().worldX = gamePanel.tileSize * 37;
		gamePanel.getPlayer().worldY = gamePanel.tileSize * 10;
	}
	
	// Event: Damage pit
	public void damagePit(int col, int row, int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.getUI().currentDialogue = "You fall into a pit!";
		gamePanel.getPlayer().life -= 1;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	// Event: Healing pool
	public void healingPool(int col, int row, int gameState) {
		if (gamePanel.getKeyHandler().enterPressed) {
			gamePanel.gameState = gameState;
			gamePanel.getUI().currentDialogue = "You drink the water.\nYour life is fully restored!";
			gamePanel.getPlayer().life = gamePanel.getPlayer().maxLife;
			gamePanel.getPlayer().attackCanceled = true;
			
			// Respawn monsters
			gamePanel.getAssetSetter().setMonster();
		}
	}
	
}
