package main;

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
		
		int tileNum1, tileNum2;
		
		String direction = entity.direction;
		if (entity.knockBack) {
			// Temp direction during knockback
			direction = entity.knockBackDirection;
		}
		
		// Predict player position and check for collision on 2 tiles infront of the player
		switch(direction) {
			case "up": 
				entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityRightCol][entityTopRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityLeftCol][entityBottomRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityLeftCol][entityBottomRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
			case "right": 
				entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityRightCol][entityTopRow];
				tileNum2 = gamePanel.getTileManager().mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
				// Check if a tile is solid
				if (gamePanel.getTileManager().tile[tileNum1].collision || gamePanel.getTileManager().tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
				break;
		}
	}
	
	// Check object collision and return index of the object collided with
	public int checkObject(Entity entity, boolean player) {
		int index = 999;

		for (int i = 0; i < gamePanel.getObject()[1].length; i++) {
			if (gamePanel.getObject()[gamePanel.currentMap][i] != null) {
				// Get entity's solid area position
				entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
				entity.solidArea.setY(entity.worldY + entity.solidArea.getY());
				
				// Get object's solid area position
				gamePanel.getObject()[gamePanel.currentMap][i].solidArea.setX(
						gamePanel.getObject()[gamePanel.currentMap][i].worldX + gamePanel.getObject()[gamePanel.currentMap][i].solidArea.getX());
				gamePanel.getObject()[gamePanel.currentMap][i].solidArea.setY(
						gamePanel.getObject()[gamePanel.currentMap][i].worldY + gamePanel.getObject()[gamePanel.currentMap][i].solidArea.getY());
				
				// Predict entity position
				switch (entity.direction) {
					case "up":
						entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
						break;
					case "down":
						entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
						break;
					case "left":
						entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
						break;
					case "right":
						entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
						break;
				}
				
				if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getObject()[gamePanel.currentMap][i].solidArea.getBoundsInParent())) {
					if (gamePanel.getObject()[gamePanel.currentMap][i].collision) {
						entity.collisionOn = true;
					}
					if (player) {
						// Only player can pick up items
						index = i;
					}						
				}
				
				// Reset solid area position
				entity.solidArea.setX(entity.solidAreaDefaultX);
				entity.solidArea.setY(entity.solidAreaDefaultY);
				gamePanel.getObject()[gamePanel.currentMap][i].solidArea.setX(gamePanel.getObject()[gamePanel.currentMap][i].solidAreaDefaultX);
				gamePanel.getObject()[gamePanel.currentMap][i].solidArea.setY(gamePanel.getObject()[gamePanel.currentMap][i].solidAreaDefaultY);
			}
		}
		return index;
	}
	
	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;
		
		String direction = entity.direction;
		if (entity.knockBack) {
			// Temp direction during knockback
			direction = entity.knockBackDirection;
		}
		
		for (int i = 0; i < target[1].length; i++) {
			if (target[gamePanel.currentMap][i] != null) {
				// Get entity's solid area position
				entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
				entity.solidArea.setY(entity.worldY + entity.solidArea.getY());
				
				// Get object's solid area position
				target[gamePanel.currentMap][i].solidArea.setX(
						target[gamePanel.currentMap][i].worldX + target[gamePanel.currentMap][i].solidArea.getX());
				target[gamePanel.currentMap][i].solidArea.setY(
						target[gamePanel.currentMap][i].worldY + target[gamePanel.currentMap][i].solidArea.getY());
				
				// Predict entity position
				switch (direction) {
					case "up":
						entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
						break;
					case "down":
						entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
						break;
					case "left":
						entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
						break;
					case "right":
						entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
						break;
				}
				
				if (entity.solidArea.getBoundsInParent().intersects(target[gamePanel.currentMap][i].solidArea.getBoundsInParent()) && target[gamePanel.currentMap][i] != entity) {
					entity.collisionOn = true;
					index = i;
				}
				
				// Reset solid area position
				entity.solidArea.setX(entity.solidAreaDefaultX);
				entity.solidArea.setY(entity.solidAreaDefaultY);
				target[gamePanel.currentMap][i].solidArea.setX(target[gamePanel.currentMap][i].solidAreaDefaultX);
				target[gamePanel.currentMap][i].solidArea.setY(target[gamePanel.currentMap][i].solidAreaDefaultY);
			}
		}
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		// Get entity's solid area position
		// Get entity's solid area position
		entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
		entity.solidArea.setY(entity.worldY + entity.solidArea.getY());
		
		// Get object's solid area position
		gamePanel.getPlayer().solidArea.setX(gamePanel.getPlayer().worldX + gamePanel.getPlayer().solidArea.getX());
		gamePanel.getPlayer().solidArea.setY(gamePanel.getPlayer().worldY + gamePanel.getPlayer().solidArea.getY());
		
		// Predict entity position
		switch (entity.direction) {
			case "up":
				entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
				break;
			case "down":
				entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
				break;
			case "left":
				entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
				break;
			case "right":
				entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
				break;
		}
		
		if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getPlayer().solidArea.getBoundsInParent())) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		// Reset solid area position
		entity.solidArea.setX(entity.solidAreaDefaultX);
		entity.solidArea.setY(entity.solidAreaDefaultY);
		gamePanel.getPlayer().solidArea.setX(gamePanel.getPlayer().solidAreaDefaultX);
		gamePanel.getPlayer().solidArea.setY(gamePanel.getPlayer().solidAreaDefaultY);
		
		return contactPlayer;
	}	
}
