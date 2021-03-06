package ar.com.osdepym.mobile.cartilla.dto;

import com.orm.SugarRecord;

public class PrestadorTelefono extends SugarRecord <PrestadorTelefono>{

	private Integer idPrestador;
	private String telefono;
	private String descripcionTelefono;

	public PrestadorTelefono (){}

	public PrestadorTelefono (Integer idPrestador, String telefono, String descripcionTelefono) {
		super();
		this.idPrestador = idPrestador;
		this.telefono = telefono;
		this.descripcionTelefono = descripcionTelefono;
	}

	public Integer getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Integer idPrestador) {
		this.idPrestador = idPrestador;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDescripcionTelefono() {
		return descripcionTelefono;
	}

	public void setDescripcionTelefono(String descripcionTelefono) {
		this.descripcionTelefono = descripcionTelefono;
	}
	
}