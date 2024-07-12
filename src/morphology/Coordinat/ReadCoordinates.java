package morphology.Coordinat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCoordinates {
	public static void main(String[] args) {

		try (BufferedReader reader = new BufferedReader(new FileReader("contour_coordinates2.txt"))) {

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
