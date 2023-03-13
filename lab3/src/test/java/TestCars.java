import org.example.Car;
import org.example.CarRepository;
import org.example.CarsDBRepository;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class TestCars {
    @Test
    public void testAdd(){
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CarRepository carRepo=new CarsDBRepository(props);
        Car c = new Car("Tesla","Model S", 2019);
        carRepo.add(c);

        assert(StreamSupport.stream(carRepo.findAll().spliterator(), false).anyMatch(c::equals));

    }

    @Test
    public void testUpdate(){
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CarRepository carRepo=new CarsDBRepository(props);
        Car c = new Car("Tesla","Model S", 2019);
        carRepo.add(c);
        Car newC = new Car("Tesla","Model S", 2020);
        carRepo.update(c.getId(), newC);

        assert(StreamSupport.stream(carRepo.findAll().spliterator(), false).anyMatch(newC::equals));
    }

    @Test
    public void testAllCond(){
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        List<Car> list = new ArrayList<>();

        CarRepository carRepo=new CarsDBRepository(props);
        Car c = new Car("Tesla","Model S", 2019);
        carRepo.add(c);
        Car c2 = new Car("Tesla","Model B", 2019);
        carRepo.add(c);
        Car c3 = new Car("Dacia","Model B", 2020);
        carRepo.add(c);
        list.add(c);
        list.add(c2);

        assert(carRepo.findByManufacturer("Tesla")==list);
        assert(carRepo.findBetweenYears(2019,2019)==list);
    }
}
