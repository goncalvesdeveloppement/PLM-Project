import java.util.ArrayList;
import java.util.Arrays;
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
	public static HashMap<String, ArrayList<String>> planes = new HashMap<String, ArrayList<String>>();
	private static ArrayList<String> planesToAdd = new ArrayList<String>();
	private static boolean frenchMode = false;

	public static HashMap<Integer, ArrayList<String>> parts = new HashMap<Integer, ArrayList<String>>();

	public static void main(String[] args) {
		Initialize();

		int menuChoice = MainMenu(frenchMode);

		while (menuChoice != 6) {
			switch (menuChoice) {
			case 1:
				DisplayAllPlanes("");
				break;
			case 2:
				System.out.println(frenchMode ? "Entrez un mot-clé de recherche :" : "Enter a search keyword");
				String keyword = scan.next();
				DisplayAllPlanes(keyword);
				break;
			case 3:
				int partMenuChoice = PartMenu(frenchMode);

				while (partMenuChoice != 3) {
					switch (partMenuChoice) {
					case 1:
						DisplayAllParts();
						break;
					case 2:
						// Select a plane
						break;
					case 3:
						break;
					default:
						break;
					}

					partMenuChoice = PartMenu(frenchMode);
				}
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

	// Displays the part menu
	private static int PartMenu(boolean french) {
		System.out.println("\n\n\n\n");

		if (french)
			System.out.println(
					"Bienvenue dans la boutique de customisation.\n\nACCUEIL CUSTOM - Entrez votre choix.\n-------------------------------------------------------------------------------\n"
							+ "1. Lister les pièces disponibles\n" + "2. Sélectionner un avion pour modification\n"
							+ "3. Retourner au menu principal");
		else
			System.out.println(
					"Welcome to customs center.\n\nCUSTOMS HOME - Please enter your choice.\n-------------------------------------------------------------------------------\n"
							+ "1. Display all available parts\n" + "2. Select a plane to custom\n"
							+ "3. Back to main menu");

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
			ArrayList<String> samplePlane = new ArrayList<String>();
			samplePlane.add(planeID.substring(1, 6)); // 0 : Program
			samplePlane.add(planeID.substring(0, 1)); // 1 : Type
			samplePlane.add("STD"); // 2 : State
			samplePlane.add(planeID.substring(7)); // 3 : 6-digit ID

			planes.put(planeID, samplePlane);
			return true;
		} else
			return false;
	}

	// Add a new part
	private static void AddIncomingPart(String name, String category, String price) {
		int newID = parts.size() + 1;

		String[] arr = { name, category, price };
		parts.put(newID, new ArrayList<String>(Arrays.asList(arr)));
	}

	// Displays all planes in database (optionnally planes matching a specific
	// program keyword)
	private static void DisplayAllPlanes(String keyword) {
		System.out.println(frenchMode
				? "PROGRAMME :   TYPE D'AVION :            GÉNÉRATION :      ÉTAT ACTUEL :         IDENTIFIANT :"
				: "PROGRAM :     PLANE TYPE :              GENERATION :      ACTUAL STATE :        FULL ID :");
		boolean planesFound = false;

		// Formats each plane's data for further displaying
		for (Map.Entry<String, ArrayList<String>> plane : planes.entrySet()) {
			String planeKey = plane.getKey();
			ArrayList<String> planeInfo = plane.getValue();

			if (keyword != "" && !planeInfo.get(0).substring(0, 5).contains(keyword))
				continue;

			planesFound = true;
			planeInfo.add(planeKey); // 4 : Full ID
			planeInfo.add(((String) planeInfo.get(0)).substring(0, 1) + "              "); // 5 : Generation

			planeInfo.add(planeInfo.get(0).substring(0, 4) // 6 : Program (formatted)
					+ (planeInfo.get(0).substring(4, 5).equals("M") ? "M" : " "));

			switch (planeInfo.get(1)) { // 7 : Type (formatted)
			case "F":
				planeInfo.add("Fret Transport         ");
				break;
			case "T":
				planeInfo.add("Passenger Tourism      ");
				break;
			case "M":
				planeInfo.add("Military Transport     ");
				break;
			case "B":
				planeInfo.add("Business Plane         ");
				break;
			default:
				planeInfo.add("Transport not specified");
				break;
			}

			switch (planeInfo.get(2)) { // 8 : State (formatted)
			case "STD":
				planeInfo.add("In studying        ");
				break;
			case "CPT":
				planeInfo.add("In conception      ");
				break;
			case "DEF":
				planeInfo.add("In definition      ");
				break;
			case "BLD":
				planeInfo.add("In build process   ");
				break;
			case "SVC":
				planeInfo.add("In service         ");
				break;
			case "END":
				planeInfo.add("Service closed     ");
				break;
			default:
				planeInfo.add("State not specified");
				break;
			}

			System.out.println(planeInfo.get(6) + "         " + planeInfo.get(7) + "   " + planeInfo.get(5) + "   "
					+ planeInfo.get(8) + "   " + planeInfo.get(4));
		}

		if (!planesFound)
			System.out.printf(
					"\n\n" + (frenchMode ? "Aucun avion trouvé pour '%s'..." : "No plane found matching '%s '"),
					keyword);
	}

	// Displays all available parts
	private static void DisplayAllParts() {
		System.out.println(frenchMode ? "ID PIECE   NOM            CATÉGORIE           PRIX"
				: "PART ID    NAME           CATEGORY            PRICE");

		parts.forEach((key, value) -> {
			System.out.println(String.format("%-11s", key) + String.format("%-15s", value.get(0))
					+ String.format("%-20s", value.get(1)) + String.format("%-8s", value.get(2)));
		});

	}

	// Adds a set of parts to a specific plane
	// private static boolean AddPartsToPlane(String planeID, ArrayList<String>
	// parts)...

	// Removes a part from a specific plane
	// private static boolean RemovePartFromPlane(String planeID, String partID)...

	// Displays a detailed view about a specific plane
	// private static void DisplayPlaneDetails(String planeID)...

	// Testing initialization
	private static void Initialize() {
		// Planes adding
		planesToAdd.add("FA3200-000001");
		planesToAdd.add("TD400M-972137");
		planesToAdd.add("TA3800-752820");
		planesToAdd.add("MB3200-047250");
		planesToAdd.add("MA380M-885985");
		planesToAdd.add("BA3000-432980");

		for (String plane : planesToAdd)
			AddIncomingPlaneByID(plane);

		// Parts adding
		AddIncomingPart("Compass", "navigation", "10000");
		AddIncomingPart("GPS", "navigation", "25000");
		AddIncomingPart("Anti-collide", "safety", "150000");
		AddIncomingPart("Rockets", "conflicts", "300000");
		AddIncomingPart("KeroZEN Boost", "mechanical", "775000");
		AddIncomingPart("Climatisation", "comfort", "2500");
	}
}
