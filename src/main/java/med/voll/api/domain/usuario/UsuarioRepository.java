package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author jdmon on 30/09/2023.
 * @project api-med-voll
 */
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    UserDetails findByLogin(String username);
}
