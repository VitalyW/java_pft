package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
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

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void initContactModifiction() {
    click(By.cssSelector("[title=Edit]"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void createContact(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactForm();
    returnToHomePage();
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

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<>();
    List<WebElement> elements = wd.findElements(By.cssSelector("[name=entry]"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String phone = cells.get(5).getText();
      String email = cells.get(4).getText();
      String id = element.findElement(By.tagName("input")).getAttribute("id");
      ContactData contact = new ContactData(id, firstName, lastName, phone, email, null);
      contacts.add(contact);
    }
    return contacts;
  }
}