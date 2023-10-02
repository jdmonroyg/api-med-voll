package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author jdmon on 1/10/2023.
 * @project api-med-voll
 */
@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getUsername())
                    .withClaim("id",usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }

    }

    public String getSubject(String token){
        if (token==null){
            throw new RuntimeException();
        }
        DecodedJWT verifier=null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //validando la firma
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build().
                    verify(token);
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        if (verifier==null){
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();


    }
    //para generar fecha de expiracion
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-05:00"));
    }
}
