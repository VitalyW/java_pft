package ru.stqa.pft.rest;

import java.util.Objects;

public class Issue {
  private int id;
  private String Subject;
  private String description;

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return Subject;
  }

  public Issue withSubject(String subject) {
    Subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id &&
            Objects.equals(Subject, issue.Subject) &&
            Objects.equals(description, issue.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, Subject, description);
  }
}
