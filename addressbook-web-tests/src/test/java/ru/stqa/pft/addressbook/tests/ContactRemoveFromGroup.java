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
      app.goTo().homePage();
      app.contact().create(
              new ContactData()
                      .withName("Name").withLastname("Lastname").withHomePhone("1234567890")
                      .withEmail("test@test.com").withAddress("Hackerway 1"), true);
    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testRemoveContactFromGroup() {

//    Groups dbGroups = app.db().groups();
//    GroupData associateGroup = dbGroups.iterator().next();
//    for (GroupData group : dbGroups) {
//      if (group.getContacts().size() > 0) {
//        associateGroup = group;
//        break;
//      } else {
//        app.goTo().homePage();
//        app.contact().addToGroup(app.db().contacts().iterator().next(), associateGroup);
//        app.goTo().homePage();
//
//      }
//    }
//    Contacts beforeDeletion = associateGroup.getContacts();
//    int before = associateGroup.getContacts().size();
//    ContactData associateContact = associateGroup.getContacts().iterator().next();
//    app.goTo().groupPage();
//    app.contact().removeFromGroup(associateContact, associateGroup);
//    app.goTo().homePage();
//    assertThat(associateGroup.getContacts().size(), equalTo(before - 1));
//    assertThat(associateGroup.getContacts(), equalTo(beforeDeletion.without(associateContact)));
  }
}
