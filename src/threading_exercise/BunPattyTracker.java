package threading_exercise;

/**
 * Tracks the number of buns and patties. A singleton class
 * @author delgallegon
 *
 */
public class BunPattyTracker {

	private static BunPattyTracker sharedInstance = null;
	
	public static BunPattyTracker getInstance() {
		return sharedInstance;
	}
	
	public static void initialize(int MAX_PATTY, int MAX_BUN) {
		sharedInstance = new BunPattyTracker(MAX_PATTY, MAX_BUN);
	}
	
	private int MAX_PATTY = 0;
	private int MAX_BUN = 0;
	
	//used for checking with MAX_PATTY and MAX_BUN
	private int pattyCounter = 0;
	private int bunCounter = 0;
	
	private int patties = 0;
	private int buns = 0;
	
	private int burgers = 0;
	
	private BunPattyTracker(int patty, int bun) {
		this.MAX_BUN = bun;
		this.MAX_PATTY = patty;
	}
	
	public void addPatty() {
		this.patties++;
		this.pattyCounter++;
	}
	
	public void addBuns() {
		this.buns++;
		this.bunCounter++;
	}
	
	public boolean reachedMaxPatty() {
		return (this.pattyCounter >= MAX_PATTY);
	}
	
	public boolean reachedMaxBun() {
		return (this.bunCounter >= MAX_BUN);
	}
	
	public int getNumPatties() {
		return this.patties;
	}
	
	public int getNumBuns() {
		return this.buns;
	}
	
	public void produceBurger(int pattyConsumed) {
		this.burgers++;
		
		if(this.patties - pattyConsumed >= 0) {
			this.patties -= pattyConsumed;
		}
		
		if(this.buns - 2 >= 0) {
			this.buns -= 2;
		}
	}
	
	public int getBurgersProduced() {
		return this.burgers;
	}
}
