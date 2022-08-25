package magicarrows;

import aic2022.user.Location;
import aic2022.user.UnitController;

// [0]: known bit | lbX (12 bits)
// [1]: known bit | lbY (12 bits)
// [2]: known bit | ubX (12 bits)
// [3]: known bit | ubY (12 bits)
// [4]: known bit | baseX (12 bits)
// [5]: known bit | baseY (12 bits)
// [6]: known bit | enemybaseX (12 bits)
// [7]: known bit | enemybaseY (12 bits)
// [8]: task (0 = normal, 1 = emergency, 2 = crunch)
// [9-22] Enemy logs and ids = id (6 bits) | x (10)
//                              id (6 bits) | y (10)

public class Communication {

    UnitController uc;
    BoundInfo lbX = null, lbY = null, ubX = null, ubY = null;
    Location baseLoc = null;
    Location baseLocEnemy = null;
    int task = 0;
    final int MAX_LOGGED_ENEMIES = 12;
    final int ENEMY_LOGS_ARRAY_START = 9;
    Location[] loggedEnemies;
    int localIndex = 0; // skips by 1

    Communication(UnitController uc){
        this.uc = uc;
        getBase();
        loggedEnemies = new Location[MAX_LOGGED_ENEMIES];
        localIndex = uc.readOnSharedArray(ENEMY_LOGS_ARRAY_START + MAX_LOGGED_ENEMIES*2);
    }

    void init() {
        updateBounds();
        updateEnemyBase();
        readTask();
    }

    void setTask (int code) {
        uc.writeOnSharedArray(8, code);
    }

    void readTask() {
        task = uc.readOnSharedArray(8);
    }

    void getBase() {
        int code = uc.readOnSharedArray(4);
        if (code > 0) {
            int x = code & 0xFFF;
            int y = uc.readOnSharedArray(5) & 0xFFF;
            baseLoc = new Location(x,y);
            return;
        }
        Location myLoc = uc.getLocation();
        uc.writeOnSharedArray(4, myLoc.x | 0x1000);
        uc.writeOnSharedArray(5, myLoc.y | 0x1000);
    }

    //returns nearest newly logged enemy
    Location getLoggedEnemies() {
        Location bestLoc = null;
        Location myLoc = uc.getLocation();
            int sharedIndex = uc.readOnSharedArray(ENEMY_LOGS_ARRAY_START + MAX_LOGGED_ENEMIES*2);
            while (localIndex != sharedIndex) {
                int code = uc.readOnSharedArray(ENEMY_LOGS_ARRAY_START + localIndex);
                int x = code;
                code = uc.readOnSharedArray(ENEMY_LOGS_ARRAY_START + localIndex + MAX_LOGGED_ENEMIES);
                int y = code;
                loggedEnemies[localIndex] = new Location(x, y);
                //uc.println(loggedEnemies[localIndex]);
                if (bestLoc == null) bestLoc = loggedEnemies[localIndex];
                else if (myLoc.distanceSquared(loggedEnemies[localIndex]) < myLoc.distanceSquared(bestLoc)) {
                    bestLoc = loggedEnemies[localIndex];
                }
                //uc.println("read enemy at location " + loggedEnemies[localIndex] + " at index " + localIndex);
                localIndex = (localIndex + 1) % MAX_LOGGED_ENEMIES;
            }
        return bestLoc;
    }

    void writeEnemyToLog(Location loc) {
            int sharedIndex = uc.readOnSharedArray(ENEMY_LOGS_ARRAY_START + MAX_LOGGED_ENEMIES*2);
            int code = loc.x;
            uc.writeOnSharedArray(ENEMY_LOGS_ARRAY_START + sharedIndex, code);
            code = loc.y;
            uc.writeOnSharedArray(ENEMY_LOGS_ARRAY_START + sharedIndex + MAX_LOGGED_ENEMIES, code);
            sharedIndex = (sharedIndex + 1) % MAX_LOGGED_ENEMIES;
            uc.writeOnSharedArray(ENEMY_LOGS_ARRAY_START + MAX_LOGGED_ENEMIES*2, sharedIndex);
            //uc.println("wrote enemy at location " + loc + " at " + sharedIndex);
    }

    void updateEnemyBase() {
        if (baseLocEnemy != null) return;
        int code = uc.readOnSharedArray(6);
        if (code > 0) {
            int x = code & 0xFFF;
            int y = uc.readOnSharedArray(7) & 0xFFF;
            baseLocEnemy = new Location(x,y);
            return;
        }
    }

    void writeEnemyBase(Location loc) {
        if (baseLocEnemy != null) return;
        uc.writeOnSharedArray(6, loc.x | 0x1000);
        uc.writeOnSharedArray(7, loc.y | 0x1000);
        baseLocEnemy = loc;
        uc.println("wrote enemy base at " + baseLocEnemy);
    }

    void updateBounds() {
        if (lbX == null) {
            int code = uc.readOnSharedArray(0);
            if (code > 0) lbX = new BoundInfo(code & 0xFFF, true);
        }
        if (lbY == null) {
            int code = uc.readOnSharedArray(1);
            if (code > 0) lbY = new BoundInfo(code & 0xFFF, true);
        }
        if (ubX == null) {
            int code = uc.readOnSharedArray(2);
            if (code > 0) ubX = new BoundInfo(code & 0xFFF, true);
        }
        if (ubY == null) {
            int code = uc.readOnSharedArray(3);
            if (code > 0) ubY = new BoundInfo(code & 0xFFF, true);
        }
    }

    void exploredNorth(int value){
        if (ubY == null){
            ubY = new BoundInfo(value, false);
            uc.writeOnSharedArray(3, value | 0x1000);
            uc.println(uc.getInfo().getID() + " wrote ubY at " + value);
            return;
        }
        ubY.value = value;
    }

    void exploredSouth(int value){
        if (lbY == null){
            lbY = new BoundInfo(value, false);
            uc.writeOnSharedArray(1, value | 0x1000);
            uc.println(uc.getInfo().getID() + " wrote lbY at " + value);
            return;
        }
        lbY.value = value;
    }

    void exploredEast(int value){
        if (ubX == null){
            ubX = new BoundInfo(value, false);
            uc.writeOnSharedArray(2, value | 0x1000);
            uc.println(uc.getInfo().getID() + " wrote ubX at " + value);
            return;
        }
        ubX.value = value;
    }

    void exploredWest(int value){
        if (lbX == null){
            lbX = new BoundInfo(value, false);
            uc.writeOnSharedArray(0, value | 0x1000);
            uc.println(uc.getInfo().getID() + " wrote lbX at " + value);
            return;
        }
        lbX.value = value;
    }

    Integer getBound(BoundInfo b){
        if (b == null) return null;
        return b.value;
    }

    class BoundInfo {
        int value;
        boolean known;
        BoundInfo(int value, boolean known){
            this.value = value;
            this.known = known;
        }
    }
}
