package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {

	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean moved;
	
	protected final void walk(int direction) {	
		energy -= Params.walk_energy_cost;
		
		if (!moved) {
			moved = true;
			switch (direction) {
			case 0:
				x_coord = (x_coord + 1) % Params.world_width;
				break;

			case 1:
				x_coord = (x_coord + 1) % Params.world_width;
				if (y_coord == 0) {
					y_coord = Params.world_height - 1;
				} else {
					y_coord--;
				}
				break;

			case 2:
				if (y_coord == 0) {
					y_coord = Params.world_height - 1;
				} else {
					y_coord--;
				}
				break;

			case 3:
				if (x_coord == 0) {
					x_coord = Params.world_width - 1;
				} else {
					x_coord--;
				}

				if (y_coord == 0) {
					y_coord = Params.world_height - 1;
				} else {
					y_coord--;
				}
				break;

			case 4:
				if (x_coord == 0) {
					x_coord = Params.world_width - 1;
				} else {
					x_coord--;
				}

				break;

			case 5:
				if (x_coord == 0) {
					x_coord = Params.world_width - 1;
				} else {
					x_coord--;
				}
				y_coord = (y_coord + 1) % Params.world_height;
				break;

			case 6:
				y_coord = (y_coord + 1) % Params.world_height;
				break;

			case 7:
				x_coord = (x_coord + 1) % Params.world_width;
				y_coord = (y_coord + 1) % Params.world_height;
			}
		}
	}
	
	protected final void run(int direction) {
		if(moved) {
			energy -= Params.run_energy_cost;
		}
		else {
			energy += (Params.walk_energy_cost * 2);
			energy -= Params.run_energy_cost;
			walk(direction);
			moved = false;
			walk(direction);
		}
	
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(energy >= Params.min_reproduce_energy) {
			offspring.energy = (energy/2);
			if(energy%2 == 1) {
				energy++;
			}
			energy = (energy/2);
			switch(direction) {
			case 0:
				offspring.x_coord = (offspring.x_coord+1) % Params.world_width;
				break;
				
			case 1:
				offspring.x_coord = (offspring.x_coord+1) % Params.world_width;
				if(y_coord == 0) {
					offspring.y_coord = Params.world_height-1;
				}
				else {
					offspring.y_coord--;
				}
				break;
				
			case 2:
				if(y_coord == 0) {
					offspring.y_coord = Params.world_height-1;
				}
				else {
					offspring.y_coord--;
				}
				break;
				
			case 3:
				if(x_coord == 0) {
					offspring.x_coord = Params.world_width-1;
				}
				else { 
					offspring.x_coord--;
				}
				
				if(y_coord == 0) {
					offspring.y_coord = Params.world_height-1;
				}
				else {
					offspring.y_coord--;
				}
				break;
				
			case 4:
				if(x_coord == 0) {
					offspring.x_coord = Params.world_width-1;
				}
				else { 
					offspring.x_coord--;
				}
				
				break;
				
			case 5:
				if(x_coord == 0) {
					offspring.x_coord = Params.world_width-1;
				}
				else { 
					offspring.x_coord--;
				}
				offspring.y_coord = (offspring.y_coord+1) % Params.world_height;
				break;
				
			case 6:
				offspring.y_coord = (offspring.y_coord+1) % Params.world_height;
				break;
				
			case 7:
				offspring.x_coord = (offspring.x_coord+1) % Params.world_width;
				offspring.y_coord = (offspring.y_coord+1) % Params.world_height;
			}
			babies.add(offspring);
		}
		return;
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	@SuppressWarnings("deprecation")
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			critter_class_name = "assignment4." + critter_class_name;
			Class<?> temp = Class.forName(critter_class_name);
			
			Critter critter = (Critter) temp.newInstance();
			
			critter.x_coord = getRandomInt(Params.world_width);
			critter.y_coord = getRandomInt(Params.world_height);
			critter.energy = Params.start_energy;
			critter.moved = false;
			population.add(critter);
			
		}
		catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		
	}
	
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		String class_name = "assignment4."+critter_class_name;
		List<Critter> result = new java.util.ArrayList<Critter>();
			for(Critter c: population) {
				if(c.getClass().getName().equalsIgnoreCase(class_name)) {
					result.add(c);
				}
			}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
		population.clear();
		babies.clear();
	}
	
	public static void worldTimeStep() {
		// Complete this method.
		for (int p = 0; p < population.size(); p++) {
			population.get(p).energy -= Params.rest_energy_cost;
			if(population.get(p).energy <= 0) {
				population.remove(p);
				if(p > 0) {
				p--;
				}
				break;
			}
			population.get(p).doTimeStep();
			if(population.get(p).energy <= 0) {
				population.remove(p);
				if(p > 0) {
				p--;
				}
				break;
			}
		}
		for (int a = 0; a < population.size()-1; a++) {
			int check_x = population.get(a).x_coord;
			int check_y = population.get(a).y_coord;
			
			for (int b = a+1; b < population.size(); b++) {
				
				if(check_x == population.get(b).x_coord && check_y == population.get(b).y_coord) {
					
					boolean A_fight = population.get(a).fight(population.get(b).toString());
					boolean B_fight = population.get(b).fight(population.get(a).toString());
					
					//This means they both haven't or can't flee
					if(check_x == population.get(b).x_coord && check_y == population.get(b).y_coord) {
						int A_roll = 0;
						int B_roll = 0;
						if(A_fight) {
							A_roll = getRandomInt(population.get(a).energy);
						}
						if(B_fight) {
							B_roll = getRandomInt(population.get(b).energy);
						}
						//A = B
						if(A_roll == B_roll) {
							population.get(a).energy += (population.get(b).energy/2);
							population.remove(b);
							if(a > 0) {
							a--;
							}
							if(b > 0) {
							b--;
							}
						}
						//A < B
						else if(A_roll < B_roll) {
							population.get(b).energy += (population.get(a).energy/2);
							population.remove(a);
							if(a > 0) {
							a--;
							}
							if(b > 0) {
							b--;
							}
						}
						//A > B
						else {
							population.get(a).energy += (population.get(b).energy/2);
							population.remove(b);
							if(a >0) {
							a--;
							}
							if(b >0) {
							b--;
							}
						}
					}
				}
			}
		}
		for(Critter c: population) {
			c.moved = false;
		}
		population.addAll(babies);
		babies.clear();
	//Algae refresh time
		for(int i = 0 ; i < Params.refresh_algae_count; i ++) {
			Critter temp = new Algae();
			temp.x_coord = getRandomInt(Params.world_width);
			temp.y_coord = getRandomInt(Params.world_height);
			temp.energy = Params.start_energy;
			population.add(temp);
		}
	}
	
	public static void displayWorld() {
		// Complete this method.
		boolean spotfound = false;
		System.out.print("+");	
		for(int i = 0 ; i < Params.world_width ; i++) {
			System.out.print("-");
		}
		System.out.print("+");
		System.out.println();
		for(int y = 0; y < Params.world_height; y++) {
			System.out.print("|");
			
			for(int x = 0; x < Params.world_width; x++) {
				
				for (int p = 0; p < population.size(); p++) {
					if(population.get(p).x_coord == x && population.get(p).y_coord == y) {
						spotfound = true;
						System.out.print(population.get(p).toString());
						break;
					}
				}
				
				if(!spotfound) {
					System.out.print(" ");
				}
				spotfound = false;
			}
			System.out.println("|");
		}
		
		System.out.print("+");	
		for(int i = 0 ; i < Params.world_width ; i++) {
			System.out.print("-");
		}
		System.out.print("+");
		System.out.println();
		//runStats(population); //For testing
	}
}
