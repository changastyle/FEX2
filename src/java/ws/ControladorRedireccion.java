package ws;

import javax.servlet.http.HttpServletRequest;
import controller.MasterController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRedireccion
{
    @RequestMapping(value = "/")
    public ModelAndView redireccionamiento()
    {
        return new ModelAndView("redireccionamiento");
    }
    
}