package magicarrows;

import aic2022.user.Location;

public class MapTracker {

    final int MAX_MAP_SIZE = 64;
    final int INT_BITS = 32;
    final int ARRAY_SIZE = 128;

    int[] visitedLocations = new int[ARRAY_SIZE];


    MapTracker(){
    }

    void reset(){
        visitedLocations = new int[ARRAY_SIZE];
    }

    void add(Location loc){
        int arrayPos = (loc.x%MAX_MAP_SIZE)*(1 + (loc.y%MAX_MAP_SIZE)/INT_BITS);
        int bitPos = loc.y%INT_BITS;
        visitedLocations[arrayPos] |= (1 << bitPos);
    }

    boolean check(Location loc){
        int arrayPos = (loc.x%MAX_MAP_SIZE)*(1 + (loc.y%MAX_MAP_SIZE)/INT_BITS);
        int bitPos = loc.y%INT_BITS;
        return ((visitedLocations[arrayPos] & (1 << bitPos)) > 0);
    }

}
