package tile_interactive;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {

	public IT_DryTree(GamePanel gamePanel, int col, int row) {
		super(gamePanel, col, row);
		
		this.worldX = gamePanel.tileSize * col;
		this.worldY = gamePanel.tileSize * row;
		
		down1 = new Image(getClass().getResourceAsStream("/Tiles_Interactive/drytree.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		destructible = true;
		life = 3;
	}
	
	public boolean isCorrectItem(Entity entity) {
		return entity.currentWeapon.type == type_axe;
	}
	
	public void playSoundEffect() {
		gamePanel.playSoundEffect(11); // Cut tree sound
	}
	
	public InteractiveTile getDestroyedForm() {
		return new IT_Trunk(gamePanel, worldX/gamePanel.tileSize, worldY/gamePanel.tileSize);
	}

	public Color getParticleColor() {
		return new Color(0.25, 0.19, 0.11, 1.0);
	}
	
	public int getParticleSize() {
		return 6; // 6 pixels
	}
	
	public int getParticleSpeed() {
		return 1;
	}
	
	public int getParticleMaxLife() {
		return 20;
	}
}
