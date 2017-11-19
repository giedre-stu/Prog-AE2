import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private String message; 
	private String keyword;
	private MonoCipher mcipher;
	private VCipher vcipher;
	private LetterFrequencies lfrequencies;
	
	private char inChar; 
	private char outChar;

	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{	
		boolean vigenere; 
		
		// if keyword and file name are valid
		if (getKeyword() && processFileName()) 
			
			/**reacts to MonoCipher / Vigenere buttons;
			creates a new cipher using the entered keyword & processes file;
			terminates after encryption / decryption is complete*/
			
			{ if (e.getSource()==monoButton)	
		    					{mcipher = new MonoCipher(keyword);
		    					vigenere = false;
		    					if (processFile(vigenere))
			    			 		System.exit(0);
		   					}		
		
			if (e.getSource()==vigenereButton) 
				    	{vcipher = new VCipher(keyword);
				    			vigenere = true;
				    			 	if (processFile(vigenere))
				    			 		System.exit(0);
				    			 }
			}
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	
	private boolean getKeyword()
	{ 	
		keyword = (keyField.getText());
		boolean isValid = true;
		
		
		if (keyword == null || keyword.isEmpty())
			{isValid = false;} 
		
		for (int i=0; i<keyword.length() && isValid; i++)  
				{char ch = keyword.charAt(i);
						
					if (!Character.isUpperCase(ch) || !Character.isLetter(ch)) 
						{isValid = false;}
					
					for (int p=0; p<=i-1 && isValid; p++)
							{char test = keyword.charAt(p);
								if (test==ch)
									isValid = false;
								} 	
				}
					
		if (!isValid) 
				{JOptionPane.showMessageDialog(null,	
							"Please enter a valid keyword");
				resetTextFields("k");
				return false;}
		
		return true;	
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 * @throws IOException 
	 */
	
	private boolean processFileName() 
	{	
		String fName = messageField.getText();
		
		//extracts the message
		if (fName.length()>0)
			inChar = fName.charAt(fName.length()-1);
				if (inChar=='P' || inChar == 'C')
					{switch(inChar)
						{ case 'P': outChar = 'C';
							break;
						case 'C': outChar = 'D';
							break;
						}
						
					message = fName.substring(0, fName.length()-1);	
					
				return true;
			}
				
		else
		{JOptionPane.showMessageDialog(null, 
				"Please enter a valid filename");
		resetTextFields("m");
		return false;
		}
	}
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{	
			// String inFile = message + inChar + ".txt";
			
			try 
			{	FileReader reader = new FileReader(message + inChar + ".txt");
				PrintWriter writer = new PrintWriter(message + outChar + ".txt");
				lfrequencies = new LetterFrequencies();
			 
				// reads & processes characters 1 by 1
				for (int next = 0; next >= 0;) 
					{ 	next = reader.read();
						char ch = processCharacter((char)next, vigenere);
						writer.write(String.valueOf(ch));
					}
				
				reader.close();
				writer.close();
				
					// prints out report
						try { PrintWriter rWriter = new PrintWriter(message+"F.txt");
							rWriter.write(lfrequencies.getReport()); 
							rWriter.close();
							}
						finally 
						{ JOptionPane.showMessageDialog(null,
								"File processed successfuly");
						}			
			} 	
			
			catch (FileNotFoundException f)  
			{ JOptionPane.showMessageDialog(null,
						"File not found");
			resetTextFields("m");
			return false; 
			}
			
			catch (IOException e)  
				{ JOptionPane.showMessageDialog(null,
						"Unknown problem reading from the file");
				resetTextFields("m");
				return false; 
				}
			
			return true;		
	}

	private char processCharacter (char ch, boolean vigenere) 
	{	
		if (!vigenere) 
			{ switch (inChar) 
				{ 	case 'P': ch = mcipher.encode(ch);
					case 'C': ch = mcipher.decode(ch);
				}
			}
		if (vigenere)  
			{ switch (inChar) 
				{ 	case 'P': ch = vcipher.encode(ch);
					case 'C': ch = vcipher.decode(ch);
				}
			}
		
		lfrequencies.addChar(ch);
		return ch;
	}
	
	// resets text fields
	// @param field to reset
	private void resetTextFields (String field) 
	
		{ if (field.equals("k"))
				{keyField.setText("");}
		if (field.equals("m"))
				{messageField.setText("");}
		} 
}
