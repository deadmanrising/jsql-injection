package spring;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {

    private static final String template = "Hello, s!";
    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper objectMapper = new ObjectMapper();
    protected static final Logger LOGGER = Logger.getRootLogger();
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public static class Customer {
        private int custId;
        private String firstname;
        private String lastname;
        private int age;
        
        
        
        public Customer() {
            // TODO Auto-generated constructor stub
        }
        public Customer(int custId, String firstname, String lastname, int age) {
            this.custId = custId;
            this.firstname = firstname;
            this.lastname = lastname;
            this.age = age;
        }
        public int getCustId() {
            return custId;
        }
        public void setCustId(int custId) {
            this.custId = custId;
        }
        public String getFirstname() {
            return firstname;
        }
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        public String getLastname() {
            return lastname;
        }
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        @Override
        public String toString() {
            return "Customer [custId=" + custId + ", firstname=" + firstname + ", lastname=" + lastname + ", age=" + age
                    + "]";
        }
        
        
    }
    
    @PostMapping(path = "/greeting-request", consumes = MediaType.ALL_VALUE)
    public Greeting greetingRequest(@RequestBody Customer customer) throws IOException {
//        request.getParameterMap().forEach((key, value) -> System.out.println(key + Arrays.asList(value).stream().collect(Collectors.joining())));
//        Session session = this.sessionFactory.getCurrentSession();
//        Query q = session.createNativeQuery("select First_Name from Student where '1' = '"+name+"'");
//        
        System.out.println(customer);
//        System.out.println(content);
        return null;
//        name2.getParameterMap().forEach((key, value) -> System.out.println(key + Arrays.asList(value).stream().collect(Collectors.joining())));
//        body.forEach((key, value) -> System.out.println(key + value));
        
//        Greeting greeting = null;
//        try {
//            List<Object[]> results = q.getResultList();
//            
//            greeting = new Greeting(
//                this.counter.incrementAndGet(),
//                String.format(template, name)
//                + StringEscapeUtils.unescapeJava(this.objectMapper.writeValueAsString(results))
//            );
//        } catch (Exception e) {
//            // Hide useless SQL error messages
//        } finally {
//            session.close();
//        }
//        
//        return greeting;
    }
    
    @RequestMapping("/greeting-header")
    public Greeting greetingCookie(@RequestHeader(value="name", defaultValue="World") String name) throws IOException {
        
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createNativeQuery("select First_Name from Student where '1' = '"+name+"'");
        
        Greeting greeting = null;
        try {
            List<Object[]> results = q.getResultList();
            
            greeting = new Greeting(
                this.counter.incrementAndGet(),
                String.format(template, name)
                + StringEscapeUtils.unescapeJava(this.objectMapper.writeValueAsString(results))
            );
        } catch (Exception e) {
            // Hide useless SQL error messages
        } finally {
            session.close();
        }
        
        return greeting;
    }
    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) throws IOException {
        
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createNativeQuery("select First_Name from Student where '1' = '"+name+"'");
        
        Greeting greeting = null;
        try {
            List<Object[]> results = q.getResultList();
            
            greeting = new Greeting(
                this.counter.incrementAndGet(),
                String.format(template, name)
                + StringEscapeUtils.unescapeJava(this.objectMapper.writeValueAsString(results))
            );
        } catch (Exception e) {
            // Hide useless SQL error messages
        } finally {
            session.close();
        }
        
        return greeting;
    }

    @RequestMapping("/greeting-error")
    public Greeting greetingError(@RequestParam(value="name", defaultValue="World") String name) throws IOException {
        
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createNativeQuery("select First_Name from Student where '1' = '"+name+"'");
        
        Greeting greeting = null;
        try {
            q.getResultList();
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            
            LOGGER.debug(stacktrace);
            
            greeting = new Greeting(
                this.counter.incrementAndGet(),
                String.format(template+"#", name)
                + StringEscapeUtils.unescapeJava(stacktrace)
            );
        } finally {
            session.close();
        }
        
        return greeting;
    }

    @RequestMapping("/greeting-blind")
    public Greeting greetingBlind(@RequestParam(value="name", defaultValue="World") String name) throws IOException {
        
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createNativeQuery("select First_Name from Student where '1' = '"+name+"'");
        
        Greeting greeting = null;
        try {
            List<Object[]> l = q.getResultList();
            
            if (l.isEmpty()) {
                greeting = new Greeting(
                    this.counter.incrementAndGet(),
                    String.format(template+"#", name)
                    + StringEscapeUtils.unescapeJava("PREFIX It's true SUFFIX")
                );
            } else {
                greeting = new Greeting(
                    this.counter.incrementAndGet(),
                    String.format(template+"#", name)
                    + StringEscapeUtils.unescapeJava("PREFIX It's false SUFFIX")
                );
            }
        } catch (Exception e) {
            // Hide useless SQL error messages
        } finally {
            session.close();
        }
        
        return greeting;
    }

    @RequestMapping("/greeting-time")
    public Greeting greetingTime(@RequestParam(value="name", defaultValue="World") String name) throws IOException {
        
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createNativeQuery("select First_Name from Student where '1' = '"+name+"'");
        
        Greeting greeting = null;
        try {
            q.getResultList();
        } catch (Exception e) {
            // Hide useless SQL error messages
        } finally {
            session.close();
        }
        
        return greeting;
    }

}