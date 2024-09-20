import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
	// its model number + its edition, "M" for a modified edition, otherwise a "0")
	// + Unique 6-digit ID prefixed by a single dash (e.g. : TA400M-256850,
	// MA8800-852696)

	// Plane types :
	// "F" stands for Fret Transport,
	// "P" stands for Passenger Tourism,
	// "M" stands for Military Transport,
	// "B" stands for Business Planes.

	private static Scanner scan = new Scanner(System.in);
	public static HashMap<String, HashMap<String, String>> planes = new HashMap<String, HashMap<String, String>>();
	private static ArrayList<String> planesToAdd = new ArrayList<String>();
	private static boolean frenchMode = false;

	public static void main(String[] args) {
		planesToAdd.add("FA3200-000001");
		planesToAdd.add("TD400M-972137");
		planesToAdd.add("TA3800-752820");
		planesToAdd.add("MB3200-047250");
		planesToAdd.add("MA380M-885985");
		planesToAdd.add("BA3000-432980");

		for (String plane : planesToAdd)
			AddIncomingPlaneByID(plane);

		int menuChoice = MainMenu(frenchMode);

		while (menuChoice != 6) {
			switch (menuChoice) {
			case 1:
				DisplayAllPlanes();
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:
				frenchMode = !frenchMode;
				break;
			default:
				break;
			}

			menuChoice = MainMenu(frenchMode);
		}

		System.out.println(frenchMode ? "À bientôt." : "Goodbye.");
	}

	// Displays the main menu
	private static int MainMenu(boolean french) {
		System.out.println("\n\n\n\n");
		
		if (french)
			System.out.println(
					"Bonjour, user 816409.\n\nMENU PRINCIPAL - Entrez votre choix.\n-------------------------------------------------------------------------------\n"
							+ "1. Afficher tous les avions\n" + "2. Rechercher un avion par mot-clé programme\n"
							+ "3. Ajouter ou supprimer une pièce sur un avion\n" + "4. Afficher le détail d'un avion\n"
							+ "5. Switch to English\n" + "6. Quitter");
		else
			System.out.println(
					"Welcome, user 816409.\n\nMAIN MENU - Please enter your choice.\n-------------------------------------------------------------------------------\n"
							+ "1. Display all planes\n" + "2. Search for a plane by program keyword\n"
							+ "3. Add or remove a part to/from a plane\n" + "4. Display detailled view about a plane\n"
							+ "5. Passer au Français\n" + "6. Exit");

		int result = 0;

		while (result < 1 || result > 6) {
			if (!scan.hasNextInt())
				scan.next();
			else
				result = scan.nextInt();
		}

		return result;
	}

	// Adds a new plane after checking its ID and authenticity
	private static boolean AddIncomingPlaneByID(String planeID) {
		if (planeID.toUpperCase().matches("^[BFMT]{1}[A-E]{1}[0-9]{2}0[0M]{1}-[0-9]{6}$")
				&& planes.get(planeID) == null) {
			HashMap<String, String> samplePlane = new HashMap<String, String>();
			samplePlane.put("program", planeID.substring(1, 6));
			samplePlane.put("type", planeID.substring(0, 1));
			samplePlane.put("actualState", "STD");
			samplePlane.put("uniqueID", planeID.substring(7));

			planes.put(planeID, samplePlane);
			return true;
		} else
			return false;
	}

	// Displays all planes in database
	private static void DisplayAllPlanes() {
		System.out.println(frenchMode ? "PROGRAMME :   TYPE D'AVION :            GÉNÉRATION :      ÉTAT ACTUEL :         IDENTIFIANT :" : "PROGRAM :     PLANE TYPE :              GENERATION :      ACTUAL STATE :        FULL ID :");

		// Formats each plane's data for further displaying
		for (Map.Entry<String, HashMap<String, String>> plane : planes.entrySet()) {
			String planeKey = plane.getKey();
			HashMap<String, String> planeInfo = plane.getValue();

			planeInfo.put("fullID", planeKey);
			planeInfo.put("generation", ((String) planeInfo.get("program")).substring(0, 1) + "              ");

			planeInfo.put("programDisplayed", planeInfo.get("program").substring(0, 4)
					+ (planeInfo.get("program").substring(4, 5).equals("M") ? "M" : " "));

			switch (planeInfo.get("type")) {
			case "F":
				planeInfo.put("fullType", "Fret Transport         ");
				break;
			case "T":
				planeInfo.put("fullType", "Passenger Tourism      ");
				break;
			case "M":
				planeInfo.put("fullType", "Military Transport     ");
				break;
			case "B":
				planeInfo.put("fullType", "Business Plane         ");
				break;
			default:
				planeInfo.put("fullType", "Transport not specified");
				break;
			}

			switch (planeInfo.get("actualState")) {
			case "STD":
				planeInfo.put("fullState", "In studying        ");
				break;
			case "CPT":
				planeInfo.put("fullState", "In conception      ");
				break;
			case "DEF":
				planeInfo.put("fullState", "In definition      ");
				break;
			case "BLD":
				planeInfo.put("fullState", "In build process   ");
				break;
			case "SVC":
				planeInfo.put("fullState", "In service         ");
				break;
			case "END":
				planeInfo.put("fullState", "Service closed     ");
				break;
			default:
				planeInfo.put("fullState", "State not specified");
				break;
			}

			System.out.println(planeInfo.get("programDisplayed") + "         " + planeInfo.get("fullType") + "   "
					+ planeInfo.get("generation") + "   " + planeInfo.get("fullState") + "   "
					+ planeInfo.get("fullID"));
		}
	}

	// Adds a set of parts to a specific plane
	// private static boolean AddPartsToPlane(String planeID, ArrayList<String>
	// parts)...

	// Removes a part from a specific plane
	// private static boolean RemovePartFromPlane(String planeID, String partID)...

	// Displays a detailed view about a specific plane
	// private static void DisplayPlaneDetails(String planeID)...
}
