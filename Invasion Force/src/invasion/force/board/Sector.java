/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invasion.force.board;
import invasion.force.gameObjects.SpaceObject;

/**
 * 
 * @author sukhenka (Sukhenko Artur)
 * 
 */
public class Sector implements Positionable {
	protected final Position sectorPosition = new Position(-1, -1);
	protected final Position quadrantPosition = new Position(-1, -1);
	private SpaceObject inhabitant;

	public Sector(Position p, Quadrant q) {
		quadrantPosition.setPositionAt(q.getPosition());
		sectorPosition.setPositionAt(p);
		inhabitant = null;
	}

	/**
	 * @return the inhabitant
	 */
	public SpaceObject getInhabitant() {
		return inhabitant;
	}

	/**
	 * @param inhabitant
	 *            the inhabitant to set
	 */
	public void setInhabitant(SpaceObject inhabitant) {
		this.inhabitant = inhabitant;
	}

	@Override
	public Position getPosition() {
		return sectorPosition;
	}
	
	public Position getQuadPosition(){
		return quadrantPosition;
	}

}
