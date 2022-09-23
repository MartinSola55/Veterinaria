package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Mascota {
	private int id;
	private LocalDate fecha_nac;
	private String nombre;
	private int sexo;
	private Raza raza;
	private Cliente duenio;
	private String dateFormat = "dd/MM/yyyy";


	public String dateToString() {
		DateTimeFormatter dFormat = DateTimeFormatter.ofPattern(dateFormat);
		return fecha_nac.format(dFormat);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getFecha_nac() {
		return fecha_nac;
	}
	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getSexo() {
		return sexo;
	}
	public void setSexo(int sexo) {
		this.sexo = sexo;
	}
	public Raza getRaza() {
		return raza;
	}
	public void setRaza(Raza raza) {
		this.raza = raza;
	}
	public Cliente getDuenio() {
		return duenio;
	}
	public void setDuenio(Cliente duenio) {
		this.duenio = duenio;
	}
}