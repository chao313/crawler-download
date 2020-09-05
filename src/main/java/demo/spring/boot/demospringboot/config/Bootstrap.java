package demo.spring.boot.demospringboot.config;

public enum Bootstrap {
    MY("10.202.16.136:9092"),
    DEV_WIND("10.200.126.163:9092"),
    PROD_WIND("10.200.3.34:9092"),
    HONE("192.168.0.105:9092");
    public static final String HONE_IP = "192.168.0.105:9092";

    private String ip;

    Bootstrap(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static final String allowableValues = "10.200.126.163:9092,10.202.16.136:9092,192.168.0.105:9092,10.200.3.34:9092";
}
