package application;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        
        if (code == KeyCode.W) {
            upPressed = true;
        }
        if (code == KeyCode.S) {
            downPressed = true;
        }
        if (code == KeyCode.A) {
            leftPressed = true;
        }
        if (code == KeyCode.D) {
            rightPressed = true;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            upPressed = false;
        }
        if (code == KeyCode.S) {
            downPressed = false;
        }
        if (code == KeyCode.A) {
            leftPressed = false;
        }
        if (code == KeyCode.D) {
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
}
