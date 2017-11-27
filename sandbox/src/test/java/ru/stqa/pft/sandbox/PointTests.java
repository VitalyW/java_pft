package ru.stqa.pft.sandbox;


import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint() {
    Point p1 = new Point(3, 6);
    Point p2 = new Point(0, 2);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }

  @Test
  public void testPoint2() {
    Point p1 = new Point(3, 6);
    Point p2 = new Point(0, 2);
    Assert.assertNotEquals(p1.distance(p2), 5);
  }

  @Test
  public void testPoint3() {
    Point p1 = new Point(3, 6);
    Point p2 = new Point(0, 1);
    Assert.assertEquals((int) p1.distance(p2), 5);
  }
}
