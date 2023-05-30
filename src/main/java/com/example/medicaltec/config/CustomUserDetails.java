package com.example.medicaltec.config;

import com.example.medicaltec.Entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Usuario user; // Objeto que representa los datos del usuario

    public CustomUserDetails(Usuario user) {
        this.user = user;
    }

    // Implementación de los métodos de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Obtener los roles del usuario y convertirlos en instancias de GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRolesIdroles().getNombreRol()));
        return authorities;
    }

    @Override
    public String getPassword() {
        // Devolver la contraseña del usuario
        return user.getContrasena();
    }

    @Override
    public String getUsername() {
        // Devolver el nombre de usuario del usuario
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
