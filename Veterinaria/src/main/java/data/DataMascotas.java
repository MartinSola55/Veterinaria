package data;
import entities.*;

import java.sql.*;
import java.util.LinkedList;

public class DataMascotas {
	
	public LinkedList<Mascota> getAll(int id){
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Mascota> mascotas= new LinkedList<Mascota>();
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement(
					"SELECT * FROM mascota m "
					+ "INNER JOIN cliente c ON m.duenio = c.id "
					+ "INNER JOIN raza r ON m.raza = r.id "
					+ "INNER JOIN especie e ON r.cod_especie = e.id "
					+ "WHERE c.id = ?");
			stmt.setInt(1, id);
			rs=stmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					Mascota m = new Mascota();
					Cliente c = new Cliente();
					Raza r = new Raza();
					Especie e = new Especie();
					m.setId(rs.getInt("id"));
					m.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
					m.setNombre(rs.getString("nombre"));
					m.setSexo(rs.getInt("sexo"));
					c.setId(rs.getInt("duenio"));
					r.setId(rs.getInt("raza"));
					r.setDescripcion(rs.getString("r.descripcion"));
					e.setId(rs.getInt("r.cod_especie"));
					e.setDescripcion(rs.getString("e.descripcion"));
					r.setEspecie(e);
					m.setDuenio(c);
					m.setRaza(r);
					mascotas.add(m);
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
		return mascotas;
	}
	
	public Mascota getOne(Mascota mascota) {
		Mascota masc = null;
		Cliente c = new Cliente();
		Raza r = new Raza();
		Especie e = new Especie();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement(
					"SELECT * FROM mascota m "
					+ "INNER JOIN cliente c ON m.duenio = c.id "
					+ "INNER JOIN raza r ON m.raza = r.id "
					+ "INNER JOIN especie e ON r.cod_especie = e.id "
					+ "WHERE m.id = ?");
			stmt.setInt(1, mascota.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				masc = new Mascota();
				masc.setId(rs.getInt("id"));
				masc.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
				masc.setNombre(rs.getString("nombre"));
				masc.setSexo(rs.getInt("sexo"));
				c.setId(rs.getInt("duenio"));
				r.setId(rs.getInt("raza"));
				r.setDescripcion(rs.getString("r.descripcion"));
				e.setId(rs.getInt("r.cod_especie"));
				r.setEspecie(e);
				masc.setDuenio(c);
				masc.setRaza(r);
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
		return masc;
	}
	
	
	public void add(Mascota masc) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO mascota(fecha_nac, nombre, sexo, duenio, raza) values(?, ?, ? ,?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, Date.valueOf(masc.getFecha_nac()));
			stmt.setString(2, masc.getNombre());
			stmt.setInt(3, masc.getSexo());
			stmt.setInt(4, masc.getDuenio().getId());
			stmt.setInt(5, masc.getRaza().getId());
			stmt.executeUpdate();
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                masc.setId(keyResultSet.getInt(1));
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
	
	public void delete(Mascota masc) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM mascota WHERE id=?");
			stmt.setInt(1, masc.getId());
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

	public void update(Mascota mascota) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE mascota SET fecha_nac = ?, nombre = ?, sexo = ?, duenio = ?, raza = ? WHERE id= ?");
			stmt.setDate(1, Date.valueOf(mascota.getFecha_nac()));
			stmt.setString(2, mascota.getNombre());
			stmt.setInt(3, mascota.getSexo());
			stmt.setInt(4, mascota.getDuenio().getId());
			stmt.setInt(5, mascota.getRaza().getId());
			stmt.setInt(6, mascota.getId());
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
	
	public boolean esRepetido(Mascota m) {
		boolean repetido = false;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM mascota WHERE fecha_nac = ? AND nombre = ? AND raza = ? AND duenio = ? AND NOT id = ?");
			stmt.setDate(1, Date.valueOf(m.getFecha_nac()));
			stmt.setString(2, m.getNombre());
			stmt.setInt(3, m.getRaza().getId());
			stmt.setInt(4, m.getDuenio().getId());
			stmt.setInt(5, m.getId());
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