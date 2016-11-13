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
     * @param inhabitant the inhabitant to set
     */
    public void setInhabitant(SpaceObject inhabitant) {
        this.inhabitant = inhabitant;
    }

    @Override
    public Position getPosition() {
        return sectorPosition;
    }

    public Position getQuadPosition() {
        return quadrantPosition;
    }

}
