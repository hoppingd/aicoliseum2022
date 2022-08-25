package magicarrows;

import aic2022.user.UnitController;

public abstract class MyUnit {

    UnitController uc;
    Communication comm;
    Exploration explore;
    Pathfinding path;
    BFS bfs;

    public MyUnit(UnitController unitController) {
        uc = unitController;
        comm = new Communication(uc);
        explore = new Exploration(uc, comm);
        path = new Pathfinding(uc, explore, comm);
        bfs = new BFSBarb(uc, explore, comm);
    }

    abstract void play();

    void initTurn() {
        comm.init();
        explore.initTurn();
    }

    void endTurn() {
        explore.initialize();
        explore.markSeen();
    }
}
