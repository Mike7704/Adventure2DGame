package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		gamePanel.getObject()[0] = new OBJ_Key();
		gamePanel.getObject()[0].worldX = 23 * gamePanel.tileSize;
		gamePanel.getObject()[0].worldY = 7 * gamePanel.tileSize;
		
		gamePanel.getObject()[1] = new OBJ_Key();
		gamePanel.getObject()[1].worldX = 23 * gamePanel.tileSize;
		gamePanel.getObject()[1].worldY = 40 * gamePanel.tileSize;
		
		gamePanel.getObject()[2] = new OBJ_Key();
		gamePanel.getObject()[2].worldX = 38 * gamePanel.tileSize;
		gamePanel.getObject()[2].worldY = 8 * gamePanel.tileSize;
		
		gamePanel.getObject()[3] = new OBJ_Door();
		gamePanel.getObject()[3].worldX = 10 * gamePanel.tileSize;
		gamePanel.getObject()[3].worldY = 11 * gamePanel.tileSize;
		
		gamePanel.getObject()[4] = new OBJ_Door();
		gamePanel.getObject()[4].worldX = 8 * gamePanel.tileSize;
		gamePanel.getObject()[4].worldY = 28 * gamePanel.tileSize;
		
		gamePanel.getObject()[5] = new OBJ_Door();
		gamePanel.getObject()[5].worldX = 12 * gamePanel.tileSize;
		gamePanel.getObject()[5].worldY = 22 * gamePanel.tileSize;
		
		gamePanel.getObject()[6] = new OBJ_Chest();
		gamePanel.getObject()[6].worldX = 10 * gamePanel.tileSize;
		gamePanel.getObject()[6].worldY = 7 * gamePanel.tileSize;
	}
	
}
