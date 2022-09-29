package data;
import entities.*;
import logic.*;

import java.sql.*;
import java.util.LinkedList;

public class DataPracticas {
	
	private PrecioLogic pl= new PrecioLogic();
	private AtencionPracticaLogic apl=new AtencionPracticaLogic();
	
	public LinkedList<Practica> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Practica> practicas= new LinkedList<>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM practica");
			if(rs!=null) {
				while(rs.next()) {
					Practica p = new Practica();
					p.setId(rs.getInt("id"));
					p.setDescripcion(rs.getString("descripcion"));
					p.setEliminado(rs.getInt("eliminado"));
					Precio pre= pl.getOne(rs.getInt("precio"));
					p.setPrecio(pre);
				
					practicas.add(p);
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
		
		
		return practicas;
	}
	
	public Practica getOne(Practica practica) {
		Practica pra = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM practica WHERE id=?");
			stmt.setInt(1, practica.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				pra = new Practica();
				pra.setId(rs.getInt("id"));
				pra.setDescripcion(rs.getString("descripcion"));
				pra.setEliminado(rs.getInt("eliminado"));
				Precio pre= pl.getOne(rs.getInt("precio"));					
				pra.setPrecio(pre);

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
		
		return pra;
	}
	
	
	public void add(Practica pra) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		Connection con=ConectorDB.getInstancia().getConn();
		
		try {
			
			con.setAutoCommit(false);
			
			pl.add(pra.getPrecio());
			
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO practica(descripcion,precio) values(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, pra.getDescripcion());
			stmt.setInt(2, pra.getPrecio().getId());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                pra.setId(keyResultSet.getInt(1));      
            }
            
            con.commit();
			
		}  catch (SQLException e) {
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
	
	public void delete(Practica pra) {
		PreparedStatement stmt=null;
		Connection con=ConectorDB.getInstancia().getConn();
		try {
			con.setAutoCommit(false);
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE practica SET eliminado=1 WHERE id=?");
			stmt.setInt(1, pra.getId());
			stmt.executeUpdate();
			pl.delete(pra.getPrecio());

			
			con.commit();
			
		}  catch (SQLException e) {
            e.printStackTrace();
            try { if(con!=null) { 
            	System.out.println("La transaccion esta por hacer un rollback");
            	con.rollback(); } } 
			catch (SQLException e1) { e1.printStackTrace(); }
		} finally {
            try {
                if(stmt!=null)stmt.close();
                ConectorDB.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}

	public void update(Practica practica) {
		PreparedStatement stmt=null;
		Connection con=ConectorDB.getInstancia().getConn();
		try {
			con.setAutoCommit(false);
			pl.update(practica.getPrecio());
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE practica SET descripcion =? WHERE id=? AND eliminado=0");
			stmt.setString(1, practica.getDescripcion());
			stmt.setInt(2, practica.getId());
			stmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			 try { if(con!=null) { 
	            	System.out.println("La transaccion esta por hacer un rollback");
	            	con.rollback(); } } 
				catch (SQLException e1) { e1.printStackTrace(); }
		}finally {
			try {
				if(stmt!=null) {stmt.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int getByDescripcion(Practica practica) {
		int repetido = 0;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM practica WHERE descripcion = ? AND NOT id = ?  AND eliminado=0");
			stmt.setString(1, practica.getDescripcion());
			stmt.setInt(2, practica.getId());
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