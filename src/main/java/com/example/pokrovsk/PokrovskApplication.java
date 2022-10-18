package com.example.pokrovsk;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class PokrovskApplication {

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

}
