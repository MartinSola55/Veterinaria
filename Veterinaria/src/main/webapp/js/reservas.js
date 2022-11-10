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
        contenido += "<button class='btn btn-outline-light' onclick='modal(" + data[i].id + ")' data-bs-toggle='modal' data-bs-target='#staticBackdrop'>Seleccionar</button>";
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
		        $('#txtProducto').val(data['id']);
        		$("#txtStock").val(data['stock']);
         		$("#txtTipo").val(data['tipo']);
         		$("#txtPrecio").val(data['precio']);
			}
		});
}

function modal(id) {
    limpiarCampos();
	completarCampos(id);
}

$("#txtCantidad").on("input", function() {
	let total = $("#txtCantidad").val() * $("#txtPrecio").val();
	$("#labelTotal").html("Total: $" + total);
})

function limpiarCampos() {
    $(".limpiarCampo").val("");
    campos = $(".required");
    for (let i = 0; i < campos.length; i++) {
        $("#campo" + i).removeClass("error");
    }
    $("#btnAceptar").removeClass("eliminar");
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
        let producto = $("#txtProducto").val();
        let cantidad = $("#txtCantidad").val();
        let json = {
			"producto": producto,
			"cantidad": cantidad,
			"cliente": 1, //Como no hay login dejamos 1 por defecto
			"action": "save"
		};
        crudProducto(json);
    }
}

function crudProducto(json) {
    $.ajax({
        type: "POST",
        url: "Reservas",
        dataType: 'json',
        data: json,
        success: function (data) {
            if (data == 1) {      
                alert("El producto se reservo correctamente");
         		location.reload();
            } else {
                alert("La reserva no se pudo realizar. Error en la base de datos");
            }
        }
    });
}