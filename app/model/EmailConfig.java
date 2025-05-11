package model;

public class EmailConfig {

    private String host;
    private Integer port;
    private String usrName;
    private String pswrd;

    public EmailConfig(String host, Integer port, String userName, String pswrd){
        this.host = host;
        this.port = port;
        this.usrName = usrName;
        this.pswrd = pswrd;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsrName() {
        return usrName;
    }

    public String getPswrd() {
        return pswrd;
    }


}
