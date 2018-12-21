package services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthenticateServiceImpl extends AuthenticateService {
  private val bcrypt = new BCryptPasswordEncoder()

  def authenticate(rawPassword: String, hashedPassword: String): Boolean =
    bcrypt.matches(rawPassword, hashedPassword)
}
