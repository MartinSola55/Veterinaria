package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

		        GsonBuilder gsonBuilder = new GsonBuilder();
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());	        
		        Gson gson = gsonBuilder.setPrettyPrinting().create();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(gson.toJson(atencion));
				
			} else if (request.getParameter("idCliente") != null){			
				int idCliente = Integer.parseInt(request.getParameter("idCliente"));
				LinkedList<Atencion> atenciones = al.getAll(idCliente);
		        GsonBuilder gsonBuilder = new GsonBuilder();
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());	        
		        Gson gson = gsonBuilder.setPrettyPrinting().create();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(gson.toJson(atenciones));
				
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
		Mascota mas = new Mascota();
		String[] id_practicas;
		LinkedList<Practica> practicas = new LinkedList<>();
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {	
				if (request.getParameter("id") == "") {
					Atencion atencion = new Atencion();
					String[] array = request.getParameterValues("practicas[]");
					for (String id : array) {	
						Practica prac = new Practica();
						prac.setId(Integer.parseInt(id));
						practicas.add(prac);
					}
					vet.setId(Integer.parseInt(request.getParameter("id_veterinario")));
					mas.setId(Integer.parseInt(request.getParameter("id_mascota")));
					atencion.setPracticas(practicas);
					atencion.setVeterinario(vet);
					atencion.setAnimal(mas);
					LocalDate fecha_atencion = LocalDate.parse(request.getParameter("atencion"));
					atencion.setFecha_atencion(fecha_atencion);
					LocalDate fecha_pago= LocalDate.parse(request.getParameter("pago"));
					atencion.setFecha_pago(fecha_pago);
					
					boolean repetido = al.esRepetido(atencion);
					if (repetido == false) {
						al.add(atencion);
						regAfectados = 1;
					} else {
						regAfectados = -1;
					}

				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Atencion atencion = new Atencion();
					atencion.setId(ID);
					id_practicas = request.getParameterValues("practicas[]");
					for (String id : id_practicas) {	
						Practica practica = new Practica();
						practica.setId(Integer.parseInt(id));
						practicas.add(practica);
					}
					vet.setId(Integer.parseInt(request.getParameter("id_veterinario")));
					mas.setId(Integer.parseInt(request.getParameter("id_mascota")));
					atencion.setPracticas(practicas);
					atencion.setVeterinario(vet);
					atencion.setAnimal(mas);
					LocalDate fecha_atencion = LocalDate.parse(request.getParameter("atencion"));
					atencion.setFecha_atencion(fecha_atencion);
					LocalDate fecha_pago= LocalDate.parse(request.getParameter("pago"));
					atencion.setFecha_pago(fecha_pago);
					
					boolean repetido = al.esRepetido(atencion);
					if (repetido == false) {
						al.update(atencion);
						regAfectados = 1;						
					} else {
						regAfectados = -1;
					}	
				}
				break;
			}
			
			case "delete": {
				Atencion atencion = new Atencion();
				int ID = Integer.parseInt(request.getParameter("id"));
				atencion.setId(ID);
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


