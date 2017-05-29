package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadObject {
	public DateSerial deserialzeDateSerial(String filename) {

		DateSerial date = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			date = (DateSerial) ois.readObject();

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

		return date;

	}

	public DateSerial deserialzeDateSerialJDK7(String filename) {

		DateSerial date = null;

		try (ObjectInputStream ois
			= new ObjectInputStream(new FileInputStream(filename))) {

			date = (DateSerial) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return date;

	}

}
