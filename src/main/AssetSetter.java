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
		
		int i = 0;
		gamePanel.getMonster()[i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[i].worldX = gamePanel.tileSize * 21;
		gamePanel.getMonster()[i].worldY = gamePanel.tileSize * 38;
		i++;
		gamePanel.getMonster()[i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[i].worldX = gamePanel.tileSize * 23;
		gamePanel.getMonster()[i].worldY = gamePanel.tileSize * 42;
		i++;
		gamePanel.getMonster()[i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[i].worldX = gamePanel.tileSize * 24;
		gamePanel.getMonster()[i].worldY = gamePanel.tileSize * 37;
		i++;
		gamePanel.getMonster()[i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[i].worldX = gamePanel.tileSize * 34;
		gamePanel.getMonster()[i].worldY = gamePanel.tileSize * 42;
		i++;
		gamePanel.getMonster()[i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[i].worldX = gamePanel.tileSize * 38;
		gamePanel.getMonster()[i].worldY = gamePanel.tileSize * 42;
	}
}
