const API_URL = "http://localhost:8080/api/propiedades";

// Cargar propiedades
function cargarPropiedades() {
    fetch(API_URL)
        .then(response => response.json())
        .then(propiedades => mostrarPropiedades(propiedades))
        .catch(error => console.error("Error al obtener las propiedades:", error));
}

// Mostrar propiedades en la tabla
function mostrarPropiedades(propiedades) {
    const lista = document.getElementById("propiedades-list");
    lista.innerHTML = "";

    propiedades.forEach(propiedad => {
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${propiedad.idPropiedad}</td>
            <td>${propiedad.idVendedor}</td>
            <td>${propiedad.tipo.nombreTipoPropiedad}</td>
            <td>${propiedad.operacion.nombreOperacion}</td>
            <td>${propiedad.estado.nombreEstadoPropiedad}</td>
            <td>${propiedad.idPrecio}</td>
            <td>${propiedad.direccion}</td>
            <td>${propiedad.latitud}</td>
            <td>${propiedad.longitud}</td>
            <td>
                <button class="edit-btn" onclick="editarPropiedad(${propiedad.idPropiedad})">✏️ Editar</button>
                <button class="delete-btn" onclick="eliminarPropiedad(${propiedad.idPropiedad})">🗑️ Eliminar</button>
            </td>
        `;
        lista.appendChild(fila);
    });
}

// Agregar propiedad
document.getElementById("formulario").addEventListener("submit", function(event) {
    event.preventDefault();

    const nuevaPropiedad = {
        idVendedor: parseInt(document.getElementById("idVendedor").value),
        tipo: { idTipo: parseInt(document.getElementById("tipo").value) },  // Obteniendo del select
        operacion: { idOperacion: parseInt(document.getElementById("operacion").value) },  // Obteniendo del select
        estado: { idEstado: parseInt(document.getElementById("estado").value) },  // Obteniendo del select
        idPrecio: parseFloat(document.getElementById("idPrecio").value),
        direccion: document.getElementById("direccion").value,
        latitud: parseFloat(document.getElementById("latitud").value),
        longitud: parseFloat(document.getElementById("longitud").value),
    };

    fetch("/api/propiedades", {  // Asegúrate de que la URL coincida con tu backend
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(nuevaPropiedad),
    })
        .then(response => {
            if (!response.ok) throw new Error("Error al agregar la propiedad");
            return response.json();
        })
        .then(() => {
            alert("Propiedad agregada con éxito");
            cargarPropiedades();  // Recargar la lista de propiedades
            document.getElementById("formulario").reset();
        })
        .catch(error => console.error("Error al agregar propiedad:", error));
});

// Cargar opciones en los selects (esto lo puedes ejecutar al inicio)
document.addEventListener("DOMContentLoaded", function () {
    cargarOpciones("tipo", "/api/tipos");
    cargarOpciones("operacion", "/api/operaciones");
    cargarOpciones("estado", "/api/estados");
});

function cargarOpciones(selectId, endpoint) {
    fetch(endpoint)
        .then(response => response.json())
        .then(datos => {
            let select = document.getElementById(selectId);
            if (!select) {
                console.error(`Elemento con id '${selectId}' no encontrado.`);
                return;
            }

            select.innerHTML = "<option value=''>Seleccione una opción</option>"; // Opción por defecto

            datos.forEach(item => {
                let option = document.createElement("option");

                if (selectId === "tipo") {
                    option.value = item.idTipo;
                    option.textContent = item.nombreTipoPropiedad;
                } else if (selectId === "operacion") {
                    option.value = item.idOperacion;
                    option.textContent = item.nombreOperacion;
                } else if (selectId === "estado") {
                    option.value = item.idEstado;
                    option.textContent = item.nombreEstadoPropiedad;
                }

                select.appendChild(option);
            });
        })
        .catch(error => console.error("Error cargando opciones:", error));
}


function editarPropiedad(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(propiedad => {
            document.getElementById("idPropiedad").value = propiedad.idPropiedad; // Guardamos el ID
            document.getElementById("idVendedor").value = propiedad.idVendedor;
            document.getElementById("tipo").value = propiedad.tipo.idTipo;
            document.getElementById("operacion").value = propiedad.operacion.idOperacion;
            document.getElementById("estado").value = propiedad.estado.idEstado;
            document.getElementById("idPrecio").value = propiedad.idPrecio;
            document.getElementById("direccion").value = propiedad.direccion;
            document.getElementById("latitud").value = propiedad.latitud;
            document.getElementById("longitud").value = propiedad.longitud;

            document.getElementById("boton-guardar").style.display = "none"; // Ocultamos el botón de agregar
            document.getElementById("boton-actualizar").style.display = "inline-block"; // Mostramos el de actualizar
        })
        .catch(error => console.error("Error al obtener la propiedad:", error));
}

document.getElementById("boton-actualizar").addEventListener("click", function(event) {
    event.preventDefault();

    const idPropiedad = document.getElementById("idPropiedad").value;

    const propiedadActualizada = {
        idVendedor: parseInt(document.getElementById("idVendedor").value),
        tipo: { idTipo: parseInt(document.getElementById("tipo").value) },
        operacion: { idOperacion: parseInt(document.getElementById("operacion").value) },
        estado: { idEstado: parseInt(document.getElementById("estado").value) },
        idPrecio: parseFloat(document.getElementById("idPrecio").value),
        direccion: document.getElementById("direccion").value,
        latitud: parseFloat(document.getElementById("latitud").value),
        longitud: parseFloat(document.getElementById("longitud").value),
    };

    fetch(`${API_URL}/${idPropiedad}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(propiedadActualizada),
    })
        .then(response => response.json())
        .then(() => {
            alert("Propiedad actualizada con éxito");
            document.getElementById("formulario").reset();
            document.getElementById("boton-guardar").style.display = "inline-block";
            document.getElementById("boton-actualizar").style.display = "none";
            cargarPropiedades();
        })
        .catch(error => console.error("Error al actualizar propiedad:", error));
});




// Eliminar propiedad
function eliminarPropiedad(id) {
    if (confirm("¿Seguro que quieres eliminar esta propiedad?")) {
        fetch(`${API_URL}/${id}`, { method: "DELETE" })
            .then(() => {
                alert("Propiedad eliminada con éxito");
                cargarPropiedades();
            })
            .catch(error => console.error("Error al eliminar propiedad:", error));
    }
}

// Editar propiedad (básico: solo muestra el ID)
function editarPropiedad(id) {
    alert("Función de edición aún no implementada. ID: " + id);
}

