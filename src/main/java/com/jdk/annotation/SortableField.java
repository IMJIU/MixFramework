package com.jdk.annotation;

import java.lang.reflect.Field;

public class SortableField {

	public SortableField() {}

	public SortableField(FieldMapper meta, Field field) {
		super();
		this.meta = meta;
		this.field = field;
		this.name = field.getName();
		this.type = field.getType();
	}

	public SortableField(FieldMapper meta, String name, Class<?> type) {
		super();
		this.meta = meta;
		this.name = name;
		this.type = type;
	}

	private FieldMapper meta;

	private Field field;

	private String name;

	private Class<?> type;

	public FieldMapper getMeta() {
		return meta;
	}
	public void setMeta(FieldMapper meta) {
		this.meta = meta;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}