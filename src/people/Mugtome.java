package people;

import java.util.Random;

/**
 *  Mugtome is a subclass of developer. it inherits all the methods from developer, but has it's own constructor method.
 * 
 * @author Lily Martin 
 * @see Person
 * @see Developer
 */

public class Mugtome extends Developer {

	/**
	 * This constructs a Mugtome Developer, whose parameters are defined in the building class. 
	 * 
	 * @param n : (Integer) name, sets the name of the object. In this class "Mugtome Developer" is used.
	 * @param wc : (Integer*) work counter, is unused for developers as they do not leave the building.
	 * @param w : (Integer) weight, is the weight of a person. In this class it is set to "1".
	 * @param f : (Integer) destination floor, the floor which a person wants to go to.
	 * @param P : (Float) probability of changing floors, 
	 * @param Q : (Float) probability of Client spawn,
	 * @param Cc : (Integer) Complaint counter, when a Mugtome Developer has waited for 60 ticks, a complaint is made.
	 * @param r : (Random) Random object, whose seed is kept consistent throughout the simulation.
	 * @param crtflr : (Boolean) correct floor, when a Mugtome Developer is on the correct floor it is used to begin determining the probability of changing floors.. 
	 * @param busicomplete : (Boolean*) business status, is unused for developers as they do not leave the building.
	 */
	
	public Mugtome(String n,int id, int w, int f, float P, float Q, int Cc,Random r, boolean crtflr, boolean busicomplete)  {
		super(n,id,w,f,P,Q,Cc,r,crtflr,busicomplete);
	}
	

}
