package com.agung.trustwalletclone.model

import com.agung.trustwalletclone.BuildConfig
import java.util.*
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session

class Mail(
    val username: String = "axisoption.help@gmail.com",
    val password: String = BuildConfig.EMAIL_PASS,
    private val properties: Properties = Properties(),
) {
    var session: Session

    init {
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.port"] = "587"
        session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
    }
}
