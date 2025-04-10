

document.addEventListener('DOMContentLoaded', function() {
    // Verificar si el usuario ya está autenticado
    if (isAuthenticated()) {
        window.location.href = 'dashboard.html'; // Redirigir al dashboard si ya hay sesión
        return;
    }
    
    // Obtener referencias al formulario y al contenedor de alertas
    const loginForm = document.getElementById('login-form');
    const loginAlert = document.getElementById('login-alert');
    
    // Manejar el envío del formulario de login
    loginForm.addEventListener('submit', async function(event) {
        event.preventDefault();
        
        // Ocultar alerta anterior si existe
        loginAlert.style.display = 'none';
        loginAlert.classList.remove('fadeIn');
        
        // Obtener valores del formulario
        const nombreUsuario = document.getElementById('nombreUsuario').value.trim();
        const password = document.getElementById('password').value;
        
        // Validación básica
        if (!nombreUsuario || !password) {
            showAlert(loginAlert, 'Por favor completa todos los campos.');
            return;
        }
        
        try {
            // Intentar iniciar sesión
            const response = await login(nombreUsuario, password);
            
            // Si llegamos aquí, el login fue exitoso
            console.log('Login exitoso:', response);
            
            // Redirigir al dashboard o página principal
            window.location.href = 'dashboard.html';
            
        } catch (error) {
            // Mostrar mensaje de error
            showAlert(loginAlert, error.message || 'Error al iniciar sesión. Verifica tus credenciales.');
        }
    });
});

// Función para mostrar alertas con animación
function showAlert(alertElement, message) {
    alertElement.textContent = message;
    alertElement.style.display = 'block';
    alertElement.classList.add('fadeIn');
}

// Función para cargar el formulario de registro
function loadRegisterForm() {
    window.location.href = 'registro.html';
}