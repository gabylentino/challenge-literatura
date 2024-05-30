package com.challengeliteraturaoracleone.literatura.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
