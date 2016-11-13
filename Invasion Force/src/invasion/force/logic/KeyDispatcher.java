/*
 * Copyright (C) 2016 Michael Koonts, Sukhenko Artur, Laurens van Wingerden, 
 * Vitalii Egorchatov, Mandip Sangha, David Lamothe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package invasion.force.logic;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

/*
 * AUTHOR: DAVID LAMOTHE
 * OVERVIEW: Handles the key input listener event and 
 * passes it to the command parser 
 */
public class KeyDispatcher extends JFrame implements KeyEventDispatcher {

    private JTextField typingArea;
    private CommandParser parser;

    /*
	 * REQUIRES: New GameEngine, and textField
	 * MODIFIES: NONE
	 * EFFECTS: Initializes objects in game
     */
    public KeyDispatcher(GameEngine newGame, JTextField textField) {
        typingArea = textField;
        parser = new CommandParser(newGame);
    }

    /*
	 * REQUIRES: KeyEvent
	 * MODIFIES: None
	 * EFFECTS: Passes KeyEvent to keypressed
	 * 
	 * (non-Javadoc)
	 * @see java.awt.KeyEventDispatcher#dispatchKeyEvent(java.awt.event.KeyEvent)
     */
    public boolean dispatchKeyEvent(KeyEvent e) {
        //Allow the event to be redispatched
        keyPressed(e);
        return false;
    }

    /*
	 * REQUIRES: KeyEvent
	 * MODIFIES: TextField
	 * EFFECTS: If key event pressed is an enter
	 * Process the command currently in the text are
	 * 
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                String cmd = typingArea.getText();
                processCommand(cmd);
                typingArea.setText("");
            }
        }
    }

    /*
	 * REQUIRES: String Command
	 * MODIFIES: NONE
	 * EFFECTS: Sends the command from the textfield
	 * to the commanderParser for parsing
	 * 
     */
    public void processCommand(String command) {
        parser.parseCommand(command);
    }

    /*
	 *	REQUIRES: NONE 
	 *	MODIFIES: NONE
	 *	EFFECTS: Returns the textField
     */
    public JTextField getTextField() {
        return typingArea;
    }
}
