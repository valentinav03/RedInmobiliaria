document.addEventListener("DOMContentLoaded", async () => {
  const citaId = 33; // aquí pones el ID de la cita que quieres mostrar
  try {
    const response = await fetch(`http://localhost:8094/api/citas/list/${citaId}`);
    if (!response.ok) throw new Error("No se pudo obtener la cita");

    const cita = await response.json();

    const contenedor = document.getElementById("contenedor-citas");
    contenedor.innerHTML = `
      <div class="cita-card">
        <p><strong>Cliente:</strong> ${cita.idCliente?.nombreUsuario || "N/A"}</p>
        <p><strong>Dirección:</strong> ${cita.idPropiedad?.direccion || "N/A"}</p>
        <p><strong>Fecha:</strong> ${new Date(cita.fechaHora).toLocaleString()}</p>
        <p><strong>Estado:</strong> ${cita.idEstado?.nombre || "N/A"}</p>
        <button class="btn-eliminar" onclick="eliminarCita(${cita.id})">Eliminar</button>
      </div>
    `;
  } catch (error) {
    console.error("Error cargando la cita:", error);
  }
});

async function eliminarCita(id) {
  try {
    const response = await fetch(`http://localhost:8094/api/citas/${id}`, {
      method: "DELETE"
    });

    if (!response.ok) throw new Error("Error al eliminar");

    alert("Cita eliminada correctamente");
    location.reload();
  } catch (error) {
    console.error("Error eliminando cita:", error);
    alert("No se pudo eliminar la cita");
  }
}
