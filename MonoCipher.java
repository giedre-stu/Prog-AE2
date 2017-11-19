/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		// create first part of cipher from keyword
		int i=0;
		cipher = new char [SIZE];
		for (i=0; i < keyword.length(); i++) 
		cipher[i] = keyword.charAt(i);
		
		// create remainder of cipher from the remaining characters of the alphabet	
		// index of
		for (int l=25; i<SIZE; l--)
			{int searchedValue = alphabet[l]; 
			boolean found=false;  
			
			for (int p=0; p<SIZE && !found; p++) 
				{ if (searchedValue == cipher[p]) 
				{found = true;}	
				}
			if (!found) {cipher[i]=(char)searchedValue; i++;}
		 
		}
		
		// print cipher array for testing and tutor
		System.out.print(cipher);
		System.out.println(); // new line to make output more readable
	} 
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{	
		int i = ch-'A';
		if (i >= 0 && i < SIZE-1)
			{ch=cipher[i];}
			return ch;
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{	
		for (int i=0; i<SIZE && ch!=cipher[i]; i++) {
			if (ch == cipher [i])
			 ch = alphabet[i];}
	
		return ch;
	}
}
