package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
	
	public OBJ_ManaCrystal(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Mana Crystal";
		type = type_pickup;
		value = 1;
		down1 = new Image(getClass().getResourceAsStream("/Object/manacrystal_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image = new Image(getClass().getResourceAsStream("/Object/manacrystal_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image2 = new Image(getClass().getResourceAsStream("/Object/manacrystal_blank.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	public void use(Entity entity) {
		gamePanel.playSoundEffect(2); // Power up sound effect
		gamePanel.getUI().addMessage("+" + value + " Mana");
		entity.mana += value;
		if (entity.mana > entity.maxMana) {
			entity.mana = entity.maxMana;
		}
	}
}
