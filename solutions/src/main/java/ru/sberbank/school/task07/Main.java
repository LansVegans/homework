package ru.sberbank.school.task07;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        CollectionsServiceFactory factory = new CollectionsServiceFactoryImpl();

        System.out.println( factory.getDifferentWords(factory.getFileParser()).findSortedDifferentWords("D:\\json.txt"));
    }
}
