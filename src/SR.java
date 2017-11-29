/**
 * Created by segeve on 11/2/2015.
 */

public class SR {
    public int srid=0;
    public int obport=0;
    public int cpdeep=1;
    public int sqlid=0;
    public int excid=0;
    public int cpMethodTime=5;
    public int cpEndcpMethodTime=100;
    public SR()
    {
    }
    public SR(String toBparsed)
    {
        String[] params = toBparsed.split(",");
        if(params != null && params.length > 6)
        {

            srid = Integer.valueOf(params[0]);
            cpdeep = Integer.valueOf(params[1]);
            cpMethodTime = Integer.valueOf(params[2]);
            cpEndcpMethodTime = Integer.valueOf(params[3]);
            sqlid = Integer.valueOf(params[4]);
            excid = Integer.valueOf(params[5]);
            obport = Integer.valueOf(params[6]);

        }
    }
    public String Debug()
    {
        return "sr id = "+srid +" cpdeep" + cpdeep+" cpMethodTime" + cpMethodTime+" cpEndcpMethodTime" + cpEndcpMethodTime+" sqlid" + sqlid+" obport" + obport;
    }


}
