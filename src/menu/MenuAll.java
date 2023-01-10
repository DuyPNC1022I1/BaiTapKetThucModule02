package menu;

import controller.ProductManager;

import java.util.Scanner;

public class MenuAll {
    Scanner scanner = new Scanner(System.in);
    ProductManager products = new ProductManager();


    public int choice(Scanner scanner) {
        int choice = -1;
        try {
            System.out.println("Chọn chức năng: ");
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Sai định dạng! Vui lòng nhập lại lựa chọn");
        }
        return choice;
    }

    public void runMenuAll() {
        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~MENU~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Sắp xếp");
            System.out.println("6. Tìm sản phẩm có giá đắt nhất");
            System.out.println("7. Đọc từ file");
            System.out.println("8. Ghi vào file");
            System.out.println("9. Thoát");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            switch (choice(scanner)) {
                case 1:
                    products.display();
                    break;
                case 2:
                    products.add(scanner);
                    break;
                case 3:
                    products.update(scanner);
                    break;
                case 4:
                    MenuAll menu = new MenuAll();
                    products.delete(scanner, menu);
                    break;
                case 5:
                    products.displayByPriceUp(scanner);
                    break;
                case 6:
                    products.displayByPriceMax();
                    break;
                case 7:
                    products.read("D:\\Module 02\\BaithiketthucMD02\\src\\FileSave\\File");
                    break;
                case 8:
                    products.writeCsv(products.getProducts(), "D:\\Module 02\\BaithiketthucMD02\\src\\FileSave\\File");
                    break;
                case 9:
                    System.exit(0);
                default:
                    System.out.println("Out of choice. Re-enter choice: 0~2");
            }
        }
        while (true);
    }
}
