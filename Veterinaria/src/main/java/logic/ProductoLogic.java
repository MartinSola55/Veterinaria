package logic;

import java.util.LinkedList;

import data.DataProductos;
import entities.Producto;

public class ProductoLogic {
	
	private DataProductos dp = new DataProductos();

	public LinkedList<Producto> getAll() {
		LinkedList<Producto> productos = new LinkedList<Producto>();
		productos = dp.getAll();
		return productos;
	}
	public Producto getOne(int id) {
		Producto producto = new Producto();
		producto.setId(id);
		producto = dp.getOne(producto);
		return producto;
	}
	public void add (Producto c) {
        try
        {
        	dp.add(c);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
	}
	public void update (Producto c) {
		try
		{
			dp.update(c);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public void delete (Producto c) {
		try
		{
			dp.delete(c);
		} catch (Exception e)
		{
			throw e;
		}
	}
	public boolean esRepetido() {

		return false;
	}	
	public boolean esRepetido (Producto p) {
		try
		{
			boolean repetido = dp.esRepetido(p);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}
}