<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "entities.*"%>
	<%@ page import="java.util.LinkedList" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Especies</title>
		<link rel="icon" type="image/jpg" href="img/favicon.png" />
	    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
   	    <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css" />
   	    <link href="styles/mis-estilos.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<%@ include file="../Header.jsp" %>
	<main>
		<% LinkedList<Especie> especies = (LinkedList<Especie>)request.getAttribute("listaEspecies");%>
		<h1 class="text-center mt-5">Especies</h1>
		
		
			<div id="tabla-generic" class="container body-content mt-5">
				<table  class="table table-striped table-bordered table-dark table-hover">
				 <thead>
				 	<tr class="text-center">
				 		<td>ID</td>
				 		<td>Descripci�n</td>
				 		<td>Acci�n</td>
				 	</tr>
				 </thead>
				 <tbody>
				 <% for (Especie esp : especies) { %>
		                    			<tr>
		                    				<td class="text-center"><%=esp.getId()%></td>
		                    				<td><%=esp.getDescripcion()%></td>
		                    				<td class="d-flex justify-content-center">
			                    				<button class='btn btn-outline-success me-4' onclick='modalEdit(<%=esp.getId()%>)' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>
			                    				<button class='btn btn-outline-danger ms-4' onclick='modalDelete(<%=esp.getId()%>)' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>
											</td>	                    				
		                    			</tr>
		                    		<% } %>
				 </tbody>
				</table>
			</div>
			<hr />
			<div class="container">
				<button id="btnAgregar" class="btn btn-success mb-5 mt-2 px-4" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
					Agregar
				</button>
			</div>
			
			
			<!-- Modal -->
			<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			    <div class="modal-dialog modal-dialog-centered">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="staticBackdropLabel"></h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body contenedor-modal align-items-center">
			                <label class="mb-3">ID: </label>
			                <input id="txtID" type="text" class="form-control limpiarCampo w-25 mb-3" disabled />
			                <label class="mb-3 campo0">Descripci�n: </label>
			                <input id="txtDescripcion" type="text" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required mb-3" />
			            </div>
			            <div class="modal-footer">
			                <button id="btnCancelar" type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
			                <button id="btnAceptar" onclick="confirmarCambios()" type="button" class="btn btn-success">Aceptar</button>
			            </div>
			        </div>
			    </div>
			</div>
	</main>
	<%@ include file="../Footer.jsp" %>
	
	
    <script src="js/bootstrap.min.js"></script>
    <script src="js/modernizr-2.8.3.js"></script>
    <script src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
	<script src="js/especies.js"></script>
	</body>
</html>