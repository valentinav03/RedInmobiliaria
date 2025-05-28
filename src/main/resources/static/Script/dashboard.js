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

        // Actualizar la interfaz con los datos del usuario
        document.getElementById("user-name").textContent = usuario.nombre_usuario;
        document.getElementById("welcome-name").textContent = usuario.nombre_usuario;
        document.getElementById("user-role").textContent = usuario.id_tipo_usuario.nombre_tipo_usuario;

        return usuario;
    } catch (error) {
        
        console.error("Error al obtener los datos del usuario:", error);
    }
}

// Función para decodificar JWT
function parseJwt(token) {
    try {
        return JSON.parse(atob(token.split('.')[1])); // Decodifica el payload
    } catch (e) {
        return null;
    }
}

function cargarPropiedades() {
    fetch("http://localhost:8094/api/propiedades")
        .then(res => res.json())
        .then(data => {
            const container = document.querySelector(".properties-grid");
            container.innerHTML = "";

            data.forEach(async prop => {
                let imagenURL = "/img/default.jpg"; // Por defecto

                try {
                  const resImg = await fetch(`http://localhost:8094/api/imagenPropiedad/propiedad/${prop.idPropiedad}`);
                  if (resImg.ok) {
                    imagenURL = await resImg.text(); // porque es texto plano (la URL)
                  }
                } catch (error) {
                  console.warn("No se pudo cargar imagen para propiedad", prop.idPropiedad);
                }
                container.innerHTML += `
                    <div class="property-card">
                        <div class="property-image">
                            <img src="${imagenURL}" alt="Imagen de la propiedad" style="width: 100%; height: 125%; border-radius: 8px;">
                        </div>
                        <div class="property-info">
                        <h4>
                            ${prop.tipo?.nombreTipoPropiedad || "Propiedad"} 
                                 | 
                             ${prop.operacion?.nombreOperacion || "Operación"}
                        </h4>
                            <p class="property-location"><i class="fas fa-map-marker-alt"></i> ${prop.direccion}</p>
                            <p class="property-price">$${prop.precio.toLocaleString()} 
                                <button class="btn-agendar-cita" onclick="window.location.href='cita.html?idPropiedad=${prop.idPropiedad}'">Agendar Cita</button>
                            </p>
                        </div>
                    </div>
                `;
            });
        })
        .catch(err => {
            console.error("Error al cargar propiedades:", err);
        });
}

async function contarPropiedades() {
    try {
      const response = await fetch("http://localhost:8094/api/propiedades");
      const propiedades = await response.json();
      document.getElementById("total-propiedades").textContent = propiedades.length;
    } catch (error) {
      console.error("Error al contar propiedades:", error);
    }
  }
  
  document.addEventListener("DOMContentLoaded", contarPropiedades);
  
  
