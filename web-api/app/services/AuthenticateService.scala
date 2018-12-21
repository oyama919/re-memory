package services

trait AuthenticateService {
  def authenticate(rawPassword: String, hashedPassword: String): Boolean
  def hashPassword(rawPassword: String): String
}
