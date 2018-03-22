package assignment4;
import assignment4.Critter.TestCritter;

public class Critter1 extends TestCritter{
	
	private static boolean heal;
	public Critter1() {
		heal = false;
	}
	@Override
	/**
	 * Critter's ASCII is '1'
	 */
	public String toString() {
		return "1";
	}
	/**
	 * Time step. Always run in a random direction, then every other other time step, it heals a certain amount. 
	 */
	public void doTimeStep() {
		run(getRandomInt(8));
		if(heal){
			heal = false;
			setEnergy(getEnergy() + (Params.photosynthesis_energy_amount*5));
		}
		else {
			heal = true;
		}
	}
	/**
	 * Never fight
	 */
	public boolean fight(String not_used) {
		return false;
	}
}
