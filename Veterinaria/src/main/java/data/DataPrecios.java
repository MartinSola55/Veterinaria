package data;
import entities.*;
import java.time.LocalDate;
import java.sql.*;
import java.util.LinkedList;

public class DataPrecios {
	

	
	public Precio getOne(Precio precio) {
		Precio pre = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM precio WHERE id=?");
			stmt.setInt(1, precio.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				pre = new Precio();
				pre.setId(rs.getInt("id"));
				pre.setFecha(rs.getObject("fecha",LocalDate.class));
				pre.setValor(rs.getDouble("valor"));

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
		
		return pre;
	}
	
	
	public void add(Precio pre) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO precio(valor,fecha) values(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, pre.getValor());
			stmt.setObject(2, pre.getFecha());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                pre.setId(keyResultSet.getInt(1));
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
	
	
	public Precio addApractica(Precio pre) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO precio(valor,fecha) values(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, pre.getValor());
			stmt.setObject(2, pre.getFecha());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                pre.setId(keyResultSet.getInt(1));
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
		return pre;
    }
	
	public void delete(Precio pre) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM precio WHERE id=?");
			stmt.setInt(1, pre.getId());
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

	public void update(Precio pre) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE precio SET valor =?,fecha =? WHERE id=?");
			stmt.setDouble(1, pre.getValor());
			stmt.setObject(2, pre.getFecha());

			
			stmt.setInt(3, pre.getId());
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
	
	/*public int getByMatricula(Veterinario veterinario) {
		int repetido = 0;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM veterinario WHERE matricula = ? AND NOT id = ?");
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
	}*/
}