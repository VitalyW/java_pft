package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHomePage();
    int before = app.getContactHelper().getContactCount();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData(
              "Name", "Lastname", "1234567890", "test@test.com", "test1"), true);
    }
    app.getContactHelper().selectContact(before - 1);
    app.getContactHelper().initContactModifiction();
    app.getContactHelper().fillContactForm(new ContactData(
            "Name", "Lastname", "1234567890", "test@test.com", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
  }
}
