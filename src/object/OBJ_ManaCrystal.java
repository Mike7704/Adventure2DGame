package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
	
	public OBJ_ManaCrystal(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Mana Crystal";
		type = type_consumable;
		image = new Image(getClass().getResourceAsStream("/Object/manacrystal_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image2 = new Image(getClass().getResourceAsStream("/Object/manacrystal_blank.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
}
