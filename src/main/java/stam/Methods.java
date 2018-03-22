package stam;
/*** 
 * The Method that can be fail
 * @author grimbergs
 *
 */
public class Methods {

	
	
	/***
	 * Method that minus two numbers 
	 * @param num1
	 * @param num2
	 * @return
	 * @throws EllegalNumberException
	 */
	public static int calc(int num1 , int num2)throws EllegalNumberException // - declare throwing exception
	{
		
		
		if(num1 - num2 <=0)
		{
			// declare when to throw ( on which condition)
			throw new EllegalNumberException("its ellegal!!!");
		}
		
		return num1 - num2;
		
	}
}
