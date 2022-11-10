let header = ["Descripcion", "Stock","Tipo", "Precio"];
listar();

function listar() {
    $.get("ProductosAdmin", function (data) {
    	listadoProductos(header, data);
    });
}

function listadoProductos(arrayHeader, data) {
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
        contenido += "<td>" + data[i].stock + "</td>";
        contenido += "<td>" + data[i].tipo + "</td>";
        contenido += "<td>$" + data[i].precio + "</td>";
        contenido += "<td class='d-flex justify-content-center'>";
        contenido += "<button class='btn btn-outline-success me-4' onclick='modalEdit(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-pencil-square'></i></button>";
        contenido += "<button class='btn btn-outline-danger ms-4' onclick='modalDelete(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='bi bi-trash3'></i></button>";
        contenido += "</td>";
        contenido += "</tr>";
    }
    contenido += "</tbody>";
    contenido += "</table>";
    $("#tabla-producto").html(contenido);
    
}

function completarCampos(id) {
	$.ajax({
			url : 'ProductosAdmin',
			method: 'get',
			data : {
				id : id,
				pagina: 'Productos',
			},
			success : function(data) {
	        	$('#txtDescripcion').val(data['descripcion']);
		        $('#txtID').val(data['id']);
        		$("#txtStock").val(data['stock']);
         		$("#txtTipo").val(data['tipo']);
         		$("#txtPrecio").val(data['precio']);
			}
		});
}

function modalEdit(id) {
    $("#staticBackdropLabel").text("Editar producto");
    limpiarCampos();
    habilitarCampos();
	completarCampos(id);
}

function modalDelete(id) {
    $("#staticBackdropLabel").text("Eliminar producto");
    limpiarCampos();
    deshabilitarCampos();
	completarCampos(id);
	$("#btnAceptar").addClass("eliminar");
}

jQuery('#btnAgregar').on('click', function () {
    limpiarCampos();
    habilitarCampos();
    $("#staticBackdropLabel").text("Agregar producto");
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
        let stock = $("#txtStock").val();
        let tipo = $("#txtTipo").val();
		let precio = $("#txtPrecio").val();
        let json = {
			"id": id,
			"descripcion": descripcion,
			"stock": stock,
			"tipo": tipo,
			"precio": precio,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("Seguro que desea eliminar el producto?") == 1) {
				json["action"] = "delete";
                crudProducto(json);
            }
        } else {
			json["action"] = "save";
            crudProducto(json);
        }
    }
}

function crudProducto(json) {
    $.ajax({
        type: "POST",
        url: "ProductosAdmin",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("El producto se elimino correctamente");
                } else {
                    alert("El producto se guardo correctamente");
                }
         		location.reload();
            } else if ( data == -1) {
                alert("El producto ingresado ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
        }
    });
}