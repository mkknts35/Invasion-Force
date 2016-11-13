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
package invasion.force.gameObjects;

import invasion.force.board.*;
//import board.Positionable;
//import board.Quadrant;
//import board.Sector;
//import board.Space;

import java.util.Observable;

/**
 * Overview: This class is intended to serve as a base class for all the space
 * objects needed in this game. It should serve as the top of the inheritance
 * tree for: Player controlled - Ship Player produced - Weapons - all types
 * Player friendly - Space Stations All active obstacles - Jovians - all types
 * All inactive obstacles - Stars This class provides the basic functionality
 * that all the listed objects require to be represented in a meaningful way in
 * the game. It also extends Observable which provides an easy interface to the
 * GUI to keep updated on state changes.
 *
 * @author Michael Koonts
 */
public abstract class SpaceObject extends Observable implements Positionable {

    public String label;
    protected Sector sector;
    protected Quadrant quadrant;
    protected boolean detectable = true;

    /**
     * REQUIRES: @param sec - a valid board.Sector of which the inhabitant is
     * either null or ok to overwrite MODIFIES: game state EFFECTS: returns a
     * new object at the given sector
     */
    public SpaceObject(Sector sec) {
        sector = sec;
        sector.setInhabitant(this);
        quadrant = Space.getInstance().getQuadrant(sector.getQuadPosition());
        if (!(this instanceof Weapon)) {
            quadrant.getGeneratedObjects().add(this);
        }
    }

    /**
     * REQUIRES: nothing MODIFIES: nothing EFFECTS: @return a valid
     * board.Position object Note: a null value indicates a problem with either
     * this or the sector this is located in
     */
    @Override
    public Position getPosition() {
        return sector.getPosition();
    }

    /**
     * REQUIRES: the caller to ensure that the given sector is an appropriate
     * place to set this object MODIFFIES: this and @param sec EFFECTS: sets
     * this object in the sector provided
     */
    public void setSector(Sector sec) {
        if (null != sector) {
            sector.setInhabitant(null);
        }
        sector = sec;
        if (null != sector) {
            sector.setInhabitant(this);
        }
    }

    /**
     * REQUIRES: nothing MODIFIES: this EFFECTS: sets the detectable state of
     * this to @param det
     */
    public void setDetectable(boolean det) {
        detectable = det;
    }

    /**
     * REQUIRES: nothing MODIFIES: nothing EFFECTS: This method is intended for
     * use with the short range sensors ie. if @returns is false this should
     * show up as an 'X' on the display
     */
    public boolean getDetectable() {
        return detectable;
    }

    /**
     * REQUIRES: nothing MODIFIES: this, game state EFFECTS: Removes all known
     * references to this allowing garbage collection to remove this. Note: This
     * is a default method, and does not do anything spectacular. Extensions to
     * this class should override this method to add special effects.
     */
    public void selfDestruct() {
        if (this instanceof Weapon) {
            quadrant.getWeaponList().remove(this);
        } else {
            if (this instanceof JovianWarship) {
                Space.getInstance().decrementJovian();
            }
            quadrant.getGeneratedObjects().remove(this);
        }
        this.setSector(null);
    }

    /**
     *
     * @return
     */
    public Quadrant getQuadrant() {
        return this.quadrant;
    }

    /**
     * REQUIRES: nothing MODIFIES: this EFFECTS: effects very depending on the
     * implementation of the SpaceObject
     *
     * @throws CollissionException if action causes the SpaceObject to move this
     * exception can be generated in the event that the place this is moving to
     * is already occupied.
     */

    public String getLabel() {
        return this.label;
    }

    public abstract void action() throws CriticalPowerException;

    public abstract void bumped(SpaceObject object);

    public abstract void bump(SpaceObject object);

}
