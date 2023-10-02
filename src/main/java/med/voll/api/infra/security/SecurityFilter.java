package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author jdmon on 1/10/2023.
 * @project api-med-voll
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader!=null){
            var token=authHeader.replace("Bearer ","");
            var nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario!=null){
                //token valido
                var usuario=usuarioRepository.findByLogin(nombreUsuario);
                var authenticacion= new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities()); // forzamos el inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authenticacion);
            }

        }
        filterChain.doFilter(request,response);

    }
}
