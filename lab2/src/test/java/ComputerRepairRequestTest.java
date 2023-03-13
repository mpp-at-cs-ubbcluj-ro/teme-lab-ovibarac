import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.RequestRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    
    @Test
    @DisplayName("Test add")
    public void testAdd(){
        RequestRepository repo = new RequestRepository();
        repo.add(new ComputerRepairRequest(1,"A A","Address A","072222","Asus","13/10/2020","Broken display"));
        repo.add(new ComputerRepairRequest(2,"B B","Address B","072222","Acer","10/10/2020","Faulty keyboard"));
        assertEquals("A A",repo.findById(1).getOwnerName());
        assertEquals("Address A",repo.findById(1).getOwnerAddress());
        assertEquals("072222",repo.findById(1).getPhoneNumber());
        assertEquals("Asus",repo.findById(1).getModel());
        assertEquals("13/10/2020",repo.findById(1).getDate());
        assertEquals("Broken display",repo.findById(1).getProblemDescription());
    }

    @Test
    @DisplayName("Test empty")
    public void testEmpty(){
        ComputerRepairRequest req = new ComputerRepairRequest();
        assertEquals("",req.getOwnerName());
        assertEquals("",req.getOwnerAddress());
        assertEquals("",req.getPhoneNumber());
        assertEquals("",req.getModel());
        assertEquals("",req.getDate());
        assertEquals("",req.getProblemDescription());
    }
      
}
