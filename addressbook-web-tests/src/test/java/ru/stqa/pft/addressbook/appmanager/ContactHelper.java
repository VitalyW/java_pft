package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(
                contactData.getGroups().iterator().next().getName());
      }
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

//  public void addToGroup(ContactData contact, GroupData group) {
//    selectContactById(contact.getId());
//    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
//    click(By.name("add"));
//  }
//
//  public void removeFromGroup(ContactData contact, GroupData group) {
//    new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
//    selectContactById(contact.getId());
//    click(By.name("remove"));
//  }

  public void addGroup(ContactData contact) {
    selectContactById(contact.getId());
    selectGroupToAdd(contact);
    addGroupToContact();
    contactCache = null;
    returnToHomePage();
  }

  public void removeGroup(ContactData contact) {
    selectGroupToDelete(contact);
    selectContactById(contact.getId());
    removeGroupFromContact();
    contactCache = null;
    returnToHomePage();
  }

  public void selectGroupToAdd(ContactData contactData) {
    if (contactData.getGroups().size() > 0) {
      Assert.assertTrue(contactData.getGroups().size() == 1);
      new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
    } else {
      Assert.assertFalse(isElementPresent(By.name("to_group")));
    }
  }

  public void selectGroupToDelete(ContactData contactData) {
    if (contactData.getGroups().size() > 0) {
      Assert.assertTrue(contactData.getGroups().size() == 1);
      new Select(wd.findElement(By.name("group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
    } else {
      Assert.assertFalse(isElementPresent(By.name("group")));
    }
  }

  private void addGroupToContact() {
    click(By.name("add"));
  }

  private void removeGroupFromContact() {
    click(By.name("remove"));
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

  public int count() {
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
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      contactCache.add(new ContactData().withId(id).withName(firstName).withLastname(lastName)
              .withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditPage(ContactData contact) {
    initContactModifictionById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withName(firstname).withLastname(lastname).withHomePhone(home)
            .withMobilePhone(mobile).withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3)
            .withAddress(address);
  }
}