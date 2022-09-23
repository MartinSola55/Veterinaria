let header = ["ID","Dni", "Nombre","Apellido","Direccion","Telefono","Email"];
listar();

function listar() {
    $.get("Clientes", function (data) {
        listadoClientes(header, data);
    });
}

function listadoClientes(arrayHeader, data) {
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
        contenido += "<td class='text-center'>" + data[i].dni + "</td>";
        contenido += "<td>" + data[i].nombre + "</td>";
        contenido += "<td>" + data[i].apellido + "</td>";
        contenido += "<td>" + data[i].direccion + "</td>";
        contenido += "<td>" + data[i].telefono + "</td>";
        contenido += "<td>" + data[i].email + "</td>";
        contenido += "<td class='d-flex justify-content-between'>";
        contenido += "<button class='btn btn-outline-success ms-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "<button class='btn btn-outline-light me-4' onclick='selectPersona(" + data[i].id + ")'>Seleccionar</button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-cliente").html(contenido);
    
}

function completarCampos(id) {
	$.ajax({
			url : 'Clientes',
			method: 'get',
			data : {
				id : id,
				pagina: 'Clientes',
			},
			success : function(data) {
				console.log(data)
				$('#txtID').val(data['id']);
	        	$('#txtDni').val(data['dni']);
	        	$('#txtNombre').val(data['nombre']);
	        	$('#txtApellido').val(data['apellido']);
	        	$('#txtDireccion').val(data['direccion']);
	        	$('#txtTelefono').val(data['telefono']);
	        	$('#txtEmail').val(data['email']);
			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar cliente");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar cliente");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar cliente");
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
        let dni = $("#txtDni").val();
        let nombre = $("#txtNombre").val();
        let apellido = $("#txtApellido").val();
        let direccion = $("#txtDireccion").val();
        let telefono= $("#txtTelefono").val();
        let email = $("#txtEmail").val();
        let json = {
			"id": id,
			"dni": dni,
			"nombre": nombre,
			"apellido": apellido,
			"direccion": direccion,
			"telefono": telefono,
			"email": email,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar la cliente?") == 1) {
				json["action"] = "delete";
                crudCliente(json);
            }
        } else {
			json["action"] = "save";
            crudCliente(json);
        }
    }
}

function crudCliente(json) {
    $.ajax({
        type: "POST",
        url: "Clientes",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("El cliente se elimino correctamente");
                } else {
                    alert("El cliente se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("El cliente ingresado ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}

function selectPersona(id) {
    window.location.href = "/Veterinaria/DatosCliente.jsp?id=" + id;
}