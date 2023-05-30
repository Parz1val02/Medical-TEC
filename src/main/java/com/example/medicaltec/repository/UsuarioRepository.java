package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Usuario;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    Usuario findByEmail(String email);


    /*QUERYS USADOOS POR ADMINISTRADOR*/
    @Query(nativeQuery = true, value = "select contrasena from usuario u where u.dni = ?1")
    String buscarPasswordPropioUsuario(String id);
    @Query(nativeQuery = true, value = "select * from usuario u where u.roles_idroles = 2 and u.sedes_idsedes = ?1 and u.enabled = 1")
    List<Usuario> obtenerListaPacientes2(Integer sedes);

    @Query(nativeQuery = true,value = "select * from usuario u where u.roles_idroles = 1 and u.sedes_idsedes = ?1 and u.enabled = 1")
    List<Usuario> obtenerlistaDoctoresAdmin(Integer sedes);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO usuario (email, nombre, apellido, telefono, dni, sedes_idsedes, edad, direccion, sexo, contrasena, roles_idroles, estados_idestado, modoregistro, enabled) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,2,1,'invitado',1)")
    void crearPaciente(String email, String nombre, String apellido, String telefono, String dni, Integer sede, int edad, String direccion, String sexo, String contrasena  );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4 where  u.dni = ?5 and u.sedes_idsedes = ?6")
    void editarPaciente(String email, String nombre, String apellido, String telefono, String dni, Integer sede );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO usuario (email, nombre, apellido, telefono, especialidades_id_especialidad, dni, sedes_idsedes, edad, direccion, sexo, contrasena, roles_idroles, estados_idestado,enabled) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,1,1,1)")
    void crearDoctor(String email, String nombre, String apellido, String telefono, int especialidad, String dni, Integer sede, int edad, String direccion, String sexo, String contrasena  );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4, u.especialidades_id_especialidad=?5 where  u.dni = ?6 and u.sedes_idsedes = ?7")
    void editarDoctor(String email, String nombre, String apellido, String telefono, int especialidad, String dni, Integer sede );


    /*FIN QUERYS USADOOS POR ADMINISTRADOR*/
    /* ************************************ */
    /* ************************************ */


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from usuario u where u.roles_idroles = 2")
    List<Usuario> obtenerListaPacientes();
    @Query(nativeQuery = true, value = "select * from usuario u where u.roles_idroles = 1")
    List<Usuario> obtenerListaDoctores();
    @Query(nativeQuery = true, value = "select * from usuario u where u.roles_idroles = 3")
    List<Usuario> obtenerListaAdministrativos();
    @Query(nativeQuery = true, value = "select * from usuario u where u.roles_idroles = 4")
    List<Usuario> obtenerListaAdministradores();

    @Query(nativeQuery = true, value = "select * from usuario u where u.roles_idroles = 5")
    Usuario obtenerSuperAdmin();



    Usuario findByid(String id);

    @Query(nativeQuery = true,value = "select * from usuario u where u.roles_idroles = 1 and u.sedes_idsedes = ?1")
    List<Usuario> obtenerlistaDoctores(Integer idSede);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update usuario set seguros_id_seguro=?1 where dni=?2" )
    void cambiarSeguro(String idSeguro, String dni);


    @Query(value = "select * from telesystem.usuario u where u.roles_idroles = 1", nativeQuery = true)
    List<Usuario> listarDoctores();

    @Query(value = "select * from telesystem.usuario u where u.roles_idroles = 2", nativeQuery = true)
    List<Usuario> listarPacientes();
    @Modifying
    @Transactional
    @Query(value = "UPDATE telesystem.usuario u SET u.sedes_idsedes = ?1 WHERE dni=\"12345678\"", nativeQuery = true)
    void actualizarSede(int id_nuevo);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6, u.especialidades_id_especialidad = ?7 where  u.dni = ?8")
    void editarAdministrativo(String email, String nombre, String apellido, int sede, String telefono,  int estado, int especialidad, String dni);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6 where  u.dni = ?7")
    void editarAdministradores(String email, String nombre, String apellido, int sede, String telefono,  int estado,  String dni);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6, u.especialidades_id_especialidad = ?7 where  u.dni = ?8")
    void editarDoctor(String email, String nombre, String apellido, int sede, String telefono,  int estado, int especialidad, String dni);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6 where  u.dni = ?7 ")
    void editarPaciente(String email, String nombre, String apellido, int sede, String telefono,  int estado,  String dni);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem`.`usuario` (`dni`, `contrasena`, `email`, `nombre`, `apellido`, `edad`, `telefono`, `sexo`, `direccion`, `sedes_idsedes`, `estados_idestado`, `roles_idroles`) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, 4);",nativeQuery = true)
    void crearAdmSede(String dni, String password,String email,String nombre, String apellido,int edad, String telefono, String sexo, String direccion, int sede, int estado);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem`.`usuario` (`dni`, `contrasena`, `email`, `nombre`, `apellido`, `edad`, `telefono`, `sexo`, `direccion`, `sedes_idsedes`, `estados_idestado`, `especialidades_id_especialidad`,`roles_idroles`) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, 3);",nativeQuery = true)
    void crearAdmT(String dni, String password,String email,String nombre,String apellido, int edad, String telefono, String sexo, String direccion, int sede, int estado, int especialidad);


    @Transactional
    @Modifying
    @Query(value = "update `telesystem`.`usuario` set nombre = ?1, apellido=?2, email=?3, telefono=?4, sedes_idsedes =?5, estados_idestado=?6 where dni = ?7",nativeQuery = true)
    void editAdmS(String nombre, String apellido,String email,String telefono, int sede, int estado, String dni);

    @Transactional
    @Modifying
    @Query(value = "update `telesystem`.`usuario` set nombre = ?1, apellido=?2, email=?3, telefono=?4, dni =?5 where dni=?6",nativeQuery = true)
    void editSuperAdmin(String nombre, String apellido,String email,String telefono, String dni, String dnipast);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="UPDATE usuario SET `contrasena` = ?1 WHERE (`dni` = ?2)")
    void cambiarContra(String pass, String dni);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario set contrasena =?1 where dni=?2")
    void cambiarPasswSA(String password, String dni);

    @Query(value="select contrasena from usuario where dni=\"34185296\"",nativeQuery = true)
            String passAdmv();

    @Query(nativeQuery = true,value="select * from usuario where dni = \"34185296\"")
    Usuario obtenerUsuario();

    @Query(nativeQuery = true, value="select dni from usuario ")
    List<String> obtenerdnis();

    @Query(nativeQuery = true, value="select * from usuario where dni = ?")
    Usuario usuarioForm(String dni);

}
