package extra;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.util.Date;

public class DateJsonParser extends TypeAdapter<Date> 
{
    public void write(JsonWriter out, Date value)
    {
        try
        {
            if (value == null)
                out.nullValue();
            else
                out.value(value.getTime() / 1000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Date read(JsonReader in) 
    {
        Date date = null;
        try
        {
            if (in != null)
                date =  new Date(in.nextLong() * 1000);
            else
                date = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return date;
    }
}
