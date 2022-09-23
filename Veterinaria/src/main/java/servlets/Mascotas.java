package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entities.*;
import logic.*;

/**
 * Servlet implementation class Mascotas
 */
public class Mascotas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mascotas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			
			MascotaLogic ml = new MascotaLogic();
			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Mascota mascota = ml.getOne(id);
		        GsonBuilder gsonBuilder = new GsonBuilder();
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());	        
		        Gson gson = gsonBuilder.setPrettyPrinting().create();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(gson.toJson(mascota));
				
			} else if (request.getParameter("idCliente") != null) {
				int idCliente = Integer.parseInt(request.getParameter("idCliente"));
				LinkedList<Mascota> mascotas = ml.getAll(idCliente);	
		        GsonBuilder gsonBuilder = new GsonBuilder();
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());	        
		        Gson gson = gsonBuilder.setPrettyPrinting().create();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(gson.toJson(mascotas));
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
		MascotaLogic ml = new MascotaLogic();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				LocalDate fecha_nac = LocalDate.parse(request.getParameter("fecha_nac"));
				String nombre = request.getParameter("nombre");
				int sexo = Integer.parseInt(request.getParameter("sexo"));
				Raza raza = new Raza();
				Cliente cli = new Cliente();
				if (request.getParameter("id") == "") {
					Mascota mascota = new Mascota();
					mascota.setFecha_nac(fecha_nac);
					mascota.setNombre(nombre);
					mascota.setSexo(sexo);
					raza.setId(Integer.parseInt(request.getParameter("raza")));
					cli.setId(Integer.parseInt(request.getParameter("duenio")));
					mascota.setDuenio(cli);
					mascota.setRaza(raza);
					boolean repetido = ml.esRepetido(mascota);
					if (repetido == false) {
						ml.add(mascota);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Mascota mascota = new Mascota();
					mascota.setId(ID);
					mascota.setFecha_nac(fecha_nac);
					mascota.setNombre(nombre);
					mascota.setSexo(sexo);
					raza.setId(Integer.parseInt(request.getParameter("raza")));
					cli.setId(Integer.parseInt(request.getParameter("duenio")));
					mascota.setDuenio(cli);
					mascota.setRaza(raza);
					boolean repetido = ml.esRepetido(mascota);
					if (repetido == false) {
						ml.update(mascota);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}
				}
				break;
			}
			case "delete": {
				Mascota mascota = new Mascota();
				mascota.setId(Integer.parseInt(request.getParameter("id")));
				ml.delete(mascota);
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