import logo from '../assets/img/logo.png';
import {
    Link,
    createBrowserRouter,
    RouterProvider,
    Outlet
} from 'react-router-dom';
import Contenido from './Contenido';
import Preventa from './Preventa';
import Aspirantes from './Aspirantes';
import Profesiones from './Profesiones';
import { aspirantesLouder } from '../loaders/aspirantesLoader';

// Layout - Diseño
function Layout() {
    return (
        <>
            {/* Esto es un comentario en JSX*/}
            <header className="menu-wrap">
                <figure className="user">
                    <div className="user-avatar">
                        <Link to="/" ><img className="w-100" src={logo} alt="Cedavilu Web Academy" /></Link>
                    </div>
                    <figcaption>
                        Cedavilu Web Academy
                    </figcaption>
                </figure>
                <nav>
                    <section className="menu">
                        <h3>Opciones</h3>
                        <ul>
                            <li>
                                <Link to="/empresa">
                                    <i className="bi bi-building" style={{ fontSize: "1.2rem", color: "cornflowerblue" }}></i>
                                    - Empresas
                                </Link>
                            </li>
                            <li>
                                <Link to="/aspirantes">
                                    <i className="bi bi-person" style={{ fontSize: "1.2rem", color: "cornflowerblue" }}></i>
                                    - Aspirantes
                                </Link>
                            </li>
                            <li>
                                <Link to="/profesiones">
                                    <i className="bi bi-list-check"></i>
                                    - Profesiones
                                </Link>
                            </li>
                            <li>
                                <Link to="#">
                                    <i className="bi bi-person-vcard" style={{ fontSize: "1.2rem", color: "cornflowerblue" }}></i>
                                    - Postulate aquí
                                </Link>
                            </li>
                            <li>
                                <Link to="#">
                                    <i className="bi bi-chat-left-text"></i>
                                    - Contacto
                                </Link>
                            </li>
                        </ul>
                    </section>
                </nav>
            </header>
            <main>
                <Outlet/>
            </main>
        </>
    )
}

function Menu() {
    const profesiones = [
        {
            id: 1,
            nombre: 'Desarrollador web'
        },
        {
            id: 2,
            nombre: 'Mecánico'
        },
        {
            id: 3,
            nombre: 'Profesor'
        },
        {
            id: 4,
            nombre: 'Abogado'
        },
        {
            id: 5,
            nombre: 'Diseñador'
        }
    ]
    
    const router = createBrowserRouter([
        {
            path: '/',
            element : <Layout/>,
            children : [
                {
                    index: true,
                    element: <Contenido/>
                },
                {
                    path: 'empresa',
                    element: <Preventa/>
                },
                {
                    path: 'aspirantes',
                    element: <Aspirantes/>,
                    loader: aspirantesLouder
                },
                {
                    path: 'profesiones',
                    element: <Profesiones listado={profesiones}/>
                }
            ]

        }
    ])
    return <RouterProvider router={router}/>;
}   
export default Menu;