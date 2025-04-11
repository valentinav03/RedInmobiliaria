
document.addEventListener('DOMContentLoaded', function() {
    // Verificar si el usuario ya está autenticado
    if (isAuthenticated()) {
        window.location.href = 'dashboard.html'; // Redirigir al dashboard si ya hay sesión
        return;
    }
    
    // Obtener referencias al formulario y al contenedor de alertas
    const registerForm = document.getElementById('register-form');
    const registerAlert = document.getElementById('register-alert');
    
    // Manejar el envío del formulario de registro
    registerForm.addEventListener('submit', async function(event) {
        event.preventDefault();
        
        // Ocultar alerta anterior si existe
        registerAlert.style.display = 'none';
        registerAlert.classList.remove('fadeIn');
        
        // Obtener valores del formulario
        const nombreUsuario= document.getElementById('reg-nombreUsuario').value.trim();
        const email = document.getElementById('reg-email').value.trim();
        const password = document.getElementById('reg-password').value;
        const tipoUsuarioId = document.getElementById('reg-tipo-usuario').value;
        
        // Validación básica
        if (!nombreUsuario || !email || !password) {
            showAlert(registerAlert, 'Por favor completa todos los campos obligatorios.');
            return;
        }
        
        // Validar formato de email
        if (!isValidEmail(email)) {
            showAlert(registerAlert, 'Por favor ingresa un correo electrónico válido.');
            return;
        }
        
        // Crear objeto con datos del usuario
        const userData = {
            nombreUsuario: nombreUsuario,
            email: email,
            password: password,
            tipoUsuarioId: parseInt(tipoUsuarioId)
        };
        
        try {
            // Intentar registrar al usuario
            const response = await fetch('http://localhost:8094/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });

            if (response.ok) {
                showAlert(registerAlert,'Usuario registrado con éxito');
            } else {
                const text = await response.text();
                try {
                    const data = JSON.parse(text);
                    showAlert(registerAlert, data.message || 'Error al registrar usuario');
                } catch (error) {
                    showAlert(registerAlert, 'Error desconocido al registrar usuario');
                    console.error("Error al parsear JSON:", text);
                }
            }
            
            // Mostrar mensaje de éxito y redirigir al login
            alert('¡Registro exitoso! Ahora puedes iniciar sesión.');
            window.location.href = 'login.html';
            
        } catch (error) {
            // Mostrar mensaje de error
            showAlert(registerAlert, error.message || 'Error al registrar usuario. Inténtalo de nuevo.');
        }
    });
});

// Función para validar formato de email
function isValidEmail(email_usuario) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email_usuario);
}

// Función para mostrar alertas con animación
function showAlert(alertElement, message) {
    alertElement.textContent = message;
    alertElement.style.display = 'block';
    alertElement.classList.add('fadeIn');
}

// Función para cargar el formulario de login
function loadLoginForm() {
    window.location.href = 'login.html';
}