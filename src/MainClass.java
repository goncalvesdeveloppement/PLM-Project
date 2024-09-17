import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * @author A. GONÇALVES
 * 
 * @version 0.0.1
 * 
 */

public class MainClass {

	// Plane ID =
	// Plane type representated by one single uppercase letter
	// + Program (one uppercase letter indicating its generation + two digits for
	// 		its model number + its edition, "M" for a modified edition, otherwise a "0")
	// + Unique 6-digit ID prefixed by a single dash (e.g. : TA400M-256850,
	// 		MA8800-852696)

	// Plane types :
	// "F" stands for Fret Transport,
	// "P" stands for Passenger Tourism,
	// "M" stands for Military Transport,
	// "B" stands for Business Planes.

	public static HashMap<String, HashMap<String, String>> planes = new HashMap<String, HashMap<String, String>>();
	private static ArrayList<String> planesToAdd = new ArrayList<String>();

	public static void main(String[] args) {
		planesToAdd.add("FA3200-000001");
		planesToAdd.add("TA400M-972137");
		planesToAdd.add("TA3800-752820");
		planesToAdd.add("MA3200-047250");
		planesToAdd.add("MA380M-885985");
		planesToAdd.add("BA3000-432980");

		for (String plane : planesToAdd)
			System.out.println(AddIncomingPlaneByID(plane));
	}

	// Adds a new plane after checking its ID and authenticity
	private static boolean AddIncomingPlaneByID(String planeID) {
		if (planeID.toUpperCase().matches("^[BFMT]{1}[A-E]{1}[0-9]{2}0[0M]{1}-[0-9]{6}$")
				&& planes.get(planeID) == null) {
			HashMap<String, String> samplePlane = new HashMap<String, String>();
			samplePlane.put("program", planeID.substring(1, 6));
			samplePlane.put("type", planeID.substring(0, 1));
			samplePlane.put("actualState", "CONCEPTING");
			samplePlane.put("uniqueID", planeID.substring(7));

			planes.put(planeID, samplePlane);
			return true;
		} else
			return false;
	}

	// Displays all planes in database
	// private static void DisplayAllPlanes()...
	
	// Adds a set of parts to a specific plane
	// private static boolean AddPartsToPlane(String planeID, ArrayList<String> parts)...
	
	// Removes a part from a specific plane
	// private static boolean RemovePartFromPlane(String planeID, String partID)...
	
	// Displays a detailed view about a specific plane
	// private static void DisplayPlaneDetails(String planeID)...
}
