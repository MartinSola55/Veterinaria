package logic;
import java.util.LinkedList;

import data.DataClientes;
import entities.*;

public class ClienteLogic {
private DataClientes dc = new DataClientes();
	
	public LinkedList<Cliente> getAll() {
		LinkedList<Cliente> clientes = new LinkedList<Cliente>();
		clientes = dc.getAll();
		return clientes;
	}
	public Cliente getOne(int id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente = dc.getOne(cliente);
		return cliente;
	}
	public void add (Cliente c) {
        try
        {
            dc.add(c);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
	}
	public void update (Cliente c) {
		try
		{
			dc.update(c);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public void delete (Cliente c) {
		try
		{
			dc.delete(c);
		} catch (Exception e)
		{
			throw e;
		}
	}	
	public int getByDni (Cliente c) {
		try
		{
			int repetido = dc.getByDni(c);
			return repetido;
		} catch (Exception e)
		{
			throw e;
		}
	}
}
