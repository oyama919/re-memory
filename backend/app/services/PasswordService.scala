package services

trait PasswordService {
  def checkPassword(requestPassword: String, hashedPassword: String): Boolean

  def hashPassword(requestPassword: String): String
}
