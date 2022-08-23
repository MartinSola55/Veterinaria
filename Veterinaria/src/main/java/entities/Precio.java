package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Precio {
	
	private String dateFormat="dd/MM/yyyy";

	private int id;
	private Double valor;
	private LocalDate fecha;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		
		DateTimeFormatter dFormat=DateTimeFormatter.ofPattern(dateFormat) ;
		return "Precio [id=" + id + ", valor=" + valor + ", fecha=" + (fecha==null?null:fecha.format(dFormat));
	}

	

}
