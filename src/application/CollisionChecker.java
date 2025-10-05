package application;

import entity.Entity;

public class CollisionChecker {

	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity) {
		// Get boundary of entity tile
		int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.getX());
		int entityRightWorldX = (int) (entity.worldX + entity.solidArea.getX() + entity.solidArea.getWidth());
		int entityTopWorldY = (int) (entity.worldY + entity.solidArea.getY());
		int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.getY() + entity.solidArea.getHeight());
		
		int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
		int entityRightCol = entityRightWorldX/gamePanel.tileSize;
		int entityTopRow = entityTopWorldY/gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;
		
		// Predict player position and check for collision on 2 tiles infront of the player
		int tileNum1, tileNum2;
		switch(entity.direction) {
			case "up": 
				entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[entityRightCol][entityTopRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[entityLeftCol][entityBottomRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[entityRightCol][entityBottomRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[entityLeftCol][entityBottomRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
			case "right": 
				entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[entityRightCol][entityTopRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[entityRightCol][entityBottomRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
		}
	}
	
}
