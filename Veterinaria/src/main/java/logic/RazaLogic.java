package logic;

import entities.*;
import data.*;
import java.util.LinkedList;

public class RazaLogic {
	private DataRazas de = new DataRazas();
	
	public LinkedList<Raza> getAll() {
		LinkedList<Raza> razas = new LinkedList<Raza>();
		razas = de.getAll();
		return razas;
	}
	public Raza getOne(int id) {
		Raza raza = new Raza();
		raza.setId(id);
		raza = de.getOne(raza);
		return raza;
	}
	public void add (Raza raza) throws Exception {
        try
        {
            de.add(raza);
        } catch (Exception e)
        {
        	throw e;
        }
	}
	public void update (Raza raza) throws Exception {
		try
		{
			de.update(raza);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public void delete (Raza raza) {
		try
		{
			de.delete(raza);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public Raza getByDescripcion (Raza r) {
		try
		{
			Raza raza = new Raza();
			raza = de.getByDescripcion(r);
			return raza;
		} catch (Exception e)
		{
			throw e;
		}
	}
}
