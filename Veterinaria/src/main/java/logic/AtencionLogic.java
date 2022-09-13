package logic;

import java.util.LinkedList;

import data.DataAtenciones;
import entities.Atencion;

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

	public LinkedList<Atencion> getAll() {
		return da.getAll();
	}

}
