package data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Cliente;
import entities.Producto;
import entities.Reserva;

public class DataReservas {

	public LinkedList<Reserva> getAll(){
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Reserva> reservas= new LinkedList<Reserva>();
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement(
					"SELECT * FROM cliente_reserva cr "
					+ "INNER JOIN cliente c ON cr.id_cliente = c.id "
					+ "INNER JOIN producto p ON cr.id_producto= p.id ");

			rs=stmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					Reserva reserva = new Reserva();
					Cliente cliente = new Cliente();
					Producto producto = new Producto();
					
					reserva.setId(rs.getInt("cr.id"));
					reserva.setCantidad(rs.getInt("cantidad"));			
					
					cliente.setApellido(rs.getString("c.apellido"));
					cliente.setNombre(rs.getString("c.nombre"));
					cliente.setDni(rs.getString("c.dni"));
					cliente.setId(rs.getInt("c.id"));
					cliente.setDireccion(rs.getString("c.direccion"));
					cliente.setEmail(rs.getString("c.email"));
					
					producto.setDescripcion(rs.getString("p.descripcion"));
					producto.setId(rs.getInt("p.id"));
					producto.setStock(rs.getInt("p.stock"));
					producto.setTipo(rs.getInt("p.tipo"));
					
					reserva.setCliente(cliente);
					reserva.setProducto(producto);
					
					reservas.add(reserva);
				}
			}
		} catch (SQLException m) {
			m.printStackTrace();
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException m) {
				m.printStackTrace();
			}
		}
		return reservas;
	}
	
	public Reserva getOne(Reserva res) {
		Reserva reserva = null;
		Cliente cliente = new Cliente();
		Producto producto = new Producto();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement(
					"SELECT * FROM cliente_reserva cr "
					+ "INNER JOIN cliente c ON cr.id_cliente = c.id "
					+ "INNER JOIN producto p ON p.id = cr.id_producto "
					+ "WHERE cr.id = ?");
			stmt.setInt(1, res.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				reserva= new Reserva();

				reserva.setId(rs.getInt("cr.id"));
				reserva.setCantidad(rs.getInt("cantidad"));			
				
				cliente.setApellido(rs.getString("c.apellido"));
				cliente.setNombre(rs.getString("c.nombre"));
				cliente.setDni(rs.getString("c.dni"));
				cliente.setId(rs.getInt("c.id"));
				cliente.setDireccion(rs.getString("c.direccion"));
				cliente.setEmail(rs.getString("c.email"));
				
				producto.setDescripcion(rs.getString("p.descripcion"));
				producto.setId(rs.getInt("p.id"));
				producto.setStock(rs.getInt("p.stock"));
				producto.setTipo(rs.getInt("p.tipo"));
				
				reserva.setCliente(cliente);
				reserva.setProducto(producto);
			}
		} catch (SQLException m) {
			m.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException m) {
				m.printStackTrace();
			}
		}
		return reserva;
	}
	
	
	public void add(Reserva reserva) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO cliente_reserva(id_cliente, id_producto,cantidad)"
					+ " values(?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, reserva.getCliente().getId());
			stmt.setInt(2, reserva.getProducto().getId());
			stmt.setInt(3, reserva.getCantidad());

			stmt.executeUpdate();
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
            	reserva.setId(keyResultSet.getInt(1));
            }
		}  catch (SQLException m) {
            m.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                ConectorDB.getInstancia().releaseConn();
            } catch (SQLException m) {
            	m.printStackTrace();
            }
		}
    }
	
	public void delete(Reserva reserva) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM cliente_reserva WHERE id=?");
			stmt.setInt(1, reserva.getId());
			stmt.executeUpdate();
		}  catch (SQLException m) {
            m.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                ConectorDB.getInstancia().releaseConn();
            } catch (SQLException m) {
            	m.printStackTrace();
            }
		}
	}

	public void update(Reserva reserva) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE cliente_reserva SET id_cliente = ?, id_producto= ?, cantidad= ?"
					+ "WHERE id= ?");

			stmt.setInt(1, reserva.getCliente().getId());
			stmt.setInt(2,reserva.getProducto().getId());
			stmt.setInt(3, reserva.getCantidad());

			stmt.executeUpdate();
		} catch (SQLException m) {
			m.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException m) {
				m.printStackTrace();
			}
		}		
	}
	

}