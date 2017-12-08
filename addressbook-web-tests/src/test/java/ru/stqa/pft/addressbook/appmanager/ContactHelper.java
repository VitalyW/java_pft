package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactForm() {
    click(By.cssSelector("[type=submit]"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("home"), contactData.getTelephone());
    type(By.name("email"), contactData.getEmail());
  }

  public void initContactCreation() {
    click(By.cssSelector("li:nth-child(2)"));
  }

  public void deleteSelectedContacts() {
    click(By.cssSelector("[value=Delete]"));
  }

  public void selectContact() {
    click(By.cssSelector("[name='selected[]']"));
  }

  public void returnToHomePage() {
    click(By.cssSelector(".msgbox i"));
  }

  public void initContactModifiction() {
    click(By.cssSelector("[title=Edit]"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }
}
