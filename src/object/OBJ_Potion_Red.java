package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

	GamePanel gamePanel;
	
	public OBJ_Potion_Red(GamePanel gamePanel) {
		super(gamePanel);
		
		this.gamePanel = gamePanel;
		
		name = "Red Potion";
		type = type_consumable;
		value = 5;
		down1 = new Image(getClass().getResourceAsStream("/Object/potion_red.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\n+" + value + " HP";
	}
	
	public void use(Entity entity) {
		gamePanel.gameState = gamePanel.dialogueState;
		gamePanel.getUI().currentDialogue = "You drink the " + name + "!\n" + "You gained " + value + " HP";
		gamePanel.playSoundEffect(2); // Power up sound effect
		
		entity.life += value;
		if(entity.life > entity.maxLife) {
			entity.life = entity.maxLife;
		}
	}
	
}
