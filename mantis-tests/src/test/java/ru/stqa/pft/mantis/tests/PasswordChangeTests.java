package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testPasswordChange() throws IOException, MessagingException {
    app.login().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    User user = app.passwordResetHelper().users().stream()
            .filter(u -> !app.getProperty("web.adminLogin").equals(u.getLogin()))
            .findFirst().orElse(null);
    app.passwordResetHelper().resetPassword(user.getId());
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
    String passwordResetLink = findPasswordResetLink(mailMessages, user.getEmail());
    String password = password();
    app.passwordResetHelper().finish(passwordResetLink, password);
    HttpSession session = app.newSession();
    assertTrue(session.login(user.getLogin(), password));
  }

  private String findPasswordResetLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  private String password() {
    return UUID.randomUUID().toString();
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
