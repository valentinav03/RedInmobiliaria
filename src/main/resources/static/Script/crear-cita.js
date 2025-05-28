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


// Verificar si el usuario está autenticado
function isAuthenticated() {
  const token = localStorage.getItem('token');
  return !!token; // Retorna true si existe un token
}

// Función para decodificar JWT
function parseJwt(token) {
  try {
      return JSON.parse(atob(token.split('.')[1])); // Decodifica el payload
  } catch (e) {
      return null;
  }
}

// Obtener token actual
function getToken() {
  return localStorage.getItem('token');
}

document.addEventListener("DOMContentLoaded", function () {
    
  const token = localStorage.getItem("token");

  if (!isAuthenticated()) {
      window.location.href = 'login.html'; // Redirigir al login si no hay sesión
      return;
  }

  const userData = parseJwt(token);
  const userId = userData ? userData.id : null;
  

  if (!userId) {
      console.error("No se pudo obtener el ID del usuario.");
      return;
  }

  // Verificar el rol
  const tipoUser = userData ? userData.sub : null;
  console.log(tipoUser); 

  if(tipoUser === "Cliente") {
      
      // Ocultamos el menú de propiedades
      const propiedadesMenu = document.querySelector('.sidebar-menu li:nth-child(2)');
      propiedadesMenu.style.display = 'none';
  }

  cargarDatos(token, userId);
  cargarPropiedades(); // ← Llama aquí la función para cargar propiedades
  contarPropiedades();

  
});


// Cargar datos del usuario
async function cargarDatos(token, userId){
  try {

      const response = await fetch(`http://localhost:8094/api/usuario/list/${userId}`, {
          method: "GET",
          headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json"
          }
      });

      if (!response.ok) {
          throw new Error("Error al cargar los datos del usuario");
      }

      const usuario = await response.json();
      console.log("Datos del usuario:", usuario);

      // Actualizar la interfaz con los datos del usuario
      document.getElementById("user-name").textContent = usuario.nombre_usuario;
      document.getElementById("user-role").textContent = usuario.id_tipo_usuario.nombre_tipo_usuario;
  
      
      return usuario;
  } catch (error) {
      console.error("Error al obtener los datos del usuario:", error);
  }
}
