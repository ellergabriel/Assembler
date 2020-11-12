/************
 * @author Gabriel Eller
 * Class stores separate HashMaps for the different commands in binary
 ***********/
package CS220Assembler;
import java.util.HashMap;

public class CInstructionMapper {
	HashMap<String, String> compMap;
	HashMap<String, String> destMap;
	HashMap<String, String> jumpMap;
	
	public CInstructionMapper(){
		compMap = new HashMap<String, String>(40);
		destMap = new HashMap<String, String>(8);
		jumpMap = new HashMap<String, String>(8);
		this.constructMap();
	}
	
	//Function fills the three HashMaps with the matching elements 
	//Pre-Condition: N/A
	//Post-Condition: All three maps are constructed and filled with needed information
	public void constructMap() {
		compMap.put("0", "0101010");
		compMap.put("1", "0111111");
		compMap.put("-1", "0111010");
		compMap.put("D", "0001100");
		compMap.put("A", "0110000");
		compMap.put("M", "1110000");
		compMap.put("!D", "0001101");
		compMap.put("!A", "0110001");
		compMap.put("!M", "1110001");
		compMap.put("-D", "0001111");
		compMap.put("-A", "0110011");
		compMap.put("-M", "1110011");
		compMap.put("D+1", "0011111");
		compMap.put("A+1", "0110111");
		compMap.put("M+1", "1110111");
		compMap.put("D-1", "0001110");
		compMap.put("M-1", "1110010");
		compMap.put("D+A", "0000010");
		compMap.put("A+D", "0000010");
		compMap.put("D+M", "1000010");
		compMap.put("M+D", "1000010");
		compMap.put("D-A", "0010011");
		compMap.put("D-M", "1010011");
		compMap.put("A-D", "0000111");
		compMap.put("M-D", "1000111");
		compMap.put("D&A", "0000000");
		compMap.put("A&D", "0000000");
		compMap.put("D&M", "1000000");
		compMap.put("M&D", "1000000");
		compMap.put("D|A", "0010101");
		compMap.put("A|D", "0010101");
		compMap.put("D|M", "1010101");
		compMap.put("M|D", "1010101");
		
		destMap.put(null, "000");
		destMap.put("M", "001");
		destMap.put("D", "010");
		destMap.put("MD", "011");
		destMap.put("A", "100");
		destMap.put("AM", "101");
		destMap.put("AD", "110");
		destMap.put("AMD", "111");
		
		jumpMap.put(null, "000");
		jumpMap.put("JGT", "001");
		jumpMap.put("JEQ", "010");
		jumpMap.put("JGE", "011");
		jumpMap.put("JLT", "100");
		jumpMap.put("JNE", "101");
		jumpMap.put("JLE", "110");
		jumpMap.put("JMP", "111");
	}
	
	//function returns matching value for passed key
	//Pre-Condition: Passed key must be a string
	//Post-condition: if key has a matching value, value is returned; else, null is returned 
	public String getDest(String dest) {
		return destMap.get(dest);
	}
	
	//function returns matching value for passed key
	//Pre-Condition: Passed key must be a string
	//Post-condition: if key has a matching value, value is returned; else, null is returned 
	public String getComp(String comp) {
		return compMap.get(comp);
	}
	//function returns matching value for passed key
	//Pre-Condition: Passed key must be a string
	//Post-condition: if key has a matching value, value is returned; else, null is returned 
	public String getJump(String jump) {
		return jumpMap.get(jump);
	}
}
