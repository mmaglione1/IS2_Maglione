import { fecthAspirantes } from "../services/aspirantesServices";

export const aspirantesLouder = async () =>{
    const aspirantes = await fecthAspirantes();
    if(!Array.isArray(aspirantes)){
        return [];
    }
    return aspirantes;
}