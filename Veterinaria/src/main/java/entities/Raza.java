package entities;

public class Raza {

	private int id;
	private String descripcion;
	private int cod_especie;
	private String desc_especie;
	
	public int getId() {
		return id;
	}
	public String getDesc_especie() {
		return desc_especie;
	}
	public void setDesc_especie(String desc_especie) {
		this.desc_especie = desc_especie;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCod_especie() {
		return cod_especie;
	}
	public void setCod_especie(int cod_especie) {
		this.cod_especie = cod_especie;
	}
}
