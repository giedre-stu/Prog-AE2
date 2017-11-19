/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	
	private char [][] cipher;
	private int kLength;
	private int r;
        // more instance variables
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{	
		r = 0;
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			{alphabet[i] = (char)('A' + i);}
		
		// create cipher
	   		kLength = keyword.length();
	   		cipher = new char [kLength][SIZE];
	   		for (int i = 0; i < kLength; i++) {
	   			cipher[i][0] = keyword.charAt(i);
	   			
	   	//print out the rest of the alphabet
	   				for (int c=1; c<SIZE; c++) {
	   					if (cipher[i][c-1] == 'Z') {
	   						cipher [i][c] = 'A'; 
	   						} else {
	   						cipher[i][c] = (char) ((cipher[i][c-1])+(char)(1));}
	   				}
	   		}
	   		
	   		//system.out cipher
	   		for (int i = 0; i<kLength; i++) {
	   			for (int c=0; c<SIZE; c++) {
	   				System.out.print(cipher[i][c]);
	   			}
	   		System.out.println();
	   		}
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{	
		int i = ch-'A';
		
		if (r == kLength)
			{r=0;}

		if (i > 0 && i < SIZE-1)
				ch = cipher[r][i];
				r++;
			
				return ch;
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{	
		char decoded = ch;
		boolean found = false;
		
		if (r == kLength)
		{r=0;}
		
		for (int c=0; c<SIZE && !found; c++) {
			if (ch == cipher[r][c]) {
				decoded = alphabet[c];
				found=true;
				r++;}
		}//for loop end
		
		return decoded;
		  // replace with your code
	}
}
