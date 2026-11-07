import axios from "axios";

export const ObtenerClima = async (ciudad, setClima) => {
  if (!ciudad || !ciudad.trim()) {
    return;
  }
  try {
    const respuesta = await axios.get(`http://localhost:8080/ciudad/clima/${encodeURIComponent(ciudad.trim())}`);
    console.log("respuesta", respuesta.data);
    setClima(respuesta.data);
  } catch (error) {
    console.error("Error al obtener el clima de nuestro servidor", error);
  }
};
