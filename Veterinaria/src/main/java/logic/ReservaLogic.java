package logic;

import java.util.LinkedList;

import data.DataReservas;
import entities.Reserva;

public class ReservaLogic {
private DataReservas dr = new DataReservas();
	
	public LinkedList<Reserva> getAll() {
		try {			
			LinkedList<Reserva> reservas = new LinkedList<Reserva>();
			reservas = dr.getAll();
			return reservas;
		} catch (Exception e) {
			throw e;
		}
	}
	public Reserva getOne(int id) {
		try {			
			Reserva reserva = new Reserva();
			reserva.setId(id);
			reserva = dr.getOne(reserva);
			return reserva;
		} catch (Exception e) {
			throw e;
		}
	}
	public void add (Reserva reserva) {
        try
        {
        	dr.add(reserva);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
	}	
	public void delete (Reserva reserva) {
		try
		{
			dr.delete(reserva);
		} catch (Exception e)
		{
			throw e;
		}
	}	
}