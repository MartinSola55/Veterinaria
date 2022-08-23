package entities;

public class Practica {
	private int id;
	private String descripcion;
	private Precio precio;
	
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
