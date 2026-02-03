package tile_interactive;

import java.util.Random;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class IT_DestructibleWall extends InteractiveTile {

	public IT_DestructibleWall(GamePanel gamePanel, int col, int row) {
		super(gamePanel, col, row);
		
		this.worldX = gamePanel.tileSize * col;
		this.worldY = gamePanel.tileSize * row;
		
		down1 = new Image(getClass().getResourceAsStream("/Tiles_Interactive/destructibleWall.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		destructible = true;
		life = 3;
	}
	
	public boolean isCorrectItem(Entity entity) {
		return entity.currentWeapon.type == type_pickaxe;
	}
	
	public void playSoundEffect() {
		gamePanel.playSoundEffect(20); // Chip wall sound
	}
	
	public InteractiveTile getDestroyedForm() {
		return null;
	}

	public Color getParticleColor() {
		return new Color(0.25, 0.25, 0.25, 1.0);
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
	
	public void checkDrop() {
		// Called when monster dies
		int i = new Random().nextInt(100)+1;
		
		if (i <= 10) {
			dropItem(new OBJ_Coin_Bronze(gamePanel));
		}
		else if (i <= 20) {
			dropItem(new OBJ_Heart(gamePanel));
		}
		else if (i <= 30) {
			dropItem(new OBJ_ManaCrystal(gamePanel));
		}
	}

}
