package servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClienteLogic;

import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;

import entities.Cliente;

/**
 * Servlet implementation class Clientes
 */
public class Clientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Clientes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			ClienteLogic cl = new ClienteLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Cliente cliente = cl.getOne(id);	
				String json = new Gson().toJson(cliente);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			} else {
				LinkedList<Cliente> clientes = cl.getAll();		
				String json = new Gson().toJson(clientes);
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
		ClienteLogic cl = new ClienteLogic();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				int repetido;
				String dni = request.getParameter("dni");
				if (request.getParameter("id") == "") {
					Cliente cliente = new Cliente();
					cliente.setDni(dni);
					cliente.setNombre(request.getParameter("nombre"));
					cliente.setApellido(request.getParameter("apellido"));
					cliente.setDireccion(request.getParameter("direccion"));
					cliente.setTelefono(request.getParameter("telefono"));
					cliente.setEmail(request.getParameter("email"));
					repetido = cl.getByDni(cliente);
					if (repetido==0) {
						cl.add(cliente);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Cliente cliente = new Cliente();
					
					cliente.setId(ID);
					cliente.setDni(dni);
					cliente.setNombre(request.getParameter("nombre"));
					cliente.setApellido(request.getParameter("apellido"));
					cliente.setDireccion(request.getParameter("direccion"));
					cliente.setTelefono(request.getParameter("telefono"));
					cliente.setEmail(request.getParameter("email"));

					repetido = cl.getByDni(cliente);
					if (repetido==0) {
						cl.update(cliente);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete": {
				Cliente cliente = new Cliente();
				cliente.setId(Integer.parseInt(request.getParameter("id")));
				cl.delete(cliente);
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
