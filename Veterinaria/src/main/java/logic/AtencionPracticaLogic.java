package logic;

import data.DataAtencionesPracticas;
import entities.Atencion;
import entities.Practica;

public class AtencionPracticaLogic {
	
	private DataAtencionesPracticas dap= new DataAtencionesPracticas();

	public void add(Atencion atencion) {
		try
		{
			dap.add(atencion);
		} catch (Exception e)
		{
			throw e;
		}
		
	}

	public void deleteA(Atencion atencion) {
		try
		{
			dap.deleteA(atencion);
		} catch (Exception e)
		{
			throw e;
		}
		
	}
	public void deleteP(Practica practica) {
		try
		{
			dap.deleteP(practica);
		} catch (Exception e)
		{
			throw e;
		}
		
	}


}
