package common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteObject<T> {
	public void serializeObject(T obj, String file) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(obj);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void serializeObjectJDK7(T obj,String file) {

		try (ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream(file))) {

			oos.writeObject(obj);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
