package tile_interactive;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GamePanel;

public class InteractiveTile extends Entity {

	public boolean destructible;
	
	public InteractiveTile(GamePanel gamePanel, int col, int row) {
		super(gamePanel);

	}
	
	public void update() {
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 20) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(GraphicsContext gc) {

		int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
		int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;
		
		// Draw object only visible on screen around the player
		if (worldX + gamePanel.tileSize > gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX &&
			worldX - gamePanel.tileSize < gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX &&
			worldY + gamePanel.tileSize > gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY &&
			worldY - gamePanel.tileSize < gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY)
		{		
			gc.drawImage(down1, screenX, screenY);
		}
	}
	
	public boolean isCorrectItem(Entity entity) {
		// Overridden in subclasses
		return false;
	}
	
	public void playSoundEffect() {
		// Overridden in subclasses
	}
	
	public InteractiveTile getDestroyedForm() {
		// Overridden in subclasses
		return null;
	}
}
