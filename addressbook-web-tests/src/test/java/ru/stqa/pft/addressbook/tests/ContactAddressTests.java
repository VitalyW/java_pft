package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      Groups groups = app.db().groups();
      app.contact().create(
              new ContactData()
                      .withName("Name").withLastname("Lastname").withHomePhone("1234567890").withMobilePhone("923093209")
                      .withWorkPhone("2332323200").withEmail("test@test.com").withEmail2("test2@test2.com")
                      .withEmail3("test3@test3.com").inGroup(groups.iterator().next()), true);
    }
  }

  @Test
  public void testContactAddress() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditPage(contact);
    assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
  }

  private String mergeAddress(ContactData contact) {
    return Arrays.asList(contact.getAddress())
            .stream().filter((s) -> !s.equals("")).collect(Collectors.joining("\n"));
  }

}
