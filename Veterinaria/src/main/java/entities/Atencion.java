package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Atencion {
	private int id;
	private Veterinario veterinario;
	private Mascota animal;
	private LocalDate fecha_pago;
	private LocalDate fecha_atencion;
	private LinkedList<Practica> practicas;
	private String dateFormat = "dd/MM/yyyy";
	


	public String dateToString() {
		DateTimeFormatter dFormat = DateTimeFormatter.ofPattern(dateFormat);
		return fecha_atencion.format(dFormat);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Veterinario getVeterinario() {
		return veterinario;
	}
	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public Mascota getAnimal() {
		return animal;
	}
	public void setAnimal(Mascota animal) {
		this.animal = animal;
	}


	public LinkedList<Practica> getPracticas() {
		return practicas;
	}

	public void setPracticas(LinkedList<Practica> practicas) {
		this.practicas = practicas;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public LocalDate getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(LocalDate fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public LocalDate getFecha_atencion() {
		return fecha_atencion;
	}

	public void setFecha_atencion(LocalDate fecha_atencion) {
		this.fecha_atencion = fecha_atencion;
	}

	@Override
	public String toString() {
		return "Atencion [id=" + id + ", veterinario=" + veterinario + ", animal=" + animal + ", fecha_pago="
				+ fecha_pago + ", fecha_atencion=" + fecha_atencion + ", practicas=" + practicas + ", dateFormat="
				+ dateFormat + "]";
	}

	

	
	

	
	
}
