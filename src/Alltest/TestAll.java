package Alltest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Things.Floor;
import Things.LIFT;
import people.Client;
import people.Employee;
import people.Goggles;
import people.Maintenance;
import people.Mugtome;
import people.Person;

/**
 * Runs all tests in project
 * @author Riyad K Rahman
 * @author Amandeep Dhaliwal
 * @author Lily Martin
 */

@RunWith(Suite.class)
@SuiteClasses({ Things.AllTestsComponents.class , people.AllTestPeople.class })
public class TestAll {

}

