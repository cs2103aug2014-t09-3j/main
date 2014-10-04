import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * this class is for reading data from a binary file.
 */

/**
 * @author Khanh
 *
 */
public class EzBinaryReader extends FileInputStream {

	/**
	 * @param arg0
	 * @throws FileNotFoundException
	 */
	public EzBinaryReader(String arg0) throws FileNotFoundException {
		super(arg0);
	}
	
	public int read(int numBytes) throws IOException{
		int result = 0;
		for(int i=0;i<numBytes;i++){
			try{
				result = (result<<8) + read();
			}
			catch  (IOException e){
				throw e;
			}
		}
		return result;
	}
	
	public String readString(int length) throws IOException{
		String result = "";
		if (length == 0) {
			return null;
		}
		for(int i=0;i<length;i++){
			try{
				result = result + (char) read();
			}
			catch  (IOException e){
				throw e;
			}
		}
		return result;
	}
}
