import './App.css'
import { TwitterFollowCard } from './TwitterFollowCard.jsx'

export function App () {
    
    return(
        <section>
            <TwitterFollowCard >
                Prueba usuario {/* Children que nos permite tener mas de un elemento*/}
            </ TwitterFollowCard>
            
            <TwitterFollowCard userName="tincho">
                Martin Maglione
            </ TwitterFollowCard>

            <TwitterFollowCard userName="profe">
                Leandro Spadaro
            </ TwitterFollowCard>
        </section>
    )
}