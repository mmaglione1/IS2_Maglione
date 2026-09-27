import { useLoaderData } from "react-router-dom";
import AspirantesCard from "./AspirantesCard";


function Aspirantes() {
    const aspirantes = useLoaderData() ?? [];
    
    return (
        <>
            <main className="content-wrap">
                <section className="content">
                    <h2>Aspirantes</h2>
                    <article className="person-boxes">
                        {
                            aspirantes.map((aspirante)=>(
                                <AspirantesCard 
                                    key={aspirante.id}
                                    aspirante={aspirante}
                                />    
                            ))
                        }
                        
                    </article>
                </section>
            </main>
        </>
    );
}
export default Aspirantes;