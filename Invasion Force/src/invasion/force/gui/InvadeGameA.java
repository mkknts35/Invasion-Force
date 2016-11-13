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
package invasion.force.gui;

import invasion.force.logic.*;
import java.awt.KeyboardFocusManager;
import javax.swing.*;
import invasion.force.board.Space;
import invasion.force.logic.KeyDispatcher;

/*
 * AUTHOR: MANDIP SANGHA, DAVID LAMOTHE
 * OVERVIEW: STARTS GAME AND DRAWS JFRAME
 */
public class InvadeGameA extends JFrame {

    private static KeyDispatcher dispatcher;
    private GameEngine game;
    private GamePanels panel;

    /*
     * REQUIRES: NONE
     * MODIFIES: NONE
     * EFFECTS: Initializes game, UI and dispatcher
     */
    InvadeGameA() {
        initUI();
        initGame();
        dispatcher = new KeyDispatcher(game, panel.getTextField());
        //Hijack the keyboard manager
        KeyboardFocusManager manager
                = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(dispatcher);
    }

    /*
     * REQUIRES: NONE
     * MODIFIES: NONE
     * EFFECTS: Initializes UI 
     */
    private void initUI() {
        panel = new GamePanels();
        setTitle("Test");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridConstraint = new java.awt.GridBagConstraints();
        gridConstraint.gridx = 0;
        gridConstraint.gridy = 0;
        gridConstraint.weightx = .10;
        gridConstraint.weighty = 1;
        gridConstraint.fill = java.awt.GridBagConstraints.BOTH;
        add(panel.getLeftPanel(), gridConstraint);

        gridConstraint.gridx = 1;
        gridConstraint.gridy = 0;
        gridConstraint.weightx = .5;
        add(panel.getMiddlePanel(), gridConstraint);

        gridConstraint.gridx = 2;
        gridConstraint.gridy = 0;
        gridConstraint.weightx = .10;
        add(panel.getRightPanel(), gridConstraint);

        gridConstraint.gridx = 0;
        gridConstraint.gridy = 1;
        gridConstraint.weightx = 1;
        gridConstraint.gridwidth = 3;
        add(panel.getBottomPanel(), gridConstraint);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;

        //+++++++++++++++++++++++++++++
        //File Menu
        //+++++++++++++++++++++++++++++
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                KeyboardFocusManager manager
                        = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.removeKeyEventDispatcher(dispatcher);
                dispose();
                Space.clear();
                Space.getInstance();
                String[] arr = {};
                main(arr);
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem(new AbstractAction("Exit Game") {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        menu.add(menuItem);

        setJMenuBar(menuBar);

        setSize(575, 300);
        setLocationRelativeTo(null);
    }

    /*
     * REQUIRES: NONE
     * MODIFIES: NONE
     * EFFECTS: Initializes game object
     */
    private void initGame() {
        //Initialize Game
        game = new GameEngine(panel);
    }

    /*
     * REQUIRES: NONE
     * MODIFIES: NONE
     * EFFECTS: Initializes whole game
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InvadeGameA game = new InvadeGameA();
                game.setVisible(true);
            }
        });
    }
}
