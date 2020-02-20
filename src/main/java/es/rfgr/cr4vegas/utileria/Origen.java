package es.rfgr.cr4vegas.utileria;

public enum Origen {

	NINGUNO("Ninguno"),
	OPERARIO("Operario"),
	EVENTO("Evento"),
	ALARMA("Alarma"),
	PARTE_OFICINA("Parte oficina"),
	PARTE("Parte"),
	UDCONTROL("Unidad de Control"),
	VEHICULO("Vehiculo");
	
	private String cadena;
	
	private Origen (String cadena) {
		this.cadena = cadena;
	}

	@Override
	public String toString() {
		return cadena;
	}

}
