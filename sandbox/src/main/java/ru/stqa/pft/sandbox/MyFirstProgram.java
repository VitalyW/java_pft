package ru.stqa.pft.sandbox;

class MyFirstProgram {
	public static void main(String[] args) {
		Square s = new Square(4);
		Rectangle r = new Rectangle(5, 6);
		Point p1 = new Point(3, 6);
		Point p2 = new Point(0, 2);
		System.out.println(String.format("Площадь квадрата со стороной %s равнa %s", s.l, s.area()));
		System.out.println(String.format("Площадь прямоугольника со сторонами %s и %s равнa %s", r.a, r.b, r.area()));
		System.out.println(String.format("Расстояние между двумя точками (%s, %s) и (%s, %s) равно %s",
																																						p1.x, p1.y, p2.x, p2.y, p2.distance(p1)));
		System.out.println(String.format("Расстояние между двумя точками (%s, %s) и (%s, %s) равно %s",
																																					p1.x, p1.y, p2.x, p2.y, distance1(p1, p2)));
	}

	public static double distance1(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
}
