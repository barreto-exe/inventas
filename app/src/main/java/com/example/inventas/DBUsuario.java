package com.example.inventas;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Clase con funcionalidades CRUD para los usuarios en la base de datos.
 *
 * @author luisb
 */
public final class DBUsuario implements Serializable
{

    private String correo, usuario, pass, ip;
    /**
     * Es el id del usuario en la base de datos.
     */
    private int id;

    /**
     * El color de personaje que seleccionó el jugador. || 0 - NORMAL || 1 -
     * ROJO || 2 - VERDE || 3 - AMARILLO || 4 - MORADO || 5 - GRIS ||
     */
    private int personajeSeleccionado;

    /**
     * Es el id de la partida a la que pertenece el usuario.
     */
    private int partida;

    /**
     * La posición que ocupa el jugador en la tabla de participantes. Este
     * atributo es usado para saber en qué número de bioma inicia.
     */
    private int numeroJugador;

    /**
     * Crea una instancia de entidad en relación a la tabla m_usuarios de la
     * Base de Datos.
     *
     * @param correo es el correo del usuario.
     * @param usuario es el nombre de usuario.
     * @param pass es la constraseña ENCRIPTADA del usuario. en la base de
     * datos.
     */
    public DBUsuario(String correo, String usuario, String pass)
    {
        this.correo = correo;
        this.usuario = usuario;
        this.pass = encriptarMD5(pass);
        this.personajeSeleccionado = 0;
        this.partida = -1;
        this.numeroJugador = 0;
    }

    /**
     * Relaciona un usuario con su nombre y su ip dentro del Lobby de una
     * partida.
     *
     * @param usuario es el nombre de usuario.
     * @param ip es la dirección ip del usuario.
     */
    public DBUsuario(String usuario, String ip)
    {
        this.usuario = usuario;
        this.ip = ip;
        this.correo = "";
        this.pass = "";
        this.personajeSeleccionado = 0;
        this.partida = -1;
        this.numeroJugador = 0;
    }

    /**
     * Relaciona el nombre de un usuario en partida con su ip, su id y el
     * personaje que seleccionó.
     *
     * @param usuario es el nombre de usuario.
     * @param ip es la dirección ip del usuario.
     * @param id es el id del usuario en la base de datos.
     * @param personajeSeleccionado es el número del personaje seleccionado por
     * el usuario.
     */
    public DBUsuario(String usuario, String ip, int id, int personajeSeleccionado)
    {
        this.usuario = usuario;
        this.ip = ip;
        this.id = id;
        this.personajeSeleccionado = personajeSeleccionado;
        this.numeroJugador = 0;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getNumeroJugador()
    {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador)
    {
        this.numeroJugador = numeroJugador;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = encriptarMD5(pass);
    }

    public int getPersonajeSeleccionado()
    {
        return personajeSeleccionado;
    }

    public void setPersonajeSeleccionado(int personajeSeleccionado)
    {
        this.personajeSeleccionado = personajeSeleccionado;
    }

    public int getPartida()
    {
        return partida;
    }

    public void setPartida(int partida)
    {
        this.partida = partida;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getPersonajeSeleccionadoString()
    {
        switch (personajeSeleccionado)
        {
            case 0:
            {
                return "normal";
            }
            case 1:
            {
                return "rojo";
            }
            case 2:
            {
                return "verde";
            }
            case 3:
            {
                return "amarillo";
            }
            case 4:
            {
                return "morado";
            }
            case 5:
            {
                return "gris";
            }
            default:
            {
                return "";
            }
        }
    }
    
    
    public static String personajeSeleccionado2String(int personajeSeleccionado)
    {
        switch (personajeSeleccionado)
        {
            case 0:
            {
                return "normal";
            }
            case 1:
            {
                return "rojo";
            }
            case 2:
            {
                return "verde";
            }
            case 3:
            {
                return "amarillo";
            }
            case 4:
            {
                return "morado";
            }
            case 5:
            {
                return "gris";
            }
            default:
            {
                return "";
            }
        }
    }
    //</editor-fold>

    /**
     * Consulta disponibilidad de correo.
     *
     * @param correo el email que se va a consultar.
     * @return -1 si el correo no existe en la base de datos. Si existe, retorna
     * el id del usuario que lo está usando.
     */
    public static int idCorreo(String correo)
    {
        //Construir consulta de existencia 
        String consulta
                = "SELECT id FROM m_usuarios WHERE "
                + "correo  = ?";
        DBOperacion operacion = new DBOperacion(consulta);
        operacion.pasarParametro(correo);

        DBMatriz resultados = operacion.consultar();

        //Retornar el id si existe
        if (resultados.leer())
        {
            return (int) resultados.getValor("id");
        }

        //Retornar -1 si el correo está disponible
        return -1;
    }

    /**
     * Consulta disponibilidad de usuario.
     *
     * @param usuario el usuario que se va a consultar.
     * @return -1 si el usuario no existe en la base de datos. Si existe,
     * retorna el id del usuario.
     */
    public static int idUsuario(String usuario)
    {
        //Construir consulta de existencia 
        String consulta
                = "SELECT id FROM m_usuarios WHERE "
                + "usuario  = ?";
        DBOperacion operacion = new DBOperacion(consulta);
        operacion.pasarParametro(usuario);

        DBMatriz resultados = operacion.consultar();

        //Retornar el id si existe
        if (resultados.leer())
        {
            return (int) resultados.getValor("id");
        }

        //Retornar -1 si el usuario está disponible
        return -1;
    }

    /**
     * Registra un usuario en la BD.
     *
     * @param usuario instancia del usuario a registrar.
     * @return <code>true</code> si logró hacer el registro con éxito.
     */
    public static boolean registrarUsuario(DBUsuario usuario)
    {
        String query
                = "INSERT INTO `m_usuarios` (usuario, correo, password)"
                + "VALUES (?,?,?)";

        DBOperacion operacion = new DBOperacion(query);
        operacion.pasarParametro(usuario.getUsuario());
        operacion.pasarParametro(usuario.getCorreo());
        operacion.pasarParametro(usuario.getPass());

        //Retornar la validación del registro afectado.
        return operacion.ejecutar() == 1;
    }

    /**
     * Consulta existencia de un usuario que coincida con la misma clave.
     *
     * @param usuario es la instancia de usuario a consultar.
     * @return el id del usuario en la BD. Retorna -1 si no existe.
     */
    public static int consultarUsuario(DBUsuario usuario)
    {
        //Construir consulta de existencia 
        String consulta
                = "SELECT id FROM m_usuarios WHERE "
                + "usuario  = ? AND password = ?";
        DBOperacion operacion = new DBOperacion(consulta);
        operacion.pasarParametro(usuario.getUsuario());
        operacion.pasarParametro(usuario.getPass());

        DBMatriz resultados = operacion.consultar();

        //Retornar el id si existe
        if (resultados.leer())
        {
            return (int) resultados.getValor("id");
        }

        //Retornar -1 si el usuario está disponible
        return -1;
    }

    /**
     * Consulta las estadísticas totales dado el nombre de un usuario.
     * @param usuario del que se quieren las estadísticas.
     * @return HashMap, donde las claves son strings identificadorres de los valores también en string.
     */
    public static HashMap<String, String> estadisticasUsuario(String usuario)
    {
        int idUsuario = DBUsuario.idUsuario(usuario);
        
        if(idUsuario == -1)
            return null;
        
        String query = 
                "SELECT\n"
                + "	( SELECT usuario FROM m_usuarios WHERE id = p.idjugador ) AS nombre,\n"
                + "	( SELECT personajeSeleccionado AS color FROM m_partidas_progreso WHERE idjugador = p.idjugador GROUP BY personajeSeleccionado ORDER BY color DESC ) AS colorFavorito,\n"
                + "	COUNT( DISTINCT idpartida ) AS partidasJugadas,\n"
                + "	SUM( partidaGanada ) AS partidasGanadas,\n"
                + "	( COUNT( DISTINCT idpartida ) - SUM( partidaGanada ) ) AS partidasPerdidas, \n"
                + "	SUM( monstruosMatados ) AS monstruosMatados,\n"
                + "	SUM( animalesMatados ) AS animalesMatados,\n"
                + "	SUM( jefesMatados ) AS jefesMatados,\n"
                + "	SUM( objetosRecogidos ) AS objetosRecogidos, \n"
                + "	SUM( esmeraldasRecogidas ) AS esmeraldasRecogidas\n"
                + "FROM\n"
                + "	m_partidas_progreso p \n"
                + "WHERE\n"
                + "	idjugador = ? \n"
                + "GROUP BY\n"
                + "	idjugador \n"
                + "ORDER BY\n"
                + "	personajeSeleccionado DESC";
        DBOperacion operacion = new DBOperacion(query);
        operacion.pasarParametro(idUsuario);
        
        DBMatriz resultado = operacion.consultar();
        
        if(resultado.leer())
        {
            HashMap<String, String> estadisticas = new HashMap<>();
            
            estadisticas.put("nombre", (String)resultado.getValor("nombre"));
            estadisticas.put("colorFavorito", personajeSeleccionado2String((int)resultado.getValor("colorFavorito")));
            estadisticas.put("partidasJugadas", resultado.getValor("partidasJugadas").toString());
            estadisticas.put("partidasGanadas", resultado.getValor("partidasGanadas").toString());
            estadisticas.put("partidasPerdidas", resultado.getValor("partidasPerdidas").toString());
            estadisticas.put("monstruosMatados", resultado.getValor("monstruosMatados").toString());
            estadisticas.put("animalesMatados", resultado.getValor("animalesMatados").toString());
            estadisticas.put("jefesMatados", resultado.getValor("jefesMatados").toString());
            estadisticas.put("objetosRecogidos", resultado.getValor("objetosRecogidos").toString());
            estadisticas.put("esmeraldasRecogidas", resultado.getValor("esmeraldasRecogidas").toString());
            
            return estadisticas;
        }
        
        return null;
    }

    /**
     * Crea un hash a partir de un string con el algoritmo de encriptamiento
     * MD5.
     *
     * @param s string a encriptar.
     * @return string encriptado.
     */
    public static String encriptarMD5(String s)
    {
        try
        {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(s.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i)
            {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean equals(DBUsuario obj)
    {
        return this.usuario.equals(obj.usuario);
    }
}
