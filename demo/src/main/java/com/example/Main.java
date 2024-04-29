package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class Student {
    private String name;
    private String studentId;
    private double balance;

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.balance = 0;
    }

    public void makePayment(double amount) {
        this.balance += amount;
        System.out.println("Payment successful. Your new balance is: K" + this.balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }
}

class Payment {
    private String paymentId;
    private double amount;

    public Payment(double amount) {
        this.paymentId = UUID.randomUUID().toString();
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }
}

class PaymentSystem {
    private Map<String, Student> students;
    private Map<String, Payment> ledger;

    public PaymentSystem() {
        students = new HashMap<>();
        ledger = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public void processPayment(String studentId, double amount) {
        if (students.containsKey(studentId)) {
            Student student = students.get(studentId);
            Payment payment = new Payment(amount);
            student.makePayment(amount);
            ledger.put(payment.getPaymentId(), payment);
            System.out.println("Payment ID: " + payment.getPaymentId());
        } else {
            System.out.println("Student not found.");
        }
    }

    public void printLedger() {
        for (String paymentId : ledger.keySet()) {
            Payment payment = ledger.get(paymentId);
            System.out.println("Payment ID: " + paymentId + ", Amount: K" + payment.getAmount());
        }
    }
}

// Example usage:
public class Main {
    public static void main(String[] args) {
        PaymentSystem paymentSystem = new PaymentSystem();

        // Adding students
        Student student1 = new Student("John Doe", "S001");
        Student student2 = new Student("Jane Smith", "S002");
        paymentSystem.addStudent(student1);
        paymentSystem.addStudent(student2);

        // Making payments
        paymentSystem.processPayment("S001", 100);
        paymentSystem.processPayment("S002", 200);

        // Printing the ledger
        paymentSystem.printLedger();
    }
}
