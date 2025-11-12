package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_DryTree;

public class AssetSetter {

	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		int i = 0;
		gamePanel.getObject()[i] = new OBJ_Key(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 25;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 23;
		i++;
		gamePanel.getObject()[i] = new OBJ_Key(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 21;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 19;
		i++;
		gamePanel.getObject()[i] = new OBJ_Key(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 26;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 21;
		i++;
		gamePanel.getObject()[i] = new OBJ_Axe(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 33;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 21;
		i++;
		gamePanel.getObject()[i] = new OBJ_Shield_Blue(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 35;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 21;
		i++;
		gamePanel.getObject()[i] = new OBJ_Potion_Red(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 27;
		i++;
		gamePanel.getObject()[i] = new OBJ_Coin_Bronze(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 34;
		i++;
		gamePanel.getObject()[i] = new OBJ_Heart(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 29;
		i++;
		gamePanel.getObject()[i] = new OBJ_ManaCrystal(gamePanel);
		gamePanel.getObject()[i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[i].worldY = gamePanel.tileSize * 31;
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
	
	public void setInteractiveTile() {
		
		int i = 0;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 27, 12);
		i++;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 28, 12);
		i++;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 29, 12);
		i++;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 30, 12);
		i++;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 31, 12);
		i++;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 32, 12);
		i++;
		gamePanel.getInteractiveTile()[i] = new IT_DryTree(gamePanel, 33, 12);
		i++;
	}
}
