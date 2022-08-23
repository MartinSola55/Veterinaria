package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import entities.*;




/**
 * Servlet implementation class Practicas
 */
public class Practicas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Practicas() {
        super();
        // TODO Auto-generated constructor stub
    }
	PracticaLogic pl = new PracticaLogic();
    
   
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			

			if (request.getParameter("id") != null) {			
				int id = Integer.parseInt(request.getParameter("id"));
				Practica practica = pl.getOne(id);
				
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
			    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
			    gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());	        
			   	Gson gson = gsonBuilder.setPrettyPrinting().create();
					
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				
				
				
				response.getWriter().write(gson.toJson(practica));
				
			} else {
				LinkedList<Practica> practicas = pl.getAll();

		        GsonBuilder gsonBuilder = new GsonBuilder();
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());	        
		        Gson gson = gsonBuilder.setPrettyPrinting().create();
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				response.getWriter().write(gson.toJson(practicas));
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
		PracticaLogic pl = new PracticaLogic();
		Precio pre= new Precio();

		int regAfectados = 1;
		try {
			switch (request.getParameter("action")) {
			case "save": {
				//int repetido=0;
				String descripcion = request.getParameter("descripcion");
				String precio = request.getParameter("precio");
				pre.setValor(Double.parseDouble(precio));
						
				if (request.getParameter("id") == "") {
					Practica practica = new Practica();
					practica.setDescripcion(descripcion);
					practica.setPrecio(pre);					
					//repetido = pl.getByDescricpion(practica);
					
					pl.add(practica);
						
						//pre= preLog.addAPractica(Double.parseDouble(precio));
						//practica.setPrecio(pre);
						//pl.add(practica);
						//regAfectados = 1;		

				} else {
					int ID = Integer.parseInt(request.getParameter("id"));
					Precio precioViejo=pl.getOne(ID).getPrecio();
					String desc=request.getParameter("descripcion");
					Double precioNuevo= Double.parseDouble(request.getParameter("precio"));				
					Practica practica = new Practica();

					pre.setId(precioViejo.getId());
					pre.setValor(precioNuevo);
					
					practica.setId(ID);
					practica.setDescripcion(desc);
					practica.setPrecio(pre);
					//repetido = pl.getByDescricpion(practica);
					
					pl.update(practica);
				
						//preLog.update(pre);
						//pl.update(practica);
						//regAfectados = 1;
			
				}
				break;
			}
			case "delete": {
				Practica practica = new Practica();
				Precio precioABorrar= new Precio();
				int id= Integer.parseInt(request.getParameter("id"));
				
				precioABorrar= pl.getOne(id).getPrecio();
				practica.setId(id);
				practica.setPrecio(precioABorrar);			
				
				pl.delete(practica);
				//pl.delete(practica);
				//preLog.delete(aBorrar);				
				//regAfectados = 1;		
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

class LocalDateSerializer implements JsonSerializer < LocalDate > {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}

class LocalDateTimeSerializer implements JsonSerializer < LocalDateTime > {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}

class LocalDateDeserializer implements JsonDeserializer < LocalDate > {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.ENGLISH));
    }
}

class LocalDateTimeDeserializer implements JsonDeserializer < LocalDateTime > {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
            DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss").withLocale(Locale.ENGLISH));
    }
}



