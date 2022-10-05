package logic;
import java.util.LinkedList;

import data.DataProveedores;
import entities.*;

public class ProveedorLogic {
private DataProveedores dp = new DataProveedores();
	
	public LinkedList<Proveedor> getAll() {
		LinkedList<Proveedor> proveedores = new LinkedList<Proveedor>();
		proveedores = dp.getAll();
		return proveedores;
	}
	public Proveedor getOne(int id) {
		Proveedor proveedor = new Proveedor();
		proveedor.setId(id);
		proveedor = dp.getOne(proveedor);
		return proveedor;
	}
	public void add (Proveedor p) {
        try
        {
            dp.add(p);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
	}
	public void update (Proveedor p) {
		try
		{
			dp.update(p);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public void delete (Proveedor p) {
		try
		{
			dp.delete(p);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public int esRepetido (Proveedor p) {
		try
		{
			int repetido = dp.esRepetido(p);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}
}