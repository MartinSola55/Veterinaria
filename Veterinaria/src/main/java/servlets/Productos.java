package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ProductoLogic;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entities.Cliente;
import entities.Producto;

/**
 * Servlet implementation class Productos
 */
public class Productos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Productos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ProductoLogic pl = new ProductoLogic();
			if (request.getParameter("id")!=null) {
				int id = Integer.parseInt(request.getParameter("id"));
				
				Producto producto= pl.getOne(id);	
				String json = new Gson().toJson(producto);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else
			{
				LinkedList<Producto> productos = pl.getAll();		
				String json = new Gson().toJson(productos);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductoLogic pl= new ProductoLogic();
		
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {
				if (request.getParameter("id")=="") {
					Producto producto = new Producto();
					producto.setDescripcion(request.getParameter("descripcion"));
					producto.setStock(Integer.parseInt(request.getParameter("stock")));			
					producto.setTipo(Integer.parseInt(request.getParameter("tipo")));
										
					boolean repetido = pl.esRepetido(producto);
					if (repetido == false) {
						pl.add(producto);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}

				}else 
				{
					int ID = Integer.parseInt(request.getParameter("id"));
					Producto producto = new Producto();
					
					producto.setId(ID);
					producto.setDescripcion(request.getParameter("descripcion"));
					producto.setStock(Integer.parseInt(request.getParameter("stock")));			
					producto.setTipo(Integer.parseInt(request.getParameter("tipo")));
					boolean repetido = pl.esRepetido(producto);
					

					if (repetido == false) {
						pl.update(producto);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete":
			{
				Producto producto = new Producto();

				producto.setId(Integer.parseInt(request.getParameter("id")));
				pl.delete(producto);
				regAfectados=1;
				break;
			}
			}
		} catch (Exception e) {
			regAfectados = 0;
			e.printStackTrace();
		}
		String json = new Gson().toJson(regAfectados);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}