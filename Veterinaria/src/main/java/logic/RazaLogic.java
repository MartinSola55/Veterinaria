package logic;

import entities.*;
import data.*;
import java.util.LinkedList;

public class RazaLogic {
	private DataRazas de = new DataRazas();
	
	public LinkedList<Raza> getAll() {
		try {			
			LinkedList<Raza> razas = new LinkedList<Raza>();
			razas = de.getAll();
			return razas;
		} catch (Exception e) {
			throw e;
		}
	}
	public Raza getOne(int id) {
		try {			
			Raza raza = new Raza();
			raza.setId(id);
			raza = de.getOne(raza);
			return raza;
		} catch (Exception e) {
			throw e;
		}
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
	public int getByDescEsp (Raza r) {
		try
		{
			int repetido = de.getByDescEsp(r);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}
}
