package com.hb.authenticationcenter.banner;

import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author admin
 * @version 1.0
 * @description custom authentication center banner.
 * @date 2023/1/1
 */
public class AuthenticationCenterBanner implements Banner {
    /**
     * Banner: ac
     */
    private static final String[] BANNER = new String[]{"           --              /----| ", "         / -- \\           / ----| ", "        / /  \\ \\         / / ",
            "       / /----\\ \\        \\ \\ ", "      / /------\\ \\        \\ ----| ", "     / /        \\ \\        \\----| "};
    /**
     * authentication center
     */
    private static final String AUTHENTICATION_CENTER = " ☆ Authentication Center ☆ ";
    /**
     * authentication center version
     */
    private static final String AUTHENTICATION_CENTER_VERSION = "1.0.0";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        printStream.println();
        for (String line : BANNER) {
            printStream.println(line);
        }
        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, AUTHENTICATION_CENTER,
                AnsiColor.DEFAULT, " ", AnsiStyle.FAINT, AUTHENTICATION_CENTER_VERSION));
    }
}
