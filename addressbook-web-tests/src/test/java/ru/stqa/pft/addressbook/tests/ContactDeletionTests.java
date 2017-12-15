package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void ContactDeletionTests() {
    app.getNavigationHelper().goToHomePage();
    int before = app.getContactHelper().getContactCount();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData(
              "Name", "Lastname", "1234567890", "test@test.com", "test1"), true);
    }
    app.getContactHelper().selectContact(before - 1);
    app.getContactHelper().deleteSelectedContacts();
    app.getNavigationHelper().acceptAlert();
    app.getContactHelper().waitUntilPageRefreshesAfterContactDeletion();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);
  }

}
