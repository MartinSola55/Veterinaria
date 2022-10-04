$('#txtAtencion').daterangepicker({
    "autoApply": true,
    "locale": {
 		"format": "YYYY-MM-DD",
        "separator": " - ",
        "applyLabel": "Aplicar",
        "cancelLabel": "Cancelar",
        "fromLabel": "Hasta",
        "toLabel": "Desde",
    },
    
    singleDatePicker: true,     
    maxDate: hoy,
    opens: 'right',
});

$('#txtPago').daterangepicker({
    "autoApply": true,
    "locale": {
 		"format": "YYYY-MM-DD",
        "separator": " - ",
        "applyLabel": "Aplicar",
        "cancelLabel": "Cancelar",
        "fromLabel": "Hasta",
        "toLabel": "Desde",
    },
    
    singleDatePicker: true,     
    maxDate: hoy,
    opens: 'right',
});

$("#txtIDDuenio").val($.urlParam('id'));
listarAtenciones();
listarVeterinarios();
listarPracticas();
listarMascotasAtencion();

$.ajax({
	url : 'Clientes',
	method: 'get',
	data : {
		id : $.urlParam('id')
	},
	success : function(data) {
	    $("#txtNombre").val(data['nombre']);
	    $("#txtApellido").val(data['apellido']);
	    $("#txtTelefono").val(data['telefono']);
	    $("#txtDireccion").val(data['direccion']);
	    $("#txtEmail").val(data['email']);
	    $("#txtDNI").val(data['dni']);
	}
});

function listarAtenciones() {
	$.ajax({
		url : 'Atenciones',
		method: 'get',
		data : {
			idCliente : $.urlParam('id'),
		},
		success : function(data) {
			console.log(data);
			let contenedor = $("#tarjetasAtenciones");
			let tarjeta = "";
	        for (let i = 0; i < data.length; i++) {
	            tarjeta += "<div class='card me-4 col-3 mb-4' style='width: 18rem;'>";
	            tarjeta += "<div class='card-body d-flex flex-column'>"
	            tarjeta += "<h5 class='card-text'>Nombre Animal: " + data[i]["animal"]["nombre"] + "</h5>"
	          	tarjeta += "<p class='card-text'>Veterinario: " + data[i]["veterinario"]["apellido"] + ", " + data[i]["veterinario"]["nombre"] + "</p>"
				for (let n = 0; n < data[i]['practicas'].length; n++) {
	        		tarjeta += "<p class='card-text'>Practica: " + data[i]['practicas'][n]['descripcion'] + "</p>"
	        	}
	            tarjeta += "<p class='card-text'>Fecha de atencion: " + data[i]["fecha_atencion"] + "</p>"
	            tarjeta += "<p class='card-text'>Fecha de pago: " + data[i]["fecha_pago"] + "</p>"
	            tarjeta += "<div class='d-flex justify-content-between mt-auto'>"
	            tarjeta += "<button class='btn btn-outline-dark' onclick='modalEditA(" + data[i]["id"] + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop' >Ver</button>"
	            tarjeta += "<button class='btn btn-danger' onclick='modalDeleteA(" + data[i]["id"] + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop' >Eliminar</button>"
	            tarjeta += "</div>"
	            tarjeta += "</div>"
	            tarjeta += "</div>"
	            tarjeta += "<br/>"
	            contenedor.html(tarjeta);
			}
		}
	});
}


function listarVeterinarios() {	
	$.ajax({
		url : 'Veterinarios',
		method: 'get',
		success : function(data) {
			let control = $("#comboVeterinarios");
			let contenido = "";
	        contenido += "<option value='' disabled >--Seleccione un veterinario--</option>";
	        for (let i = 0; i < data.length; i++) {
	            contenido += "<option value='" + data[i]["id"] + "'>";
	            contenido += data[i]["nombre"] + " " +data[i]["apellido"];
	            contenido += "</option>";
	        }
	        control.html(contenido);
		}
	});
}

function listarPracticas() {	
	$.ajax({
		url : 'Practicas',
		method: 'get',
		success : function(data) {
			let control = $("#comboPracticas");
			let contenido = "";
	        contenido += "<option value='' disabled >--Seleccione una practica--</option>";
	        for (let i = 0; i < data.length; i++) {
	            contenido += "<option value='" + data[i]["id"] + "'>";
	            contenido += data[i]["descripcion"];
	            contenido += "</option>";
	        }
	        control.html(contenido);
		}
	});
}

function listarMascotasAtencion() {	
	$.ajax({
		url : 'Mascotas',
		method: 'get',
		data : {
		idCliente : $.urlParam('id'),
		},
		success : function(data) {
			let control = $("#comboAnimales");
			let contenido = "";
	        contenido += "<option value='' disabled >--Seleccione una mascota--</option>";
	        for (let i = 0; i < data.length; i++) {
	            contenido += "<option value='" + data[i]["id"] + "'>";
	            contenido += data[i]["nombre"];
	            contenido += "</option>";
	        }
	        control.html(contenido);
		}
	});
}

jQuery('#btnAgregarAt').on('click', function () {
    limpiarCamposA();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar atencion");
    $("#txtIDAlumno").val($.urlParam('id'));
    $("#comboCursos").val("0");
});

function completarCamposA(id) {
	$.ajax({
			url : 'Atenciones',
			method: 'get',
			data : {
				id : id,
			},
			success : function(data) {
    	        $("#txtID").val(data["id"]);
	        	$('#comboVeterinarios').val(data['veterinario']['id']);
	        	$('#comboAnimales').val(data['animal']['id']);

	        	for (let i = 0; i < data['practicas'].length; i++) {
	        		$('#comboPracticas').val(data['practicas'][i]['id']);
	        	}
	        	$('#txtAtencion').val(data['fecha_atencion']);
	        	$('#txtPago').val(data['fecha_pago']);
			}
		});
}

function modalEditA(id) {
    $("#staticBackdropLabel").text("Editar atencion");
    limpiarCamposA();
    habilitarCampos();
    completarCamposA(id);
}

function modalDeleteA(id) {
    $("#staticBackdropLabel").text("Eliminar atencion");
    limpiarCamposA();
    deshabilitarCampos();
    $("#btnAceptar").addClass("eliminar");
    completarCamposA(id);
}

function limpiarCamposA() {
    $(".limpiarCampo").val("");
    campos = $(".required2");
    for (let i = 0; i < campos.length; i++) {
        $("#campo" + i).removeClass("error");
    }
    $("#btnAceptar").removeClass("eliminar");
}

function habilitarCampos() {
    $(".habilitarCampo").removeAttr("disabled");
}

function deshabilitarCampos() {
    $(".deshabilitarCampo").attr("disabled", "disabled");
}

function campoRequiredA() {
    campos = $(".required2");
    for (let i = 0; i < campos.length; i++) {
        if (campos[i].value == "") {
            $("#campo" + i).addClass("error");
            return false;
        } else {
            $("#campo" + i).removeClass("error");
        }
    }
    return true;
}

function confirmarCambiosAtenciones() {
    if (campoRequiredA()) {
        let id = $("#txtID").val();
        let veterinario = $("#comboVeterinarios").val();
        let animal = $("#comboAnimales").val();
        let practicas = $("#comboPracticas").val();
        let atencion = $("#txtAtencion").val();
        let pago = $("#txtPago").val();
        let json = {
			"id": id,
			"id_veterinario": veterinario,
			"id_mascota": animal,
			"practicas": practicas,
			"atencion": atencion,
			"pago": pago,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la atencion?") == 1) {
				json["action"] = "delete";
                crudAtenciones(json);
            }
        } else {
			json["action"] = "save";
            crudAtenciones(json);
        }
    }
}

function crudAtenciones(json) {
    $.ajax({
        type: "POST",
        url: "Atenciones",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La atencion se elimino correctamente");
                } else {
                    alert("La atencion se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("La atencion ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}
