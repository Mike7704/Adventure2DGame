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
	
	// Check object collision and return index of the object collided with
	public int checkObject(Entity entity, boolean player) {
		int index = 999;

		for (int i = 0; i < gamePanel.getObject().length; i++) {
			if (gamePanel.getObject()[i] != null) {
				// Get entity's solid area position
				entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
				entity.solidArea.setY(entity.worldY + entity.solidArea.getY());
				
				// Get object's solid area position
				gamePanel.getObject()[i].solidArea.setX(gamePanel.getObject()[i].worldX + gamePanel.getObject()[i].solidArea.getX());
				gamePanel.getObject()[i].solidArea.setY(gamePanel.getObject()[i].worldY + gamePanel.getObject()[i].solidArea.getY());
				
				// Predict entity position
				switch (entity.direction) {
					case "up":
						entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getObject()[i].solidArea.getBoundsInParent())) {
							if (gamePanel.getObject()[i].collision) {
								entity.collisionOn = true;
							}
							if (player) {
								// Only player can pick up items
								index = i;
							}
						}
						break;
					case "down":
						entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getObject()[i].solidArea.getBoundsInParent())) {
							if (gamePanel.getObject()[i].collision) {
								entity.collisionOn = true;
							}
							if (player) {
								// Only player can pick up items
								index = i;
							}						
						}
						break;
					case "left":
						entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getObject()[i].solidArea.getBoundsInParent())) {
							if (gamePanel.getObject()[i].collision) {
								entity.collisionOn = true;
							}
							if (player) {
								// Only player can pick up items
								index = i;
							}						
						}
						break;
					case "right":
						entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getObject()[i].solidArea.getBoundsInParent())) {
							if (gamePanel.getObject()[i].collision) {
								entity.collisionOn = true;
							}
							if (player) {
								// Only player can pick up items
								index = i;
							}							
						}
						break;
				}
				// Reset solid area position
				entity.solidArea.setX(entity.solidAreaDefaultX);
				entity.solidArea.setY(entity.solidAreaDefaultY);
				gamePanel.getObject()[i].solidArea.setX(gamePanel.getObject()[i].solidAreaDefaultX);
				gamePanel.getObject()[i].solidArea.setY(gamePanel.getObject()[i].solidAreaDefaultY);
			}
		}
		return index;
	}
	
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;

		for (int i = 0; i < target.length; i++) {
			if (target[i] != null) {
				// Get entity's solid area position
				entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
				entity.solidArea.setY(entity.worldY + entity.solidArea.getY());
				
				// Get object's solid area position
				target[i].solidArea.setX(target[i].worldX + target[i].solidArea.getX());
				target[i].solidArea.setY(target[i].worldY + target[i].solidArea.getY());
				
				// Predict entity position
				switch (entity.direction) {
					case "up":
						entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
							entity.collisionOn = true;
							index = i;
						}
						break;
					case "down":
						entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
							entity.collisionOn = true;
							index = i;				
						}
						break;
					case "left":
						entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
							entity.collisionOn = true;
							index = i;				
						}
						break;
					case "right":
						entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
						if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
							entity.collisionOn = true;
							index = i;					
						}
						break;
				}
				// Reset solid area position
				entity.solidArea.setX(entity.solidAreaDefaultX);
				entity.solidArea.setY(entity.solidAreaDefaultY);
				target[i].solidArea.setX(target[i].solidAreaDefaultX);
				target[i].solidArea.setY(target[i].solidAreaDefaultY);
			}
		}
		return index;
	}
	
	public void checkPlayer(Entity entity) {
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
				if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getPlayer().solidArea.getBoundsInParent())) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
				if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getPlayer().solidArea.getBoundsInParent())) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
				if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getPlayer().solidArea.getBoundsInParent())) {
					entity.collisionOn = true;	
				}
				break;
			case "right":
				entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
				if (entity.solidArea.getBoundsInParent().intersects(gamePanel.getPlayer().solidArea.getBoundsInParent())) {
					entity.collisionOn = true;	
				}
				break;
		}
		// Reset solid area position
		entity.solidArea.setX(entity.solidAreaDefaultX);
		entity.solidArea.setY(entity.solidAreaDefaultY);
		gamePanel.getPlayer().solidArea.setX(gamePanel.getPlayer().solidAreaDefaultX);
		gamePanel.getPlayer().solidArea.setY(gamePanel.getPlayer().solidAreaDefaultY);
	}	
}
