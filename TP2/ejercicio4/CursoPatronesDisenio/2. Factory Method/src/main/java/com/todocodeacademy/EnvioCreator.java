package com.todocodeacademy;

public abstract class EnvioCreator {

    //Factory Method
    protected abstract Envio crearEnvio();

    public void procesarEnvio() {
        Envio envio = crearEnvio(); /// llamamos a nuestro factory method
        envio.enviarPaquete();

    }
}
