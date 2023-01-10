package controller;

import menu.MenuAll;
import model.Product;
import service.crud;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ProductManager implements crud<Product> {
    private ArrayList<Product> products;
    private int idDefault = 1;

    public ProductManager() {
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public void display() {
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong danh sách");
        } else {
            System.out.printf("-----------------------------------------------------------------%n");
            System.out.printf("                             DANH SÁCH SẢN PHẨM                        %n");
            System.out.printf("-----------------------------------------------------------------%n");
            System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n", "ID", "NAME", "PRICE (USD)", "QUANTITY", "INFORMATION");
            for (int i = 0; i < products.size(); i++) {
                System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n",
                        products.get(i).getId(), products.get(i).getName(), products.get(i).getPrice()
                        , products.get(i).getQuantity(), products.get(i).getInformation());
            }
        }
    }

    @Override
    public Product creatNew(Scanner scanner) {
        int id;
        int price = 0;
        int quantity = 0;
        String name = "";
        boolean check = true;
        System.out.println("Nhập vào thông tin để tạo mới sản phẩm: ");
        if (products.isEmpty()) {
            id = idDefault++;
        } else {
            id = products.get(products.size() - 1).getId() + 1;
        }
        do {
            System.out.println("Nhập vào tên sản phẩm: ");
            name = scanner.nextLine();
            if (!name.equals("")) {
                check = false;
            } else {
                System.out.println("Vui lòng nhập lại: (tên sản phẩm > 0 ký tự)");
            }
        }
        while (check);
        do {
            try {
                System.out.println("Nhập vào giá sản phẩm: ");
                price = Integer.parseInt(scanner.nextLine());
                System.out.println("Nhập vào số lượng sản phẩm: ");
                quantity = Integer.parseInt(scanner.nextLine());
                check = true;
            } catch (NumberFormatException e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
        while (!check);
        System.out.println("Nhập vào thông tin sản phẩm: ");
        String inforamation = scanner.nextLine();
        System.out.println("Thêm sản phẩm mới thành công!");
        return new Product(id, name, price, quantity, inforamation);
    }

    @Override
    public void add(Scanner scanner) {
        products.add(creatNew(scanner));
        display();
    }

    @Override
    public void delete(Scanner scanner, MenuAll menu) {
        int indexDelete = searchId(scanner);
        if (indexDelete != -1) {
            if(choose(scanner)) {
                products.remove(indexDelete);
                display();
            }
            else {
                menu.runMenuAll();
            }
        } else {
            System.out.println("Không tồn tại sản phẩm trong danh sách. Xóa lỗi!");
        }
    }

    @Override
    public void update(Scanner scanner) {
        System.out.println("Nhập dữ liệu để cập nhật thông tin sản phẩm: ");
        int indexUpdate = searchId(scanner);
        if (indexUpdate != -1) {
            boolean check = true;
            String name = "";
            do {
                try {
                    do {
                        System.out.println("Nhập vào tên mới: ");
                        name = scanner.nextLine();
                        if (!name.equals("")) {
                            check = false;
                        } else {
                            System.out.println("Vui lòng nhập lại: (tên sản phẩm > 0 ký tự)");
                        }
                    }
                    while (check);
                    products.get(indexUpdate).setName(name);
                    System.out.println("Nhập vào giá mới : ");
                    int price = Integer.parseInt(scanner.nextLine());
                    products.get(indexUpdate).setPrice(price);
                    System.out.println("Nhập vào số lượng sản phẩm mới: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    products.get(indexUpdate).setQuantity(quantity);
                    check = true;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong format, re-enter!!!");
                }
            }
            while (!check);
            System.out.println("Nhập vào thông tin sản phẩm mới: ");
            String inforamation = scanner.nextLine();
            products.get(indexUpdate).setInformation(inforamation);
            System.out.println("Cập nhật thông tin sản phẩm thành công!");
            display();
        } else {
            System.out.println("Không có sản phẩm nào trong danh sách. Lỗi cập nhật!");
        }

    }

    public int searchId(Scanner scanner) {
        if (!products.isEmpty()) {
            int id = -1;
            int index = -1;
            boolean flag = true;
            boolean check = true;
            do {
                try {
                    System.out.println("Nhập id của sản phẩm cần tìm: ");
                    id = Integer.parseInt(scanner.nextLine());
                    flag = false;
                } catch (NumberFormatException e) {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            }
            while (flag);
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == id) {
                    index = i;
                    check = false;
                }
            }
            if (check) {
                System.out.println("Không tìm thấy sản phẩm có id vừa nhập, vui lòng nhập lại!");
                searchId(scanner);
            }
            return index;
        } else {
            return -1;
        }
    }

    public boolean choose(Scanner scanner) {
        System.out.println(" Nhập vào lựa chọn của bạn: ");
        System.out.println("1. Nhập 'Y' để đồng ý:");
        System.out.println("2. Nhấn phím bất kỳ để thoát quay về menu chính");
        String input = scanner.nextLine();
        if (input.equals("Y")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void displayByPriceMax(){
        int priceMax = products.get(0).getPrice();
        int indexMax = 0;
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getPrice() > priceMax) {
                    indexMax = i;
                }
            }
            if (indexMax != 0) {
                System.out.println("Sản phẩm có giá lớn nhất là: ");
                System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n", "ID", "NAME", "PRICE (USD)", "QUANTITY", "INFORMATION");
                System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n",
                        products.get(indexMax).getId(), products.get(indexMax).getName(), products.get(indexMax).getPrice()
                        , products.get(indexMax).getQuantity(), products.get(indexMax).getInformation());
            }
            else {
                System.out.println("Sản phẩm có giá lớn nhất là: ");
                System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n", "ID", "NAME", "PRICE (USD)", "QUANTITY", "INFORMATION");
                System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n",
                        products.get(0).getId(), products.get(0).getName(), products.get(0).getPrice()
                        , products.get(0).getQuantity(), products.get(0).getInformation());
            }
        }
        else {
            System.out.println("Danh sách sản phẩm rỗng. Không tìm thấy sản phảm có giá lớn nhất");
        }
    }

    public void displayByPriceUp(Scanner scanner) {
        System.out.println("Hiển thị sản phẩm theo giá tăng dần ");
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            }
        });
        display();
    }

    public void displayFirst() {
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong danh sách");
        } else {
            System.out.printf("-----------------------------------------------------------------%n");
            System.out.printf("                             DANH SÁCH SẢN PHẨM                        %n");
            System.out.printf("-----------------------------------------------------------------%n");
            System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n", "ID", "NAME", "PRICE (USD)", "QUANTITY", "INFORMATION");
            for (int i = 0; i < products.size(); i++) {
                System.out.printf("| %-3s | %-10s | %-15s | %-10s | %-11s |%n",
                        products.get(i).getId(), products.get(i).getName(), products.get(i).getPrice()
                        , products.get(i).getQuantity(), products.get(i).getInformation());
            }
        }
    }

    public void writeCsv(ArrayList<Product> products, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (Product product : products) {
                bw.write(product.toString());
                bw.newLine();

            }
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Product> read(String path){
        ArrayList<Product> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);
            String line = "";
            while ((line=br.readLine())!=null){
                String [] txt = line.split(";");
                int id = Integer.parseInt(txt[0]);
                String name = txt[1];
                int price = Integer.parseInt(txt[2]);
                int quantity = Integer.parseInt(txt[3]);
                String information = txt[4];
                list.add(new Product(id,name,price,quantity,information));
            }
            br.close();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

}
