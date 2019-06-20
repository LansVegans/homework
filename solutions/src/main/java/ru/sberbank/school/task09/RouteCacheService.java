package ru.sberbank.school.task09;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.NonNull;
import ru.sberbank.school.task09.kryo.CitySerializer;
import ru.sberbank.school.task09.kryo.RouteSerializer;
import ru.sberbank.school.util.Solution;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@Solution(9)
public class RouteCacheService extends RouteService<City, Route<City>> {

    private Kryo kryo;

    public RouteCacheService(@NonNull String path) {
        super(path);
        initialize();
    }


    public void initialize() {
        this.kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(Route.class, new RouteSerializer());
        kryo.register(City.class, new CitySerializer());
        kryo.register(ArrayList.class);
        kryo.register(LinkedList.class);
        kryo.register(LocalDate.class);
    }


    public void saveRoute(String key, Route route) {
        try (OutputStream outputStream = new FileOutputStream(path + File.separator + key);
             Output output = new Output(outputStream)) {
            kryo.writeObject(output, route);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public Route<City> loadRoutes(String key) {
        Route routes = null;
        try (InputStream inputStream = new FileInputStream(path + File.separator + key);
             Input input = new Input(inputStream)) {
            routes = kryo.readObjectOrNull(input, Route.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return routes;

    }

    @Override
    public Route<City> getRoute(String from, String to) {
        String key = from + "_" + to;

        File file = new File(path + File.separator + key);
        System.out.println(file.getPath());
        Route<City> route;

        if (!file.exists()) {
            route = super.getRouteInner(from, to);

            saveRoute(key, route);
            System.out.println("Serialized: " + key);
            System.out.println("Route: " + route.toString());
            return route;
        } else {
            System.out.println("Deserialized: " + key);
            route = loadRoutes(key);
            System.out.println("Route: " + route.toString());
            return route;
        }

    }

    @Override
    protected City createCity(int id, String cityName, LocalDate foundDate, long numberOfInhabitants) {
        return new City(id, cityName, foundDate, numberOfInhabitants);
    }

    @Override
    protected Route<City> createRoute(List<City> cities) {
        return new Route<>(UUID.randomUUID().toString(), cities);
    }
}
