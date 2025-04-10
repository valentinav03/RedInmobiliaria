document.addEventListener("DOMContentLoaded", function () {
    
    const token = localStorage.getItem("token");

    if (!isAuthenticated()) {
        window.location.href = 'login.html'; // Redirigir al login si no hay sesión
        return;
    }

    const userData = parseJwt(token);
    const userId = userData ? userData.id : null;
    

    console.log(tipoUser);

    if (!userId) {
        console.error("No se pudo obtener el ID del usuario.");
        return;
    }

    // Verificar el rol
    const tipoUser = userData ? userData.sub : null;

    if(tipoUser === "Cliente") {
        // Ocultamos el menú de propiedades
        const propiedadesMenu = document.querySelector('.sidebar-menu li:nth-child(2)');
        propiedadesMenu.style.display = 'none';
    }

    cargarDatos(token, userId);
    
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
