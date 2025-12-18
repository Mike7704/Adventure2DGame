package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

	public OBJ_Lantern(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Lantern";
		type = type_light;
		down1 = new Image(getClass().getResourceAsStream("/Object/lantern.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\nA light for dark areas.";
		price = 80;
		lightRadius = 350;
	}

}
