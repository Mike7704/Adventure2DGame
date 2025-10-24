package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;

public class AssetSetter {

	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		// Objects
	}
	
	public void setNPC() {
		
		gamePanel.getNPC()[0] = new NPC_OldMan(gamePanel);
		gamePanel.getNPC()[0].worldX = gamePanel.tileSize * 21;
		gamePanel.getNPC()[0].worldY = gamePanel.tileSize * 21;
	}
	
	public void setMonster() {
		gamePanel.getMonster()[0] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[0].worldX = gamePanel.tileSize * 23;
		gamePanel.getMonster()[0].worldY = gamePanel.tileSize * 36;
		
		gamePanel.getMonster()[1] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[1].worldX = gamePanel.tileSize * 23;
		gamePanel.getMonster()[1].worldY = gamePanel.tileSize * 37;
	}
}
