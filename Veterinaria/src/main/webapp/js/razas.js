let header = ["ID", "Descripcion", "Especie"];
listar();
listadoEspecies();

function listar() {
    $.get("Razas", function (data) {
        listadoRazas(header, data);
    });
}

function listadoRazas(arrayHeader, data) {
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
        contenido += "<td class='text-center'>" + data[i].id + "</td>";
        contenido += "<td>" + data[i].descripcion + "</td>";
        contenido += "<td>" + data[i].desc_especie + "</td>";
        contenido += "<td class='d-flex justify-content-center'>";
        contenido += "<button class='btn btn-outline-success me-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger ms-4' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-razas").html(contenido);
    
}
	
function listadoEspecies() {	
	$.ajax({
		url : 'Especies',
		method: 'get',
		success : function(data) {
			let control = $("#comboEspecies");
			let contenido = "";
	        contenido += "<option value='' disabled >--Seleccione una especie--</option>";
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
			url : 'Razas',
			method: 'get',
			data : {
				id : id,
			},
			success : function(data) {
		        $('#txtID').val(data['id']);
	        	$('#txtDescripcion').val(data['descripcion']);
        		$("#comboEspecies option[value = " + data['cod_especie'] + "]").attr('selected', 'selected');
			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar raza");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar raza");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar raza");
});

function limpiarCampos() {
    $(".limpiarCampo").val("");
    campos = $(".required");
    for (let i = 0; i < campos.length; i++) {
        $(".campo" + i).removeClass("error");
    }
    $("#btnAceptar").removeClass("eliminar");
    $("#comboEspecies option").removeAttr('selected')
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
        let descripcion = $("#txtDescripcion").val();
        let especie = $("#comboEspecies").val();
        let json = {
			"id": id,
			"descripcion": descripcion,
			"especie": especie,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la raza?") == 1) {
				json["action"] = "delete";
                crudEspecie(json);
            }
        } else {
			json["action"] = "save";
            crudEspecie(json);
        }
    }
}

function crudEspecie(json) {
    $.ajax({
        type: "POST",
        url: "Razas",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La raza se elimino correctamente");
                } else {
                    alert("La raza se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("La raza ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}