package logic;

import entities.*;
import data.*;
import java.util.LinkedList;

public class MascotaLogic {
	private DataMascotas dm = new DataMascotas();
	
	public LinkedList<Mascota> getAll(int id) {
		try {			
			LinkedList<Mascota> mascotas = new LinkedList<Mascota>();
			mascotas = dm.getAll(id);
			return mascotas;
		} catch (Exception e) {
			throw e;
		}
	}
	public Mascota getOne(int id) {
		try {			
			Mascota mascota = new Mascota();
			mascota.setId(id);
			mascota = dm.getOne(mascota);
			return mascota;
		} catch (Exception e) {
			throw e;
		}
	}
	public void add (Mascota masc) {
        try
        {
        	dm.add(masc);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
	}
	public void update (Mascota masc) {
		try
		{
			dm.update(masc);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public void delete (Mascota masc) {
		try
		{
			dm.delete(masc);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public boolean esRepetido (Mascota m) {
		try
		{
			boolean repetido = dm.esRepetido(m);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}
}