package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Boots extends SuperObject {

	private GamePanel gamePanel;
	
	public OBJ_Boots(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		name = "Boots";
		try {
			image = new Image(getClass().getResourceAsStream("/Object/boots.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
