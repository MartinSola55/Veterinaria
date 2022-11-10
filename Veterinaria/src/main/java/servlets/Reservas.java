package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;

import entities.*;
import logic.ReservaLogic;

/**
 * Servlet implementation class Reservas
 */
public class Reservas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reservas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ReservaLogic rl = new ReservaLogic();
			if (request.getParameter("id")!=null) {
				int id = Integer.parseInt(request.getParameter("id"));
				
				Reserva reserva = rl.getOne(id);	
				String json = new Gson().toJson(reserva);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} else
			{
				LinkedList<Reserva> reservas = rl.getAll();		
				String json = new Gson().toJson(reservas);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ReservaLogic rl= new ReservaLogic();
		
		int regAfectados = 0;
		try {
			switch (request.getParameter("action")) {
			case "save": {
					Reserva reserva = new Reserva();
					Producto producto = new Producto();
					Cliente cliente = new Cliente();
					
					cliente.setId(Integer.parseInt(request.getParameter("cliente")));
					reserva.setCliente(cliente);
					
					producto.setId(Integer.parseInt(request.getParameter("producto")));
					reserva.setProducto(producto);
					
					reserva.setCantidad(Integer.parseInt(request.getParameter("cantidad")));			
										
					rl.add(reserva);
					regAfectados = 1;
					
				break;
			}
			case "delete":
			{
				Reserva reserva = new Reserva();

				reserva.setId(Integer.parseInt(request.getParameter("id")));
				rl.delete(reserva);
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
		response.getWriter().write(json);	}

}
