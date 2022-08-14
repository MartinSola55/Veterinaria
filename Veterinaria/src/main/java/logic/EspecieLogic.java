package logic;

import entities.Especie;
import data.DataEspecies;
import java.util.LinkedList;

public class EspecieLogic {
	private DataEspecies de = new DataEspecies();
	
	public LinkedList<Especie> getAll() {
		LinkedList<Especie> especies = new LinkedList<Especie>();
		especies = de.getAll();
		return especies;
	}
	public Especie getOne(int id) {
		Especie especie = new Especie();
		especie.setId(id);
		especie = de.getOne(especie);
		return especie;
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
}
