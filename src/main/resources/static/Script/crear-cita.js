// Al cargar la página
document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const idPropiedad = urlParams.get("idPropiedad");

  const userData = localStorage.getItem("user");

  if (userData) {
    const user = JSON.parse(userData);
    const nombreUsuario = user.nombreUsuario;

    // Mostrar el nombre en el HTML
    const nombreUsuarioElement = document.getElementById("nombreUsuario");
    if (nombreUsuarioElement) {
      nombreUsuarioElement.textContent = nombreUsuario;
    }
  }

 
  if (idPropiedad) {
    const idPropiedadInput = document.getElementById("idPropiedad");
    idPropiedadInput.value = idPropiedad;
    idPropiedadInput.readOnly = true;

    // Mostrar nombre de propiedad (requiere un endpoint como /api/propiedades/{id})
    fetch(`http://localhost:8094/api/propiedades/${idPropiedad}`)
      .then(response => response.json())
      .then(data => {
        document.getElementById("direccion").textContent = data.direccion;
      })
      .catch(error => {
        console.error("Error al obtener propiedad:", error);
        document.getElementById("direccion").textContent = "No se pudo cargar el nombre";
      });
  }
});

// Al enviar el formulario
document.getElementById("formCita").addEventListener("submit", async function(e) {
  e.preventDefault();

  const userData = JSON.parse(localStorage.getItem("user")); // Extraer desde aquí

  const cita = {
    idCliente: userData.id,
    idPropiedad: parseInt(document.getElementById("idPropiedad").value),
    fechaHora: document.getElementById("fechaHora").value,
    idEstado: parseInt(document.getElementById("idEstado").value)
  };

  try {
    const response = await fetch("http://localhost:8094/api/citas/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(cita)
    });
    console.log("Cita enviada:", cita);

    if (!response.ok) throw new Error("Error al guardar la cita");

    const data = await response.json();
    alert("Cita agendada correctamente con ID: " + data.id);
    window.location.href = "dashboard.html";
  } catch (err) {
    console.error(err);
    alert("Error al agendar la cita");
  }
});
