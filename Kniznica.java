package Databaza;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class Kniznica {

	private List<Kniha> knihy = new ArrayList<>();

	public void pridatKnihu(Kniha kniha) {

		knihy.add(kniha);
	}

	public void vypisKnihy() {
		knihy.forEach(kniha -> System.out.println(kniha.getInfo()));
	}

	public void zmazKnihy(String nazovKnihy) {
		knihy.removeIf(kniha -> kniha.nazov.equals(nazovKnihy));
	}

	public void vypisKnihyAbeceda() {
		knihy.stream()
				.sorted(Comparator.comparing(kniha -> kniha.nazov))
				.forEach(kniha -> System.out.println(kniha.getInfo()));
	}

	public void stavKnihy(String nazovKnihy, int stav) {
		for (Kniha kniha : knihy) {
			if (kniha.nazov.equals(nazovKnihy)) {
				if (stav == 1) {
					kniha.vratit();
				} else {
					kniha.pozicat();
				}
				return;
			}
		}
		System.out.println("Kniha s názvom: " + nazovKnihy + " nebola nájdená");
	}

	public void upravKnihu(String nazovKnihy, String novyAutor, int novyRok) {
		for (Kniha kniha : knihy) {
			if (kniha.nazov.equals(nazovKnihy)) {
				kniha.autor = novyAutor;
				kniha.rok = novyRok;
			} else
				System.out.println("Kniha s názvom:" + nazovKnihy + " nebula nájdená");
		}

	}

	public void vypisPodlaNazvu(String nazovKnihy) {
		boolean najdena = false;
		for (Kniha kniha : knihy) {
			if (kniha.nazov.equals(nazovKnihy)) {
				System.out.println(kniha.getInfo());
				najdena = true;
			}
		}
		if (!najdena) {
			System.out.println("Kniha s názvom:" + nazovKnihy + " nebula nájdená");
		}
	}

	public void vypisChronologicky(String autor) {

		List<Kniha> knihyAutora = knihy.stream()
				.filter(knihy -> knihy.autor.equals(autor))
				.sorted(Comparator.comparingInt(knihy -> knihy.rok))
				.collect(Collectors.toList());

		if (knihyAutora.isEmpty()) {
			System.out.println("Autor " + autor + " nie je v zozname");
		} else {
			for (Kniha kniha : knihyAutora) {
				System.out.println(kniha.getInfo());
			}
		}
	}

	public void vypisPodlaZanru(String zaner) {
		boolean najdena = false;
		for (Kniha kniha : knihy) {
			if (kniha.getInfo().equals(zaner)) {
				System.out.println(kniha.getInfo());
				najdena = true;
			}
		}
		if (!najdena) {
			System.out.println("Kniha s názvom:" + zaner + " nebula nájdená");
		}
	}

	public void vypisDruhovKnih() {
		String typKnihy = "";
		for (Kniha kniha : knihy) {
			if (kniha.jePozicana) {
				if (kniha instanceof Roman) {
					typKnihy = "Román";
					System.out.println("Typ požičanej knihy je: " + typKnihy);
				} else {
					typKnihy = "Učebnica";
					System.out.println("Typ požičanej knihy je: " + typKnihy);
				}
			}

		}
	}

	public void ulozenieDoSuboru(String nazovKnihy) throws IOException {
		Kniha hladanaKniha = null;
		for (Kniha kniha : knihy) {
			if (kniha.nazov.equals(nazovKnihy)) {
				hladanaKniha = kniha;
				break;
			}
		}

		String cestaSouboru = "C:\\Users\\Dell\\Documents\\bpc-pc2t\\projekt.txt";
		if (hladanaKniha != null) {
			File soubor = new File(cestaSouboru);
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(soubor))) {
				writer.write(hladanaKniha.getInfo());
				writer.newLine();
			}
			System.out.println("Informácie o knihe boli uložené do souboru: " + cestaSouboru);
		} else {
			System.out.println("Kniha s názvom: " + nazovKnihy + " nebola nájdená");
		}
	}

	public void nacitajKnihu() {
		String cestaSouboru1 = "C:\\Users\\Dell\\Documents\\bpc-pc2t\\projekt.txt";
		File soubor = new File(cestaSouboru1);

		if (!soubor.exists()) {
			System.out.println("Soubor " + cestaSouboru1 + " neexistuje.");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(soubor))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(", ");
				String nazov = parts[0].split(": ")[1];
				String autor = parts[1].split(": ")[1];
				int rokVydania = Integer.parseInt(parts[2].split(": ")[1]);
				String zaner = parts[3].split(": ")[0];
				String premenna = parts[3].split(": ")[1];
				String stav = parts[4].split(": ")[1];

				Kniha kniha = null;
				System.out.println(line);
				if (zaner.equalsIgnoreCase("Román")) {
					kniha = new Roman(nazov, autor, rokVydania, premenna);
				} else {
					int vekKategorie = Integer.parseInt(premenna);
					kniha = new Ucebnica(nazov, autor, rokVydania, vekKategorie);
				}

				if (stav.equalsIgnoreCase("Dostupná")) {
					kniha.vratit();
				} else {
					kniha.pozicat();
				}

				knihy.add(kniha);

			}
			System.out.println("Kniha bola úspešne načítaná do databáze.");
		} catch (IOException e) {
			System.err.println("Došlo k chybe pri načítaní súboru: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Došlo k chybe pri prevode číselných hodnôt: " + e.getMessage());
		}
	}

}
