package com.github.davstromb.shell4j.execute;

import com.github.davstromb.shell4j.execute.print.Printer;
import com.github.davstromb.shell4j.model.JavaCode;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaExecutor implements Executor {


    private static final String JAVA_BASE_TXT = "/JavaBase.txt";
    private static final String JAVA_BASE_FILE = "/com/github/dastromb/shell4j/execute/code/Code.java";
    private StringBuilder cache;

    public JavaExecutor() {
        try {
            this.cache = new StringBuilder(new String(
                    Files.readAllBytes(
                            Paths.get(getClass().getResource(JAVA_BASE_TXT).toURI())))
            );
        } catch(Exception e) {
            throw new ExecutionException("Can not read java base code lol", e);
        }

    }

    public Executor clean() {
        this.cache = new StringBuilder();
        return this;
    }

    public Executor append(JavaCode code) {
        this.cache.append(code.toString());
        return this;
    }

    public String execute() {
        try {

            cache = new StringBuilder("import java.util.*;\n" +
                    "\n" +
                    "public class Code {\n" +
                    "\n" +
                    "    public static void main(String[] args) {\n" +
                    "            System.out.println(\"Mega Arne smek\");\n" +
                    "    }\n" +
                    "}");
            DynamicCompiler.create().run(cache.toString());




        } catch (Exception e) {
            throw new ExecutionException("Could not write code to file lol", e);
        }
        return "";
    }

    private void deleteFile() throws IOException {
//        Files.delete(Paths.get(JAVA_BASE_FILE));
    }

    public Executor print() {
        Printer.create().save(cache);
        return this;
    }

    @Override
    public String getCode() {
        return cache.toString();
    }

    public static Executor create() {
        return new JavaExecutor();
    }

    public static void main(String[] args) {
        String code = JavaExecutor.create().execute();

    }
}
