package com.jewel.test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Test {

	public static class Person {
		private String name;
		private int age;

		public void setAge(int age) {
			this.age = age;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}

	}

	public static void main(String[] args) {
		PriorityQueue<Person> priorityQueue = new PriorityQueue<Test.Person>(1, new Comparator<Person>() {

			public int compare(Person o1, Person o2) {
				if (o1.getAge() < o2.getAge()) {
					return 1;
				} else if (o1.getAge() > o2.getAge()) {
					return -1;
				}
				return 0;
			}
		});
		Person person = new Person();
		person.setAge(12);
		person.setName("myt");
		priorityQueue.add(person);
		Person person1 = new Person();
		person1.setAge(12);
		person1.setName("myt");
		priorityQueue.add(person1);
		Person person2 = new Person();
		person2.setAge(14);
		person2.setName("myt");
		priorityQueue.add(person2);
		Person person3 = new Person();
		person3.setAge(1);
		person3.setName("myt");
		priorityQueue.add(person3);
		Person person4 = new Person();
		person4.setAge(88);
		person4.setName("myt");
		priorityQueue.add(person4);
		Person person5 = new Person();
		person5.setAge(34);
		person5.setName("myt");
		priorityQueue.add(person5);
		Person person6 = new Person();
		person6.setAge(67);
		person6.setName("myt");
		priorityQueue.add(person6);
		System.out.println(priorityQueue.peek().toString());
		Iterator<Person> iterator = priorityQueue.iterator();
		while (iterator.hasNext()) {
			Person p=iterator.next();
			System.out.println(p.toString());
		}
		System.out.println("---------------------------");
/*		System.out.println(priorityQueue.poll().toString());
		System.out.println(priorityQueue.poll().toString());
		System.out.println(priorityQueue.poll().toString());
		System.out.println(priorityQueue.poll().toString());
		System.out.println(priorityQueue.poll().toString());
		System.out.println(priorityQueue.poll().toString());*/
		System.out.println("---------------------------");
		
		
		priorityQueue.remove(person4);
		person4.setAge(11);
		person4.setName("33");
		priorityQueue.add(person4);
		System.out.println(priorityQueue.peek().toString());
		Iterator<Person> iterator1 = priorityQueue.iterator();
		while (iterator1.hasNext()) {
			Person p=iterator1.next();
			System.out.println(p.toString());
		}
	}
}
