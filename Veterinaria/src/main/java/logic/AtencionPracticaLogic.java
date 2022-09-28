package logic;

import data.DataAtencionesPracticas;
import entities.Atencion;
import entities.Practica;

public class AtencionPracticaLogic {
	
	private DataAtencionesPracticas dap= new DataAtencionesPracticas();

	public void add(Atencion atencion) {
		dap.add(atencion);
		
	}

	public void delete(Atencion atencion) {
		dap.delete(atencion);
		
	}

}
