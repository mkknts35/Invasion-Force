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

/**
 * @author sukhenka (Sukhenko Artur)
 * @version Fri Nov 28 5:45 PM
 */
import invasion.force.gameObjects.SpaceObject;
import invasion.force.gameObjects.Weapon;
import java.util.ArrayList;
import java.util.Random;

import invasion.force.settings.Configs;

public class Quadrant implements Positionable {

    protected final Position position = new Position(-1, -1);
    /**
     * sectors[][] array of Sectors(Sector contain SpaceObject like Ship, Jovian
     * etc)
     */
    private final Sector sectors[][] = new Sector[Configs.QUADRANT_SIZE][Configs.QUADRANT_SIZE];
    private boolean initialized = false;
    private final ArrayList<SpaceObject> generatedObjects = new ArrayList<SpaceObject>();
    private Random rand = new Random();
    private final ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
    /**
     * If ship is in this Quadrant active = true
     */
    public boolean active = false;

    /**
     * @return the sectors[][]
     */
    public Sector[][] getSectors() {
        return sectors;
    }

    private void init() {
        if (!initialized) {
            for (int i = 0; i < sectors.length; i++) {
                for (int j = 0; j < sectors.length; j++) {
                    sectors[i][j]
                            = new Sector(new Position(i, j), this);
                    // is it right order?
                }
            }
            initialized = true;
        }
    }

    /**
     * returns the position of the Quadrant in Space
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * method put generated spaceObjects to the sectors.
     */
    public void populate() {
        for (SpaceObject obj : generatedObjects) {
            populateSector(obj);
        }

    }

    private void populateSector(SpaceObject obj) {
        Position p = randomPosition();
        if (getSector(p).getInhabitant() == null) {
            obj.setSector(getSector(p));
            //getSector(p).setInhabitant(obj);
        } else {
            populateSector(obj);
        }
    }

    private Position randomPosition() {
        return (new Position(rand.nextInt(Configs.QUADRANT_SIZE),
                rand.nextInt(Configs.QUADRANT_SIZE)));
    }

    public ArrayList<SpaceObject> getAllObjectsFromQuadrant() {
        ArrayList<SpaceObject> obcts = new ArrayList<SpaceObject>();
        for (Sector[] sctrs : sectors) {
            for (Sector sector : sctrs) {
                obcts.add(sector.getInhabitant());
            }
        }
        return obcts;

    }

    public Sector getSector(Position p) {
        return sectors[p.getRow()][p.getCol()];

    }

    public void unpopulate() {
        weaponList.clear();
        for (SpaceObject i : generatedObjects) {
            //for (Sector sector : sctrs) {
            //	sector.setInhabitant(null);
            //}
            i.setSector(null);
        }
    }

    public Sector getSector(int row, int col) {
        Position p = new Position(row, col);
        return sectors[p.getRow()][p.getCol()];

    }

    public Quadrant() {
        init();
    }

    public Quadrant(Position p) {
        position.setPositionAt(p);
        init();
    }

    public Sector getNext(Sector sector, int direction) {
        int nextRow = sector.getPosition().getRow();
        int nextCol = sector.getPosition().getCol();
        Position pos = new Position();
        switch (direction) {
            case Configs.NORTH:
                nextCol -= 1;
                break;
            case Configs.NORTH_EAST:
                nextRow += 1;
                nextCol -= 1;
                break;
            case Configs.EAST:
                nextRow += 1;
                break;
            case Configs.SOUTH_EAST:
                nextRow += 1;
                nextCol += 1;
                break;
            case Configs.SOUTH:
                nextCol += 1;
                break;
            case Configs.SOUTH_WEST:
                nextRow -= 1;
                nextCol += 1;
                break;
            case Configs.WEST:
                nextRow -= 1;
                break;
            case Configs.NORTH_WEST:
                nextRow -= 1;
                nextCol -= 1;
                break;
            default:
            //return sector;
        }
        pos.setCol(nextCol);
        pos.setRow(nextRow);
        if (!pos.isValid()) {
            return null;
        }
        return getSector(pos);
    }

    public ArrayList<SpaceObject> getGeneratedObjects() {
        return generatedObjects;
    }

    public ArrayList<Weapon> getWeaponList() {
        return weaponList;
    }

}
