package people;

import java.util.Random;

public class Maintenance extends Person {
	/**
	 * This constructs a Maintenance crew, whose parameters are defined in the building class. 
	 * 
	 * @param n : (Integer) name, sets the name of the object. In this class "Maintenance crew" is used.
	 * @param wc : (Integer) work counter, is used to allow a Maintenance crew  to generate random probabilities to leave the building.
	 * @param w : (Integer) weight, is the weight of a person. In this class it is set to "1".
	 * @param f : (Integer) destination floor, the floor which a person wants to go to.
	 * @param P : (Float) probability of changing floors, 
	 * @param Q : (Float) probability of Client spawn,
	 * @param Cc : (Integer) Complaint counter, when a Maintenance crew has waited for 60 ticks, a complaint is made.
	 * @param r : (Random) Random object, whose seed is kept consistent throughout the simulation.
	 * @param crtflr : (Boolean) correct floor, when a Maintenance crew is on the correct floor it is used to begin determining the probability of changing floors.. 
	 * @param busicomplete : (Boolean) business status, used to leave the building once a Maintenance crew has finished their work.
	 */
	
	public Maintenance(String n,int id, int w, int f, float P, float Q, int Cc,Random r, boolean crtflr, boolean busicomplete)  {
		super(n,id,w,f,P,Q,Cc,r,crtflr,busicomplete);
	}
	
	
	
	
	
	
	
//-------------------------------------------------Maintenance crew  moving methods-----------------------------------------------------//

/**
 *  Manages probability of Maintenance crew leaving the building
 * @param correctfloor - boolean which is set to true when a Maintenance crew reaches their destination floor
*/	
	@Override
	public boolean addprobability(boolean correctfloor) {
		boolean genafloor = false;
		
//		Business last for count: 120 to 240 ticks
		if(correctfloor  == true) {	
			this.workcounter += 1;
			
				if(workcounter >= 120 && workcounter < 240) {
					double leaveprob;
					leaveprob = randomobject.nextDouble();
					
					if (leaveprob <  0.009 ) {
						genafloor = true;
					}
				}
				
				else if (workcounter >= 240) { genafloor = true; }
		}
		return genafloor;
	}
	

/**
 * Generates destination floor (ground floor) for a Maintenance crew when they are leaving.
 * @return destFloor - returns the destination floor which the  Maintenance crew wants to go to.
 */
	
	@Override 
	public int generateDestFloor() {
		setBstatus(true);
		return destfloor = 0;				// when work is done crew will go to floor
	}
	
	
}

