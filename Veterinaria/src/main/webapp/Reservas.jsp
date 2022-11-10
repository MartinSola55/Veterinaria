<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "entities.*"%>
	<%@ page import="java.util.LinkedList" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Reservar productos</title>
		<link rel="icon" type="image/jpg" href="img/favicon.png" />
	    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
   	    <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css" />
   	    <link href="styles/mis-estilos.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<%@ include file="Header.jsp" %>
	<main>
		<h1 class="text-center mt-5">Reservar productos</h1>
		
		
			<div id="tabla-producto" class="container body-content mt-5">
			</div>
			<hr />

			
			
			<!-- Modal -->
			<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			    <div class="modal-dialog modal-dialog-centered">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="staticBackdropLabel">Reservar producto</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			           
			            <div class="modal-body contenedor-modal align-items-center">
			            	
			                <label class="mb-3">ID Producto: </label>
			                <input id="txtProducto" type="text" class="form-control limpiarCampo w-25 mb-3" disabled />
			                
			                <label id="campo0" class="mb-3">Descripcion: </label>
			                <input id="txtDescripcion" type="text" class="form-control limpiarCampo required mb-3" disabled/>

			                <label id="campo1" class="mb-3">Stock: </label>
			                <input id="txtStock" type="text" class="form-control limpiarCampo  required mb-3" disabled/>
			                
			                <label id="campo2" class="mb-3">Tipo: </label>
			                <input id="txtTipo" type="text" class="form-control limpiarCampo required mb-3" disabled/>
			                
			                <label id="campo3" class="mb-3">Precio: </label>
			                <input id="txtPrecio" type="text" class="form-control limpiarCampo required mb-3" disabled/>
			                
			                <label id="campo4" class="mb-3">Cantidad: </label>
        			        <input id="txtCantidad" type="number" min="0" max="20" class="form-control limpiarCampo required mb-3"/>
			                
			                <h3 id="labelTotal" class="fs-5 fw-bold"></h3>
			                
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
	<script src="js/reservas.js"></script>
	</body>
</html>