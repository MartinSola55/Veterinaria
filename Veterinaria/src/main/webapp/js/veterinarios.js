let header = ["Matricula","Nombre","Apellido","Telefono","Direccion","Email"];
listar();

function listar() {
    $.get("Veterinarios", function (data) {
		
        listadoVeterinarios(header, data);
    });
}

function listadoVeterinarios(arrayHeader, data) {
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
        contenido += "<td class='text-center'>" + data[i].matricula + "</td>";
        contenido += "<td>" + data[i].nombre + "</td>";
        contenido += "<td>" + data[i].apellido + "</td>";
        contenido += "<td>" + data[i].telefono + "</td>";
        contenido += "<td>" + data[i].direccion + "</td>";
        contenido += "<td>" + data[i].email + "</td>";
        
        contenido += "<td class='d-flex justify-content-center'>";
        contenido += "<button class='btn btn-outline-success me-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger ms-4' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-veterinario").html(contenido);
    
}

function completarCampos(id) {
	$.ajax({
			url : 'Veterinarios',
			method: 'get',
			data : {
				id : id,
			},
			success : function(data) {
			 $("#txtID").val(data['id']);
       		 $("#txtMatricula").val(data['matricula']);
       	 	 $("#txtNombre").val(data['nombre']);
        	 $("#txtApellido").val(data['apellido']);
        	 $("#txtTelefono").val(data['telefono']);
        	 $("#txtEmail").val(data['email']);
         	 $("#txtDireccion").val(data['direccion']);

			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar veterinario");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar veterinario");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar veterinario");
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
        let matricula = $("#txtMatricula").val();
        let nombre = $("#txtNombre").val();
        let apellido = $("#txtApellido").val();
        let telefono = $("#txtTelefono").val();
        let email = $("#txtEmail").val();
        let direccion = $("#txtDireccion").val();
        let json = {
			"id": id,
			"matricula": matricula,
			"nombre": nombre,
			"apellido": apellido,
			"telefono": telefono,
			"email": email,
			"direccion": direccion,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar el veterinario?") == 1) {
				json["action"] = "delete";
                crudVeterinario(json);
            }
        } else {
			json["action"] = "save";
            crudVeterinario(json);
        }
    }
}

function crudVeterinario(json) {
    $.ajax({	
        type: "POST",
        url: "Veterinarios",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("El veterinario se elimino correctamente");
                } else {
                    alert("El veterinario se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("El veterinario ingresado ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}