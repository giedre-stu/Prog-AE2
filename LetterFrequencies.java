import java.util.Arrays;

/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	// 1% 
	private final double oneP = 0.01;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		// creates alphabet array
				alphabet = new char [SIZE];
				for (int i = 0; i < SIZE; i++)
					alphabet[i] = (char)('A' + i);
				
		// creates array with char frequencies 
				alphaCounts = new int [SIZE];		
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{ 	
		int i = 0;
		char searchedValue = ch; 
			
		boolean found=false;  
			
		for (i=0; i<25 && !found; i++) 
				{ if (searchedValue == alphabet[i]) 
					{ found = true;
					alphaCounts[i]++;
					totChars++;
					}
				}
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
        {
		// find the most frequent value
		int i;
		int max = 0;
		for (i=0; i<25; i++) {
			if (max < alphaCounts[i]) {
					max = alphaCounts[i];
					maxCh = alphabet[i];}
			}
		
		return (double)max/(totChars*oneP);
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{	
		String body = ("");
		getMaxPC();
		
		// create report elements
		String title = String.format("%s", "LETTER ANALYSIS");
		String topRow = String.format("\n \n%-10s %-10s %-10s %-10s %-10s\n", 
				"Letter", "Freq", "Freq%", "AvgFreq%", "Diff");
		String maxF = String.format("\n%s %c %s %.1f%s", 
				"The most frequent letter is", maxCh,"at", getMaxPC(), "%");
		
		// creates report body
		for (int i = 0; i < SIZE; i++){
			double freqPercent = (double)(alphaCounts[i])/(totChars*oneP);
			double diff = freqPercent-avgCounts[i];
				
				//creates a new line
				String newLine= (String.format("%-10s %-10d %-10.1f %-10.1f %-10.1f \n", 
						(char)('A' + i), alphaCounts[i], freqPercent, avgCounts[i], diff));
				
				body = body + newLine;
				}
		
		// concatenates & returns report String
		String report = title + topRow + body + maxF;
		return report;
	}
	
}
