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
package invasion.force.settings;

import java.awt.Color;

/**
 *
 * @author Michael Koonts
 */
public class Configs {

    public static final int SPACE_SIZE = 10;
    public static final int QUADRANT_SIZE = 10;
    //Ship systems
    public static final int SHIELD = 0;
    public static final int LRSENSOR = 1;
    public static final int SRSENSOR = 2;
    public static final int MASER = 3;
    public static final int LAUNCHER = 4;
    public static final int ION = 5;
    public static final int HYPER = 6;
    public static final int POWER_AVAILABLE = 7;
    public static final int ENGINE = 8;
    public static final int TRT_MISSILE = 9;
    public static final int ANITMATTER_POD = 10;

    //Ship total power system
    public static final int TOTAL_POWERS = 7;

    //default star date
    public static final int INITTIME = 300200;
    public static final int TIMEINCREMENT = 1;

    //default power levels
    public static final double INIT_SHIELD = 20.0;
    public static final double INIT_ENGINE = 10.0;
    public static final double INIT_LRSENSOR = 10.0;
    public static final double INIT_SRSENSOR = 20.0;
    public static final double INIT_MASER = 9.0;
    public static final double INIT_TRT_MISSILE = 11.0;

    //minimum system power(This is the minimum power a system must have in order to function)
    public static final double MIN_SYSTEM_POWER = 2.0;
    public static final double MIN_TOTAL_POWER = 0;
    public static final double POWER_SAP = 2.0;

    public static final int TOTAL_SENSORS_ROWS = 3;

    //Capacities
    public static final int TRT_MISSILES = 10;
    public static final int ANTIMATTER_PODS = 3;
    public static final double MAX_POWER = 99.0;

    //GUI INFO
    public static final int WINDOW_SIZE = 500;
    public static final int SECTORS_PER_SIDE = 10;

    //Object totals
    public static final int TOTAL_JOVIANS = 44;
    public static final int TOTAL_STARS = 33;
    public static final int TOTAL_STATIONS = 2;
    public static final int TOTAL_COMMANDERS = 2;
    public static final int TOTAL_CRUISERS = 42;

    //Navigational directions
    public static final int NORTH = 8;
    public static final int SOUTH = 2;
    public static final int WEST = 4;
    public static final int EAST = 6;
    public static final int NORTH_WEST = 7;
    public static final int NORTH_EAST = 9;
    public static final int SOUTH_WEST = 1;
    public static final int SOUTH_EAST = 3;
    public static final int NEUTRAL = 5;

    //Location names
    public static final int SECTOR_X = 0;
    public static final int SECTOR_Y = 1;
    public static final int QUADRENT_X = 2;
    public static final int QUADRENT_Y = 3;
    public static final int X = 0;
    public static final int Y = 1;

    //BOARD CHARACTERS
    public static final String MINE = "%";
    public static final String SHIP = "^";
    public static final String SSTN = "0";
    public static final String JCC = "@";
    public static final String JBC = "&";
    public static final String UNKN = "X";
    public static final String EMPTY = ".";
    public static final String STAR = "*";
    public static final String TRT_MSSL = "+";
    public static final String MSR = "~";
    public static final String ANTM_POD = "#";

    //SPEED CONSTANTS
    public static final int SZERO = 0;
    public static final int SONE = 1;
    public static final int STWO = 2;
    public static final int STHREE = 3;
    public static final int SFOUR = 4;
    public static final int SFIVE = 5;
    public static final int SSIX = 6;
    public static final int SSEVEN = 7;
    public static final int SEIGHT = 8;
    public static final int SNINE = 9;
    public static final int STEN = 10;

    //CONDITION LEVELS
    public static final double CONDITIONLEVELS[] = {75, 50, 25, 0};
    public static final String CONDITIONSTRINGVALUES[] = {"GREEN", "YELLOW", "RED", "CRITICAL"};
    public static final Color CONDITIONCOLOR[] = {Color.GREEN, Color.YELLOW, Color.RED, Color.PINK};
    public static final int CONDITIONLEVELAMOUNT = 3;

    //PANEL VARIABLES
    public static final int LRARRSIZE = 3;

    //VELOCITY VARIABLES
    public static final int SPEED = 0;
    public static final int DIRECTION = 1;
    public static final int HYPER_DELTA = 5;
    public static final int INITDESTRUCTTIMER = 16;
    public static final int SELFDESTRUCTMAX = 0;
    public static final int INITCRITICALTIMER = 10;
    public static final int CRITICALMASS = 0;
    public static final int MAX_ION = 10;

    //DELTA
    public static final int JOVIAN_DELTA = 8;

}
