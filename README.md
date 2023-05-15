# CS1410_JavaDevelopment_Assignment

## 1.0 Project summary
A simulation of the elevator of an office building. Developers, other employees, clients, and maintenance crew will all attempt to use the elevator. The purpose of the simulation is to see how much internal and external activity we can handle in the building.

## 1.1 Types of people
Several types of people will queue up to use the elevator:
Employees who are not developers will attempt to go to any other floor with the same probability.
Developers only work in the top half of the building. Developers may randomly decide to move to
another floor in the top half.
Clients will enter the building and go to one of the floors in the bottom half of the building (which
may include the ground floor). After they complete their business, they will return to the ground
floor and leave.
Maintenance crews will arrive and go to the top floor, taking up 4 spaces with their equipment and
materials. After they complete their work, they will return to the ground floor and leave.

## 1.2 Main features of the simulation
Your simulation should model the building with a time resolution of 10 seconds (1 “tick”). It should
not be a real-time simulation: it is not that a tick should take 10 seconds to run, but rather that it
represents 10 seconds of the simulation. You should simulate a working day (8 hours).
The simulation is set up as follows:
1. The building has 7 floors (ground floor + floors 1–6) and 1 elevator with capacity for 4 people or
1 maintenance crew.
2. The elevator starts at the ground floor. If there are no pending requests, the elevator will go by
itself to the ground floor and rest there.
3. The elevator only moves when its doors are closed, and moving up or down one floor takes 1 tick.
When the elevator reaches one of the requested floors, it will open its doors in the same tick. The
doors will stay open until the elevator detects nobody entered or left in the last tick, and then they
will close. It takes 1 tick to open or close the doors.
Copyright (c) Aston University 2019
CS1410 Coursework Specification 2019-20 v1.2 3
4. The elevator keeps going in the same direction (either up or down) until there are no more requests
in that direction or it cannot go any further.
For instance, assume that the elevator is currently at floor 2 going up, and has requests for floors
1 (person A waiting to go to floor 6), 3 (person B riding the elevator) and 4 (person C waiting to
go to floor 5). The elevator could do this:
• Stop at floor 3: B leaves. Keep going up.
• Stop at floor 4: C enters and asks for floor 5. Keep going up.
• Stop at floor 5: C leaves. Since there are no requests further up, start going down.
• Stop at floor 1: A enters and asks for floor 6. Start going up.
5. Any number of people can leave and/or enter the elevator in 1 tick. Everyone who wants to leave
the elevator will leave first, and then the people who can enter the elevator will enter. If the elevator
reports that there is not enough space for someone to come in, they will have to wait and request
the elevator once more after it has left the current floor.
6. The user sets the number of employees and developers in the building. These enter the building
at the beginning of the simulation through the ground floor and stay within the building for the
entire simulation. No new employees or developers enter the building during the simulation.
7. Upon entering the building, employees and developers will immediately pick a floor to move to.
After they reach that floor, each employee and developer may independently decide to change floor
with probability p for each tick. Note that you can simulate probabilities by generating a random
value through the nextDouble() method in the java.util.Random class, and checking that the
generated value is less than p.
8. Clients will arrive at the ground floor with a probability of q. Between 10 and 30 minutes after
they arrive at their desired floor, they will go to the ground floor and leave the building.
9. Maintenance crews will arrive with a probability of 0.005 per tick. Between 20 and 40 minutes after
they arrive at their desired floor, they will also go to the ground floor and leave the building.
For the most basic version of this simulation (for a maximum mark of 69%), you can assume that the
elevator has only one “call” button (no “up” or “down” buttons), and the interface will be entirely in
text.

## 1.3 Advanced requirements
The developers would be now divided between two companies: Goggles and Mugtome. Their rivalry
is so fierce that their developers will not ride the elevator at the same time. If a developer sees a
rival riding the elevator, they will go back to the end of the queue and wait for the next elevator.
Clients will only wait for 10 minutes to take the elevator: if it takes any longer, they will file a
complaint and leave. To reduce the number of complaints, clients will now have priority when
entering the elevator: you may find priority queues useful to model this fact.

