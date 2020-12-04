package ru.ermakovis;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory emf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        EntityManager em = emf.createEntityManager();

        String input;
        while (scanner.hasNext() && !(input = scanner.nextLine()).equals("exit")) {
            if (input.equals("list users")) {
                listUsers(em);
            } else if (input.equals("list products")) {
                listProducts(em);
            } else if (input.equals("list orders")) {
                listOrders(em);
            } else if (input.matches("findby\\s+customer\\s\\w+")) {
                findByCustomer(em, input);
            } else if (input.matches("findby\\s+product \\w+")) {
                findByProduct(em, input);
            } else if (input.matches("findby\\s+pair\\s+\\w+\\s\\w+")) {
                findByPair(em, input);
            } else {
                System.out.println("Incorrect input");
            }
        }
    }

    private static void findByPair(EntityManager em, String input) {
        String[] words = input.split("\\s+");
        String customerName = words[2];
        String productName = words[3];
        String query = "SELECT o FROM Orders o " +
                "JOIN o.product p " +
                "JOIN o.customer c " +
                "WHERE p.name = :productName " +
                "AND c.name = :customerName";
        em.createQuery(query, Orders.class)
                .setParameter("productName", productName)
                .setParameter("customerName", customerName)
                .getResultList()
                .forEach(System.out::println);
    }

    private static void findByProduct(EntityManager em, String input) {
        String productName = input.split("\\s+")[2];
        String query = "SELECT o FROM Orders o " +
                "JOIN o.product p " +
                "JOIN o.customer c " +
                "WHERE p.name = :name";
        em.createQuery(query, Orders.class)
                .setParameter("name", productName)
                .getResultList()
                .forEach(System.out::println);
    }

    private static void findByCustomer(EntityManager em, String input) {
        String customerName = input.split("\\s+")[2];
        String query = "SELECT o FROM Orders o " +
                "JOIN o.product p " +
                "JOIN o.customer c " +
                "WHERE c.name = :name";
        em.createQuery(query, Orders.class)
                .setParameter("name", customerName)
                .getResultList()
                .forEach(System.out::println);
    }

    private static void listOrders(EntityManager em) {
        String query = "SELECT o FROM Orders o " +
                "JOIN o.product p " +
                "JOIN o.customer c";
        em.createQuery(query, Orders.class)
                .getResultList()
                .forEach(System.out::println);
    }

    private static void listProducts(EntityManager em) {
        em.createQuery("FROM Product", Product.class)
                .getResultList()
                .forEach(System.out::println);
    }

    private static void listUsers(EntityManager em) {
        em.createQuery("FROM Customer", Customer.class)
                .getResultList()
                .forEach(System.out::println);
    }
}
