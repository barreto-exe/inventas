package com.teamihc.inventas.backend.entidades;

public interface Entidad
{
    /**
     * Hace un insert de la entidad en la base de datos.
     */
    public boolean registrar();
    
    /**
     * Hace una búsqueda de la instancia en la base de datos.
     * @return el id de la primera instancia encontrada, si existe al menos una. Si no existe,
     * retorna -1.
     */
    public int obtenerId();
}
