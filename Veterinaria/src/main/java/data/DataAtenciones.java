package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import entities.*;
import logic.*;

public class DataAtenciones {
	
	VeterinarioLogic vl= new VeterinarioLogic();
	PracticaLogic pl = new PracticaLogic();
	MascotaLogic ml= new MascotaLogic();
	AtencionPracticaLogic apl= new AtencionPracticaLogic();

	public void add(Atencion atencion) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		Connection con=ConectorDB.getInstancia().getConn();
		try {
			
			con.setAutoCommit(false);
			System.out.println(atencion);
			System.out.println(atencion.getVeterinario());
			System.out.println(atencion.getVeterinario().getId());
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO atencion(id_veterinario,id_mascota,fecha_pago,fecha_atencion) values(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, atencion.getVeterinario().getId());
			stmt.setInt(2, atencion.getAnimal().getId());
			stmt.setObject(3, atencion.getFecha_pago());
			stmt.setObject(4, atencion.getFecha_atencion());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
            	atencion.setId(keyResultSet.getInt(1));
            }
            
            apl.add(atencion);
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

	public void delete(Atencion atencion) {
		PreparedStatement stmt=null;
		Connection con=ConectorDB.getInstancia().getConn();
		try {
			con.setAutoCommit(false);
			apl.delete(atencion);
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM atencion WHERE id=?");
			stmt.setInt(1, atencion.getId());
			stmt.executeUpdate();
			con.commit();
			
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
		Connection con=ConectorDB.getInstancia().getConn();
		try {
			con.setAutoCommit(false);
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE atencion SET id_veterinario =?,id_mascota =?,fecha_pago =?,fecha_atencion =? WHERE id=?");
			stmt.setInt(1, atencion.getVeterinario().getId());
			stmt.setInt(2, atencion.getAnimal().getId());
			stmt.setObject(3, atencion.getFecha_pago());
			stmt.setObject(4, atencion.getFecha_atencion());
			
			stmt.setInt(5, atencion.getId());
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
		PreparedStatement stmt2=null;
		ResultSet rs2=null;
		Atencion at=null;
		LinkedList<Practica> practicas= new LinkedList<Practica>();
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM atencion WHERE id=?");
			stmt.setInt(1, atencion.getId());
			rs=stmt.executeQuery();
			
			stmt2=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM atencion_practica WHERE atencion_id=?");
			stmt2.setInt(1, atencion.getId());
			rs2=stmt2.executeQuery();
			
			if(rs!=null && rs.next() && rs2!=null) {
				at = new Atencion();
				at.setId(rs.getInt("id"));
				at.setVeterinario(vl.getOne(rs.getInt("veterinario")));
				at.setAnimal(ml.getOne(rs.getInt("mascota")));
				at.setFecha_pago(rs.getObject("fecha_pago",LocalDate.class));
				at.setFecha_atencion(rs.getObject("fecha_atencion",LocalDate.class));
				//at.setPracticas(pl.getOne(rs2.getInt("practica_id")));
				
				while(rs2.next()) {
					Practica prac = new Practica();
					prac=pl.getOne(rs2.getInt("practica_id"));
					practicas.add(prac);
				}
				at.setPracticas(practicas);
				
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

	public LinkedList<Atencion> getAll(Cliente cli) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		PreparedStatement stmt2=null;
		ResultSet rs2=null;
		LinkedList<Atencion> atenciones= new LinkedList<>();
		System.out.println(cli.getId());
		
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM atencion ate INNER JOIN"
					+ " mascota masc ON masc.id=ate.id_mascota "
					+ "INNER JOIN cliente cli ON cli.id=masc.duenio"
					+ " WHERE cli.id=?");
			stmt.setInt(1, cli.getId());
			rs=stmt.executeQuery();
			
			
			
			if(rs!=null) {
				while(rs.next()) {
					Atencion at = new Atencion();
					at.setVeterinario(vl.getOne(rs.getInt("id_veterinario")));
					at.setAnimal(ml.getOne(rs.getInt("id_mascota")));
					at.setFecha_pago(rs.getObject("fecha_pago",LocalDate.class));
					at.setFecha_atencion(rs.getObject("fecha_atencion",LocalDate.class));

					
				/*	stmt2=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM atencion_practica ap WHERE ap.atencion_id=?");
					stmt2.setInt(1, cli.getId());
					rs2=stmt2.executeQuery();
					if(rs2!=null) {
						at.setPractica(pl.getOne(rs2.getInt("practica_id")));
					}*/
					System.out.println("LLEGA");
				
					atenciones.add(at);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				if(stmt2!=null) {stmt2.close();}
				if(rs2!=null) {rs2.close();}
				ConectorDB.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		System.out.println(atenciones);
		return atenciones;	
}

	public boolean esRepetido(Atencion atencion) {
		boolean repetido = false;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM atencion WHERE id_veterinario = ? AND id_mascota = ? AND fecha_pago = ? AND fecha_atencion = ? AND NOT id = ?");
			stmt.setInt(1, atencion.getVeterinario().getId());
			stmt.setInt(2, atencion.getAnimal().getId());
			stmt.setObject(3,atencion.getFecha_pago());	
			stmt.setObject(4,atencion.getFecha_atencion());	
			stmt.setInt(5, atencion.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				repetido = true;
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

