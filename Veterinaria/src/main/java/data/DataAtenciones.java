package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.Atencion;
import entities.Veterinario;
import logic.PracticaLogic;
import logic.VeterinarioLogic;

public class DataAtenciones {
	
	VeterinarioLogic vl= new VeterinarioLogic();
	PracticaLogic pl = new PracticaLogic();

	public void add(Atencion atencion) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO atencion(veterinario,animal,practica,fecha_pago,fecha_atencion) values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, atencion.getVeterinario().getId());
			/*stmt.setInt(2, atencion.getAnimal().getId());*/
			stmt.setInt(3, atencion.getVeterinario().getId());
			stmt.setInt(4, atencion.getPractica().getId());
			stmt.setObject(5, atencion.getFecha_pago());
			stmt.setObject(6, atencion.getFecha_atencion());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
            	atencion.setId(keyResultSet.getInt(1));
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

	public void delete(Atencion atencion) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM atencion WHERE id=?");
			stmt.setInt(1, atencion.getId());
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

	public void update(Atencion atencion) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE atencion SET veterinario =?,animal =?,practica =?,fecha_pago =?,fecha_atencion =? WHERE id=?");
			stmt.setInt(1, atencion.getVeterinario().getId());
			/*stmt.setInt(2, atencion.getAnimal().getId());*/
			stmt.setInt(3, atencion.getPractica().getId());
			stmt.setObject(4, atencion.getFecha_pago());
			stmt.setObject(5, atencion.getFecha_atencion());
			
			stmt.setInt(6, atencion.getId());
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

	public Atencion getOne(Atencion atencion) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Atencion at=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM atencion WHERE id=?");
			stmt.setInt(1, atencion.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				at = new Atencion();
				at.setId(rs.getInt("id"));
				at.setVeterinario(vl.getOne(rs.getInt("veterinario")));
				at.setPractica(pl.getOne(rs.getInt("practica")));
				/*at.setAnimal;*/
				at.setFecha_pago(rs.getObject("fecha_pago",LocalDateTime.class));
				at.setFecha_atencion(rs.getObject("fecha_atencion",LocalDateTime.class));

				
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
		
		return at;
	}

	public LinkedList<Atencion> getAll() {
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Atencion> atenciones= new LinkedList<>();
		
		try {
			stmt= ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("SELECT * FROM atencion");
			if(rs!=null) {
				while(rs.next()) {
					Atencion at = new Atencion();
					at.setId(rs.getInt("id"));
					at.setVeterinario(vl.getOne(rs.getInt("veterinario")));
					at.setPractica(pl.getOne(rs.getInt("practica")));
					/*at.setAnimal;*/
					at.setFecha_pago(rs.getObject("fecha_pago",LocalDateTime.class));
					at.setFecha_atencion(rs.getObject("fecha_atencion",LocalDateTime.class));
					
					
					atenciones.add(at);
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
		return atenciones;	
}
	
	
}

