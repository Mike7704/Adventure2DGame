package main;

import entity.Entity;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Fireball;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class EntityGenerator {

	private GamePanel gamePanel;
	
	public EntityGenerator(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public Entity getObject(String itemName) {
		Entity obj = null;
		
		switch (itemName) {
			case OBJ_Axe.objName:			obj = new OBJ_Axe(gamePanel); break;
			case OBJ_Boots.objName:			obj = new OBJ_Boots(gamePanel); break;
			case OBJ_Coin_Bronze.objName:	obj = new OBJ_Coin_Bronze(gamePanel); break;
			case OBJ_Chest.objName: 		obj = new OBJ_Chest(gamePanel); break;
			case OBJ_Door.objName: 			obj = new OBJ_Door(gamePanel); break;
			case OBJ_Key.objName: 			obj = new OBJ_Key(gamePanel); break;
			case OBJ_ManaCrystal.objName: 	obj = new OBJ_ManaCrystal(gamePanel); break;
			case OBJ_Potion_Red.objName: 	obj = new OBJ_Potion_Red(gamePanel); break;
			case OBJ_Rock.objName: 			obj = new OBJ_Rock(gamePanel); break;
			case OBJ_Fireball.objName: 		obj = new OBJ_Fireball(gamePanel); break;
			case OBJ_Heart.objName: 		obj = new OBJ_Heart(gamePanel); break;
			case OBJ_Lantern.objName: 		obj = new OBJ_Lantern(gamePanel); break;
			case OBJ_Shield_Blue.objName: 	obj = new OBJ_Shield_Blue(gamePanel); break;
			case OBJ_Shield_Wood.objName: 	obj = new OBJ_Shield_Wood(gamePanel); break;
			case OBJ_Sword_Normal.objName: 	obj = new OBJ_Sword_Normal(gamePanel); break;
			case OBJ_Tent.objName: 			obj = new OBJ_Tent(gamePanel); break;
			default: break;
		}
		
		return obj;
	}
}
