package data;
import entities.*;

import java.sql.*;
import java.util.LinkedList;

public class DataEspecies {
	
	public LinkedList<Especie> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Especie> especies= new LinkedList<>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM especie");
			if(rs!=null) {
				while(rs.next()) {
					Especie e = new Especie();
					e.setId(rs.getInt("id"));
					e.setDescripcion(rs.getString("descripcion"));
					
					especies.add(e);
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
	
	public Especie getOne(Especie especie) {
		Especie esp = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM especie WHERE id=?");
			stmt.setInt(1, especie.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				esp = new Especie();
				esp.setId(rs.getInt("id"));
				esp.setDescripcion(rs.getString("descripcion"));
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
		
		return esp;
	}
	
	
	public void add(Especie esp) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO especie(descripcion) values(?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, esp.getDescripcion());
			System.out.println(esp.getDescripcion());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                esp.setId(keyResultSet.getInt(1));
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
	
	public void delete(Especie esp) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM especie WHERE id=?");
			stmt.setInt(1, esp.getId());
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

	public void update(Especie especie) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE especie SET descripcion =? WHERE id=?");
			stmt.setString(1, especie.getDescripcion());
			System.out.println(especie.getDescripcion());
			stmt.setInt(2, especie.getId());
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
	
	
}