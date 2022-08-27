package data;
import entities.*;
import java.sql.*;
import java.util.LinkedList;

public class DataClientes {
	
	public LinkedList<Cliente> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Cliente> especies= new LinkedList<Cliente>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM cliente");
			if(rs!=null) {
				while(rs.next()) {
					Cliente c = new Cliente();
					c.setId(rs.getInt("id"));
					c.setNombre(rs.getString("nombre"));
					c.setApellido(rs.getString("apellido"));
					c.setDireccion(rs.getString("direccion"));
					c.setTelefono(rs.getString("telefono"));
					c.setDni(rs.getString("dni"));
					c.setEmail(rs.getString("email"));
					
					especies.add(c);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return especies;
	}
	
	public Cliente getOne(Cliente cliente) {
		Cliente c = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM cliente WHERE id=?");
			stmt.setInt(1, cliente.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				c = new Cliente();
				c.setId(rs.getInt("id"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setDireccion(rs.getString("direccion"));
				c.setTelefono(rs.getString("telefono"));
				c.setDni(rs.getString("dni"));
				c.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return c;
	}
	
	
	public void add(Cliente c) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO cliente(nombre, apellido, direccion, telefono, dni, email) values(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, c.getNombre());
			stmt.setString(2, c.getApellido());
			stmt.setString(3, c.getDireccion());
			stmt.setString(4, c.getTelefono());
			stmt.setString(5, c.getDni());
			stmt.setString(6, c.getEmail());
			
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                c.setId(keyResultSet.getInt(1));
            }
			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                ConectorDB.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }
	
	public void delete(Cliente c) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM cliente WHERE id=?");
			stmt.setInt(1, c.getId());
			stmt.executeUpdate();
			

			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                ConectorDB.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}

	public void update(Cliente c) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE cliente SET nombre =?, apellido=? ,direccion=? ,telefono=? ,dni=? ,email=?  WHERE id=?");
			stmt.setString(1, c.getNombre());
			stmt.setString(2, c.getApellido());
			stmt.setString(3, c.getDireccion());
			stmt.setString(4, c.getTelefono());
			stmt.setString(5, c.getDni());
			stmt.setString(6, c.getEmail());
			stmt.setInt(7, c.getId());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int getByDni(Cliente c) {
		
		int repetido=0;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM cliente WHERE  dni = ? AND NOT id = ?");
			
			stmt.setString(1, c.getDni());
			stmt.setInt(2, c.getId());
			
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				repetido=1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return repetido;
	}
}