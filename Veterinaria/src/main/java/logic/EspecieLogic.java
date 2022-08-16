package logic;

import entities.*;
import data.*;
import java.util.LinkedList;

public class EspecieLogic {
	private DataEspecies de = new DataEspecies();
	
	public LinkedList<Especie> getAll() {
		try {			
			LinkedList<Especie> especies = new LinkedList<Especie>();
			especies = de.getAll();
			return especies;
		} catch (Exception e) {
			throw e;
		}
	}
	public Especie getOne(int id) {
		try {			
			Especie especie = new Especie();
			especie.setId(id);
			especie = de.getOne(especie);
			return especie;
		} catch (Exception e) {
			throw e;
		}
	}
	public void add (Especie esp) {
        try
        {
            de.add(esp);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
	}
	public void update (Especie esp) {
		try
		{
			de.update(esp);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public void delete (Especie esp) {
		try
		{
			de.delete(esp);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public int getByDescripcion (Especie esp) {
		try
		{
			int repetido = de.getByDescripcion(esp);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}
}
