package magicarrows;

import aic2022.user.*;

public abstract class BFS {

    final int GREEDY_TURNS = 4;

    Pathfinding path;
    Exploration explore;
    UnitController uc;
    Communication comm;
    MapTracker mapTracker = new MapTracker();

    int turnsGreedy = 0;

    Location currentTarget = null;




    BFS(UnitController uc, Exploration explore, Communication comm){
        this.uc = uc;
        this.explore = explore;
        this.comm = comm;
        this.path = new Pathfinding(uc, explore, comm);
    }

    void reset(){
        turnsGreedy = 0;
        mapTracker.reset();
    }

    void update(Location target){
        if (currentTarget == null || target.distanceSquared(currentTarget) > 0){
            reset();
        } else --turnsGreedy;
        currentTarget = target;
        mapTracker.add(uc.getLocation());
    }

    void activateGreedy(){
        turnsGreedy = GREEDY_TURNS;
    }

    void move(Location target){
        move(target, false);
    }

    void move(Location target, boolean greedy){
        if (target == null) return;
        if (!uc.canMove()) return;
        if (uc.getLocation().distanceSquared(target) == 0) return;

        update(target);

        if (!greedy && turnsGreedy <= 0){

            //uc.println("Using bfs");
            Direction dir = getBestDir(target);
            if (dir != null && !mapTracker.check(uc.getLocation().add(dir)) && uc.canMove(dir) && isSafeFromBase(uc.getLocation().add(dir))){
                uc.move(dir);
                return;
            } else activateGreedy();
        }

        if (uc.getEnergyLeft() >= 2500) {
            //uc.println("Using greedy");
            path.move(target);
            --turnsGreedy;
        }
    }

    boolean isSafeFromBase(Location loc) {
        if (comm.baseLocEnemy == null) return true;
        if (comm.task == 2) return true;
        return loc.distanceSquared(comm.baseLocEnemy) > UnitType.BASE.getStat(UnitStat.ATTACK_RANGE) || (uc.canSenseLocation(comm.baseLocEnemy) && uc.isObstructed(loc, comm.baseLocEnemy, uc.getOpponent())); // what if obstructed? i guess we don't care
    }

    boolean doMicro() {
        return path.doMicro();
    }

    abstract Direction getBestDir(Location target);


}