/************
 * @author Gabriel Eller
 * Class takes in .asm file and reads file for labels first, and then variable symbols. 
 * Both are stored in the symbols HashMap
 ***********/

package CS220Assembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SymbolMapper {
	
	private HashMap<String, String> symbols;
	private Scanner reader;
	private int variableCount;
	private int lineCount;
	
	public SymbolMapper() {
		symbols = new HashMap<String,String>();
		reader = null;
		variableCount = 15;
		lineCount = 0;
	}
	
	public SymbolMapper(File file) throws FileNotFoundException {
		symbols = new HashMap<String, String>();
		reader = new Scanner(file);
		variableCount = 15;
		lineCount = 0;
		fillMap(file);
	}
	
	//Function fills symbol map with label first, and then variables
	//Pre-Condition: Passed file must exist and be filled with assembly instructions
	//Post-Condition: symbols HashMap is filled with label/lineNumber and variable/register pairs 
	public void fillMap(File file) throws FileNotFoundException {
		String line = "";
		while(reader.hasNext()) {
			line = reader.nextLine();
			line = Assembler.getCleanLine(line);
			if(line.startsWith("(")) { //Check for labels first
				if (symbols.get(line.substring(1, line.length() - 1)) == null) {//label has not been added yet
					symbols.put(line.substring(1, line.length() - 1), "0" + Assembler.doubleDabble(lineCount)); 
																	//Name is stored with current line of label
				}
			} else if (line.startsWith("//") || line.equals("")) {
				continue;
			} else {
				lineCount++;
			}
			
		}
		
		//Adding special constants
		symbols.put("SCREEN", "0" + Assembler.doubleDabble(16384));
		symbols.put("KBD", "0" + Assembler.doubleDabble(24576));
		//Close Scanner and reopen to read second time, finding variable names
		reader.close();
		reader = new Scanner(file);
		line = "";
		while (reader.hasNext()) {
			line = reader.nextLine();
			line = Assembler.getCleanLine(line);
			if(line.startsWith("@") && line.length() != 0) { //Second go around checks for variable names
				if(symbols.get(line.substring(1)) != null) { //If name is already in symbol map, symbol is for label
					//do nothing
				} else { //If null is returned, variable name needs to be added
					symbols.put(line.substring(1), "0" + Assembler.doubleDabble(variableCount));
					variableCount++;
				}
			}
		}
		
	}
	
	//Function returns matching value of passed key
	//Pre-condtion: passed key must be a string
	//Post-condition: if key has a matching value, return value; else return null
	public String get(String key) {
		return symbols.get(key);
	}
}

