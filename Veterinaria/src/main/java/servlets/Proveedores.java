package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClienteLogic;
import logic.ProveedorLogic;

import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;

import entities.Cliente;
import entities.Proveedor;

/**
 * Servlet implementation class Proveedores
 */
public class Proveedores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Proveedores() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			ProveedorLogic pl = new ProveedorLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Proveedor proveedor = pl.getOne(id);	
				String json = new Gson().toJson(proveedor);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			} else {
				LinkedList<Proveedor> proveedores = pl.getAll();		
				String json = new Gson().toJson(proveedores);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProveedorLogic pl = new ProveedorLogic();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				int repetido;
				String cuil = request.getParameter("cuil");
				if (request.getParameter("id") == "") {
					Proveedor proveedor= new Proveedor();
					proveedor.setCuil(cuil);
					proveedor.setRazonSocial(request.getParameter("razonSocial"));
					proveedor.setDireccion(request.getParameter("direccion"));
					proveedor.setTelefono(request.getParameter("telefono"));
					proveedor.setEmail(request.getParameter("email"));
					repetido = pl.esRepetido(proveedor);
					if (repetido==0) {
						pl.add(proveedor);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Proveedor proveedor= new Proveedor();
					
					proveedor.setId(ID);
					proveedor.setCuil(cuil);
					proveedor.setRazonSocial(request.getParameter("razonSocial"));
					proveedor.setDireccion(request.getParameter("direccion"));
					proveedor.setTelefono(request.getParameter("telefono"));
					proveedor.setEmail(request.getParameter("email"));
					repetido = pl.esRepetido(proveedor);
					
					if (repetido==0) {
						pl.update(proveedor);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete": {
				Proveedor proveedor = new Proveedor();
				proveedor.setId(Integer.parseInt(request.getParameter("id")));
				pl.delete(proveedor);
				regAfectados = 1;
				break;
			}
			}
		} catch (Exception e) {
			regAfectados = 0;
		}
		String json = new Gson().toJson(regAfectados);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}

