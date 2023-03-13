package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement("select * from \"Cars\" where manufacturer=?")){
            ps.setString(1, manufacturerN);
            try(ResultSet resultSet=ps.executeQuery()){
                while(resultSet.next()){
                    Car car = new Car(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                    car.setId(resultSet.getInt(1));
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement("select * from \"Cars\" where year between ? and ?")){
            ps.setInt(1, min);
            ps.setInt(2, max);
            try(ResultSet resultSet=ps.executeQuery()){
                while(resultSet.next()){
                    Car car = new Car(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                    car.setId(resultSet.getInt(1));
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement ps = con.prepareStatement("insert into \"Cars\" (manufacturer, model, year) values (?,?,?)")){
            ps.setString(1, elem.getManufacturer());
            ps.setString(2, elem.getModel());
            ps.setInt(3, elem.getYear());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.traceEntry("update task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement ps = con.prepareStatement("update \"Cars\" set manufacturer=?, model=?, year=? where id=?")){
            ps.setString(1, elem.getManufacturer());
            ps.setString(2, elem.getModel());
            ps.setInt(3, elem.getYear());
            ps.setInt(4, elem.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement("select * from \"Cars\"")){
            try(ResultSet resultSet=ps.executeQuery()){
                while(resultSet.next()){
                    Car car = new Car(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                    car.setId(resultSet.getInt(1));
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
	    return cars;
    }
}
