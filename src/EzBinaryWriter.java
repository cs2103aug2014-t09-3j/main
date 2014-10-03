import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 */

/**
 * @author Khanh
 *
 */
public class EzBinaryWriter extends FileOutputStream {

	/**
	 * @param arg0
	 * @throws FileNotFoundException
	 */
	public EzBinaryWriter(String arg0) throws FileNotFoundException {
		super(arg0);
	}

	public void write(int data, int numBytes) throws IOException{
		if (numBytes==1){
			write(data%256);
		} else {
			write(data/256,numBytes-1);
		}
	}
	
	public void writeString(String data) throws IOException{
		if (data.length()>0){
			write(data.charAt(0));
		} else {
			writeString(data.substring(1));
		}
	}
}
