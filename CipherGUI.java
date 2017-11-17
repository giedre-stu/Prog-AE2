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
	private PrintWriter writer;
	private FileReader reader;
	
	private char inType; 
	private char outType;
	private boolean vigenere;
	
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
    		if (getKeyword() && processFileName())	
    				{ if (e.getSource() == monoButton) 
		    			{ mcipher = new MonoCipher(keyword);
				    		vigenere = false;}
						
					}    		
				else if (e.getSource() == vigenereButton) 
		    			{ vcipher = new VCipher(keyword);
		    			vigenere = true;
					} 
    		
    		// exits if processing has been successful
    		if (processFile(vigenere)==true)
    			{System.exit(0);}	
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	
	private boolean getKeyword()
	{ 	
		keyword = (keyField.getText());
		int kLength = keyword.length();
		boolean isValid = false;
		
		// checks the validity of each char
		for (int i=0; i<kLength; i++) {
			
			char ch = keyword.charAt(i);
			
			// checks if char is unique
			boolean unique = true;
			for (int p=0; p<i && unique; p++ ) {  
					char test = keyword.charAt(p);
					if (test==ch)
						unique = false;}
			
			// checks if char meets all conditions
			if (Character.isUpperCase(ch) && unique) {
				isValid = true;
				} else {
					isValid = false;}
			}//for loop ends
			
		// determines the output & return value 
		if (!isValid) {
			JOptionPane.showMessageDialog(null, "Please enter a valid keyword");
			resetTextFields(keyword);
			return false;
		} else {
			return true;}
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
		int LENGTH = fName.length();
		
		//extracts the message
		//calls method to determine if file is encoded
		message = fName.substring(0, LENGTH-1);
		char fType = fName.charAt(LENGTH-1);
		if (fType=='P' || fType == 'C') 
			{inType = fType;
					switch(fType)
					{case 'P': outType = 'C';
					case 'C': outType = 'D';}
			}
		
		else
		{JOptionPane.showMessageDialog(null, "Please enter a valid filename");
		resetTextFields(message);
		return false;}
				
		message = fName.substring(0, LENGTH-1);
		return true;
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
		try 	{  
			String inName = message + inType + ".txt";
			reader = new FileReader(inName);
			writer = new PrintWriter(message + outType + ".txt");
			lfrequencies = new LetterFrequencies();
			 
				for (int next = 0; next >= 0;) {
						try {next = reader.read();}
						
						catch (IOException e) {
							JOptionPane.showMessageDialog(null,"Problem reading from a file");
							resetTextFields(message);
							return false; }
						
						char ch = (char)next;
						char c = 0;
					
							if (!vigenere) {
								c = mcipher.encode(ch);}
							// here
							if (vigenere)  {
								c = vcipher.encode(ch);}

						lfrequencies.addChar(c);}
				
					// prints out report
							try {PrintWriter rWriter = new PrintWriter(message+"F.txt");
								rWriter.write(lfrequencies.getReport()); 
								rWriter.close();}
					
							finally {JOptionPane.showMessageDialog(null,"File processed successfuly");}
						return true;
				} 
			
			catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"File not found");
				resetTextFields(message);
				return false;}
	}

	private void resetTextFields(String field) {
		
		if (field.equals(keyword))
				{keyField.setText("");}
		if (field.equals(message))
				{messageField.setText("");}
		} 
}
