import React from 'react';
// Importamos las herramientas de React Testing Library
import { render, screen, fireEvent } from '@testing-library/react';
// Importamos aserciones extra para Jest (como toBeInTheDocument)
import '@testing-library/jest-dom'; 

import Saludo from './Saludo';

// 'describe' y 'test' provienen de Jest (el "laboratorio")
describe('Componente Saludo', () => {
  
  test('debe mostrar el texto oculto cuando el usuario hace clic', () => {
    
    // PASO 1: Renderizar (React Testing Library)
    // Montamos el componente en un navegador virtual.
    render(<Saludo />);
    
    // Comprobamos que el mensaje NO está en la pantalla inicialmente.
    // Usamos queryByText porque devuelve null si no lo encuentra (no lanza error).
    expect(screen.queryByText('¡Hola, mundo React!')).toBeNull();

    // PASO 2: Interactuar (React Testing Library)
    // Buscamos el botón por el texto que ve el usuario.
    const boton = screen.getByText('Mostrar saludo');
    // Simulamos un clic real.
    fireEvent.click(boton);

    // PASO 3: Afirmar o Validar (Jest + jest-dom)
    // Buscamos el mensaje que ahora debería existir.
    const mensaje = screen.getByText('¡Hola, mundo React!');
    // Le pedimos a Jest que confirme que el elemento está en el documento.
    expect(mensaje).toBeInTheDocument();
  });

});