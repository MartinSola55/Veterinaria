package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
//java json
//jersey

import com.google.gson.Gson;

import entities.*;
import logic.*;

/**
 * Servlet implementation class Especie
 */
public class Especies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Especies() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			EspecieLogic el = new EspecieLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Especie especie = el.getOne(id);	
				String json = new Gson().toJson(especie);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			} else {
				LinkedList<Especie> especies = el.getAll();		
				String json = new Gson().toJson(especies);
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
		EspecieLogic el = new EspecieLogic();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				String descripcion = request.getParameter("descripcion");
				if (request.getParameter("id") == "") {
					Especie especie = new Especie();
					especie.setDescripcion(descripcion);
					int repetido = el.getByDescripcion(especie);
					if (repetido == 0) {
						el.add(especie);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Especie especie = new Especie();
					especie.setId(ID);
					especie.setDescripcion(descripcion);
					int repetido = el.getByDescripcion(especie);
					if (repetido == 0) {
						el.update(especie);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete": {
				Especie especie = new Especie();
				especie.setId(Integer.parseInt(request.getParameter("id")));
				el.delete(especie);
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
