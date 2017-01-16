package com.test;

public class TestEnum {
	public static void main(String[] args) {
		System.out.println(PayStatus.PAID.toString());
	}
}
 enum PayStatus{

    UNPAID(0, "待支付"),
    PAID(1, "已支付");

    private Integer value;

    private String alias;

    PayStatus(Integer value, String alias) {
        this.value = value;
        this.alias = alias;
    }

    public Integer getValue() {
        return value;
    }

    public String getValueString() {
        return value.toString();
    }

    public String getAlias() {
        return alias;
    }
}

