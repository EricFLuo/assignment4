package assignment4;
import assignment4.Critter.TestCritter;

public class Critter2 extends Critter{
	
	private static int dir;
	public Critter2() {
		dir = getRandomInt(8);
	}
	@Override
	/**
	 * Critter's ASCII is '2'
	 */
	public String toString() {
		return "2";
	}
	/**
	 * in this time step, it will always walk in 1 direction which was chosen randomly. If it has more than
	 * 20 energy, create a child 1 step counterclockwise of the direction it travels
	 */
	public void doTimeStep() {
		walk(dir);
		if(getEnergy() > 20) {
			Critter2 child = new Critter2();
			reproduce(child, ((dir+1)%7));
		}
		
	}
	/**
	 * Fight routine, which is to always fight.
	 */
	public boolean fight(String not_used) {
		return true;
	}
}

