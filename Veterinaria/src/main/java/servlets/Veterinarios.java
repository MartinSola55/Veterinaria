package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.*;


import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;

import entities.*;


/**
 * Servlet implementation class Veterinarios
 */
public class Veterinarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Veterinarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			VeterinarioLogic vl = new VeterinarioLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Veterinario veterinario = vl.getOne(id);	
				String json = new Gson().toJson(veterinario);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			} else {
				LinkedList<Veterinario> veterinarios = vl.getAll();		
				String json = new Gson().toJson(veterinarios);
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
		VeterinarioLogic vl = new VeterinarioLogic();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {
				int repetido=0;
				String matricula = request.getParameter("matricula");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String email = request.getParameter("email");
				String telefono = request.getParameter("telefono");
				String direccion = request.getParameter("direccion");
			
				if (request.getParameter("id") == "") {
					Veterinario veterinario = new Veterinario();
					veterinario.setMatricula(matricula);
					veterinario.setNombre(nombre);
					veterinario.setApellido(apellido);
					veterinario.setEmail(email);
					veterinario.setTelefono(telefono);
					veterinario.setDireccion(direccion);
					repetido = vl.getByMatricula(veterinario);
					if (repetido==0) {
						vl.add(veterinario);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Veterinario veterinario = new Veterinario();
					veterinario.setId(ID);
					veterinario.setMatricula(matricula);
					veterinario.setNombre(nombre);
					veterinario.setApellido(apellido);
					veterinario.setEmail(email);
					veterinario.setTelefono(telefono);
					veterinario.setDireccion(direccion);
					repetido = vl.getByMatricula(veterinario);
					if (repetido==0) {
						vl.update(veterinario);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete": {
				Veterinario veterinario = new Veterinario();
				veterinario.setId(Integer.parseInt(request.getParameter("id")));
				vl.delete(veterinario);
				regAfectados = 1;
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
