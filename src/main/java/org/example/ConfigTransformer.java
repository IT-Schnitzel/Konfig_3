package org.example;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class ConfigTransformer {
    public static void main(String[] args) throws Exception {
        // Задаем путь к XML файлу вручную
        String filePath = "D:\\Универ\\2_курс\\НиАСПО\\konfig3\\second.xml"; // Укажите свой путь

        // Создаем объект для парсинга XML
        File inputFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputFile);

        // Нормализуем XML, чтобы избавиться от лишних пробелов
        doc.getDocumentElement().normalize();

        // Печать комментариев
        System.out.println("C Это конфигурация сервера");

        // Преобразование данных: ищем словари (dictionary)
        NodeList dictionaries = doc.getElementsByTagName("dictionary");
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

        // Преобразование констант
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
