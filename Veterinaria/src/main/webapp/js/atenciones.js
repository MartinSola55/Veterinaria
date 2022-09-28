let header = ["ID","Veterinario","Animal","Fecha Pago","Fecha Atencion", "Practica"];
listar();
listadoVeterinarios();
listadoAnimales();
listadoPracticas();

function listar() {
    $.get("Atenciones", function (data) {
        listadoAtenciones(header, data);
    });
}

function listadoAtenciones(arrayHeader, data) {
    let contenido = "";
    contenido += "<table id='tabla-generic' class='table table-oscura table-striped table-bordered table-hover'>";
    contenido += "<thead>";
    contenido += "<tr class='fw-bold'>";
    for (let i = 0; i < arrayHeader.length; i++) {
        contenido += "<td class='text-center'>";
        contenido += arrayHeader[i];
        contenido += "</td>";
    }
    contenido += "<td class='no-sort text-center'>Accion</td>";
    contenido += "</tr>";
    contenido += "</thead>";
    contenido += "<tbody>";
    for (let i = 0; i < data.length; i++) {
        contenido += "<tr>";
        contenido += "<td>" + data[i].descripcion + "</td>";
        contenido += "<td>" + data[i].descripcion + "</td>";
        contenido += "<td>" + data[i].veterinario.nombre + data[i].veterinario.apellido + "</td>";
        contenido += "<td>" + data[i].animal.desripcion + "</td>";
        contenido += "<td>" + data[i].fecha_pago + "</td>";
        contenido += "<td>" + data[i].fecha_atencion+ "</td>";
        contenido += "<td>" + data[i].practica.descripcion + "</td>";
        contenido += "<td class='d-flex justify-content-center'>";
        contenido += "<button class='btn btn-outline-success me-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger ms-4' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-atencion").html(contenido);
    
}


function listadoVeterinarios() {	
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

function listadoAnimales() {	
	$.ajax({
		url : 'Mascotas',
		method: 'get',
		success : function(data) {
			let control = $("#comboAnimales");
			let contenido = "";
	        contenido += "<option value='' disabled >--Seleccione un animal--</option>";
	        for (let i = 0; i < data.length; i++) {
	            contenido += "<option value='" + data[i]["id"] + "'>";
	            contenido += data[i]["descripcion"];
	            contenido += "</option>";
	        }
	        control.html(contenido);
		}
	});
}


function listadoPracticas() {	
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


function completarCampos(id) {
	$.ajax({
			url : 'Atenciones',
			method: 'get',
			data : {
				id : id,
				pagina: 'Atenciones',
			},
			success : function(data) {
		        $('#txtID').val(data['id']);
	        	$("#comboVeterinarios option[value = " + data['veterinario']['id'] + "]").attr('selected', 'selected');
	        	$("#comboAnimales option[value = " + data['animal']['id'] + "]").attr('selected', 'selected');
	        	$("#comboPracticas option[value = " + data['practica']['id'] + "]").attr('selected', 'selected');
			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar atención");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar atención");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar atención al cliente");
});

function limpiarCampos() {
    $(".limpiarCampo").val("");
    campos = $(".required");
    for (let i = 0; i < campos.length; i++) {
        $("#campo" + i).removeClass("error");
    }
    $("#btnAceptar").removeClass("eliminar");
    $("#comboVeterinarios option").removeAttr('selected')
    $("#comboAnimales option").removeAttr('selected')
    $("#comboPracticas option").removeAttr('selected')
}

function habilitarCampos() {
    $(".habilitarCampo").removeAttr("disabled");
}

function deshabilitarCampos() {
    $(".deshabilitarCampo").attr("disabled", "disabled");
}

function campoRequired() {
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
    if (campoRequired()) {
        let id = $("#txtID").val();
        let veterinario = $("#comboVeterinarios").val();
        let animal = $("#comboAnimales").val();
        let practica = $("#comboPracticas").val();
        let json = {
			"id": id,
			"veterinario": veterinario,
			"animal": animal,
			"practica": practica,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la atención?") == 1) {
				json["action"] = "delete";
                crudAtencion(json);
            }
        } else {
			json["action"] = "save";
            crudAtencion(json);
        }
    }
}

function crudAtencion(json) {
    $.ajax({
        type: "POST",
        url: "Atenciones",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La atención se elimino correctamente");
                } else {
                    alert("La atención se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("La atención ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}