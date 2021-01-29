package spring;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import java.io.File;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
 
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{
 
    @Override
    protected Class<?>[] getRootConfigClasses() 
    {
        return new Class[] { Config.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() 
    {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() 
    {
        return new String[] { "/" };
    }
}