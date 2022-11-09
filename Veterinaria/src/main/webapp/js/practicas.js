let header = ["id","Descripcion","Precio"];
listar();


function listar() {
    $.get("Practicas", function (data) {
		
        listadoPracticas(header, data);
    });
}

function listadoPracticas(arrayHeader, data) {
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
	if (data[i].eliminado == 0){
        contenido += "<tr>";
        contenido += "<td class='text-center'>" + data[i].id+ "</td>";
        contenido += "<td>" + data[i].descripcion + "</td>";
        contenido += "<td class='text-center'>$" + data[i].precio.valor+ "</td>";

        
        contenido += "<td class='d-flex justify-content-center'>";
        contenido += "<button class='btn btn-outline-success me-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger ms-4' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-practica").html(contenido);
    
}


function completarCampos(id) {
	$.ajax({
			url : 'Practicas',
			method: 'get',
			data : {
				id : id,
			},
			success : function(data) {
			 $("#txtID").val(data['id']);
       		 $("#txtDescripcion").val(data['descripcion']);
       		 $("#txtPrecio").val(data.precio.valor);

			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar practica");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar practica");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar practica");
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
        let precio = $("#txtPrecio").val();
        let json = {
			"id": id,
			"descripcion": descripcion,
			"precio": precio,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la practica?") == 1) {
				json["action"] = "delete";
                crudPractica(json);
            }
        } else {
			json["action"] = "save";
            crudPractica(json);
        }
    }
}

function crudPractica(json) {
    $.ajax({	
        type: "POST",
        url: "Practicas",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La practica se elimino correctamente");
                } else {
                    alert("La practica se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("La practica ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}