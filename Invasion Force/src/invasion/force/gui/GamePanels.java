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

import java.awt.Color;
import static invasion.force.settings.Configs.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * AUTHOR: MANDIP SANGHA, DAVID LAMOTHE
 * OVERVIEW: DRAWS GAME GUI
 */
public class GamePanels {

    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;

    private JFrame myFrame;
    private JPanel leftPanel;
    private JPanel middlePanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    //Left Panel Labels
    public JLabel lRSensorLabel[];
    public JLabel powerLabels[];

    //Right Panel Labels
    public JLabel starTimeLabel;
    public JLabel conditionLabel;
    public JLabel quadrantLabel;
    public JLabel sectorLabel;
    public JLabel tritonMislsLabel;
    public JLabel powerAvailLabel;
    public JLabel totalPowerLabel;
    public JLabel joviansLeftLabel;
    public JLabel antimatterPodsLabel;
    public JTextField textField;

    //Middle Panel Labels
    public JLabel[][] grid;

    //Bottom Panel Labels
    public JLabel invalidCommandLabel;

    /*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: Creates all panels
     */
    GamePanels() {
        createLeftPanel();
        createMiddlePanel();
        createRightPanel();
        createBottomPanel();
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: lRSensorLabel, powerLabels, leftPanel
	 * EFFECTS: Creates all labels that belong in the Left panel
     */
    private void createLeftPanel() {
        int X = 0;
        int Y = 0;

        JPanel test = new JPanel();
        test.setLayout(new java.awt.GridBagLayout());

        textField = new JTextField(5);
        powerLabels = new JLabel[TOTAL_POWERS];
        lRSensorLabel = new JLabel[TOTAL_SENSORS_ROWS];

        java.awt.GridBagConstraints gridConstraint = new java.awt.GridBagConstraints();
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.weightx = 1;
        gridConstraint.weighty = 1;
        gridConstraint.gridwidth = 2;
        //gridConstraint.fill = java.awt.GridBagConstraints.BOTH;
        gridConstraint.anchor = java.awt.GridBagConstraints.NORTHWEST;

        //Filler to move other label to right positions
        test.add(new JLabel(""), gridConstraint);

        //Column Zero 
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.weighty = 0;
        gridConstraint.gridwidth = 1;
        test.add(new JLabel("L-R SENSOR"), gridConstraint);

        for (int i = 0; i < TOTAL_SENSORS_ROWS; i++) {
            gridConstraint.gridy = Y++;
            lRSensorLabel[i] = new JLabel("000-000-000");
            test.add(lRSensorLabel[i], gridConstraint);

            if (i != 2) {
                gridConstraint.gridy = Y++;
                test.add(new JLabel("----------------"), gridConstraint);
            }
        }

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("PWR DIST"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("DEFLECTORS"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("LR SENSOR"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("SR SENSOR"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("MASER"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("TRITONMISSLE"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("ION"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("HYPER"), gridConstraint);

        //Column Two
        X = 1;
        Y = 1;
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(MINE + "-MINE"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(SHIP + "-HEPHS"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(SSTN + "-SSTN"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(JCC + "-JCC"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(JBC + "-JBC"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(UNKN + "-UNKN"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("%"), gridConstraint);

        for (int i = 0; i < TOTAL_POWERS; i++) {
            gridConstraint.gridy = Y++;
            powerLabels[i] = new JLabel("20");
            test.add(powerLabels[i], gridConstraint);
        }

        test.setOpaque(true);
        test.setBackground(Color.BLACK);
        for (int i = 0; i < test.getComponentCount(); i++) {
            test.getComponent(i).setForeground(Color.WHITE);
        }
        leftPanel = test;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: grid, middlePanel
	 * EFFECTS: Creates all labels that belong in the middle panel
     */
    private void createMiddlePanel() {
        int X = 0;
        int Y = 0;
        grid = new JLabel[GRID_HEIGHT][GRID_WIDTH];
        JPanel test = new JPanel();
        test.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagConstraints gridConstraint = new java.awt.GridBagConstraints();
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.weightx = 1;
        gridConstraint.weighty = 1;
        gridConstraint.gridwidth = 2;
        gridConstraint.anchor = java.awt.GridBagConstraints.NORTHWEST;

        //Filler to move other label to right postions
        test.add(new JLabel(""), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.weighty = 0;
        gridConstraint.gridwidth = GRID_WIDTH;
        gridConstraint.anchor = java.awt.GridBagConstraints.CENTER;
        test.add(new JLabel("-----RADIO SHACK-----"), gridConstraint);

        gridConstraint.gridwidth = 1;

        for (int i = 0; i < GRID_HEIGHT; i++) {
            gridConstraint.gridx = X;
            gridConstraint.gridy = Y + i;
            test.add(new JLabel(i + "I"), gridConstraint);
        }

        X++;

        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                gridConstraint.gridx = X + j;
                gridConstraint.gridy = Y + i;
                grid[i][j] = new JLabel("" + j);
                test.add(grid[i][j], gridConstraint);
            }
        }

        for (int i = 0; i < GRID_HEIGHT; i++) {
            gridConstraint.gridx = X + GRID_WIDTH;
            gridConstraint.gridy = Y + i;
            test.add(new JLabel("I"), gridConstraint);
        }

        --X;
        Y += GRID_HEIGHT;

        for (int i = 0; i < GRID_WIDTH + 2; i++) {
            gridConstraint.gridx = X + i;
            gridConstraint.gridy = Y;
            if (i == 0 || i == GRID_WIDTH + 1) {
                test.add(new JLabel("I"), gridConstraint);
            } else {
                test.add(new JLabel("-"), gridConstraint);
            }

            gridConstraint.gridx = X + i;
            gridConstraint.gridy = Y + 1;
            if (i == 0 || i == GRID_WIDTH + 1) {
                test.add(new JLabel("I"), gridConstraint);
            } else {
                test.add(new JLabel("" + (i - 1)), gridConstraint);
            }
        }

        test.setOpaque(true);
        test.setBackground(Color.BLACK);
        for (int i = 0; i < test.getComponentCount(); i++) {
            test.getComponent(i).setForeground(Color.WHITE);
        }
        middlePanel = test;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: starTimeLabel, conditionLabel, quadrantLabel, sectorLabel,
	 * tritonMislsLabel, powerAvailLabel, totalPowerLabel, joviansLeftLabel,
	 * antimatterPodsLabel, textField, rightPanel
	 * EFFECTS: Creates all labels that belong in the Right panel
     */
    private void createRightPanel() {
        int X = 0;
        int Y = 0;

        JPanel test = new JPanel();
        test.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagConstraints gridConstraint = new java.awt.GridBagConstraints();
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.weightx = 1;
        gridConstraint.weighty = 1;
        gridConstraint.gridwidth = 2;
        //gridConstraint.fill = java.awt.GridBagConstraints.BOTH;
        gridConstraint.anchor = java.awt.GridBagConstraints.NORTHWEST;

        //Filler to move other label to right positions
        test.add(new JLabel(""), gridConstraint);

        //Column Zero 
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.weighty = 0;
        gridConstraint.gridwidth = 2;
        gridConstraint.anchor = java.awt.GridBagConstraints.CENTER;
        test.add(new JLabel("^ USS HEPHAESTUS ^"), gridConstraint);

        gridConstraint.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        gridConstraint.gridwidth = 1;
        test.add(new JLabel("STAR_TIME"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("CONDITION"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("QUADRANT"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("SECTOR"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("TRITON MISLS"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("TOTAL POWER"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("POWER AVAIL"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("JOVIANS LEFT"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("ANTIMATTER PODS"), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(" "), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("SCDM: "), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(" "), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel("COMMAND:"), gridConstraint);

        //Column Two
        X = 1;
        Y = 2;

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        starTimeLabel = new JLabel("300200");
        test.add(starTimeLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        conditionLabel = new JLabel("GREEN");
        test.add(conditionLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        quadrantLabel = new JLabel("1-3");
        test.add(quadrantLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        sectorLabel = new JLabel("9-2");
        test.add(sectorLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        tritonMislsLabel = new JLabel("10");
        test.add(tritonMislsLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        totalPowerLabel = new JLabel("0%");
        test.add(totalPowerLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        powerAvailLabel = new JLabel("99%");
        test.add(powerAvailLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        joviansLeftLabel = new JLabel("44");
        test.add(joviansLeftLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        antimatterPodsLabel = new JLabel("3");
        test.add(antimatterPodsLabel, gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(NORTH_WEST + " " + NORTH + " " + NORTH_EAST), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(WEST + " " + NEUTRAL + " " + EAST), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(new JLabel(SOUTH_WEST + " " + SOUTH + " " + SOUTH_EAST), gridConstraint);

        gridConstraint.gridx = X;
        gridConstraint.gridy = Y++;
        test.add(textField, gridConstraint);

        test.setOpaque(true);
        test.setBackground(Color.BLACK);
        for (int i = 0; i < test.getComponentCount(); i++) {
            test.getComponent(i).setForeground(Color.WHITE);
        }
        rightPanel = test;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: invalidCommandLabel, bottomPanel
	 * EFFECTS: Creates all labels that belong in the Bottom panel
     */
    private void createBottomPanel() {
        JPanel test = new JPanel();
        test.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagConstraints gridConstraint = new java.awt.GridBagConstraints();
        gridConstraint.gridx = 0;
        gridConstraint.gridy = 0;
        gridConstraint.weightx = 1;
        gridConstraint.weighty = 0;
        gridConstraint.anchor = java.awt.GridBagConstraints.CENTER;

        test.add(new JLabel("HYPR=H,ION=I,MSR=M,TRM=T,PWRDST=D,SELFD=S,EXPRAY=E,POD=A,XPOD=X"), gridConstraint);

        gridConstraint.gridy = 1;
        invalidCommandLabel = new JLabel("");
        test.add(invalidCommandLabel, gridConstraint);

        //Filler to move other label to right postions
        gridConstraint.gridy = 2;
        gridConstraint.weighty = 1;
        test.add(new JLabel(""), gridConstraint);

        test.setOpaque(true);
        test.setBackground(Color.BLACK);
        for (int i = 0; i < test.getComponentCount(); i++) {
            test.getComponent(i).setForeground(Color.WHITE);
        }
        bottomPanel = test;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: returns Left panel
     */
    public JPanel getLeftPanel() {
        return leftPanel;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: returns middle panel
     */
    public JPanel getMiddlePanel() {
        return middlePanel;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: returns right panel
     */
    public JPanel getRightPanel() {
        return rightPanel;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: returns bottom panel
     */
    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    /*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: returns text field panel
     */
    public JTextField getTextField() {
        return textField;
    }
}
