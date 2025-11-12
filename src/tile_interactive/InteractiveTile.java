package tile_interactive;

import entity.Entity;
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
