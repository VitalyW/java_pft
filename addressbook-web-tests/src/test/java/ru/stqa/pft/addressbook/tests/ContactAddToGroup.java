package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(
              new ContactData()
                      .withName("Name").withLastname("Lastname").withHomePhone("1234567890")
                      .withEmail("test@test.com").withAddress("Hackerway 1"), true);
    } else if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("group1"));
//    } else if (app.db().contacts().size() != 0) {
//      Groups groups = app.db().groups();
//      Contacts before = app.db().contacts();
//      ContactData modifiedContact = before.iterator().next();
//      ContactData contact = new ContactData().withId(modifiedContact.getId()).inGroup(groups.iterator().next());
//      app.goTo().homePage();
//      app.contact().removeGroup(contact);
    }
  }

  @Test
  public void testAddContactToGroup() {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).inGroup(groups.iterator().next());
    app.goTo().homePage();
    app.contact().addGroup(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(contact)));
    verifyContactListInUI();
  }
}