package services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordServiceImpl extends PasswordService {
  private val bcrypt = new BCryptPasswordEncoder()

  def checkPassword(rawPassword: String, hashedPassword: String): Boolean =
    bcrypt.matches(rawPassword, hashedPassword)

  def hashPassword(rawPassword: String): String = bcrypt.encode(rawPassword)
}
