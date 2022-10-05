package data;
import entities.*;
import java.sql.*;
import java.util.LinkedList;

public class DataProveedores {
	
	public LinkedList<Proveedor> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Proveedor> proveedores= new LinkedList<Proveedor>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM proveedor");
			if(rs!=null) {
				while(rs.next()) {
					Proveedor p = new Proveedor();
					p.setId(rs.getInt("id"));
					p.setRazonSocial(rs.getString("razon_social"));
					p.setDireccion(rs.getString("direccion"));
					p.setTelefono(rs.getString("telefono"));
					p.setCuil(rs.getString("cuil"));
					p.setEmail(rs.getString("email"));
					
					proveedores.add(p);
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
		
		return proveedores;
	}
	
	public Proveedor getOne(Proveedor proveedor) {
		Proveedor p = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM proveedor WHERE id=?");
			stmt.setInt(1, proveedor.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p = new Proveedor();
				p.setId(rs.getInt("id"));
				p.setRazonSocial(rs.getString("razon_social"));
				p.setDireccion(rs.getString("direccion"));
				p.setTelefono(rs.getString("telefono"));
				p.setCuil(rs.getString("cuil"));
				p.setEmail(rs.getString("email"));
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
		
		return p;
	}
	
	
	public void add(Proveedor p) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO proveedor(razon_social, cuil, direccion, telefono, email) values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, p.getRazonSocial());
			stmt.setString(2, p.getCuil());
			stmt.setString(3, p.getDireccion());
			stmt.setString(4, p.getTelefono());
			stmt.setString(5, p.getEmail());
			
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                p.setId(keyResultSet.getInt(1));
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
	
	public void delete(Proveedor p) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM proveedor WHERE id=?");
			stmt.setInt(1, p.getId());
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

	public void update(Proveedor p) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE proveedor SET razon_social =?, cuil=? ,direccion=? ,telefono=? ,email=?  WHERE id=?");
			stmt.setString(1, p.getRazonSocial());
			stmt.setString(2, p.getCuil());
			stmt.setString(3, p.getDireccion());
			stmt.setString(4, p.getTelefono());
			stmt.setString(5, p.getEmail());
			stmt.setInt(6, p.getId());
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
	
	public int esRepetido(Proveedor p) {
		//si el proveedor ya existe (su cuil ya está en el sistema), es repetido y retornamos 1, si no existe 0
		
		int repetido=0;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM proveedor WHERE  cuil = ? AND NOT id = ?");
			
			stmt.setString(1, p.getCuil());
			stmt.setInt(2, p.getId());
			
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