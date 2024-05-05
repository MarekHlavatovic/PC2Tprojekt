package Databaza;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Connection conn=null;
        Scanner scanner = new Scanner(System.in);
        Kniznica knihy = new Kniznica();
        try {
        	conn = Databaza.getConnection("C:\\Users\\hlava\\OneDrive\\Počítač\\PC2T");
            Databaza.initDb();
	            boolean stav = true;
		while(stav) {
			System.out.println("Zadaj požadovanú činnosť:");
			System.out.println("1:vlož knihu:");
			System.out.println("2:uprav knihu");
			System.out.println("3:zmaž knihu");
			System.out.println("4:označenie knihy ako požičaná/dostupná");
			System.out.println("5:výpis kníh v abecednom poradí");
			System.out.println("6:výpis knihy podľa názvu");
			System.out.println("7:vyhľadávanie kníh podľa mena autor chronologicky");
			System.out.println("8:vyhľadávanie kníh podľa zánru");
			System.out.println("9:výpis pozičaných kníh podľa druhu");
			System.out.println("10:uloženie do súboru");
			System.out.println("11:načítanie zo súboru");
            System.out.println("12:uloženie všetkých kníh do SQL");
            System.out.println("13:načítanie kníh z SQL");

		switch(scanner.nextInt()) {
			case 1:
				System.out.println("/nZadaj požadovanú činnosť:");
				System.out.println("1:vlož román:");
				System.out.println("2:vlož učebnicu");
				switch(scanner.nextInt()) {
					case 1:
						scanner.nextLine();
						System.out.println("Názov,Autor,Rok,Žáner");
						String nazovRomanu = scanner.nextLine();
                        String autorRomanu = scanner.nextLine();
                        int rokRomanu = scanner.nextInt();
                        scanner.nextLine();
                        String zanerRomanu = scanner.nextLine();
                        Roman roman = new Roman(nazovRomanu, autorRomanu, rokRomanu, zanerRomanu);
                        knihy.pridatKnihu(roman);
						break;
					case 2:
						scanner.nextLine();
						System.out.println("Názov,Autor,Rok,Vek");
						String nazovUcebnice = scanner.nextLine();
                        String autorUcebnice = scanner.nextLine();
                        int rokUcebnice = scanner.nextInt();
                        int vekovaKategoria = scanner.nextInt();
                        scanner.nextLine();
                        Ucebnica ucebnica = new Ucebnica(nazovUcebnice, autorUcebnice, rokUcebnice, vekovaKategoria);
                        knihy.pridatKnihu(ucebnica);
						break;
				}
				break;
			case 2:
				scanner.nextLine();
				System.out.println("Uprav knihu");
				System.out.println("Názov,Autor,Rok");
				String nazovKnihy=scanner.nextLine();
				String Autor=scanner.nextLine();
				int Rok=scanner.nextInt();
				knihy.upravKnihu(nazovKnihy,Autor,Rok);
				break;
			case 3:
				scanner.nextLine();
				System.out.println("Zmaž knihu");
				System.out.println("Názov");
				knihy.zmazKnihy(scanner.nextLine());
				break;
			case 4:
				scanner.nextLine();
				System.out.println("Zmeň dostupnosť knihy");
				System.out.println("Nazov,Dostupnosť(1=dostupná,ostatné čísla=nedostupná)");
				String nazov=scanner.nextLine();
				int stav1=scanner.nextInt();
				knihy.stavKnihy(nazov,stav1);
				break;
			case 5:
				System.out.println("Výpis kníh v abecednom poradí");
				knihy.vypisKnihyAbeceda();
				break;
			case 6:
				scanner.nextLine();
				System.out.println("Vyhľadanie knihy podľa názvu");
				String nazovKniha=scanner.nextLine();
				knihy.vypisPodlaNazvu(nazovKniha);
				break;
			case 7:
				scanner.nextLine();
				System.out.println("Vyhľadávanie kníh podľa mena autor chronologicky");
				String autor=scanner.nextLine();
				knihy.vypisChronologicky(autor);
				break;
			case 8:
				scanner.nextLine();
				System.out.println("Vyhľadávanie kníh podľa zanru");
				String zaner=scanner.nextLine();
				knihy.vypisChronologicky(zaner);
				break;
			case 9:
				System.out.println("Výpis pozičaných kníh podľa druhu");
				knihy.vypisDruhovKnih();
				break;
			case 10:
				scanner.nextLine();
				System.out.println("Uloženie do súboru");
				nazov=scanner.nextLine();
				knihy.ulozenieDoSuboru(nazov);
				break;
			case 11:
				System.out.println("Načítanie zo súboru");
				knihy.nacitajKnihu();
				break;
			case 12:
			    System.out.println("Ukladanie kníh do SQL databázy...");
			    List<Kniha> knihyNaUpload = knihy.vypisKnih();
			    Databaza.uploadVsetkyKnihy(knihyNaUpload);  
			    System.out.println("Všetky knihy boli úspešne uložené do databázy.");
			    break;
			case 13:
			    System.out.println("Načítanie kníh z SQL databázy...");
			    List<Kniha> nacitaneKnihy = Databaza.ziskatVsetkyKnihy();  
			    knihy.setKnihy(nacitaneKnihy);
			    System.out.println("Knihy boli úspešne načítané z databázy.");
			    break;  	
			default:
                System.out.println("Neplatná voľba!");
                break;
        }
    }
} catch (SQLException ex) {
    System.err.println("SQL chyba: " + ex.getMessage());
} catch (Exception e) {
    System.err.println("Vyskytla sa chyba: " + e.getMessage());
} finally {
    if (scanner != null) {
        scanner.close();
    }
    if (conn != null) {
        try {
            conn.close();  // Correct method to close the connection
        } catch (SQLException ex) {
            System.err.println("Chyba pri odpojovaní z databázy: " + ex.getMessage());
        }
    }
}}}
