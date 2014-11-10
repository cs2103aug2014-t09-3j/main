import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//@author A0112129U
public class EzBinaryReader extends FileInputStream {

	/**
	 * this function will create a reader by a file name
	 * 
	 * @param arg0
	 *            is the file name to be read from
	 * @throws FileNotFoundException
	 */
	public EzBinaryReader(String arg0) throws FileNotFoundException {
		super(arg0);
	}

	/**
	 * read @param numBytes bytes and convert them into an integer
	 * 
	 * @param numBytes
	 * @return an integer
	 * @throws IOException
	 */
	public int read(int numBytes) throws IOException {
		int result = 0;
		for (int i = 0; i < numBytes; i++) {
			try {
				result = (result << 8) + read();
			} catch (IOException e) {
				throw e;
			}
		}
		return result;
	}

	/**
	 * this function will read a string from the file
	 * 
	 * @param length
	 *            is the length of the string to be read
	 * @return a string
	 * @throws IOException
	 */
	public String readString(int length) throws IOException {
		String result = "";
		for (int i = 0; i < length; i++) {
			try {
				result = result + (char) read();
			} catch (IOException e) {
				throw e;
			}
		}
		return result;
	}
}
