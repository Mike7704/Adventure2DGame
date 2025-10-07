package main;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        
        if (code == KeyCode.W || code == KeyCode.UP) {
            upPressed = true;
        }
        if (code == KeyCode.S || code == KeyCode.DOWN) {
            downPressed = true;
        }
        if (code == KeyCode.A || code == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (code == KeyCode.D || code == KeyCode.RIGHT) {
            rightPressed = true;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W || code == KeyCode.UP) {
            upPressed = false;
        }
        if (code == KeyCode.S || code == KeyCode.DOWN) {
            downPressed = false;
        }
        if (code == KeyCode.A || code == KeyCode.LEFT) {
            leftPressed = false;
        }
        if (code == KeyCode.D || code == KeyCode.RIGHT) {
            rightPressed = false;
        }
    }

	public boolean isUpPressed() {
		return upPressed;
	}
	
	public boolean isDownPressed() {
		return downPressed;
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public boolean isKeyPressed() {
		return upPressed || downPressed || leftPressed || rightPressed;
	}
}
