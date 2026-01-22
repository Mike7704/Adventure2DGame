package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Tent;
import tile_interactive.IT_DryTree;

public class AssetSetter {

	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gamePanel.getObject()[mapNum][i] = new OBJ_Key(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 25;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 23;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Key(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 21;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 19;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Key(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 26;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 21;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Axe(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 33;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 21;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Shield_Blue(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 35;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 21;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Potion_Red(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 27;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Coin_Bronze(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 34;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Heart(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 29;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_ManaCrystal(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 31;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Door(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 12;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 12;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Chest(gamePanel);
		gamePanel.getObject()[mapNum][i].setLoot(new OBJ_Potion_Red(gamePanel));
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 12;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 10;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Lantern(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 22;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 36;
		i++;
		gamePanel.getObject()[mapNum][i] = new OBJ_Tent(gamePanel);
		gamePanel.getObject()[mapNum][i].worldX = gamePanel.tileSize * 23;
		gamePanel.getObject()[mapNum][i].worldY = gamePanel.tileSize * 36;
	}
	
	public void setNPC() {
		int mapNum = 0;
		int i = 0;
		gamePanel.getNPC()[mapNum][i] = new NPC_OldMan(gamePanel);
		gamePanel.getNPC()[mapNum][i].worldX = gamePanel.tileSize * 21;
		gamePanel.getNPC()[mapNum][i].worldY = gamePanel.tileSize * 21;
		i++;
		
		mapNum = 1;
		i = 0;
		gamePanel.getNPC()[mapNum][i] = new NPC_Merchant(gamePanel);
		gamePanel.getNPC()[mapNum][i].worldX = gamePanel.tileSize * 12;
		gamePanel.getNPC()[mapNum][i].worldY = gamePanel.tileSize * 7;
	}
	
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		gamePanel.getMonster()[mapNum][i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[mapNum][i].worldX = gamePanel.tileSize * 21;
		gamePanel.getMonster()[mapNum][i].worldY = gamePanel.tileSize * 38;
		i++;
		gamePanel.getMonster()[mapNum][i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[mapNum][i].worldX = gamePanel.tileSize * 23;
		gamePanel.getMonster()[mapNum][i].worldY = gamePanel.tileSize * 42;
		i++;
		gamePanel.getMonster()[mapNum][i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[mapNum][i].worldX = gamePanel.tileSize * 24;
		gamePanel.getMonster()[mapNum][i].worldY = gamePanel.tileSize * 37;
		i++;
		gamePanel.getMonster()[mapNum][i] = new MON_GreenSlime(gamePanel);
		gamePanel.getMonster()[mapNum][i].worldX = gamePanel.tileSize * 34;
		gamePanel.getMonster()[mapNum][i].worldY = gamePanel.tileSize * 42;
		i++;
		gamePanel.getMonster()[mapNum][i] = new MON_Orc(gamePanel);
		gamePanel.getMonster()[mapNum][i].worldX = gamePanel.tileSize * 12;
		gamePanel.getMonster()[mapNum][i].worldY = gamePanel.tileSize * 33;
	}
	
	public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 27, 12);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 28, 12);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 29, 12);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 30, 12);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 31, 12);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 32, 12);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 33, 12);
		i++;
		
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 31, 21);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 18, 40);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 17, 40);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 16, 40);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 15, 40);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 14, 40);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 13, 40);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 13, 41);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 12, 41);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 11, 41);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 10, 41);
		i++;
		gamePanel.getInteractiveTile()[mapNum][i] = new IT_DryTree(gamePanel, 10, 40);
		i++;
	}
}
