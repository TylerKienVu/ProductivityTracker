package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadObject<T> {
	@SuppressWarnings("unchecked")
	public T deserialzeObject(String filename) {

		T obj = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			obj = (T) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return obj;

	}

	@SuppressWarnings("unchecked")
	public T deserialzeObjectJDK7(String filename) {

		T obj = null;

		try (ObjectInputStream ois
			= new ObjectInputStream(new FileInputStream(filename))) {

			obj = (T) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return obj;

	}

}
