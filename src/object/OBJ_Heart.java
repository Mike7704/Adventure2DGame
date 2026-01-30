package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	public static final String objName = "Heart";
	
	public OBJ_Heart(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_pickup;
		value = 2;
		down1 = new Image(getClass().getResourceAsStream("/Object/heart_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image = new Image(getClass().getResourceAsStream("/Object/heart_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image2 = new Image(getClass().getResourceAsStream("/Object/heart_half.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		image3 = new Image(getClass().getResourceAsStream("/Object/heart_blank.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	public boolean use(Entity entity) {
		gamePanel.playSoundEffect(2); // Power up sound effect
		gamePanel.getUI().addMessage("+" + value + " Life");
		entity.life += value;
		if (entity.life > entity.maxLife) {
			entity.life = entity.maxLife;
		}
		return true;
	}
}
