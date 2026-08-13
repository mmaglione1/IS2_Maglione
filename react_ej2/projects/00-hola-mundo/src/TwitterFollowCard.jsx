import { useState } from 'react'

export function TwitterFollowCard ({children, userName = 'unknown', name}){
    const [isFollowing, setIsFollowing] = useState(false)

    {/*renderizado condicional*/}
    const text = isFollowing ? 'Siguiendo' : 'Seguir'

    const buttonClassName = isFollowing
    ? 'tw-followCard-button is-following'
    : 'tw-followCard-button'

    const handleClick = () => {
        setIsFollowing(!isFollowing)
    }

    return(
        <article className='tw-followCard'>
            <header className='tw-followCard-header'>
                <img
                    className='tw-followCard-avatar' 
                    alt="Avatar" 
                    src={`https://unavatar.io/${userName}`} 
                    width="80px" />
                <div className='tw-followCard-info'>
                    <strong>{children}</strong>
                    <span 
                    className='tw-followCard-infoUserName'>@{userName}</span>
                </div>
            </header>
            <aside>
                <button className={buttonClassName} onClick={handleClick}>
                    {text}
                </button>
            </aside>
        </article>
    )
}