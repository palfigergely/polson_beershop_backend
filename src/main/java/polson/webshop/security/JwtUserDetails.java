package polson.webshop.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
public class JwtUserDetails implements UserDetails {

  private String userName;
  private Long userId;
  private String brewery;

  public JwtUserDetails(String userName, Long userId, String brewery) {
    this.userName = userName;
    this.userId = userId;
    this.brewery = brewery;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return userName;
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

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getBrewery() {
    return brewery;
  }

  public void setBrewery(String brewery) {
    this.brewery = brewery;
  }

}
