import './App.css'
import { TwitterFollowCard } from './TwitterFollowCard.jsx'

export function App () {
    const users = [
        {
            userName: 'listaUser',
            name: 'Usuario de lista',
            isFollowing: true
        },
        {
            userName: 'renderLista',
            name: 'test2',
            isFollowing: false
        }
    ]

    return(
        <section className="App">
            <TwitterFollowCard >
                Prueba usuario {/* Children que nos permite tener mas de un elemento*/}
            </ TwitterFollowCard>
            
            <TwitterFollowCard userName="tincho" initialIsFollowing>
                Martin Maglione
            </ TwitterFollowCard>

            <TwitterFollowCard userName="profe">
                Leandro Spadaro
            </ TwitterFollowCard>

            {
                users.map(user => {
                    const { userName, name, isFollowing } = user
                    return(
                        <TwitterFollowCard 
                            key={userName}
                            userName={userName} 
                            initialIsFollowing={isFollowing}
                        >
                            {name}
                        </ TwitterFollowCard>
                    )
                    }
                )
            }
        </section>
    )
}