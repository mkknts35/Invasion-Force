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

import static invasion.force.settings.Configs.*;

/*
 * AUTHOR: DAVID LAMOTHE, MANDIP SANGHA
 * OVERVIEW: PARSINGS COMMANDS SENT BY
 * INPUT HANDLER AND SENDS THEM TO THE 
 * GAME ENGINE FOR PROCESSING
 */

public class CommandParser {
	
	private static final int firstIndex = 0;
	private static final int subCommand = 1;
	private GameEngine game;
	
	/*
	 * REQUIRES: New Game Object
	 * MODIFIES: NONE
	 * EFFECTS: Initializes game object
	 */
	public CommandParser(GameEngine newGame) {
		game = newGame;
	}
	
	/*
	 * REQUIRES: String Command 
	 * MODIFIES: NONE
	 * EFFECTS: Parses various commands 
	 * and calls various methods on the game object to
	 * act on the commands
	 */

	public void parseCommand(String command) {
		game.clearInvalidCommand();
		switch(command.charAt(firstIndex)) {
			case 'H':
				handleHyperDirection(command.substring(subCommand));
				break;
			case 'I':
				handleIonDirection(command.substring(subCommand));
				break;
			case 'M':
				handleWeapon(command.substring(subCommand),MASER);
				break;
			case 'T':
				handleWeapon(command.substring(subCommand),TRT_MISSILE);
				break;
			case 'D':
				handlePower(command.substring(subCommand));
				break;
			case 'S':
				handleDestruct(command.substring(subCommand));
				break;
			case 'E':
				handleExperiment(command.substring(subCommand));
				break;
			case 'A':
				handleWeapon(command.substring(subCommand),ANITMATTER_POD);
				break;
			case 'X':
				handleEx();
				break;
			default:
				invalidCommand();

				System.out.println("IC 0");
				break;
				
		}
	}
	
	/*
	 * REQUIRES: Substring of the initial command
	 * MODIFIES: NONE
	 * EFFECTS: Parses various the direction
	 * and calls handle speed method to 
	 * act on the commands and catch if number format is wrong
	 * or if the substring is an empty string
	 * 
	 */

	private void handleHyperDirection(String substring) {
		try {
			switch(Integer.parseInt(substring.substring(firstIndex) )) {
				case NORTH: 
					handleSpeed(String.valueOf(STEN), NORTH);
					break;
				case SOUTH:
					handleSpeed(String.valueOf(STEN), SOUTH);
					break;
				case WEST:
					handleSpeed(String.valueOf(STEN), WEST);
					break;
				case EAST:
					handleSpeed(String.valueOf(STEN), EAST);
					break;
				case NORTH_WEST:
					handleSpeed(String.valueOf(STEN), NORTH_WEST);
					break;
				case NORTH_EAST:
					handleSpeed(String.valueOf(STEN), NORTH_EAST);
					break;
				case SOUTH_WEST:
					handleSpeed(String.valueOf(STEN), SOUTH_WEST);
					break;
				case SOUTH_EAST:
					handleSpeed(String.valueOf(STEN), SOUTH_EAST);
					break;
				case NEUTRAL:
					handleSpeed(String.valueOf(SZERO), NEUTRAL);
					break;
				default: 
					invalidCommand();
					break;
					
			}
		} catch(NumberFormatException e) {
			invalidCommand();
		} catch(IndexOutOfBoundsException e) {
			invalidCommand();
		}
	}
	
	/*
	 * REQUIRES: Substring of the initial command
	 * MODIFIES: NONE
	 * EFFECTS: Parses various the direction
	 * and calls handle speed method to 
	 * act on the commands and catch if number format is wrong
	 * or if the substring is an empty string
	 * 
	 */
	
	private void handleIonDirection(String substring) {
		try {
			switch(Integer.parseInt(substring.substring(firstIndex, firstIndex + 1) )) {
				case NORTH: 
					handleSpeed(substring.substring(subCommand), NORTH);
					break;
				case SOUTH:
					handleSpeed(substring.substring(subCommand), SOUTH);
					break;
				case WEST:
					handleSpeed(substring.substring(subCommand), WEST);
					break;
				case EAST:
					handleSpeed(substring.substring(subCommand), EAST);
					break;
				case NORTH_WEST:
					handleSpeed(substring.substring(subCommand), NORTH_WEST);
					break;
				case NORTH_EAST:
					handleSpeed(substring.substring(subCommand), NORTH_EAST);
					break;
				case SOUTH_WEST:
					handleSpeed(substring.substring(subCommand), SOUTH_WEST);
					break;
				case SOUTH_EAST:
					handleSpeed(substring.substring(subCommand), SOUTH_EAST);
					break;
				case NEUTRAL:
					handleSpeed(substring.substring(subCommand), NEUTRAL);
					break;
				default: 
					invalidCommand();
					break;
					
			}
		} catch(NumberFormatException e) {
			invalidCommand();
		} catch(IndexOutOfBoundsException e) {
			invalidCommand();
		}
	}
	
	/*
	 * REQUIRES: Substring of the initial command and direction
	 * MODIFIES: NONE
	 * EFFECTS: Parses various the direction and speed to
	 * game object to act on the commands and catch if number format is wrong
	 * or if the substring is an empty string
	 * 
	 */
	
	private void handleSpeed(String substring, int direction) {
		try {
			switch(Integer.parseInt(substring)) {
				case SZERO: 
					game.setVelocity(SZERO,direction);
					break;
				case SONE: 
					game.setVelocity(SONE,direction);
					break;
				case STWO:
					game.setVelocity(STWO,direction);
					break;
				case STHREE:
					game.setVelocity(STHREE,direction);
					break;
				case SFOUR:
					game.setVelocity(SFOUR,direction);
					break;
				case SFIVE:
					game.setVelocity(SFIVE,direction);
					break;
				case SSIX:
					game.setVelocity(SSIX,direction);
					break;
				case SSEVEN:
					game.setVelocity(SSEVEN,direction);
					break;
				case SEIGHT:
					game.setVelocity(SEIGHT,direction);
					break;
				case SNINE:
					game.setVelocity(SNINE,direction);
					break;
				case STEN:
					game.setVelocity(STEN, direction);
					break;
				default: 
					invalidCommand();
					break;
					
			}
		} catch(NumberFormatException e) {
			invalidCommand();
		} catch(IndexOutOfBoundsException e) {
			invalidCommand();
		}
	}
	
	/*
	 * REQUIRES: Substring of the initial command and type
	 * MODIFIES: NONE
	 * EFFECTS: Parses various the direction and type to
	 * game object to act on the commands and catch if number format is wrong
	 * or if the substring is an empty string
	 * 
	 */
	
	private void handleWeapon(String substring, int type) {
		try {
			switch(Integer.parseInt(substring.substring(firstIndex, firstIndex + 1) )) {
				case NORTH: 
					game.shootWeapon(type, NORTH);
					break;
				case SOUTH:
					game.shootWeapon(type, SOUTH);
					break;
				case WEST:
					game.shootWeapon(type, WEST);
					break;
				case EAST:
					game.shootWeapon(type, EAST);
					break;
				case NORTH_WEST:
					game.shootWeapon(type, NORTH_WEST);
					break;
				case NORTH_EAST:
					game.shootWeapon(type, NORTH_EAST);
					break;
				case SOUTH_WEST:
					game.shootWeapon(type, SOUTH_WEST);
					break;
				case SOUTH_EAST:
					game.shootWeapon(type, SOUTH_EAST);
					break;
				default: 
					invalidCommand();
					break;
					
			}
		} catch(NumberFormatException e) {
			invalidCommand();
		} catch(IndexOutOfBoundsException e) {
			invalidCommand();
		}
	}
	
	/*
	 * REQUIRES: Substring of the initial command 
	 * MODIFIES: NONE
	 * EFFECTS: Parses various the command and type to
	 * game object to act on the commands and catch if number format is wrong
	 * or if the substring is an empty string
	 * 
	 */
	
	private void handlePower(String cmd) {
		try {
			if(Double.parseDouble((cmd.substring(subCommand))) >= 0) {
				switch(cmd.charAt(firstIndex)) {
					case 'H':
						game.setPower(HYPER, Double.parseDouble((cmd.substring(subCommand))));
						break;
					case 'I':
						game.setPower(ION, Double.parseDouble((cmd.substring(subCommand))));
						break;
					case 'L':
						game.setPower(LRSENSOR, Double.parseDouble((cmd.substring(subCommand))));
						break;
					case 'S':
						game.setPower(SRSENSOR, Double.parseDouble((cmd.substring(subCommand))));
						break;
					case 'D':
						game.setPower(SHIELD, Double.parseDouble((cmd.substring(subCommand))));
						break;
					case 'M':
						game.setPower(MASER, Double.parseDouble((cmd.substring(subCommand))));
						break;
					case 'T':
						game.setPower(LAUNCHER, Double.parseDouble((cmd.substring(subCommand))));
						break;
					default:
						invalidCommand();
						break;
				} 
			} else {
					invalidCommand();
			}
		} catch(NumberFormatException e) {
			invalidCommand();
		} catch(IndexOutOfBoundsException e) {
			invalidCommand();
		}
	}

	/*
	 * REQUIRES: Substring of the initial command and type
	 * MODIFIES: NONE
	 * EFFECTS: Calls game object self destruct
	 * 
	 */
	private void handleDestruct(String substring) {
		// TODO Auto-generated method stub
		System.out.println("S Process: " + substring);
		game.selfDestruct();
	}
	/*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: Calls game object explode pod
	 */
	private void handleEx() {
		// TODO Auto-generated method stub
		game.explodePod();
		
	}

	/*NOT USED
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: NONE
	 */
	private void handleExperiment(String substring) {
		// TODO Auto-generated method stub
		System.out.println("X Process");
		
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: Prints "invalid command" to console and
	 * calls game invalid command 
	 */
	private void invalidCommand() {
		// TODO Auto-generated method stub
		System.out.println("Invalid Command");
		game.invalidCommand();
		
	}
}
