package data;
import entities.*;
import java.sql.*;
import java.util.LinkedList;

public class DataProductos {
	
	public LinkedList<Producto> getAll()
	{
		Statement stmt = null;
		ResultSet rs= null;
		LinkedList<Producto> productos = new LinkedList<Producto>();
		
		try {
			stmt = ConectorDB.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select * from producto");
			if (rs!=null) {
				while (rs.next()) {
					Producto p = new Producto();
					p.setId(rs.getInt("id"));
					p.setDescripcion(rs.getString("descripcion"));
					p.setTipo(rs.getInt("tipo"));
					p.setStock(rs.getInt("stock"));
					p.setPrecio(rs.getFloat("precio"));
					
					productos.add(p);
				}
			}
			
		} catch (Exception e) {
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
		return productos;	
	}
	
	public Producto getOne(Producto p) 
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Producto producto = null;
		
		try {
			stmt = ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM producto WHERE id=?");
			stmt.setInt(1,p.getId());
			rs= stmt.executeQuery();
			if(rs!=null && rs.next()) 
			{
				producto = new Producto();
				producto.setId(rs.getInt("id"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setTipo(rs.getInt("tipo"));
				producto.setStock(rs.getInt("stock"));
				producto.setPrecio(rs.getFloat("precio"));
			}
		} catch (Exception e) {
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
		return producto;
	}
	public void add(Producto p) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("INSERT INTO producto(descripcion,tipo,stock, precio) values(?,?,?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setString(1, p.getDescripcion());
			stmt.setInt(2, p.getTipo());
			stmt.setInt(3, p.getStock());
			stmt.setFloat(4, p.getPrecio());
			

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
	
	public void delete(Producto p) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("DELETE FROM producto WHERE id=?");
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

	public void update(Producto p) {
		PreparedStatement stmt=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("UPDATE producto SET descripcion =?,tipo=?,stock=?, precio=? WHERE id=?");
			stmt.setString(1, p.getDescripcion());
			stmt.setInt(2, p.getTipo());
			stmt.setInt(3, p.getStock());
			stmt.setInt(4, p.getId());
			stmt.setFloat(5, p.getPrecio());
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
	
	public boolean esRepetido(Producto p) {
		boolean repetido = false;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=ConectorDB.getInstancia().getConn().prepareStatement("SELECT * FROM producto WHERE descripcion = ? AND tipo = ? AND NOT id = ?");
			stmt.setString(1, p.getDescripcion());
			stmt.setInt(2, p.getTipo());
			stmt.setInt(3, p.getId());

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
