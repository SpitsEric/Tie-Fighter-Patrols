//Eric Spits EMS220005
//CS 2336.005
//Professor Feng

//This program reads a text file of pilots names and their coordinates,
//Stores the coordinates and names in 1D and 3D arrays,
//Then calculates the area from the coordinates,
//And finally writes the pilots name followed by the calculated area into another text file

package tieFighterPatrols;

import java.util.*;
import java.io.*;
import java.io.IOException;

class Main{
	public static void main(String []args) {
		
		double coordinates[][][] = new double[20][16][2]; //storing coordinates
		
		String pilots[] = new String[20]; //storing pilots names
		
		int numOfCoordinates[] = new int[20]; //storing the num of coordinates entered for each pilot
		
		int counter = 0; // used for setting pilots and their coordinates in the correct array positions
		
		//prompt user for file name
		System.out.println("Please enter the name of the file.");
		System.out.println("The file must contain 20 or less pilots, with 16 or less sets of coordinates.");
		System.out.print("File name: ");
		Scanner scanner = new Scanner(System.in);
		String fileName = scanner.nextLine();
		
		//attempt to open file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName)); //file reader
			BufferedWriter writer = new BufferedWriter(new FileWriter("pilot_areas.txt")); //file writer, Enter file path here
			String line;
			
			//Loop reads all lines of file
			while ((line = reader.readLine()) != null)
			{
				String[] string = line.split("[, ]"); //splits line of the file into separate strings

				//storing data and calculating area
				storePilots(string, pilots, counter);
				storeCoordinates(string, coordinates, numOfCoordinates, counter);
				double pilot_area = calculateArea(coordinates,numOfCoordinates, counter);
				
				//writes pilot's name and area into new file "pilot_areas.txt"
				writer.write(pilots[counter] + " " + String.format("%.2f", pilot_area));
				writer.newLine();
				
				//display pilots and their area
				System.out.println(pilots[counter] + " " + String.format("%.2f", pilot_area));
				
				counter++; //increment next line of the file if it exists
			}
			//close file reader and writer
			reader.close();
			writer.close();
			
			//error if file is not found
		}catch(IOException e){			
			System.err.println("File not found.");			
		}
		scanner.close();	
	}
	
	//stores pilot names
	static void storePilots(String[] string, String pilots[], int counter) {
		pilots[counter] = string[0];
	}
	
	//stores coordinates
	static void storeCoordinates(String[] s, double coordinates[][][], int numOfCoordinates[], int counter) {
		
		int set = 0;
		
		for(int i = 1; i < s.length; i+=2) {			
			coordinates[counter][set][0] = Double.parseDouble(s[i]);
			coordinates[counter][set][1] = Double.parseDouble(s[i+1]);
			
			set++;
		}
		numOfCoordinates[counter] = set;
	}
	
	//calculates pilots area
	static double calculateArea(double coordinates[][][], int numOfCoordinates[], int counter) {
		double area = 0;
		
		for(int i = 1; i < numOfCoordinates[counter]; i++) {
			area += ((coordinates[counter][i][0])+(coordinates[counter][i-1][0]))*
					((coordinates[counter][i][1])-(coordinates[counter][i-1][1]));
		}
		area = Math.abs(area);
		area = area/2;
		
		return area;
	}
	
}