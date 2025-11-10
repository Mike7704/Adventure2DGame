package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

	public OBJ_Coin_Bronze(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Bronze Coin";
		type = type_pickup;
		value = 1;
		down1 = new Image(getClass().getResourceAsStream("/Object/coin_bronze.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\nA bronze coin.";
	}
	
	public void use(Entity entity) {
		gamePanel.playSoundEffect(1); // Coin sound effect
		gamePanel.getUI().addMessage("+" + value + " Coin");
		entity.coin += value;
	}
}
