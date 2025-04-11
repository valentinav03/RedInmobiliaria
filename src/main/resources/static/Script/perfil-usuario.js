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

    const tipoUser = userData ? userData.sub : null;

    if(tipoUser === "Cliente") {
        // Ocultamos el menú de propiedades
        const propiedadesMenu = document.querySelector('.sidebar-menu li:nth-child(2)');
        propiedadesMenu.style.display = 'none';
    }

    cargarDatos(token, userId);

    // Botón para editar 
    document.getElementById('edit-profile-btn').addEventListener('click', () => {
        document.getElementById('profile-info-view').classList.add('hidden');
        document.getElementById('profile-edit-form').classList.remove('hidden');
    });
    
    // Botón para cancelar edición
    document.getElementById('cancel-edit-btn').addEventListener('click', () => {
        document.getElementById('profile-edit-form').classList.add('hidden');
        document.getElementById('profile-info-view').classList.remove('hidden');
    });

    // Botón para guardar cambios del perfil
    document.getElementById('save-profile-btn').addEventListener('click', () => {
        actualizarDatos(token, userId);
    });

    // Botón para cambiar contraseña
    document.getElementById('change-password-btn').addEventListener('click', () => {
        cambiarContraseña(token, userId);
    });
    
    // Botón para eliminar cuenta
    document.getElementById('delete-account-btn').addEventListener('click', () => {
        document.getElementById('delete-confirm-modal').classList.remove('hidden');
    });
    
    // Cerrar modal
    document.querySelector('.close-modal').addEventListener('click', () => {
        document.getElementById('delete-confirm-modal').classList.add('hidden');
    });
    
    // Botón para cancelar eliminación
    document.querySelector('.cancel-delete').addEventListener('click', () => {
        document.getElementById('delete-confirm-modal').classList.add('hidden');
    });
    
    // Botón para confirmar eliminación
    document.getElementById('confirm-delete-btn').addEventListener('click', () => {
        eliminarCuenta(token, userId)});
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
        document.getElementById("user-role").textContent = usuario.id_tipo_usuario.nombre_tipo_usuario;
        document.getElementById("profile-name").textContent = usuario.nombre_usuario;
        document.getElementById("profile-email").textContent = usuario.email_usuario;
        document.getElementById("display-fullname").textContent = usuario.nombre_usuario;
        document.getElementById("display-email").textContent = usuario.email_usuario;
        document.getElementById("profile-joined-date").textContent = "Fecha de registro no disponible"; // Ajustar si hay fecha

        return usuario;
    } catch (error) {
        console.error("Error al obtener los datos del usuario:", error);
    }
}

// Actualizar datos del usuario
async function actualizarDatos(token, userId) {

    const nombreUsuario= document.getElementById("edit-fullname").value;
    const email = document.getElementById('edit-email').value;

    const usuarioActualizado ={};

    if (nombreUsuario.trim() !== "") {
        usuarioActualizado.nombre_usuario = nombreUsuario;
    }

    if (email.trim() !== "") {
        usuarioActualizado.email_usuario = email;
    }

    try {

        const response = await fetch(`http://localhost:8094/api/usuario/${userId}`, {
            method: "PATCH",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(usuarioActualizado)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message ||"Error al actualizar los datos del usuario");
        }

        // Obtener la respuesta que incluye el nuevo token
        const data = await response.json();
        
        // Actualizar el token en localStorage
        if (data.token) {
            localStorage.setItem("token", data.token);

        }

        alert("Perfil actualizado correctamente");
        document.getElementById('profile-edit-form').classList.add('hidden');
        document.getElementById('profile-info-view').classList.remove('hidden');

        cargarDatos(data.token || token, userId);

    } catch (error) {
        console.error("Error al actualizar el perfil:", error);
    }
}

//Función para cambiar contraseña
async function cambiarContraseña(token, userId){

    const currentPassword = document.getElementById("current-password").value;
    const newPassword = document.getElementById("new-password").value;
    const confirmPassword = document.getElementById("confirm-password").value;

    if (newPassword !== confirmPassword) {
        alert("Las contraseñas deben ser iguales.");
        return;
    }

    const nuevoPassword = {
        password: currentPassword,
        newPassword: newPassword
    };

    try {

        const response = await fetch(`http://localhost:8094/api/usuario/${userId}`, {
            method: "PATCH",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevoPassword)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message ||"Error al al cambiar la contraseña");
        }

        alert("Contraseña actualizada correctamente.");

        document.getElementById("current-password").value= "";
        document.getElementById("new-password").value = "";
        document.getElementById("confirm-password").value ="";

    } catch (error) {
        console.error("Error al actualizar el perfil:", error);
    }
}

//Función para eliminar cuenta
async function eliminarCuenta(token, userId){

    const password = document.getElementById("delete-confirm-password").value;

    try {
        const response = await fetch(`http://localhost:8094/api/usuario/${userId}`, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json"
            },
            body: JSON.stringify({password: password})
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Error al eliminar la cuenta");
        }

        alert("La cuenta ha sido eliminada");
        logout();
        window.location.href = "../vista/login.html"; 

    } catch (error) {
        alert("Error: " + error.message);
        console.error("Error al eliminar la cuenta:", error);
    }
}

// Función para decodificar JWT
function parseJwt(token) {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
}


