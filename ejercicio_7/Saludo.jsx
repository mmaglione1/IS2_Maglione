import React, { useState } from 'react';

function Saludo() {
  const [mostrar, setMostrar] = useState(false);

  return (
    <div>
      <button onClick={() => setMostrar(true)}>
        Mostrar saludo
      </button>
      
      {/* El mensaje solo existe en el DOM si 'mostrar' es true */}
      {mostrar && <p>¡Hola, mundo React!</p>}
    </div>
  );
}

export default Saludo;