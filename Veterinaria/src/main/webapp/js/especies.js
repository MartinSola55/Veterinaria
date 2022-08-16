let header = ["Descripcion"];
listar();

function listar() {
    $.get("Especies", function (data) {
        listadoEspecies(header, data);
    });
}

function listadoEspecies(arrayHeader, data) {
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
        contenido += "<td class='d-flex justify-content-center'>";
        contenido += "<button class='btn btn-outline-success me-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger ms-4' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-especie").html(contenido);
    
}

function completarCampos(id) {
	$.ajax({
			url : 'Especies',
			method: 'get',
			data : {
				id : id,
				pagina: 'Especies',
			},
			success : function(data) {
		        $('#txtID').val(data['id']);
	        	$('#txtDescripcion').val(data['descripcion']);
			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar especie");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar especie");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar especie");
});

function limpiarCampos() {
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
        let json = {
			"id": id,
			"descripcion": descripcion,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la especie?") == 1) {
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
        url: "Especies",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La especie se elimino correctamente");
                } else {
                    alert("La especie se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("La especie ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}