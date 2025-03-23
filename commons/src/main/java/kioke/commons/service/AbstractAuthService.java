package kioke.commons.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.PublicKey;
import java.util.Date;
import kioke.commons.exception.security.TokenExpiredException;
import kioke.commons.exception.security.TokenInvalidException;

public abstract class AbstractAuthService {

  protected abstract PublicKey getPublicKey();

  public String extractSubjectFromToken(String token)
      throws TokenInvalidException, TokenExpiredException {
    Claims claims;

    try {
      claims = extractClaimsFromToken(token);
    } catch (IllegalArgumentException | JwtException e) {
      throw new TokenInvalidException();
    }

    if (claims.getExpiration().before(new Date())) {
      throw new TokenExpiredException();
    }

    return claims.getSubject();
  }

  private Claims extractClaimsFromToken(String token) {
    Claims claims =
        Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();

    return claims;
  }
}
