package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      Groups groups = app.db().groups();
      app.goTo().homePage();
      app.contact().create(
              new ContactData()
                      .withName("Name").withLastname("Lastname").withHomePhone("1234567890").withMobilePhone("923093209")
                      .withWorkPhone("2332323200").withEmail("test@test.com").withEmail2("test2@test2.com")
                      .withEmail3("test3@test3.com").withAddress("Hackerway 1").inGroup(groups.iterator().next()), true);
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withName("Name").withLastname("Lastname").withHomePhone("1234567890").withMobilePhone("923093209")
            .withWorkPhone("2332323200").withEmail("test@test.com").withEmail2("test2@test2.com")
            .withEmail3("test3@test3.com").withAddress("Hackerway 1")
            .withPhoto(new File("src/test/resources/bmw.png")).inGroup(groups.iterator().next());
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }
}
