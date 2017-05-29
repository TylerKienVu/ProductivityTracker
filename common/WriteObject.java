package common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteObject {
	public void serializeDateSerial(DateSerial date) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			fout = new FileOutputStream("src/resources/data/date.ser");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(date);

			System.out.println("Done");

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

	public void serializeDateSerialJDK7(DateSerial date) {

		try (ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream("src/resources/data/date.ser"))) {

			oos.writeObject(date);
			System.out.println("Done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
