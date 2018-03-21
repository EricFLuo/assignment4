package assignment4;
import assignment4.Critter.TestCritter;

public class Critter1 extends TestCritter{
	
	private static boolean heal;
	public Critter1() {
		heal = false;
	}
	@Override
	public String toString() {
		return "1";
	}
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
	
	public boolean fight(String not_used) {
		run(2);
		return false;
	}
}
