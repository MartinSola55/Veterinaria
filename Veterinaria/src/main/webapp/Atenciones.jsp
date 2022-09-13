<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "entities.*"%>
	<%@ page import="java.util.LinkedList" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Atenciones</title>
		<link rel="icon" type="image/jpg" href="img/favicon.png" />
	    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
   	    <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css" />
   	    <link href="styles/mis-estilos.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<%@ include file="Header.jsp" %>
	<main>
		<h1 class="text-center mt-5">Atenciones</h1>
		
		
			<div id="tabla-atencion" class="container body-content mt-5">
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
			                
			                <label id="campo0" class="mb-3">Veterinario: </label>
			                <select id="comboVeterinarios" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required mb-3" ></select>
			                
			        		<label id="campo1" class="mb-3">Animal: </label>
			                <select id="comboAnimales" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required mb-3" ></select>
			                 
			                <label id="campo2" class="mb-3">Práctica: </label>
			                <select id="comboPracticas" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required mb-3" ></select>
			                
			            </div>
			            <div class="modal-footer">
			                <button id="btnCancelar" type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
			                <button id="btnAceptar" onclick="confirmarCambios()" type="button" class="btn btn-success">Aceptar</button>
			            </div>
			        </div>
			    </div>
			</div>
	</main>
	<%@ include file="Footer.jsp" %>
	
	
    <script src="js/bootstrap.min.js"></script>
    <script src="js/modernizr-2.8.3.js"></script>
    <script src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
	<script src="js/atenciones.js"></script>
	</body>
</html>