package demo.spring.boot.demospringboot.vo;

public enum LanguageType {

    JAVA("java"), PHP("php"), ASP("asp");

    private String type;

    LanguageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static LanguageType getLanguageTypeByType(String type) {
        for (LanguageType vo : LanguageType.values()) {
            if (vo.getType().equals(type)) {
                return vo;
            }
        }
        return null;
    }
}
