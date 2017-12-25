package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactForm() {
    click(By.cssSelector("[type=submit]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("home"), contactData.getTelephone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.cssSelector("li:nth-child(2)"));
  }

  public void deleteSelectedContacts() {
    click(By.cssSelector("[value=Delete]"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();

  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void initContactModifictionById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%d']", id ))).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactForm();
    contactCache = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModifictionById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    returnToHomePage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContacts();
    acceptAlert();
    contactCache = null;
    waitUntilPageRefreshesAfterContactDeletion();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    acceptAlert();
    contactCache = null;
    waitUntilPageRefreshesAfterContactDeletion();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.cssSelector("[name='selected[]']"));
  }

  public int getContactCount() {
    return wd.findElements(By.cssSelector("[name='selected[]']")).size();
  }

  public void waitUntilPageRefreshesAfterContactDeletion() {
    WebDriverWait wait = new WebDriverWait(wd, 10);
    wait.until(d -> d.findElement(By.cssSelector("[name='selected[]']")));
  }

  public void acceptAlert() {
    wd.switchTo().alert().accept();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("[name=entry]"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String phone = cells.get(5).getText();
      String email = cells.get(4).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      contactCache.add(new ContactData().withId(id).withName(firstName).withLastname(lastName).withTelephone(phone).withEmail(email));
    }
    return new Contacts(contactCache);
  }
}