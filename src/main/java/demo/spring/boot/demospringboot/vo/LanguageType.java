package demo.spring.boot.demospringboot.vo;

import demo.spring.boot.demospringboot.framework.exception.catcher.TypeInterruptException;

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


    public static void check(String fileName, LanguageType checkLanguageType) {
        if (null != checkLanguageType) {
            for (LanguageType vo : LanguageType.values()) {
                if (fileName.endsWith(vo.getType())) {
                    if (!vo.equals(checkLanguageType)) {
                        throw new TypeInterruptException("不是期望类型,期望类型是:" + checkLanguageType.getType() + "，实际类型是:" + vo.getType());
                    }
                }
            }
        }
    }
}
