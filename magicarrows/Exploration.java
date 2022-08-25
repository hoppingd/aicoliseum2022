package magicarrows;

import aic2022.user.*;

public class Exploration {

    UnitController uc;
    Communication comm;
    float visionRadius;
    Direction[] dirPath;

    boolean[][] visited = new boolean[GameConstants.MAX_MAP_SIZE][];
    boolean initialized = false;
    int initRow = 0;

    final int initEnergyLeft = 300;
    final int visitedEnergyLeft = 300;
    int initialX, initialY;

    Location exploreTarget = null;
    int turnsExploring = 0;
    final int MAX_TURNS_EXPLORING = 50;

    Exploration(UnitController uc, Communication comm) {
        this.uc = uc;
        this.comm = comm;
        visionRadius = uc.getType().getStat(UnitStat.VISION_RANGE);
        fillDirPath();
        initialX = uc.getLocation().x;
        initialY = uc.getLocation().y;
    }

    void initTurn(){
        checkBounds();
    }

    boolean inTheMap(Location loc){
        if (comm.getBound(comm.ubX) != null && loc.x >= comm.getBound(comm.ubX)) return false;
        if (comm.getBound(comm.lbX) != null && loc.x <= comm.getBound(comm.lbX)) return false;
        if (comm.getBound(comm.ubY) != null && loc.y >= comm.getBound(comm.ubY)) return false;
        if (comm.getBound(comm.lbY) != null && loc.y <= comm.getBound(comm.lbY)) return false;
        return true;
    }

    boolean hasVisited (Location loc){
        if (!initialized) return false;
        return visited[loc.x%GameConstants.MAX_MAP_SIZE][loc.y%GameConstants.MAX_MAP_SIZE];
    }

    void markSeen(){
        if (!initialized) return;
        /*
        Location loc = uc.getLocation();
        for (int i = dirPath.length; i-- > 0; ) {
            if (uc.getEnergyLeft() < visitedEnergyLeft) return;
            loc = loc.add(dirPath[i]);
            if (!uc.isOutOfMap(loc)) visited[loc.x%GameConstants.MAX_MAP_SIZE][loc.y%GameConstants.MAX_MAP_SIZE] = true; //encoded
        }*/
        Location[] visibleLocations = uc.getVisibleLocations(); // MAY BE POORLY SORTED, BUT ALL ARE IN MAP
        for (Location l : visibleLocations) {
            if (uc.getEnergyLeft() < visitedEnergyLeft) return;
            visited[l.x%GameConstants.MAX_MAP_SIZE][l.y%GameConstants.MAX_MAP_SIZE] = true;
        }
    }

    int encode (Location loc){
        return (loc.x*GameConstants.MAX_MAP_SIZE + loc.y)%(GameConstants.MAX_MAP_SIZE*GameConstants.MAX_MAP_SIZE);
    }

    void emergencyTarget(int tries){
        Location myLoc = uc.getLocation();
        if (exploreTarget != null && myLoc.distanceSquared(exploreTarget) > visionRadius) return;
        int X = uc.getLocation().x;
        int Y = uc.getLocation().y;
        for (int i = tries; i-- > 0; ){
            int dx = (int)(Math.random()*(GameConstants.MAX_MAP_SIZE*2) - GameConstants.MAX_MAP_SIZE);
            int dy = (int)(Math.random()*(GameConstants.MAX_MAP_SIZE*2) - GameConstants.MAX_MAP_SIZE);
            exploreTarget = new Location(X+dx,Y+dy);
            if (myLoc.distanceSquared(exploreTarget) > visionRadius) return;
        }
        exploreTarget = null;
    }

    Location getExploreTarget(){
        if (!initialized) emergencyTarget(10);
        else getNewTarget(10);
        //if (exploreTarget != null) uc.println(uc.getInfo().getID() + " is exploring " + exploreTarget + " on round " + uc.getRound());
        return exploreTarget;
    }

    boolean isPositive(Location loc) {
        return loc.x >= 0 && loc.y >= 0;
    }

    void getNewTarget(int tries){
        if (turnsExploring < MAX_TURNS_EXPLORING && exploreTarget != null && isPositive(exploreTarget) && !hasVisited(exploreTarget) && inTheMap(exploreTarget)) {
            turnsExploring++;
            return;
        }
        turnsExploring = 0;
        int X = uc.getLocation().x;
        int Y = uc.getLocation().y;
        for (int i = tries; i-- > 0; ){
            int dx = (int)(Math.random()*(GameConstants.MAX_MAP_SIZE*2) - GameConstants.MAX_MAP_SIZE);
            int dy = (int)(Math.random()*(GameConstants.MAX_MAP_SIZE*2) - GameConstants.MAX_MAP_SIZE);
            exploreTarget = new Location(X+dx, Y+dy); // ?
            if (isPositive(exploreTarget) && !hasVisited(exploreTarget) && inTheMap(exploreTarget)) return;
        }
    }

    void initialize(){
        if (initialized){
            //rc.setIndicatorDot(rc.getLocation(), 255, 0, 0);
            return;
        }
        while(initRow < GameConstants.MAX_MAP_SIZE){
            if (uc.getEnergyLeft() < initEnergyLeft) return;
            visited[initRow] = new boolean[GameConstants.MAX_MAP_SIZE];
            initRow++;
        }
        initialized = true;
    }

    void fillDirPath() {
        // for now, just filling dirpath for visionradius 30
        if (visionRadius == 82)
            dirPath = new Direction[]{Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST, Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.ZERO};
        else if (visionRadius == 61) dirPath = new Direction[]{Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST, Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.ZERO};
        else {
            dirPath = new Direction[0];
        }
    }

    void checkBounds(){
        checkNorth();
        checkSouth();
        checkEast();
        checkWest();
    }

    void checkNorth(){
        if (comm.getBound(comm.ubY) != null) return;
        Location loc = uc.getLocation().add(Direction.NORTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (visionRadius < 41) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (visionRadius < 50) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (visionRadius < 82) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.NORTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
    }

    void checkSouth(){
        if (comm.getBound(comm.lbY) != null) return;
        Location loc = uc.getLocation().add(Direction.SOUTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredNorth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (visionRadius < 41) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (visionRadius < 50) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (visionRadius < 82) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
        loc = loc.add(Direction.SOUTH);
        if (uc.isOutOfMap(loc)){
            comm.exploredSouth(loc.y);
            return;
        }
    }

    void checkEast(){
        if (comm.getBound(comm.ubX) != null) return;
        Location loc = uc.getLocation().add(Direction.EAST);
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (visionRadius < 41) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (visionRadius < 50) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (visionRadius < 82) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
        loc = loc.add(Direction.EAST);
        if (uc.isOutOfMap(loc)){
            comm.exploredEast(loc.x);
            return;
        }
    }

    void checkWest(){
        if (comm.getBound(comm.lbX) != null) return;
        Location loc = uc.getLocation().add(Direction.WEST);
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (visionRadius < 41) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (visionRadius < 50) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (visionRadius < 82) return;
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
        loc = loc.add(Direction.WEST);
        if (uc.isOutOfMap(loc)){
            comm.exploredWest(loc.x);
            return;
        }
    }
}
