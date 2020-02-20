package es.rfgr.cr4vegas.utileria;

public enum Prioridad {

	NINGUNA("Ninguna"),
	BAJA("Baja"),
	MEDIA("Media"),
	ALTA("Alta"),
	URGENTE("Urgente"),
	ALARMA("Alarma");
	
	private String cadena;
	
	private Prioridad(String cadena) {
		this.cadena = cadena;
	}
	
	@Override
	public String toString() {
		return this.cadena;
	}
}
