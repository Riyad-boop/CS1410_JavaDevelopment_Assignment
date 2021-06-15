package people;

import java.util.Random;

/**
 *  Developer is a subclass of Person. it inherits all the methods from Person, but has it's own constructor method, as well as overwritten methods, which include:
 * 
 * <pre>
 * {@link #addprobability(boolean)} 
 * {@link #generateDestFloor()}
 * </pre> 
 * @author Lily Martin 
 * @see Person
 * @see Mugtome
 * @see Goggles
 */

public class Developer extends Person{

	/**
	 * This constructs a Developer, whose parameters are defined in the building class. 
	 * 
	 * @param n : (Integer) name, sets the name of the object. In this class "Developer" is used.
	 * @param wc : (Integer*) work counter, is unused for developers as they do not leave the building.
	 * @param w : (Integer) weight, is the weight of a person. In this class it is set to "1".
	 * @param f : (Integer) destination floor, the floor which a person wants to go to.
	 * @param P : (Float) probability of changing floors, 
	 * @param Q : (Float) probability of Client spawn,
	 * @param Cc : (Integer) Complaint counter, when a Developer has waited for 60 ticks, a complaint is made.
	 * @param r : (Random) Random object, whose seed is kept consistent throughout the simulation.
	 * @param crtflr : (Boolean) correct floor, when a Developer is on the correct floor it is used to begin determining the probability of changing floors.. 
	 * @param busicomplete : (Boolean*) business status, is unused for developers as they do not leave the building.
	 */
	
	public Developer(String n,int wc, int w, int f, float P, float Q, int Cc,Random r, boolean crtflr, boolean busicomplete)  {
		super(n,wc,w,f,P,Q,Cc,r,crtflr,busicomplete);
	}
	
	

	
//----------------------------------------------------------developer moving methods------------------------------------------------------------------------//	

	/**
	 * Manages probability of developer moving floor
	 * @param correctfloor - boolean which is set to true when a developer reaches their destination floor
	 */

	@Override
	
	public boolean addprobability(boolean correctfloor ) {
		boolean genafloor = false;
		
		double probability;
		probability = randomobject.nextDouble();
		
		if (probability < this.p ) {
			genafloor = true;
			
		}
		
		return genafloor;
	}
	
	
	
/**
 * generates a random destination floor between 3 and 6.
 * @return destFloor - returns the destination floor which the developer wants to go to. 
 */
	@Override 
	public int generateDestFloor() {
		return destfloor = randomobject.nextInt((6 - 3) + 1) + 3;				
	}
	
}








































//By Riyad Rahman- 180129650
