package assignment4;
import assignment4.Critter.TestCritter;

public class Critter2 extends Critter{
	
	private static int dir;
	public Critter2() {
		dir = getRandomInt(8);
	}
	@Override
	public String toString() {
		return "2";
	}
	public void doTimeStep() {
		walk(dir);
		if(getEnergy() > 20) {
			Critter2 child = new Critter2();
			reproduce(child, dir);
		}
		
	}
	
	public boolean fight(String not_used) {
		return true;
	}
}

