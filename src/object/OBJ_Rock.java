package object;

import entity.Projectile;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

	GamePanel gamePanel;
	public static final String objName = "Rock";
	
	public OBJ_Rock(GamePanel gamePanel) {
		super(gamePanel);
		this.gamePanel = gamePanel;
		
		name = objName;
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	private void getImage() {
		up1 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/Projectile/rock_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	public Color getParticleColor() {
		return new Color(0.15, 0.19, 0, 1.0);
	}
	
	public int getParticleSize() {
		return 10; // 10 pixels
	}
	
	public int getParticleSpeed() {
		return 1;
	}
	
	public int getParticleMaxLife() {
		return 20;
	}
}
