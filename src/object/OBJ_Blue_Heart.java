package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Blue_Heart extends Entity {
	
	public static final String objName = "Blue Heart";
	
	public OBJ_Blue_Heart(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_pickup;
		down1 = new Image(getClass().getResourceAsStream("/Object/blueheart.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You picked up a blue heart.";
		dialogues[0][1] = "It is a legendary treasure.";
	}
	
	public boolean use(Entity entity) {
		gamePanel.gameState = gamePanel.cutsceneState;
		gamePanel.getCutsceneManager().sceneNum = gamePanel.getCutsceneManager().ending;
		return true;
	}
}
