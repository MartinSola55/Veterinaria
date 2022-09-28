package logic;

import java.util.LinkedList;

import data.DataAtenciones;
import entities.*;

public class AtencionLogic {
	
	DataAtenciones da= new DataAtenciones();

	public void add(Atencion atencion) {
		da.add(atencion);
	}

	public void delete(Atencion atencion) {
		da.delete(atencion);
		
	}

	public void update(Atencion atencion) {
		da.update(atencion);
		
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
		return da.esRepetido(atencion);
	}

}
