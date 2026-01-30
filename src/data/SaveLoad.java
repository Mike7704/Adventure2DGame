package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class SaveLoad {

	private GamePanel gamePanel;
	
	public SaveLoad(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			
			// Store player stats
			ds.level = gamePanel.getPlayer().level;
			ds.maxLife = gamePanel.getPlayer().maxLife;
			ds.life = gamePanel.getPlayer().life;
			ds.maxMana = gamePanel.getPlayer().maxMana;
			ds.mana = gamePanel.getPlayer().mana;
			ds.strength = gamePanel.getPlayer().strength;
			ds.dexterity = gamePanel.getPlayer().dexterity;
			ds.exp = gamePanel.getPlayer().exp;
			ds.nextLevelExp = gamePanel.getPlayer().nextLevelExp;
			ds.coin = gamePanel.getPlayer().coin;
			
			// Store player inventory
			for (int i = 0; i < gamePanel.getPlayer().inventory.size(); i++) {
				ds.itemNames.add(gamePanel.getPlayer().inventory.get(i).name);
				ds.itemAmounts.add(gamePanel.getPlayer().inventory.get(i).stackAmount);
			}
			ds.currentWeaponSlot = gamePanel.getPlayer().getCurrentWeaponSlot();
			ds.currentShieldSlot = gamePanel.getPlayer().getCurrentShieldSlot();
			
			// Objects on map
			ds.mapObjectNames = new String[gamePanel.maxMap][gamePanel.getObject()[1].length];
			ds.mapObjectWorldX = new int[gamePanel.maxMap][gamePanel.getObject()[1].length];
			ds.mapObjectWorldY = new int[gamePanel.maxMap][gamePanel.getObject()[1].length];
			ds.mapObjectLootNames = new String[gamePanel.maxMap][gamePanel.getObject()[1].length];
			ds.mapObjectOpened = new boolean[gamePanel.maxMap][gamePanel.getObject()[1].length];
			
			for (int map = 0; map < gamePanel.maxMap; map++) {
				for (int i = 0; i < gamePanel.getObject()[1].length; i++) {
					if (gamePanel.getObject()[map][i] != null) {
						ds.mapObjectNames[map][i] = gamePanel.getObject()[map][i].name;
						ds.mapObjectWorldX[map][i] = gamePanel.getObject()[map][i].worldX;
						ds.mapObjectWorldY[map][i] = gamePanel.getObject()[map][i].worldY;
						if (gamePanel.getObject()[map][i].loot != null) {
							ds.mapObjectLootNames[map][i] = gamePanel.getObject()[map][i].loot.name;
						}
						ds.mapObjectOpened[map][i] = gamePanel.getObject()[map][i].isOpen;
					}
					else {
						ds.mapObjectNames[map][i] = "NA";
					}
				}
			}
			
			// Write the DataStorage object to the file
			oos.writeObject(ds);
			
		} catch (Exception e) {
			System.out.println("Save Exception!");
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			DataStorage ds = (DataStorage) ois.readObject();
			
			// Set player stats
			gamePanel.getPlayer().level = ds.level;
			gamePanel.getPlayer().maxLife = ds.maxLife;
			gamePanel.getPlayer().life = ds.life;
			gamePanel.getPlayer().maxMana = ds.maxMana;
			gamePanel.getPlayer().mana = ds.mana;
			gamePanel.getPlayer().strength = ds.strength;
			gamePanel.getPlayer().dexterity = ds.dexterity;
			gamePanel.getPlayer().exp = ds.exp;
			gamePanel.getPlayer().nextLevelExp = ds.nextLevelExp;
			gamePanel.getPlayer().coin = ds.coin;
						
			// Set player inventory
			gamePanel.getPlayer().inventory.clear();
			for (int i = 0; i < ds.itemNames.size(); i++) {
				gamePanel.getPlayer().inventory.add(gamePanel.getEntityGenerator().getObject(ds.itemNames.get(i)));
				gamePanel.getPlayer().inventory.get(i).stackAmount = ds.itemAmounts.get(i);
			}
			gamePanel.getPlayer().currentWeapon = gamePanel.getPlayer().inventory.get(ds.currentWeaponSlot);
			gamePanel.getPlayer().currentShield = gamePanel.getPlayer().inventory.get(ds.currentShieldSlot);
			gamePanel.getPlayer().getAttack();
			gamePanel.getPlayer().getDefense();
			gamePanel.getPlayer().getPlayerAttackImage();
			
			// Load objects on map
			for (int map = 0; map < gamePanel.maxMap; map++) {
				for (int i = 0; i < gamePanel.getObject()[1].length; i++) {
					if (!ds.mapObjectNames[map][i].equals("NA")) {
						gamePanel.getObject()[map][i] = gamePanel.getEntityGenerator().getObject(ds.mapObjectNames[map][i]);
						gamePanel.getObject()[map][i].worldX = ds.mapObjectWorldX[map][i];
						gamePanel.getObject()[map][i].worldY = ds.mapObjectWorldY[map][i];
						if (ds.mapObjectLootNames[map][i] != null) {
							gamePanel.getObject()[map][i].loot = gamePanel.getEntityGenerator().getObject(ds.mapObjectLootNames[map][i]);
						}
						gamePanel.getObject()[map][i].isOpen = ds.mapObjectOpened[map][i];
						if (gamePanel.getObject()[map][i].isOpen) {
							gamePanel.getObject()[map][i].down1 = gamePanel.getObject()[map][i].image2; // Chest opened image
						}
					}
					else {
						gamePanel.getObject()[map][i] = null;
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Load Exception!");
			e.printStackTrace();
		}
	}
}
