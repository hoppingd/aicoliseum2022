package magicarrows;

import aic2022.user.UnitController;
import aic2022.user.UnitType;

public class UnitPlayer {

	public void run(UnitController uc) {
		/*Insert here the code that should be executed only at the beginning of the unit's lifespan*/

		MyUnit me;
		UnitType myType = uc.getType();
		if(myType == UnitType.BASE) me = new Base(uc);
		else if(myType == UnitType.EXPLORER) me = new Explorer(uc);
		else if(myType == UnitType.KNIGHT) me = new Knight(uc);
		else if(myType == UnitType.BARBARIAN) me = new Barbarian(uc);
		else if(myType == UnitType.CLERIC) me = new Cleric(uc);
		else if(myType == UnitType.RANGER) me = new Ranger(uc);
		else if(myType == UnitType.ASSASSIN) me = new Assassin(uc);
		else me = new Mage(uc);

		while (true) {
			me.initTurn();
			me.play();
			me.endTurn();
			uc.yield();
		}
	}
}

