package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

	GamePanel gamePanel;
	int healAmount = 5;
	
	public OBJ_Potion_Red(GamePanel gamePanel) {
		super(gamePanel);
		
		this.gamePanel = gamePanel;
		
		name = "Red Potion";
		type = type_consumable;
		down1 = new Image(getClass().getResourceAsStream("/Object/potion_red.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		description = "[" + name + "]\n+" + healAmount + " HP";
	}
	
	public void use(Entity entity) {
		gamePanel.gameState = gamePanel.dialogueState;
		gamePanel.getUI().currentDialogue = "You drink the " + name + "!\n" + "You gained " + healAmount + " HP";
		gamePanel.playSoundEffect(2); // Power up sound effect
		
		entity.life += healAmount;
		if(entity.life > entity.maxLife) {
			entity.life = entity.maxLife;
		}
	}
	
}
