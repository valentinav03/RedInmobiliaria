// auth-service.js - Servicio para manejar la autenticación y comunicación con el backend

// URL base del servidor backend
const API_URL = 'http://localhost:8094/api'; 

// Función para iniciar sesión
async function login(nombreUsuario, password) {
    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nombreUsuario, password })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Error en la autenticación');
        }

        const data = await response.json();
        
        // Guardar el token en localStorage
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(data.user || {}));
        
        return data;
    } catch (error) {
        console.error('Error de login:', error);
        throw error;
    }
}

// Función para registrar un nuevo usuario
async function register(userData) {
    try {
        const response = await fetch(`${API_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Error en el registro');
        }

        return await response.json();
    } catch (error) {
        console.error('Error de registro:', error);
        throw error;
    }
}

// Verificar si el usuario está autenticado
function isAuthenticated() {
    const token = localStorage.getItem('token');
    return !!token; // Retorna true si existe un token
}

// Obtener token actual
function getToken() {
    return localStorage.getItem('token');
}

// Cerrar sesión
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = 'login.html';
}

// Función para agregar el token JWT a las solicitudes
function fetchWithAuth(url, options = {}) {
    const token = getToken();
    
    if (!options.headers) {
        options.headers = {};
    }
    
    if (token) {
        options.headers['Authorization'] = `Bearer ${token}`;
    }
    
    options.headers['Content-Type'] = 'application/json';
    
    return fetch(url, options);
}

// Función para verificar si el usuario está autenticado
function isLoggedIn() {
    return localStorage.getItem('Token') !== null;
}