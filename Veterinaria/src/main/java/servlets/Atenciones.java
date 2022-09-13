package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import com.google.gson.Gson;

import entities.*;

/**
 * Servlet implementation class Atenciones
 */
public class Atenciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Atenciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			AtencionLogic al = new AtencionLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Atencion atencion= al.getOne(id);	
				String json = new Gson().toJson(atencion);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			} else {
				LinkedList<Atencion> atenciones= al.getAll();		
				String json = new Gson().toJson(atenciones);
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
		AtencionLogic al = new AtencionLogic();
		/*animal logic*/
		
		Veterinario vet= new Veterinario();
		/*animla*/
		Practica prac = new Practica();
		
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				if (request.getParameter("id") == "") {
					Atencion atencion = new Atencion();
					prac.setId(Integer.parseInt(request.getParameter("practica")));
					vet.setId(Integer.parseInt(request.getParameter("veterinario")));
					/*animal*/
				
					atencion.setPractica(prac);
					atencion.setVeterinario(vet);
					/*atencion.setAnimal*/
					atencion.setFecha_atencion(LocalDateTime.now());
					
					al.add(atencion);

				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Atencion atencion = new Atencion();
					atencion.setId(ID);
					prac.setId(Integer.parseInt(request.getParameter("practica")));
					vet.setId(Integer.parseInt(request.getParameter("veterinario")));
					/*animal*/
					
					atencion.setPractica(prac);
					atencion.setVeterinario(vet);
					/*atencion.setAnimal*/
					atencion.setFecha_atencion(LocalDateTime.now());
					
					al.update(atencion);					
				
				break;
			}
			}
			case "delete": {
				Atencion atencion = new Atencion();
				atencion.setId(Integer.parseInt(request.getParameter("id")));
				al.delete(atencion);
				regAfectados = 1;
				break;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			regAfectados = 0;
		}
		String json = new Gson().toJson(regAfectados);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}


