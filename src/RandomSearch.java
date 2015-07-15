package local.randomsearch;

import java.util.ArrayList;
import java.util.Random;

public class RandomSearch {
	public static void main (String args[]){

		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		Random randomGenerator = new Random();
		Agent agentA = new Agent();
		Agent agentB = new Agent();
		Board board = new Board(x,y);
		int steps = 0;

		//Place the agents on random board locations
		agentA.x = randomGenerator.nextInt(x);
		agentA.y = randomGenerator.nextInt(y);
		agentB.x = randomGenerator.nextInt(x);
		agentB.y = randomGenerator.nextInt(y);

		//Ensure that agents do not start in the same location
		if (agentA.y == agentB.y && agentA.x == agentB.x) {
			do {
				agentB.x = randomGenerator.nextInt(x);
				agentB.y = randomGenerator.nextInt(y);
			} while (agentA.y == agentB.y && agentA.x == agentB.x);
		}

		System.out.print("Agent A : " + agentA.x + "," + agentA.y + " ");
		System.out.println("Agent B : " + agentB.x + "," + agentB.y);

		while (!(agentA.y == agentB.y && agentA.x == agentB.x)) {
			Sector sectorA = board.sectors[agentA.x][agentA.y];
			Sector sectorB = board.sectors[agentB.x][agentB.y];
			System.out.print("Agent A : " + agentA.x + "," + agentA.y + " ");
			System.out.println("Agent B : " + agentB.x + "," + agentB.y);

			int directionIndexA = randomGenerator.nextInt(sectorA.actions.size());
			String directionA = sectorA.actions.get(directionIndexA);
			int directionIndexB = randomGenerator.nextInt(sectorB.actions.size());
			String directionB = sectorB.actions.get(directionIndexB);
			System.out.print("Agent A : " + sectorA.actions.get(directionIndexA) + " ");
			System.out.println("Agent B : " + sectorB.actions.get(directionIndexB));
			agentA.move(directionA);
			agentB.move(directionB);
			steps++;
		}
			System.out.println("Steps: " + steps);

	}
}

class Agent {

	int x;
	int y;

	public void move (String direction) {
		switch (direction) {
			case "left": x-- ;
				 break;
			case "right": x++ ;
				 break;
			case "up": y-- ;
				 break;
			case "down": y++ ;
				 break;
			default: System.out.println("Invalid direction");
		}
	}

}

class Sector {
	ArrayList<String> actions = new ArrayList<String> ();
	Sector() {
		actions.add("left");
		actions.add("right");
		actions.add("up");
		actions.add("down");
	}
}

class RightEdgeSector extends Sector  {
	RightEdgeSector() {
		actions.remove("right");
	}
}

class LeftEdgeSector extends Sector  {
	LeftEdgeSector() {
		actions.remove("left");
	}
}

class TopEdgeSector extends Sector  {
	TopEdgeSector() {
		actions.remove("up");
	}
}

class BottomEdgeSector extends Sector  {
	BottomEdgeSector() {
		actions.remove("down");
	}
}

class SectorFactory{

	int width, height;

	SectorFactory(int x, int y) {
		width = x;
		height = y;
	}

	public  Sector getSector(int i, int j) {
		// Sector is on the left edge
		if (i == 0) {
			LeftEdgeSector returnSector = new LeftEdgeSector();
			if (j == 0) {
				//This is the top left corner, remove ability to move up
				returnSector.actions.remove("up");
			}
			if (j == height - 1) {
				//This is the bottom left corner, remove ability to move down
				returnSector.actions.remove("down");
			}
			return returnSector;
		}
		//Sector is on the top edge
		else if (j == 0) {
			TopEdgeSector returnSector = new TopEdgeSector();
			if (i == width - 1) {
				//This is the top right corner, remove ability to go right
				returnSector.actions.remove("right");
			}
			return returnSector;
		}
		//Sector is on the bottom edge
		else if (j == height - 1) {
			BottomEdgeSector returnSector = new BottomEdgeSector();
			if (i == width - 1) {
				//This is the bottom right corner, remove ability to go right
				returnSector.actions.remove("right");
			}
			return returnSector;
		}
		//Sector is on the right edge
		else if (i == width - 1) { 
			return new RightEdgeSector();
		}
		else {
			//Create a normal sector that is not on an edge
			return new Sector();
		}
	}
}

class Board {

	Sector sectors[][];
	SectorFactory sectorFactory;

	Board(int width, int height) {
		sectorFactory = new SectorFactory(width, height);
		createBoard(width, height);
	}

	void createBoard(int width, int height) {
		sectors = new Sector[width][height];
		for (int i = 0; i < sectors.length; i++) {
			for (int j=0; j < sectors[i].length; j++){
				sectors[i][j] = sectorFactory.getSector(i,j);
			}
		}
	}
}



