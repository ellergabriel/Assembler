package CS220Assembler;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedWriter;
/**********************
 * @author Gabriel Eller
 * Main method takes in a .asm file and converts the contents into binary 
 */

class Assembler implements Serializable{

	
	public static void main(String[] args) throws FileNotFoundException, IOException{
			String instruction = "";
			File file = new File("Rect.asm");
			Scanner reader = new Scanner(file);
			FileWriter writer = new FileWriter("Rect.hack");
			BufferedWriter buWriter = new BufferedWriter(writer);
			String clean = "";
			
			//sym object reads file twice; first for labels, second for variables names
			SymbolMapper sym = new SymbolMapper(new File("Rect.asm"));
			
			while(reader.hasNext()) {
				CInstructionMapper map = new CInstructionMapper();

				// How would I clean this code?
				// 1) Take out comments
				// 2) Get rid of whitespace (\t, \n, spaces)
				clean = getCleanLine(reader.nextLine());
				String dest = null, comp = null, jump = null;
				
				try{
						//A commands
						if(clean.startsWith("@")) {
						try {
							int number = Integer.parseInt(clean.substring(1));
							String bi = "0" + doubleDabble(number);
							buWriter.flush();
							buWriter.write(bi + "\n");
						} catch (NumberFormatException n) { //If number format exception is caught, variable is named
							buWriter.flush();
							buWriter.write(sym.get(clean.substring(1)) + "\n"); //Variables already read and stored in sym
						}
						
						} else if((clean == null) || (clean.length() == 0)
								  || clean.startsWith("(")) {
							//do nothing if the line is empty or has label 
							
						//C-command 	
						} else {
							instruction = "111";
							int index = clean.indexOf("=");
							int jmp = clean.indexOf(";");
							if(index != -1) {
								dest = clean.substring(0, index);
								
								if(jmp != -1) {
								comp = clean.substring(index + 1, jmp);
								jump = clean.substring(jmp + 1);
								
								} else {
									comp = clean.substring(index + 1);
								}
							
							} else {
								if(jmp != -1) {
									comp = clean.substring(0, jmp);
									jump = clean.substring(jmp + 1);
								} else {
									comp = clean;
								}
								
							}
							
							instruction += map.getComp(comp);
							instruction += map.getDest(dest);
							instruction += map.getJump(jump);
							
							buWriter.flush();
							buWriter.write(instruction + "\n");
							instruction = "";
							
						}
				} catch (NoSuchElementException nos) {
					break;
				}
			}
			buWriter.close();
			reader.close();
		}
		
		//Function takes a string and removes all whitespace characters from the string
		//Pre-condition: Passed value must be a valid string
		//Post-condition: passed string is returned with all whitespace characters removed
		public static String getCleanLine(String raw) {
		// Remove whitespace (take out tabs, new lines, spaces)
		// trim() method on Strings => removes leading/trailing whitespace
		// replaceAll("want to replace", "replace it with")

		// replace all spaces with empty Strings
		// e.g. raw =   "\t0;JMP  //unconditional jump  "
		//      clean = "\t0;JMP//unconditionaljump"

		// REGEX => Regular Expressions
		// \\s  => all whitespace characters
		// \\S  => all non-whitespace characters
		// \\w  => all word characters (letters)
		// \\W  => all non-word characters (symbols, numbers, etc)

		String clean = raw.replaceAll("\\s", "");

		// How to search a String to see if it contains 
		// a specific character:  contains() or indexOf()
		// Check line to see if it contains comments:
		int index = clean.indexOf("//");
		// index == -1 means no comments
		if (index == -1)
		  return clean;
		else
		  return clean.substring(0, index);

		}
			
		//Function takes an int and converts it into 16 digit binary 
		//Pre condition: Passed integer must be of appropriate size
		//Post-condition: String containing the integer as a binary number is returned
		public static String doubleDabble(int dec){
			String str = "";
			String zero = "";
			str = doubleRecursive(dec, str);
			int zeros = 15 - str.length();
			for(int i = 0; i < zeros; i++) { //Loop adds however many 0s are needed to make 16 digits
				zero += "0";
			}
			zero += str;
			return zero;
		}
		
		//Function takes in an int and recursively converts it into binary
		//Pre-condition: passed integer must be positive and appropriate size
		//Post-condition: integer converted to a binary number is returned 
		public static String doubleRecursive(int dec, String str){
			if(dec / 2 == 0) {
				return Integer.toString(Math.abs(dec % 2));
			} 
			return str += doubleRecursive(dec / 2, str) + Integer.toString(Math.abs(dec % 2));
			
		}

}
