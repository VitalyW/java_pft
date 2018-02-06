package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroup extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      createContact();
    }
    if (app.db().groups().size() == 0) {
      createGroup();
    }
  }

  private void createContact() {
    app.goTo().homePage();
    app.contact().create(
            new ContactData()
                    .withName("Name").withLastname("Lastname").withHomePhone("1234567890")
                    .withEmail("test@test.com").withAddress("Hackerway 1"), true);
  }

  private void createGroup() {
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("group1").withHeader("header1").withFooter("footer2"));
  }

  @Test
  public void testRemoveContactFromGroup() {
    Groups groups = app.db().groups();
    GroupData group = app.db().groups().iterator().next();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).inGroup(groups.iterator().next());
    app.goTo().homePage();
    app.contact().removeGroup(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(contact)));
    verifyContactListInUI();
  }
}
