package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getNavigationHelper().goToHomePage();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().initContactCreation();
    app.getContactHelper().createContact(new ContactData(
            "Name", "Lastname", "1234567890", "test@test.com", "test1"), true);

    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 1);
  }

}
