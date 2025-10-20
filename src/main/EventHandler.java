package main;

import entity.Player;
import javafx.scene.shape.Rectangle;

public class EventHandler {

	GamePanel gamePanel;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;
	
	public EventHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		// Small rectangle for player to touch to trigger event
		eventRect = new Rectangle(23, 23, 2, 2);
		eventRectDefaultX = (int) eventRect.getX();
		eventRectDefaultY = (int) eventRect.getY();
	}
	
	// Check for events
	public void checkEvent() {
		// Teleport
		if (hit(23, 7, "any")) {
			teleport(gamePanel.dialogueState);
		}
		
		// Damage pit
		if (hit(27, 16, "right")) {
			damagePit(gamePanel.dialogueState);
		}
		
		// Healing pool
		if (hit(23, 12, "up")) {
			healingPool(gamePanel.dialogueState);
		}
	}
	
	// Check if player hits event rectangle
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		
		boolean hit = false;
		Player player = gamePanel.getPlayer();
		
		// Set player solid area position
		player.solidArea.setX(player.worldX + player.solidArea.getX());
		player.solidArea.setY(player.worldY + player.solidArea.getY());
		
		// Set event rectangle position
		eventRect.setX(eventCol * gamePanel.tileSize + eventRect.getX());
		eventRect.setY(eventRow * gamePanel.tileSize + eventRect.getY());
		
		// Check intersection and player direction
		if (player.solidArea.getBoundsInParent().intersects(eventRect.getBoundsInParent())) {
			if (reqDirection.equals(player.direction) || reqDirection.equals("any")) {
				hit = true;
			}
		}
		
		// Reset player solid area position
		player.solidArea.setX(player.solidAreaDefaultX);
		player.solidArea.setY(player.solidAreaDefaultY);
		eventRect.setX(eventRectDefaultX);
		eventRect.setY(eventRectDefaultY);
		
		return hit;
	}
	
	// Event: Teleport
	public void teleport(int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.getUI().currentDialogue = "Teleport!";
		gamePanel.getPlayer().worldX = gamePanel.tileSize * 37;
		gamePanel.getPlayer().worldY = gamePanel.tileSize * 10;
	}
	
	// Event: Damage pit
	public void damagePit(int gameState) {
		gamePanel.gameState = gameState;
		gamePanel.getUI().currentDialogue = "You fall into a pit!";
		gamePanel.getPlayer().life -= 1;
	}
	
	// Event: Healing pool
	public void healingPool(int gameState) {
		if (gamePanel.getKeyHandler().enterPressed) {
			gamePanel.gameState = gameState;
			gamePanel.getUI().currentDialogue = "You drink the water.\nYour life is fully restored!";
			gamePanel.getPlayer().life = gamePanel.getPlayer().maxLife;
		}
	}
	
}
