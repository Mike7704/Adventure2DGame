package object;

import entity.Projectile;
import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

	GamePanel gamePanel;
	
	public OBJ_Fireball(GamePanel gamePanel) {
		super(gamePanel);
		this.gamePanel = gamePanel;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	private void getImage() {
		up1 = new Image(getClass().getResourceAsStream("/Projectile/fireball_up_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/Projectile/fireball_up_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/Projectile/fireball_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/Projectile/fireball_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/Projectile/fireball_left_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/Projectile/fireball_left_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/Projectile/fireball_right_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/Projectile/fireball_right_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
}
