package services

trait PasswordService {
  def checkPassword(rawPassword: String, hashedPassword: String): Boolean

  def hashPassword(rawPassword: String): String
}
