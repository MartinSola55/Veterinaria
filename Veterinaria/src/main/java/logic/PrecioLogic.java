package logic;

import java.time.LocalDate;

import data.DataPrecios;
import entities.Precio;

public class PrecioLogic {
	
	DataPrecios dp= new DataPrecios();

	public Precio getOne(int id) {
		Precio pre=new Precio();
		pre.setId(id);
		return dp.getOne(pre);
	
	}

	public void add(Precio precio) {
		precio.setFecha(LocalDate.now());
		dp.add(precio);
	} 
	
	/*
	public Precio addAPractica(Double precio) {
		Precio pre= new Precio();
		pre.setValor(precio);
		pre.setFecha(LocalDate.now());
		return dp.addApractica(pre);
	}*/

	public void delete(Precio pre) {
		dp.delete(pre);
		
	}

	public void update(Precio pre) {
		pre.setFecha(LocalDate.now());
		dp.update(pre);
		
	}



}
