package logic;

import java.util.LinkedList;

import data.*;
import entities.*;

public class VeterinarioLogic {
	
	private DataVeterinarios dv = new DataVeterinarios();

	public Veterinario getOne(int id) {
		Veterinario vet= new Veterinario();
		vet.setId(id);
		return dv.getOne(vet) ;
	}

	public LinkedList<Veterinario> getAll() {
		return dv.getAll();
	}

	public int getByMatricula(Veterinario veterinario) {
		try
		{
			int repetido = dv.getByMatricula(veterinario);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}

	public void add(Veterinario veterinario) {
		dv.add(veterinario);
	}

	public void update(Veterinario veterinario) {
		dv.update(veterinario);
		
	}

	public void delete(Veterinario veterinario) {
		dv.delete(veterinario);
		
	}
	
	

}
