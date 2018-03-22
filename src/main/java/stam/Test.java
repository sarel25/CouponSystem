package stam;


import javax.swing.JOptionPane;


public class Test {

	public static void main(String[] args)  {

		// trying this code
		try {
			JOptionPane.showMessageDialog(null, Methods.calc(1, 3));
			
		}
		// if its fails -> run this code
		catch (EllegalNumberException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		
	
	}

}
