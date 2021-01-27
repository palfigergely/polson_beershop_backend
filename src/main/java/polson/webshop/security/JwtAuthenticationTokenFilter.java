package polson.webshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  public JwtAuthenticationTokenFilter() {
    super();
  }

  @Value("${POLSON_JWT_SECRET_KEY}")
  private String tokenSecretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = header.substring(7);
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(tokenSecretKey.getBytes()).build()
          .parseClaimsJws(token).getBody();

      JwtUserDetails jwtUserDetails = createJwtUserDetails(claims);

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, null);
      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
      SecurityContextHolder.clearContext();
      throw e;
    }
    filterChain.doFilter(request, response);
  }

  private JwtUserDetails createJwtUserDetails(Claims claims) {
    JwtUserDetails jwtUserDetails = new JwtUserDetails();
    jwtUserDetails.setUserId(Long.valueOf((String) claims.get("userId")));
    jwtUserDetails.setUserName((String) claims.get("userName"));
    jwtUserDetails.setBrewery((String) claims.get("brewery"));
    return jwtUserDetails;
  }
}
