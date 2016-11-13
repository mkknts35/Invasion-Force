/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invasion.force.board;

import invasion.force.gameObjects.*;
import java.util.Random;
import invasion.force.settings.Configs;

/**
 * Purpose: To provide a space for the game objects to exist in, and move around within.
 * The class is the largest scale that the game space can be seen as, and is broken into 
 * quadrants.  Space follows the singleton design pattern and provides a static getInstance 
 * method as the only way to access the private constructor.
 * @author sukhenka (Sukhenko, Artur)
 * @version Fri Nov 28 5:45 PM
 */
public class Space {
	private final Quadrant quadrant[][] = new Quadrant[Configs.SPACE_SIZE][Configs.SPACE_SIZE];
	private boolean initialized = false;
	public boolean debug = false;
	public int TotalJovian = Configs.TOTAL_JOVIANS;
	public int TotalStars = Configs.TOTAL_STARS;
	public int TotalStations = Configs.TOTAL_STATIONS;

	/**
	 * @return the set of quadrants
	 */
	public Quadrant[][] getQuadrants() {
		return quadrant;
	}

	/**
	 * static Singleton instance
	 */
	private static Space instance;

	/**
	 * Private constructor for singleton
	 */
	private Space() {
		init();
	}

	private void init() {
		if (!initialized) {
			for (int i = 0; i < quadrant.length; i++) {
				for (int j = 0; j < quadrant.length; j++) {
					quadrant[i][j] = new Quadrant(new Position(i, j));
					if (debug) {
						System.out.println("init: quadrant row: " + i + " col:"
								+ j);
					}
				}
			}
			initialized = true;
			if (debug) {
				System.out.println("Initialized space");
			}
		}
	}

	/**
	 * @param spaceObject
	 *            (object you want to find)
	 * @return Quadrant where spaceObject is.
	 */
	public invasion.force.board.Quadrant getQuadrantOfObject(
			invasion.force.gameObjects.SpaceObject spaceObject) {
		for (invasion.force.board.Quadrant[] quadrants : quadrant) {
			for (invasion.force.board.Quadrant quadrant : quadrants) {
				for (invasion.force.board.Sector[] secs : quadrant.getSectors()) {
					for (invasion.force.board.Sector sec : secs) {
						if (sec.getInhabitant() == spaceObject) {
							return quadrant;
						}
					}
				}
			}
		}
		return null; // if not find returns null
	}

	/**
	 * @param p
	 *            - Use Position instead of row,col if you want.
	 * @return the quadrant in Space with position (row,col)
	 */
	public Quadrant getQuadrant(Position p) {
		return quadrant[p.getRow()][p.getCol()];

	}

	/**
	 * @param row
	 * @param col
	 * @return the quadrant in Space with position (row,col)
	 */
	public Quadrant getQuadrant(int row, int col) {
		Position p = new Position(row, col);
		return quadrant[p.getRow()][p.getCol()];

	}

	/**
	 * REQUIRES: nothing
	 * MODIFIES: space
	 * EFFECTS: Initializes the population of space.  Create an appropriate number of
	 * jovians, stars and space stations and distribute them throughout the space
	 */
	public void initPopulation(Ship ship){
		int TotalCruisers = Configs.TOTAL_CRUISERS;
		int TotalCommanders = Configs.TOTAL_COMMANDERS;
		int TotalStarsInGame = Configs.TOTAL_STARS;
		int TotalStationsInGame = Configs.TOTAL_STATIONS;
		Random random = new Random();
		Sector [][]tempSec;
		for (int i = 0; i < quadrant.length; i++) {
			for (int j = 0; j < quadrant.length; j++) {
				int numCruisersInSector = 0;
				int numCommandersInSector = 0;
				int numStarsinSec = 0;
				int numStationInSec = 0;
				if(TotalCruisers >0 ){
					numCruisersInSector = random.nextInt(5);
					TotalCruisers -= numCruisersInSector;
				}
				
				if(TotalCommanders > 0) {
					numCommandersInSector = random.nextInt(1);
					TotalCommanders -= numCommandersInSector;
				}
				
				if(TotalStarsInGame > 0) {
					numStarsinSec = random.nextInt(5);
					TotalStarsInGame -= numStarsinSec;
					
				}
				if(TotalStationsInGame > 0) {
					numStationInSec = random.nextInt(2);
					TotalStationsInGame -= numStationInSec;
				}
				for(int k =0; k < numCruisersInSector; k++){
					tempSec = quadrant[i][j].getSectors();
					quadrant[i][j].getAllObjectsFromQuadrant().add(new JovianBattleCruiser(tempSec[0][k],ship));
				}
				
				for(int k = 0; k < numStarsinSec; k++) {
					tempSec = quadrant[i][j].getSectors();
					quadrant[i][j].getAllObjectsFromQuadrant().add(new Star(tempSec[1][k]));
				}
				for(int k = 0; k < numStationInSec; k++) {
					tempSec = quadrant[i][j].getSectors();
					quadrant[i][j].getAllObjectsFromQuadrant().add(new SpaceStation(tempSec[2][k]));
				}
				for(int k = 0; k < numCommandersInSector; k++) {
					tempSec = quadrant[i][j].getSectors();
					quadrant[i][j].getAllObjectsFromQuadrant().add(new JovianCommandCruiser(tempSec[3][k],ship));
				}
				
				quadrant[i][j].unpopulate();
			}
		}
	}
	
	/**
         * @return: the singleton instance of space
	 */
	public static Space getInstance() {
		if (instance == null) {
			instance = new Space();
		}
		return instance;
	}
	
	public static void clear() {
		instance = null;
	}
	
	public void decrementJovian() {
		TotalJovian--;
	}

}