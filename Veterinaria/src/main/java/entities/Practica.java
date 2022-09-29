package entities;

public class Practica {
	private int id;
	private String descripcion;
	private Precio precio;
	private int eliminado;
	
	
	
	public int getEliminado() {
		return eliminado;
	}
	public void setEliminado(int eliminado) {
		this.eliminado = eliminado;
	}
	public int getId() {
		return id;
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
	public Precio getPrecio() {
		return precio;
	}
	public void setPrecio(Precio precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Practica [id=" + id + ", descripcion=" + descripcion + ", precio=" + precio + "]";
	}
	
	
	
}
