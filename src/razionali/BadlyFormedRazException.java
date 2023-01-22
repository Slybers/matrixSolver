
package razionali;


import java.io.IOException;

public class BadlyFormedRazException extends NumberFormatException
{	public int num, den;

    public BadlyFormedRazException(String s, int n, int d)
    {	super(s);
		num = n;
		den = d;
    }
	
}	