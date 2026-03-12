package BaiKiemTra;

import java.util.ArrayList;
import java.util.Optional;
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

    private static void addProduct() {
        try {
            System.out.print("Nhap ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (isDuplicate(id)) {
                throw new InvalidProductException("ID da ton tai");
            }

            System.out.print("Nhap ten: ");
            String name = scanner.nextLine();
            System.out.print("Nhap so luong: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            System.out.print("Nhap gia: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Nhap danh muc: ");
            String category = scanner.nextLine();

            productList.add(new Product(id, name, price, quantity, category));
            System.out.println("Them thanh cong");
        } catch (NumberFormatException e) {
            System.out.println("Sai dinh dang so.");
        } catch (InvalidProductException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isDuplicate(int newId) {
        return productList.stream().anyMatch(product -> product.getId() == newId);
    }

    private static void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("Danh sach trong.");
        } else {
            System.out.println("+-------+----------------------+------------+----------+-----------------+");
            System.out.println("| ID    | Ten                  | Gia       | So luong | Danh muc        |");
            System.out.println("+-------+----------------------+------------+----------+-----------------+");
            productList.forEach(p -> {
                System.out.printf("| %-5d | %-20s | %-10.2f | %-8d | %-15s |\n",
                        p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategory());
            });
            System.out.println("+-------+----------------------+------------+----------+-----------------+");
        }
    }

    private static void updateQuantity() {
        try {
            System.out.print("Nhap ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Optional<Product> optionalProduct = productList.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst();

            if (optionalProduct.isPresent()) {
                Product p = optionalProduct.get();
                System.out.print("Nhap so luong moi: ");
                int newQuantity = Integer.parseInt(scanner.nextLine());
                p.setQuantity(newQuantity);
                System.out.println("Cap nhap thanh cong");
            } else {
                throw new InvalidProductException("Khong tim thay sp voi id: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Sai dinh dang so.");
        } catch (InvalidProductException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteOutOfStock() {
        int oldSize = productList.size();
        productList.removeIf(p -> p.getQuantity() == 0);
        int newSize = productList.size();
        System.out.println("Da xoa " + (oldSize - newSize) + " sp co quantity = 0");
    }
}
