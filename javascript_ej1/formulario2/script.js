document.addEventListener('DOMContentLoaded', () => {
    const formulario = document.querySelector('.formulario')
    const parrafo = document.querySelector('#parrafo')
    parrafo.style.display = 'none'

    formulario.addEventListener('submit', (e) => {
        e.preventDefault()

        const nombre = document.querySelector('#nombre').value
        const password = document.querySelector('#password').value

        parrafo.style.display = 'block'

        if (nombre === '' || password === '') {
            parrafo.textContent = 'Debe rellenar todos los campos'
            parrafo.style.backgroundColor = 'red'
        } else {
            parrafo.textContent = 'Formulario enviado'
            parrafo.style.backgroundColor = 'green'
        }
    })

})