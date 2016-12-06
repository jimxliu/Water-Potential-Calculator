package webstart;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;

public class WPC extends JFrame{
	private JPanel pA; // pA contains all the entries(higher, lower and dry readings).
	private JPanel pB; // pB contins all the buttons.
	private JPanel pC; // pC shows the outcome of the WP calculation
	private JPanel hPanel;  // The panel for higher standard and readings.
	private JPanel lPanel;  // The panel for lower standard and readings.
	private JPanel dPanel;  // The panel for dry readings.
	private JTextField hStands; // higher standard water potential
	private JTextField lStands; // lower standard water potential
	private JTextField[] hr;   // An array of higher standard readings.
	private JTextField[] lr;    // An array of lower standard readings.
	private JTextField[] dr;    // An array of dry readings.
	private JButton submitButton;
	private JButton resetButton;
	private JButton cancelButton;
	private JTextField wp;   // Calculated water potential.
	private JTextField slope; // Show calculated slope.
	
	public WPC(){
		
		Container cp = this.getContentPane();
	    cp.setLayout(new BorderLayout());
		pA = new JPanel();
		pA.setLayout(new BoxLayout(pA, BoxLayout.X_AXIS));
		pB = new JPanel(new FlowLayout());
		
		/**** Higher Panel ****/
		hPanel = new JPanel();  // Initialize hPanel.
		hPanel.setLayout(new GridLayout(4,1));
		JPanel p10 = new JPanel();    // p10 contains Higher Standard
		p10.add(new JLabel("Higher Standard(MPa)"));
        hStands = new JTextField(4);
		p10.add(hStands);
		hPanel.add(p10); // Add p10 to hPanel.
		
		hr = new JTextField[3];  // Make hr have 3 readings.
		for(int i=0; i<3; i++){
			hr[i] = new JTextField(4);
		}
	
		JPanel p11 = new JPanel();  // p11 contains "Read1" label and a textfield.
		p11.add(new JLabel("H.Read1"));
		p11.add(hr[0]);
		hPanel.add(p11); // add p11 to hPanel panel.
		JPanel p12 = new JPanel(); // p12 contains "Read2" label and textfield.
		p12.add(new JLabel("H.Read2"));
		p12.add(hr[1]);
		hPanel.add(p12); // Add p12 to hPanel.
		JPanel p13 = new JPanel(); // p13 contains "Read3" label and textfield.
		p13.add(new JLabel("H.Read3"));
		p13.add(hr[2]);
		hPanel.add(p13); // Add p13 to hPanel.
		
		/**** Lower Panel  ****/
		lPanel = new JPanel();  // Initialize lPanel.
		lPanel.setLayout(new GridLayout(4,1));
		JPanel p20 = new JPanel();    // p20 contains Lower Standard
		p20.add(new JLabel("Lower Standard(MPa)"));
        lStands = new JTextField(4);
		p20.add(lStands);
		lPanel.add(p20); // Add p20 to hPanel.
		
		lr = new JTextField[3];  // Make lr have 3 readings.
		for(int i=0; i<3; i++){
			lr[i] = new JTextField(4);
		}
		JPanel p21 = new JPanel();  // p21 contains "Read1" label and lr1 field.
		p21.add(new JLabel("L.Read1"));
		p21.add(lr[0]);
		lPanel.add(p21); // add p21 to lPanel.
		JPanel p22 = new JPanel(); // p22 contains "Read2" label and lr2 field.
		p22.add(new JLabel("L.Read2"));
		p22.add(lr[1]);
		lPanel.add(p22); // Add p22 to lPanel.
		JPanel p23 = new JPanel(); // p23 contains "Read3" label and lr3 field.
		p23.add(new JLabel("L.Read3"));
		p23.add(lr[2]);
		lPanel.add(p23); // Add p23 to lPanel.
		
		/**** Dry Panel  ****/
		dPanel = new JPanel();  // Initialize dPanel.
		dPanel.setLayout(new GridLayout(4,1));
		JPanel p30 = new JPanel();
		p30.add(new JLabel("Dry Reading"));
		dPanel.add(p30);
		
		dr = new JTextField[3];  // Make dr have 3 readings.
		for(int i=0; i<3; i++){
			dr[i] = new JTextField(4);
		}
		JPanel p31 = new JPanel();  // p31 contains "Read1" label and dr1 field.
		p31.add(new JLabel("D.Read1"));
		p31.add(dr[0]);
		dPanel.add(p31); // add p31 to dPanel.
		JPanel p32 = new JPanel(); // p32 contains "Read2" label and dr2 field.
		p32.add(new JLabel("D.Read2"));
		p32.add(dr[1]);
		dPanel.add(p32); // Add p32 to dPanel.
		JPanel p33 = new JPanel(); // p33 contains "Read3" label and dr3 field.
		p33.add(new JLabel("D.Read3"));
		p33.add(dr[2]);
		dPanel.add(p33); // Add p33 to dPanel.
		
		
		
		pA.add(hPanel);  // add panels for higher, lower ,and dry readings
		pA.add(lPanel);
		pA.add(dPanel);
		JPanel pA1 = new JPanel(); // notion lable
		pA1.add(new JLabel("Three readings for each column required to be statistically accurate.")); // Set limit on the number of reading entries.
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		pCenter.add(pA);
		pCenter.add(pA1);
		cp.add(pCenter, BorderLayout.CENTER);  // Layout hPanel/lPanel/dPanel in the center.
		
		/**Initialize the buttons.**/
		submitButton = new JButton("Submit"); 
		resetButton = new JButton("Reset");
		
		/**Layout the buttons to the bottom of the frame**/
		pB.add(submitButton);
	    pB.add(resetButton);
		cp.add(pB, BorderLayout.SOUTH);
		
		submitButton.addActionListener(new ActionListener(){ // Set up action of submitButton.
			@Override
			public void actionPerformed(ActionEvent e){

				double hsValue = 0;
				double lsValue = 0;
				try{
					hsValue = Double.parseDouble(hStands.getText()); //***Need to check validity
				} catch (Exception ex){
					hStands.setText("Invalid");
				}	
				try{
					lsValue = Double.parseDouble(lStands.getText());//*** Need to check validity
				} catch (Exception ex){
					lStands.setText("Invalid");
				}
				if(checkStandard(hsValue,lsValue) && !hStands.equals("Invalid") && !lStands.equals("Invalid") && isReadsValid(hr) && isReadsValid(lr) && isReadsValid(dr)){
					double outcome = wpCalculator(hsValue,meanRead(hr),lsValue,meanRead(lr),meanRead(dr));
					wp.setText(Double.toString(outcome));
					double s = slopeCalculator(hsValue,meanRead(hr),lsValue,meanRead(lr));
			        slope.setText(Double.toString(s));
					if(s < 30 || s > 50)
						slope.setForeground(Color.RED);
					else
						slope.setForeground(Color.BLACK);					
				} else {
					wp.setText("#####");
					slope.setText("#####");
				}
			}			
		});
	
		resetButton.addActionListener(new ActionListener(){ // Set up action of resetButton.
			@Override
			public void actionPerformed(ActionEvent e){
				hStands.setText("");
				lStands.setText("");
				for(int i=0; i<hr.length; i++){
					hr[i].setText("");
					lr[i].setText("");
					dr[i].setText("");
				}
				wp.setText("");
				slope.setText("");
			}
		});
		
		
		/**Initialize the result field and position it to the to top of the frame**/
		wp = new JTextField();
		wp.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		wp.setColumns(5);
		wp.setEditable(false);
		wp.setBackground(Color.WHITE);
		pC = new JPanel();
		pC.add(new JLabel("Water Potential(MPa):"));
		pC.add(wp);
		pC.add(new JLabel(" Slope:"));
		slope = new JTextField();
		slope.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		slope.setColumns(5);		
		slope.setEditable(false);
		slope.setBackground(Color.WHITE);
		pC.add(slope);
		cp.add(pC, BorderLayout.NORTH);
		
		/**Specify the paramters of JFrame**/	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Water_Potential_Calculator");
		setSize(550,230);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}
	
	public boolean isEmpty(String s){  // To see if the textfield is empty.
	return s.equals("");
	}
	
	public boolean checkStandard(double h, double l){  // check if the water potential of high standard solution is higher than the low standard solution
		return h > l;
	}
	
    public double meanRead(JTextField[] jtc){   // return the average of the reads
		double sum = 0;
		for(int i=0; i<jtc.length; i++){
			if(!isEmpty(jtc[i].getText())){
				try{
				 sum += Double.parseDouble(jtc[i].getText());
				} catch(NumberFormatException e){
					jtc[i].setText("Invalid");
					wp.setText("#####");
					slope.setText("#####");
				}
			} 
		}
		 return Math.round(sum/3*100.0)/100.0;
	}

	public boolean isReadsValid(JTextField[] list){
		for(JTextField jt: list){
			try {
				double d = Double.parseDouble(jt.getText());
			}catch(NumberFormatException e){
				jt.setText("Invalid");				
				return false;
			}		
		}
		return true;
	}
	public double wpCalculator(double h, double hr, double l, double lr, double dr){  // return calculated water potential
	    double wp;
		wp = (hr-dr)/(hr-lr)*(l-h)+h;
		return Math.round(wp*100.0)/100.0;
	}
	public double slopeCalculator(double h, double hr, double l, double lr){ // return calculated slope
		double s =  (hr-lr)/(h-l);
		return Math.round(s*100.0)/100.0;
	}
	 
	public String getWP(){  // return the value of water potential on the textfield
		return wp.getText();
	}
	
	public String getSlope(){ // return the value of slope on the textfield
		return slope.getText();
	}
	
	/*public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new WPC();
			}
		});
		
	} */
}
