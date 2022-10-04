let hoy = new Date();
let dd = String(hoy.getDate()).padStart(2, '0');
let mm = String(hoy.getMonth() + 1).padStart(2, '0');
let yyyy = hoy.getFullYear();
hoy = yyyy + '-' + mm + '/' + dd;
moment.locale('es');

$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

$('#txtNacimiento').daterangepicker({
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
    /*isInvalidDate: function (date) {
        if (date.day() == 1 ||  date.day() == 2  || date.day() == 3 ||  date.day() == 4 ||  date.day() == 5)
            return false;
        return true;
    }*/
})

listarMascotas();
listarRazas();

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
	            tarjeta += "<button class='btn btn-outline-dark' onclick='modalEditM(" + data[i]["id"] + ")' data-bs-toggle='modal' data-bs-target='#staticBackdropMascota' >Ver</button>"
	            tarjeta += "<button class='btn btn-danger' onclick='modalDeleteM(" + data[i]["id"] + ")' data-bs-toggle='modal' data-bs-target='#staticBackdropMascota' >Eliminar</button>"
	            tarjeta += "</div>"
	            tarjeta += "</div>"
	            tarjeta += "</div>"
	            tarjeta += "<br/>"
	            contenedor.html(tarjeta);
			}
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

function modalEditM(id) {
    $("#staticBackdropLabelMascota").text("Editar mascota");
    limpiarCamposM();
    habilitarCampos();
    completarCamposM(id);
}

function modalDeleteM(id) {
    $("#staticBackdropLabelMascota").text("Eliminar mascota");
    limpiarCamposM();
    deshabilitarCampos();
    $("#btnAceptar").addClass("eliminar");
    completarCamposM(id);
}

function limpiarCamposM() {
    $(".limpiarCampo").val("");
    campos = $(".required");
    for (let i = 0; i < campos.length; i++) {
        $("#campo" + i).removeClass("error");
    }
    $("#btnAceptar").removeClass("eliminar");
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
