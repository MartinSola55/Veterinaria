package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Atencion {
	private int id;
	private Veterinario veterinario;
	private int animal;
	private LocalDateTime fecha_pago;
	private LocalDateTime fecha_atencion;
	private Practica practica;
	
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
	public LocalDateTime getFecha_pago() {
		return fecha_pago;
	}
	public void setFecha_pago(LocalDateTime fecha_pago) {
		this.fecha_pago = fecha_pago;
	}
	public LocalDateTime getFecha_atencion() {
		return fecha_atencion;
	}
	public void setFecha_atencion(LocalDateTime fecha_atencion) {
		this.fecha_atencion = fecha_atencion;
	}
	public Practica getPractica() {
		return practica;
	}
	public void setPractica(Practica practica) {
		this.practica = practica;
	}

	
	
}
