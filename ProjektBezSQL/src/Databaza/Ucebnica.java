package Databaza;

public class Ucebnica extends Kniha{
	private int vek_kategoria;

	public Ucebnica(String nazov, String autor, int rok,int vek_kategoria) {
		super(nazov, autor, rok);
		this.vek_kategoria=vek_kategoria;
	}
	
	@Override
	public String getInfo() {
		return String.format("Názov: %s, Autor: %s, Rok Vydania: %d, Veková kategória: %d, Stav: %s", 
				nazov, autor,rok, vek_kategoria,  jePozicana ? "Požičaná":"Dostupná");
	}
	


}


