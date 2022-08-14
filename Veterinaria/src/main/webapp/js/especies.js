function completarCampos(id) {
	$.ajax({
			url : 'Especies',
			method: 'get',
			data : {
				id : id,
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
        $(".campo" + i).removeClass("error");
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
            $(".campo" + i).addClass("error");
            return false;
        } else {
            $(".campo" + i).removeClass("error");
        }
    }
    return true;
}

function confirmarCambios() {
    if (campoRequired()) {
        let id = $("#txtID").val();
        let descripcion = $("#txtDescripcion").val();
        let datos = {
			"id": id,
			"descripcion": descripcion,
			"action": ""
		};
        if ($("#btnAceptar").hasClass("eliminar")) {
            if (confirm("¿Seguro que desea eliminar la especie?") == 1) {
				datos["action"] = "delete";
                crudEspecie(datos);
            }
        } else {
			datos["action"] = "save";
            crudEspecie(datos);
        }
    }
}

function crudEspecie(datos) {
    $.ajax({
        type: "POST",
        url: "Especies",
        dataType: 'json',
        data: datos,
        success: function (data) {
            if (data == 1) {
                if ($("#btnAceptar").hasClass("eliminar")) {
                    alert("La especie se elimino correctamente");
                } else {
                    alert("La especie se guardo correctamente");
                }
                $("#btnCancelar").click();

            } else if ( data == -1) {
                alert("La especie ingresada ya existe");
            } else {
                alert("Los cambios no se guardaron. Error en la base de datos");
            }
         	location.reload();
        }
    });
}