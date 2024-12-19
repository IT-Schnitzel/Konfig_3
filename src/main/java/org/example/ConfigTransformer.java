package org.example;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Scanner;

import static gen.lib.cgraph.scan__c.input;

public class ConfigTransformer {
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\Универ\\2_курс\\НиАСПО\\konfig3\\first.xml";
        if (args.length > 0) {
            filePath = args[0]; // Если путь передан в командной строке, используем его
        } else {
            // Если нет, просим пользователя ввести путь к файлу вручную
            System.out.println("Введите путь к XML файлу:");
            try (Scanner scanner = new Scanner(System.in)) {
                filePath = scanner.nextLine();
            }
        }
        File inputFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        doc.getDocumentElement().normalize();

        NodeList dictionaries = doc.getElementsByTagName("dictionary");
        String configType = "Неизвестная конфигурация";
        for (int i = 0; i < dictionaries.getLength(); i++) {
            Node dictionary = dictionaries.item(i);
            NodeList entries = dictionary.getChildNodes();
            for (int j = 0; j < entries.getLength(); j++) {
                Node entry = entries.item(j);
                if (entry.getNodeType() == Node.ELEMENT_NODE) {
                    String name = null;
                    NodeList children = entry.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeName().equals("name")) {
                            name = child.getTextContent();
                        }
                    }
                    if (name != null && name.contains("database")) {
                        configType = "C Это конфигурация базы данных";
                        break;
                    }
                    if (name != null && name.contains("server")) {
                        configType = "C Это конфигурация веб-сервера";
                        break;
                    }
                }
            }
        }

        System.out.println(configType);

        if (dictionaries.getLength() > 0) {
            Node dictionary = dictionaries.item(0);
            NodeList entries = dictionary.getChildNodes();
            System.out.println("[");
            for (int i = 0; i < entries.getLength(); i++) {
                Node entry = entries.item(i);
                if (entry.getNodeType() == Node.ELEMENT_NODE) {
                    String name = null;
                    String value = null;
                    NodeList children = entry.getChildNodes();
                    for (int j = 0; j < children.getLength(); j++) {
                        Node child = children.item(j);
                        if (child.getNodeName().equals("name")) {
                            name = child.getTextContent();
                        } else if (child.getNodeName().equals("value")) {
                            value = child.getTextContent();
                        }
                    }
                    if (name != null && value != null) {
                        System.out.println(name + " => " + value + ",");
                    }
                }
            }
            System.out.println("]");
        }

        NodeList constants = doc.getElementsByTagName("constant");
        for (int i = 0; i < constants.getLength(); i++) {
            Node constant = constants.item(i);
            String name = null;
            String value = null;
            NodeList children = constant.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node child = children.item(j);
                if (child.getNodeName().equals("name")) {
                    name = child.getTextContent();
                } else if (child.getNodeName().equals("value")) {
                    value = child.getTextContent();
                }
            }
            if (name != null && value != null) {
                System.out.println(name + " := " + value + ";");
            }
        }
    }
}
