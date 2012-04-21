package com.firebase.jfirebase;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: reiz
 * Date: 4/21/12
 * Time: 12:37 PM
 */
public class DriverTest {

    @Test
    public void doWrite(){
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "rob");
        data.put("user", "robi");
        IDriver driver = new Driver();
        boolean responseCode = driver.write(data);
        assert responseCode;
    }

    @Test
    public void doRead() {
        IDriver driver = new Driver();
        Reader reader = driver.read("-IQLhiRv3cUHmZP9KR29");
        try{
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(reader, User.class);
            System.out.println(user.getName());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("END");
    }

    @Test
    public void doDelete() {
        IDriver driver = new Driver();
        boolean deleted = driver.delete("-IQLhiRv3cUHmZP9KR29");
        assert deleted;
    }

}
