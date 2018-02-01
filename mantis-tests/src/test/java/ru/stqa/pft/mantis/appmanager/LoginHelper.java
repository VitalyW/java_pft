package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

  public LoginHelper(ApplicationManager app) {
    super(app);
  }

  public void login(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type((By.cssSelector("#username")), username);
    click(By.cssSelector("input[type='submit']"));
    type((By.cssSelector("#password")), password);
    click(By.cssSelector("input[type='submit']"));
  }

}
