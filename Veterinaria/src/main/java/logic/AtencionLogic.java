package logic;

import java.util.LinkedList;

import data.DataAtenciones;
import entities.*;

public class AtencionLogic {
	
	DataAtenciones da= new DataAtenciones();

	public void add(Atencion atencion) {
		try
		{
			da.add(atencion);
		} catch (Exception e)
		{
			throw e;
		}
	}
	

	public void delete(Atencion atencion) {
		try
		{
			da.delete(atencion);
		} catch (Exception e)
		{
			throw e;
		}
	}

	public void update(Atencion atencion) {
		try
		{
			da.update(atencion);

		} catch (Exception e)
		{
			throw e;
	
	}
		
	}

	public Atencion getOne(int id) {
		Atencion at= new Atencion();
		at.setId(id);
		return da.getOne(at);
	}

	public LinkedList<Atencion> getAll(int idCli) {
		try {
			Cliente cli= new Cliente();
			cli.setId(idCli);
			return da.getAll(cli);
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean esRepetido(Atencion atencion) {
		try
		{
			boolean repetido = da.esRepetido(atencion);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}

}
