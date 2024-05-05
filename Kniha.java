package Databaza;

public abstract class Kniha {
	protected String nazov;
	protected String autor;
	protected int rok;
	protected boolean jePozicana;
	public Kniha(String nazov, String autor, int rok) {
		super();
		this.nazov = nazov;
		this.autor = autor;
		this.rok = rok;
		this.jePozicana = false;
	}
	
	public void pozicat() {
		jePozicana=true;
	}
	
	public void vratit() {
		jePozicana=false;
	}
	
	public abstract String getInfo();

}
