package com.projeto.centralerros.usuario.repository;

import com.projeto.centralerros.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUserName(String userName);
}
