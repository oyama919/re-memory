package services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordServiceImpl extends PasswordService {
  private val bcrypt = new BCryptPasswordEncoder()

  def checkPassword(requestPassword: String, hashedPassword: String): Boolean =
    bcrypt.matches(requestPassword, hashedPassword)

  def hashPassword(requestPassword: String): String =
    bcrypt.encode(requestPassword)
}
