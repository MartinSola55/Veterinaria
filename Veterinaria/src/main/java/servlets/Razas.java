package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;

import entities.*;
import logic.*;


/**
 * Servlet implementation class Razas
 */
public class Razas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Razas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			RazaLogic rl = new RazaLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Raza raza = rl.getOne(id);	
				String json = new Gson().toJson(raza);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			} else {
				LinkedList<Raza> razas= rl.getAll();		
				String json = new Gson().toJson(razas);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RazaLogic rl = new RazaLogic();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				String descripcion = request.getParameter("descripcion");
				Especie e = new Especie();
				if (request.getParameter("id") == "") {
					Raza raza = new Raza();
					raza.setDescripcion(descripcion);
					e.setId(Integer.parseInt(request.getParameter("especie")));
					raza.setEspecie(e);
					int repetido = rl.getByDescEsp(raza);						
					if (repetido == 0) {
						rl.add(raza);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Raza raza = new Raza();
					raza.setId(ID);
					raza.setDescripcion(descripcion);
					e.setId(Integer.parseInt(request.getParameter("especie")));
					raza.setEspecie(e);
					int repetido = rl.getByDescEsp(raza);
					if (repetido == 0) {
						rl.update(raza);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete": {
				Raza raza = new Raza();
				raza.setId(Integer.parseInt(request.getParameter("id")));
				rl.delete(raza);
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
