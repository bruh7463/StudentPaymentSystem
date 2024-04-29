package com.example;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.YamlPrinter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Parser1 {
    public static void main(String[] args) throws IOException {
        YamlPrinter printer = new YamlPrinter(true);
        FileInputStream in = new FileInputStream("demo/src/main/java/com/example/Main.java");

        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
        } finally {
            in.close();
        }

        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                super.visit(n, arg);
                System.out.println("Class name: " + n.getName());
                System.out.println("AST: " + printer.output(n));
            }
        }.visit(cu, null);

        TokenRange tokenRange = cu.getTokenRange().get();
        Map<String, Integer> tokenTypes = new HashMap<>();
        int totalTokens = 0;

        for (JavaToken token : tokenRange) {
            if (!token.getCategory().equals(JavaToken.Category.WHITESPACE_NO_EOL)) {
                String tokenType = token.getCategory().name();
                tokenTypes.put(tokenType, tokenTypes.getOrDefault(tokenType, 0) + 1);
                totalTokens++;
            }
        }

        System.out.println("Total number of tokens (excluding whitespace): " + totalTokens);
        System.out.println("Types of tokens and counts:");
        for (Map.Entry<String, Integer> entry : tokenTypes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
