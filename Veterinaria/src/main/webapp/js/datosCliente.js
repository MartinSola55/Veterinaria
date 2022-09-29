let hoy = new Date();
let dd = String(hoy.getDate()).padStart(2, '0');
let mm = String(hoy.getMonth() + 1).padStart(2, '0');
let yyyy = hoy.getFullYear();
hoy = dd + '/' + mm + '/' + yyyy;
moment.locale('es');

$(function () {
    $('#txtNacimiento').daterangepicker({
        "autoApply": true,
        "locale": {
            "applyLabel": "Aplicar",
            "cancelLabel": "Cancelar",
            "fromLabel": "Hasta",
            "toLabel": "Desde",
        },
        singleDatePicker: true,
       //minDate: minDia, 
      
        maxDate: hoy,
        opens: 'right',
        isInvalidDate: function (date) {
            if (date.day() == 1 ||  date.day() == 2  || date.day() == 3 ||  date.day() == 4 ||  date.day() == 5)
                return false;
            return true;
        }
    })
});



$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

listarMascotas();
listarRazas();
listarAtenciones();
listarVeterinarios();
listarPracticas();
listarMascotasAtencion();

$("#txtIDDuenio").val($.urlParam('id'));

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

function listarRazas() {
    	$.ajax({
		url : 'Razas',
		method: 'get',
		success : function(data) {
			let control = $("#comboRazas");
			let contenido = "";
	        contenido += "<option value='' disabled >--Seleccione una raza--</option>";
	        for (let i = 0; i < data.length; i++) {
	            contenido += "<option value='" + data[i]["id"] + "'>";
	            contenido += data[i]["descripcion"];
	            contenido += "</option>";
	        }
	        control.html(contenido);
		}
	});
}

function listarMascotas() {
	$.ajax({
		url : 'Mascotas',
		method: 'get',
		data : {
			idCliente : $.urlParam('id'),
		},
		success : function(data) {
			let contenedor = $("#tarjetasMascotas");
			let tarjeta = "";
	        for (let i = 0; i < data.length; i++) {
	            tarjeta += "<div class='card me-4 col-3 mb-4' style='width: 18rem;'>";
	            tarjeta += "<div class='card-body d-flex flex-column'>"
	            tarjeta += "<h5 class='card-title'>Raza: " + data[i]["raza"]["descripcion"] + "</h5>"
	            tarjeta += "<h5 class='card-title'>Especie: " + data[i]["raza"]["especie"]["descripcion"] + "</h5>"
	            tarjeta += "<p class='card-text'>Nombre: " + data[i]["nombre"] + "</p>"
	            tarjeta += "<p class='card-text'>Fecha de nacimiento: " + data[i]["fecha_nac"] + "</p>"
	            let sexo = "";
	            if (data[i]["sexo"] === 1) {
					sexo = "Masculino";
				} else {
					sexo = "Femenino";
				}
	            tarjeta += "<p class='card-text'>Sexo: " + sexo + "</p>"
	            tarjeta += "<div class='d-flex justify-content-between mt-auto'>"
	            tarjeta += "<button class='btn btn-outline-dark' onclick='modalEditM(" + data[i]["id"] + ")' data-bs-toggle='modal' data-bs-target='#staticBackdropMascotaE' >Ver</button>"
	            tarjeta += "<button class='btn btn-danger' onclick='modalDeleteM(" + data[i]["id"] + ")' data-bs-toggle='modal' data-bs-target='#staticBackdropMascotaD' >Eliminar</button>"
	            tarjeta += "</div>"
	            tarjeta += "</div>"
	            tarjeta += "</div>"
	            tarjeta += "<br/>"
	            //console.log(tarjeta);
	            contenedor.html(tarjeta);
			}
		}
	});
}



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
	          	tarjeta += "<p class='card-text'>Veterinario: " + data[i]["veterinario"]["nombre"] + "</p>"
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
	        console.log(contenido);
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

jQuery('#btnAgregarMas').on('click', function () {
    limpiarCamposM();
    habilitarCampos();
    $("#staticBackdropLabelMascota").text("Agregar mascota");
    $("#txtIDAlumno").val($.urlParam('id'));
    $("#comboCursos").val("0");
});

jQuery('#btnAgregarAt').on('click', function () {
    limpiarCamposA();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar atencion");
    $("#txtIDAlumno").val($.urlParam('id'));
    $("#comboCursos").val("0");
});

function completarCamposM(id) {
	$.ajax({
			url : 'Mascotas',
			method: 'get',
			data : {
				id : id,
			},
			success : function(data) {
    	        $("#txtID").val(data["id"]);
	        	$('#txtNombreMascota').val(data['nombre']);
	        	$('#txtNacimiento').val(data['fecha_nac']);
	        	$('#comboSexo').val(data['sexo']);
	        	$('#comboRazas').val(data['raza']['id']);
			}
		});
}

function completarCamposA(id) {
	$.ajax({
			url : 'Atenciones',
			method: 'get',
			data : {
				id : id,
			},
			success : function(data) {
				console.log(data);
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
function modalEditM(id) {
    $("#staticBackdropLabelMascotaE").text("Editar mascota");
    limpiarCamposM();
    habilitarCampos();
    completarCamposM(id);
}

function modalDeleteA(id) {
    $("#staticBackdropLabel").text("Eliminar atencion");
    limpiarCamposA();
    deshabilitarCampos();
    $("#btnAceptar").addClass("eliminar");
    completarCamposA(id);
}
function modalDeleteM(id) {
    $("#staticBackdropLabelMascotaD").text("Eliminar mascota");
    limpiarCamposM();
    deshabilitarCampos();
    $("#btnAceptar").addClass("eliminar");
    completarCamposM(id);
}

function limpiarCamposA() {
    $(".limpiarCampo").val("");
    campos = $(".required2");
    for (let i = 0; i < campos.length; i++) {
        $("#campo" + i).removeClass("error");
    }
    $("#btnAceptar").removeClass("eliminar");
}
function limpiarCamposM() {
    $(".limpiarCampo").val("");
    campos = $(".required");
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
function campoRequiredM() {
    campos = $(".required");
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

function confirmarCambios() {
    if (campoRequiredM()) {
        let id = $("#txtID").val();
        let duenio = $("#txtIDDuenio").val();
        let nombre = $("#txtNombreMascota").val();
        let fecha_nac = $("#txtNacimiento").val();
        let sexo = $("#comboSexo").val();
        let raza = $("#comboRazas").val();
        let json = {
			"id": id,
			"duenio": duenio,
			"nombre": nombre,
			"fecha_nac": fecha_nac,
			"sexo": sexo,
			"raza": raza,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la mascota?") == 1) {
				json["action"] = "delete";
                crudMascota(json);
            }
        } else {
			json["action"] = "save";
            crudMascota(json);
        }
    }
}

function crudMascota(json) {
    $.ajax({
        type: "POST",
        url: "Mascotas",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La mascota se elimino correctamente");
                } else {
                    alert("La mascota se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("La mascota ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}

function confirmarCambiosAtenciones() {
    if (campoRequiredA()) {
        let id = $("#txtID").val();
        let veterinario = $("#comboVeterinarios").val();
        let animal = $("#comboAnimales").val();
        let practica = $("#comboPracticas").val();
        let atencion = $("#txtAtencion").val();
        let pago = $("#txtPago").val();
        let json = {
			"id": id,
			"id_veterinario": veterinario,
			"id_mascota": animal,
			"practica": practica,
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
	console.log(json);
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
