package people;
import java.util.Random;

import Main.Simulation;


/**
 * Base class which is inherited by every type of person and handles the ticking of each person through the method {@link #tickppl()}. Some functions are overwritten which include:
 * 
 * <pre>
 * {@link #addprobability(boolean)} 
 * {@link #generateDestFloor()}
 * </pre>
 * @author Riyad K Rahman
 * @see Client
 * @see Developer
 * @see Mugtome
 * @see Goggles
 * @see Employee
 * @see Maintanance
 */

public abstract class Person {

	public final String name;
	public int workcounter;
	public final int weight;
	public int destfloor;
	protected final float q;
	protected final float p;
	public int complaintcounter;
	public final Random randomobject;
	public boolean atfloor;					
	public boolean businessStatus;			
	public boolean genfloor;				// used to trigger generate a new floor function
	public int totalWaitTime; 				// used to hold the total waiting time of a person 
	
	/**
	 * This constructs a Person. The superclass which every person extends.
	 * 
	 * @param n : (Integer) name, sets the name of the object. In this class "client/crew" is used.
	 * @param wc : (Integer) work counter, is used to allow a client/crew to generate random probabilities to leave the building.
	 * @param w : (Integer) weight, is the weight of a person. In this class it is set to "1".
	 * @param f : (Integer) destination floor, the floor which a person wants to go to.
	 * @param p2 : (Float) probability of changing floors, 
	 * @param q2 : (Float) probability of client/crew spawn,
	 * @param cc : (Integer) Complaint counter, when a client/crew has waited for 60 ticks, a complaint is made.
	 * @param ran : (Random) Random object, whose seed is kept consistent throughout the simulation.
	 * @param crtflr : (Boolean) correct floor, when a client/crew  is on the correct floor it is used to begin determining the probability of a client/crew leaving the building. 
	 * @param busicomplete : (Boolean) business status, used to leave the building once a client/crew has finished their work.
	 */
	
	public Person(String n, int wc, int w, int f, float p2, float q2, int cc ,Random ran,boolean crtflr, boolean busicomplete) {
	
		this.name = n;
		this.workcounter = wc;
		this.weight = w;
		this.destfloor = f;
		this.p = p2;
		this.q= q2;
		this.complaintcounter = cc;
		this.randomobject = ran;
		this.atfloor = false;
		this.businessStatus = busicomplete;
	}
	
//----------------------------------------------------------person moving methods------------------------------------------------------------------------//

	/**
	 * Overridden by each type of person in their classes
	 * @return destfloor - returns the destination floor of a person 
	 */
	public int generateDestFloor() { return destfloor;}
	
	
	
	/**
	 * Overridden by each type of person in their classes
	 * @param correctfloor : used to trigger calculating probabilities to change floor.
	 */
	public boolean addprobability(boolean correctfloor) {
		return genfloor;
	}
	
	
	/**
	 * Main method to generate floors and calculate probabilities for each person.
	 */
	public void tickppl() {
		genfloor = addprobability(atfloor);
		if (genfloor == true) {
			generateDestFloor();
		}
	
	}
	

	

	
//----------------------------------------------------------setter and getter methods------------------------------------------------------------------------//
	
public String getname() { return name; }

public double getWeight() { return weight; }

public int getdestFloor() { return destfloor; }

public int setdestFloor(int d) { return destfloor = d; }

public void setatcorrectFloor(boolean x) {atfloor = x; }

public int getComplaintCount() {return this.complaintcounter;}

public void incComplaintCount() { complaintcounter++; }

public void resetComplaintCount() { complaintcounter = 0; }

public boolean getbuisnessstatus() { return businessStatus; }

public void setBstatus(boolean x) {businessStatus = x; }

public int getTotalWait() {return this.totalWaitTime;}	

}