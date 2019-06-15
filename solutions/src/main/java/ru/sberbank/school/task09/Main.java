package ru.sberbank.school.task09;

public class Main {
    public static void main(String[] args) {
        RouteCacheService routeCacheService = new RouteCacheService();

        routeCacheService.initialize();
        System.out.println(routeCacheService.getRoute("Moscow","London").toString());
        System.out.println(routeCacheService.getRoute("Moscow","London").toString());
        System.out.println(routeCacheService.getRoute("Rome","London").toString());
        System.out.println(routeCacheService.getRoute("Rome","London").toString());

    }
}
