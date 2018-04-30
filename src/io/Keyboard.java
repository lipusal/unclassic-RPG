package io;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.Game;


public class Keyboard implements KeyListener {
	
	public static final int INTERACT = KeyEvent.VK_Q,
							USE = KeyEvent.VK_E,
							RUN_AWAY = KeyEvent.VK_R,
							DROP = KeyEvent.VK_T,
							ATTACK = KeyEvent.VK_F,
							EXAMINE = KeyEvent.VK_G,
							UNEQUIP = KeyEvent.VK_CONTROL,
							SAVE_AND_QUIT = KeyEvent.VK_ESCAPE;
	
	private static Keyboard instance = new Keyboard();
	
	private Keyboard() {}
	
	public static Keyboard getKeyboardController() {
		return instance;
	}
	
	/**
	 * Checks whether the provided key code corresponds to an arrow
	 * key or a directional letter (WASD).
	 * @param pressedKeyCode The code of the key to check.
	 * @return Whether the pressed key corresponds to a directional key.
	 */
	public static boolean isDirectionalKey(int pressedKeyCode) {
		return pressedKeyCode == KeyEvent.VK_W || pressedKeyCode == KeyEvent.VK_UP
				|| pressedKeyCode == KeyEvent.VK_A || pressedKeyCode == KeyEvent.VK_LEFT
				|| pressedKeyCode == KeyEvent.VK_S || pressedKeyCode == KeyEvent.VK_DOWN
				|| pressedKeyCode == KeyEvent.VK_D || pressedKeyCode == KeyEvent.VK_RIGHT;
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		Game.getInstance().respondToKeyboardInput(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
	}

}