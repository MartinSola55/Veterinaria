package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.*;

public class DataRazas {
	public LinkedList<Raza> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Raza> razas= new LinkedList<Raza>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM raza r INNER JOIN especie e ON r.cod_especie = e.id");
			if(rs!=null) {
				while(rs.next()) {
					Raza r = new Raza();
					r.setId(rs.getInt("r.id"));
					r.setDescripcion(rs.getString("r.descripcion"));
					r.setCod_especie(rs.getInt("r.cod_especie"));
					r.setDesc_especie(rs.getString("e.descripcion"));
					
					razas.add(r);
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
		
		
		return razas;
	}
	
	public Raza getOne(Raza r) {
		Raza raza = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM raza r INNER JOIN especie e ON r.cod_especie = e.id WHERE r.id=?");
			stmt.setInt(1, r.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				raza = new Raza();
				raza.setId(rs.getInt("r.id"));
				raza.setDescripcion(rs.getString("r.descripcion"));
				raza.setCod_especie(rs.getInt("r.cod_especie"));
				raza.setDesc_especie(rs.getString("e.descripcion"));
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
		
		return raza;
	}
	
	
	public void add(Raza r) throws SQLException {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO raza(descripcion, cod_especie) values(?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, r.getDescripcion());
			stmt.setInt(2, r.getCod_especie());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                r.setId(keyResultSet.getInt(1));
            }
			
		}  catch (SQLException e) {
            throw e;
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
	
	public void delete(Raza r) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM raza WHERE id=?");
			stmt.setInt(1, r.getId());
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

	public void update(Raza r) throws SQLException {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE raza SET descripcion = ?, cod_especie = ? WHERE id = ?");
			stmt.setString(1, r.getDescripcion());
			stmt.setInt(2, r.getCod_especie());
			stmt.setInt(3, r.getId());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		}finally {
			try {
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public Raza getByDescripcion(Raza r) {
		Raza raza = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM raza WHERE descripcion = ? AND cod_especie = ? AND NOT id = ?");
			stmt.setString(1, r.getDescripcion());
			stmt.setInt(2, r.getCod_especie());
			stmt.setInt(3, r.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				raza = new Raza();
				raza.setId(rs.getInt("id"));
				raza.setDescripcion(rs.getString("descripcion"));
				raza.setCod_especie(rs.getInt("cod_especie"));
			} else {
				raza = new Raza();
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
		
		return raza;
	}
}
