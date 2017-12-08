package ru.stqa.pft.sandbox;

public class Equation {

  private int a;
  private int b;
  private int c;
  private int n;
  double d;

  public Equation(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;

    d = Math.pow(b, 2) - 4 * a * c;

    if (a != 0) {
      if (d > 0) {
        n = 2;
      } else if (d == 0) {
        n = 1;
      } else {
        n = 0;
      }
    } else if (b != 0) {
      n = 1;
    } else if (c != 0) {
      n = 0;
    } else {
      n = -1;
    }

  }

  public int rootNumber() {
    return n;
  }
}
