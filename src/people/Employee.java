package people;

import java.util.Random;

/**
 * Client is a subclass of Person. it inherits all the methods from Person, but has it's own constructor method, as well as overwritten methods, which include:
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

public class Employee extends Person {

	/**
	 * This constructs a Employee, whose parameters are defined in the building class. 
	 * 
	 * @param n : (Integer) name, sets the name of the object. In this class "Employee" is used.
	 * @param wc : (Integer*) work counter, is unused for Employee as they do not leave the building.
	 * @param w : (Integer) weight, is the weight of a person. In this class it is set to "1".
	 * @param f : (Integer) destination floor, the floor which a person wants to go to.
	 * @param P : (Float) probability of changing floors, 
	 * @param Q : (Float) probability of Client spawn,
	 * @param Cc : (Integer) Complaint counter, when a Employee has waited for 60 ticks, a complaint is made.
	 * @param r : (Random) Random object, whose seed is kept consistent throughout the simulation.
	 * @param crtflr : (Boolean) correct floor, when a Employee is on the correct floor it is used to begin determining the probability of changing floors.. 
	 * @param busicomplete : (Boolean*) business status, is unused for Employee as they do not leave the building.
	 */
	
	public Employee(String n,int id, int w, int f, float P, float Q, int Cc,Random r, boolean crtflr, boolean busicomplete)  {
		super(n,id,w,f,P,Q,Cc,r,crtflr,busicomplete);
	}
	
	

//----------------------------------------------------------Employee moving methods------------------------------------------------------------------------//	

/**
 * Manages probability of Employee moving floor
 * @param correctfloor - boolean which is set to true when a Employee reaches their destination floor
 */
	
	
	@Override
	public boolean addprobability(boolean correctfloor) {
		boolean genafloor = false;
		
		double probability;
		probability = randomobject.nextDouble();
		
		if (probability < this.p ) {
			genafloor = true;
			generateDestFloor();
		}
		
		return genafloor;
}
	
	
/**
 * generates a random destination floor between 0 and 6.
* @return destFloor - returns the destination floor which the Employee wants to go to. 
 */	
	@Override 
	public int generateDestFloor() {
		return destfloor = (int) (randomobject.nextInt(7));				// people can choose to stay on floor 0
	}
	
	
	
//----------------------------------------------------------setter method---------------------------------------------------------------//

}

