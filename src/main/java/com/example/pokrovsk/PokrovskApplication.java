package com.example.pokrovsk;

import com.example.pokrovsk.entity.Product;
import com.example.pokrovsk.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.time.LocalDateTime;

@SpringBootApplication

public class PokrovskApplication implements CommandLineRunner {

    private final ProductRepo productRepo;

    public PokrovskApplication(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PokrovskApplication.class);
        app.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.print("\n\n\n    /$$$$$      /$$$$$$       /$$$$$$  /$$      /$$\n" +
                        "   |__  $$     /$$__  $$     /$$__  $$| $$  /$ | $$\n" +
                        "      | $$    | $$  \\__/    | $$  \\__/| $$ /$$$| $$\n" +
                        "      | $$    |  $$$$$$     |  $$$$$$ | $$/$$ $$ $$\n" +
                        " /$$  | $$     \\____  $$     \\____  $$| $$$$_  $$$$\n" +
                        "| $$  | $$     /$$  \\ $$     /$$  \\ $$| $$$/ \\  $$$\n" +
                        "|  $$$$$$/ /$$|  $$$$$$/ /$$|  $$$$$$/| $$/   \\  $$\n" +
                        " \\______/ |__/ \\______/ |__/ \\______/ |__/     \\__/\n\n\n");
            }
        });
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!productRepo.findAll().isEmpty()) return;
        this.productRepo.save(new Product("Яблоко Голд", 5, "Магазин", 99.9));
        this.productRepo.save(new Product("Тыква среднеплодная", 5, "Магазин", 54.5));
        this.productRepo.save(new Product("Бананы", 5, "Магазин", 76.9));
        this.productRepo.save(new Product("Цемент", 5, "Строительный", 349.9));
        this.productRepo.save(new Product("Средство для мойки полов", 5, "Химия", 120.0));
    }

    String sql = "SELECT * FROM product WHERE name LIKE '%яблоко%'";

}
