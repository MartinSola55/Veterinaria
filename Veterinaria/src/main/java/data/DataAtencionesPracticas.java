package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import entities.*;

public class DataAtencionesPracticas {

	public void add(Atencion atencion) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		Connection con=ConectorDB.getInstancia().getConn();
		
		try {
			con.setAutoCommit(false);
			
			 for (Practica practica : atencion.getPracticas()){
				 
				try {
					stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO atencion_practica(atencion_id,practica_id) values(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
					stmt.setInt(1, atencion.getId());
					stmt.setInt(2, practica.getId());
					stmt.executeUpdate();
					
					keyResultSet=stmt.getGeneratedKeys();
	        	    if(keyResultSet!=null && keyResultSet.next()){
	        	    	atencion.setId(keyResultSet.getInt(1));
	        	    }
					
				}  catch (SQLException e) {
	        	    e.printStackTrace();
				}
				
				finally {
	        	    try {
	        	        if(keyResultSet!=null)keyResultSet.close();
	        	        if(stmt!=null)stmt.close();
	        	        ConectorDB.getInstancia().releaseConn();
	        	    } catch (SQLException e) {
	        	    	e.printStackTrace();
	       	 	    }
				}
			
		}
			con.commit();
	
		} catch (SQLException e) {
            e.printStackTrace();
            try { if(con!=null) { 
            	System.out.println("La transaccion esta por hacer un rollback");
            	con.rollback(); } } 
			catch (SQLException e1) { e1.printStackTrace(); }
			 
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

	public void delete(Atencion atencion) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM atencion_practica WHERE atencion_id=?");
			stmt.setInt(1, atencion.getId());	
			
			
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
	
	

}
