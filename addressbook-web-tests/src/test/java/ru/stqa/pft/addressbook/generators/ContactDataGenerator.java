package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException e) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    save(contacts, new File(file));
  }

  private static void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getName(), contact.getLastname(),
              contact.getAddress(), contact.getEmail(), contact.getEmail2(), contact.getEmail3(), contact.getHomePhone(),
              contact.getMobilePhone(), contact.getWorkPhone()));
    }
    writer.close();
  }

  private static List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withName(String.format("Name %s", i)).withLastname(String.format("Lastname %s", i))
              .withAddress(String.format("Hackerway %s", i)).withEmail(String.format("test%s@test.com", i))
              .withEmail2(String.format("test%s@test2.com", i)).withEmail3(String.format("test%s@test3.com", i))
              .withHomePhone(String.format("121223%s", i)).withMobilePhone(String.format("93%s12335", i))
              .withWorkPhone(String.format("%s121223", i)));
    }
    return contacts;
  }
}
