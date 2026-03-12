package BaiKiemTra;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Product> productList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========= PRODUCT MANAGEMENT SYSTEM ===");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Hien thi danh sach sp");
            System.out.println("3. Cap nhap so luong theo id");
            System.out.println("4. Xoa sp da het hang");
            System.out.println("5. Thoat");
            System.out.println("======================================");
            System.out.print("Lua chon: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        displayProducts();
                        break;
                    case 3:
                        updateQuantity();
                        break;
                    case 4:
                        deleteOutOfStock();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Lua chon sai");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so hop le.");
            }
        }
    }


    private static boolean isDuplicate(String newId) {
        return productList.stream().anyMatch(product -> product.getId().equals(newId));
    }

    private static void addProduct() {
        System.out.print("Nhap ID: ");
        String id = scanner.nextLine();

        if (isDuplicate(id)) {
            System.out.println("ID da ton tai");
            return;
        }

        System.out.print("Nhap ten: ");
        String name = scanner.nextLine();
        System.out.print("Nhap so luong: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhap gia: ");
        double price = Double.parseDouble(scanner.nextLine());

        productList.add(new Product(id, name, quantity, price));
        System.out.println("Them thanh cong");
    }

    private static void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            productList.forEach(System.out::println);
        }
    }
    private static void updateQuantity() {
        System.out.print("Nhap ID: ");
        String id = scanner.nextLine();
        for (Product p : productList) {
            if (p.getId().equals(id)) {
                System.out.print("Nhap so luong: ");
                p.setQuantity(Integer.parseInt(scanner.nextLine()));
                System.out.println("Cap nhap thanh cong");
                return;
            }
        }
        System.out.println("Khong thay sp.");
    }
    private static void deleteOutOfStock() {
        int oldSize = productList.size();
        productList.removeIf(p -> p.getQuantity() <= 0);
        int newSize = productList.size();
        System.out.println("Da xoa " + (oldSize - newSize) + " sp co quantity <= 0");
    }
}
