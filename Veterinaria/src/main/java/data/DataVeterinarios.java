package data;
import entities.*;

import java.sql.*;
import java.util.LinkedList;

public class DataVeterinarios {
	
	public LinkedList<Veterinario> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Veterinario> veterinarios= new LinkedList<>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM veterinario");
			if(rs!=null) {
				while(rs.next()) {
					Veterinario v = new Veterinario();
					v.setId(rs.getInt("id"));
					v.setMatricula(rs.getString("matricula"));
					v.setNombre(rs.getString("nombre"));
					v.setApellido(rs.getString("apellido"));
					v.setDireccion(rs.getString("direccion"));
					v.setTelefono(rs.getString("telefono"));
					if (rs.getString("email") != null) {						
						v.setEmail(rs.getString("email"));
					}
					v.setEliminado(rs.getInt("eliminado"));
					
					
					veterinarios.add(v);
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
		
		
		return veterinarios;
	}
	
	public Veterinario getOne(Veterinario veterinario) {
		Veterinario vet = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM veterinario WHERE id=? AND eliminado=0");
			stmt.setInt(1, veterinario.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				vet = new Veterinario();
				vet.setId(rs.getInt("id"));
				vet.setMatricula(rs.getString("matricula"));
				vet.setNombre(rs.getString("nombre"));
				vet.setApellido(rs.getString("apellido"));
				vet.setDireccion(rs.getString("direccion"));
				vet.setTelefono(rs.getString("telefono"));
				vet.setEmail(rs.getString("email"));
				vet.setEliminado(rs.getInt("eliminado"));
				
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
		
		return vet;
	}
	
	
	public void add(Veterinario vet) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO veterinario(matricula,nombre,apellido,direccion,telefono,email) values(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, vet.getMatricula());
			stmt.setString(2, vet.getNombre());
			stmt.setString(3, vet.getApellido());
			stmt.setString(4, vet.getDireccion());
			stmt.setString(5, vet.getTelefono());
			stmt.setString(6, vet.getEmail());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                vet.setId(keyResultSet.getInt(1));
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
	
	public void delete(Veterinario vet) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE veterinario SET eliminado=1 WHERE id=?");
			stmt.setInt(1, vet.getId());
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

	public void update(Veterinario veterinario) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE veterinario SET matricula =?,nombre =?,apellido =?,telefono =?,direccion =?,email =? WHERE id=?");
			stmt.setString(1, veterinario.getMatricula());
			stmt.setString(2, veterinario.getNombre());
			stmt.setString(3, veterinario.getApellido());
			stmt.setString(4, veterinario.getTelefono());
			stmt.setString(5, veterinario.getDireccion());
			stmt.setString(6, veterinario.getEmail());
			
			stmt.setInt(7, veterinario.getId());
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
	
	public int getByMatricula(Veterinario veterinario) {
		int repetido = 0;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM veterinario WHERE matricula = ? AND NOT id = ? AND eliminado=0");
			stmt.setString(1, veterinario.getMatricula());
			stmt.setInt(2, veterinario.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				repetido = 1;
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