package vip.efactory.ejpa.base.enums;

import lombok.Getter;

/**
 * Description: 实体属性的查询条件,例如:age ge 35
 *
 * @author dbdu
 * Created at:2019-03-09 16:06,
 */
@Getter
public enum SearchTypeEnum {
    FUZZY(0, "模糊查询"),
    EQ(1, "等于查询"),
    RANGE(2, "范围查询"),
    NE(3, "不等于查询"),
    LT(4, "小于查询"),
    LE(5, "小于等于查询"),
    GT(6, "大于查询"),
    GE(7, "大于等于查询"),
    IS_NULL(8, "Null值查询"),       // 3.0+
    NOT_NULL(9, "非Null值查询"),    // 3.0+
    LEFT_LIKE(10, "左模糊查询"),    // 3.0+
    RIGHT_LIKE(11, "右模糊查询"),   // 3.0+
    IN(12, "包含查询"),             // 3.4+ ,内置暂不支持not in查询！除非手写hql或sql实现
    ;

    // 枚举值
    private int value;

    //枚举说明
    private String desc;

    SearchTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SearchTypeEnum getByValue(int value) {
        switch (value) {
            case 1:
                return EQ;
            case 2:
                return RANGE;
            case 3:
                return NE;
            case 4:
                return LT;
            case 5:
                return LE;
            case 6:
                return GT;
            case 7:
                return GE;
            default:
                // 0 或其他情况,则为模糊查询
                return FUZZY;
        }


    }

}
