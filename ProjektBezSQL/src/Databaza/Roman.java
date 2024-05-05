package Databaza;

public class Roman extends Kniha{
	private String zaner;

	public Roman(String nazov, String autor, int rok,String zaner) {
		super(nazov, autor, rok);
		this.zaner=zaner;
	}
	
	
	@Override
	public String getInfo() {
		return String.format("Názov: %s, Autor: %s, Rok Vydania: %d, Žáner: %s, Stav: %s", 
				nazov, autor,rok, zaner,  jePozicana ? "Požičaná":"Dostupná");
	}
	


}