import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//@author A0112129U
public class EzBinaryWriter extends FileOutputStream {

	/**
	 * this function will create a writer by a file name
	 * 
	 * @param arg0
	 *            is the file name
	 * @throws FileNotFoundException
	 */
	public EzBinaryWriter(String arg0) throws FileNotFoundException {
		super(arg0);
	}

	/**
	 * write the @param data into @param numBytes bytes
	 * 
	 * @param data
	 * @param numBytes
	 * @throws IOException
	 */
	public void write(int data, int numBytes) throws IOException {
		if (numBytes == 1) {
			write(data % 256);
		} else {
			write(data >> 8, numBytes - 1);
			write(data % 256);
		}
	}

	/**
	 * this function will write a string into the file
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void writeString(String data) throws IOException {
		if (data != null) {
			for (int i = 0; i < data.length(); i++) {
				write(data.charAt(i));
			}
		}
	}
}
