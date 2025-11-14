package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.GamePanel;

public class Particle extends Entity {

	Entity generator; // The entity that produces the particle
	Color color;
	int size;
	int xd;
	int yd;
	
	public Particle(GamePanel gamePanel, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
		super(gamePanel);
		
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xd = xd;
		this.yd = yd;
		
		life = maxLife;
		int offset = (gamePanel.tileSize / 2) - (size / 2);
		worldX = generator.worldX + offset;
		worldY = generator.worldY + offset;
	}

	public void update() {		
		worldX += xd * speed;
		worldY += yd * speed;
		
		life--;
		
		if (life < maxLife / 3) {
			yd++; // Add gravity
		}
		
		if (life == 0) {
			alive = false;
		}
	}
	
	public void draw(GraphicsContext gc) {
		int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
		int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;
		
		gc.setFill(color);
		gc.fillRect(screenX, screenY, size, size);
	}
}
