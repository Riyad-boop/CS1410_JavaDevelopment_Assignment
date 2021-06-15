module NewElevator {
	// We need these two modules from JavaFX and the other two are for JUnit
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires org.junit.jupiter.api;
	requires junit;

	// We have to export Main package so JavaFX can access it
	exports Main;
	// We have to export these packages so JUnit can access it
	exports people;
	exports Things;
	exports Alltest;
}