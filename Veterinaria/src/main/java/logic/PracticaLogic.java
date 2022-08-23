package logic;

import java.util.LinkedList;

import data.DataPracticas;
import entities.Practica;

public class PracticaLogic {
	
	DataPracticas dp= new DataPracticas();

	public LinkedList<Practica> getAll() {
		return dp.getAll();
	}

	public Practica getOne(int id) {
		Practica pra = new Practica();
		pra.setId(id);
		return dp.getOne(pra);
	}

	public int getByDescricpion(Practica practica) {
		return dp.getByDescripcion(practica);
	}

	public void add(Practica practica) {
		dp.add(practica);
		
	}

	public void delete(Practica practica) {
		dp.delete(practica);
		
	}

	public void update(Practica practica) {
		dp.update(practica);
		
	}

}
