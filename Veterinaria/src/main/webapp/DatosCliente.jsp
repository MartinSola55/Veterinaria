<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "entities.*"%>
	<%@ page import="java.util.LinkedList" %>
	<%@ page import="logic.*" %>
	<%@ page import="data.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Animales</title>
		<link rel="icon" type="image/jpg" href="img/favicon.png" />
	    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
   	    <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css" />
   	    <link href="styles/mis-estilos.css" rel="stylesheet" type="text/css" />
   	    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

	
	</head>
	<body>
	<%@ include file="Header.jsp" %>
	<main class="container">
		<h1 class="text-center mt-4">Datos del cliente</h1>

		<div id="datos-cliente" class="container-fluid mt-5">
		    <label class="mb-2">Nombre: </label>
		    <input id="txtNombre" type="text" class="form-control mb-2" disabled />

		    <label class="mb-2">Apellido: </label>
		    <input id="txtApellido" type="text" class="form-control mb-2" disabled />

		    <label class="mb-2">Teléfono: </label>
		    <input id="txtTelefono" type="text" class="form-control mb-2" disabled />

		    <label class="mb-2">Dirección: </label>
		    <input id="txtDireccion" type="text" class="form-control mb-2" disabled />

		    <label class="mb-2">Email: </label>
		    <input id="txtEmail" type="text" class="form-control mb-2" disabled />

		    <label class="mb-2">DNI: </label>
		    <input id="txtDNI" type="text" class="form-control mb-2" disabled />

	        <button id="btnAgregarMas" type="button" class="btn btn-success px-4 mt-4" data-bs-toggle="modal" data-bs-target="#staticBackdropMascota">
	            Agregar mascota
	        </button>
	        
	        <hr/>
	        <hr/>
	        
	        <button id="btnAgregarAt" type="button" class="btn btn-success px-4 mt-4" data-bs-toggle="modal"  data-bs-target="#staticBackdrop"> 
	            Agregar atencion
	        </button>
															
		</div>

		<hr class="mt-5" />

		<h2 class="text-center mb-4">Mascotas</h2>

		<div id="tarjetasMascotas" class="container d-flex flex-row row mx-auto justify-content-center">

		</div>
		
	
		
		
				<!-- Modal atenciones -->
   			<div id="staticBackdrop" class="modal fade" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
			                <select id="comboVeterinarios" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3" ></select>
			                
			        		<label id="campo1" class="mb-3">Animal: </label>
			                <select id="comboAnimales" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3" ></select>
			                 
			               <label id="campo2" class="mb-3">Prácticas: </label>
			              	<select id="comboPracticas" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3" ></select>
			               <!--<label><input id="cb1" type="checkbox" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3" disabled />Uno</label>
			               <label><input type="checkbox" id="comboPracticas" value="first_checkbox" class=" limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3"> Este es mi primer checkbox</label><br>-->
			               
			                <label id="campo3" class="mb-3">Fecha de atención: </label>
			               	<input id="txtAtencion" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3" />
			                
			                <label id="campo4" class="mb-3">Fecha de pago: </label>
			               	<input id="txtPago" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required2 mb-3" />
			               					<!-- HACER Q LA FECHA DE PAGO PUEDA SER NULA -->
			                
			            </div>
			            <div class="modal-footer">
			                <button id="btnCancelar" type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
			                <button id="btnAceptar" onclick="confirmarCambiosAtenciones()" type="button" class="btn btn-success">Aceptar</button>
			            </div>
			        </div>
			    </div>
			</div>
			
		
		<!-- Modal mascotas -->
        <div class="modal fade" id="staticBackdropMascota" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabelMascota" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabelMascota"></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body contenedor-modal align-items-center">
                        <label class="mb-3">ID: </label>
                        <input id="txtID" class = "form-control limpiarCampo w-25 mb-3" readonly />

                        <label class="mb-3">ID Dueño: </label>
                        <input id="txtIDDuenio" class = "form-control w-25 mb-3" readonly />

                        <label id="campo0" class="mb-3">Nombre: </label>
                        <input id="txtNombreMascota" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required mb-3" />

                        <label id="campo1" class="mb-3">Fecha de nacimiento: </label>
                        <input id="txtNacimiento" class="form-control limpiarCampo habilitarCampo deshabilitarCampo required mb-3" />

                        <label id="campo2" class="mb-3">Sexo: </label>
                        <select id="comboSexo" class="form-select limpiarCampo habilitarCampo deshabilitarCampo required mb-3">
                        	<option value="" disabled selected>--Seleccione un sexo--</option>
                        	<option value="1">M</option>
                        	<option value="2">F</option>
                        </select>

                        <label id="campo3" class="mb-3">Raza: </label>
                        <select id="comboRazas" class="form-select limpiarCampo habilitarCampo deshabilitarCampo required mb-3">
                        </select>
                    </div>
                    <div class="modal-footer">
		                <button id="btnCancelar" type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
		                <button id="btnAceptar" onclick="confirmarCambios()" type="button" class="btn btn-success">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>

      
        
        
        <hr class="mt-5" />

		<h2 class="text-center mb-4">Atenciones</h2>

		<div id="tarjetasAtenciones" class="container d-flex flex-row row mx-auto justify-content-center">

		</div>


        

	</main>

	<%@ include file="Footer.jsp" %>

	
    <script src="js/bootstrap.min.js"></script>
    <script src="js/modernizr-2.8.3.js"></script>
    <script src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
	<script src="js/moment-with-locales.js"></script>
	<script src="js/daterangepicker.js"></script>
	<script src="js/daterangepicker-es.js"></script>
	<script src="js/datosCliente.js"></script>
	</body>
</html>