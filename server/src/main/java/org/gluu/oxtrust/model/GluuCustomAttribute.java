/*
 * oxTrust is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.gluu.oxtrust.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import lombok.Data;

import org.xdi.model.GluuAttribute;

import com.unboundid.util.StaticUtils;

/**
 * Attribute
 * 
 * @author Yuriy Movchan Date: 10.07.2010
 */
public @Data class GluuCustomAttribute implements Serializable, Comparable<GluuCustomAttribute> {

	private static final long serialVersionUID = 1468440094325406153L;

	private String name;
	private String[] values;

	private transient GluuAttribute metadata;

	private transient boolean newAttribute = false;

	private transient boolean mandatory = false;
	
	private transient boolean readonly = false;

	public GluuCustomAttribute() {
	}

	public GluuCustomAttribute(String name, String value) {
		this.name = name;
		setValue(value);
	}

	public GluuCustomAttribute(String name, String value, boolean newAttribute) {
		this.name = name;
		setValue(value);
		this.newAttribute = newAttribute;
	}

	public GluuCustomAttribute(String name, String value, boolean newAttribute, boolean mandatory) {
		this.name = name;
		setValue(value);
		this.newAttribute = newAttribute;
		this.mandatory = mandatory;
	}

	public GluuCustomAttribute(String name, String[] values) {
		this.name = name;
		this.values = values;
	}

	// To avoid extra code in CR interceptor script
	public GluuCustomAttribute(String name, Set<String> values) {
		this.name = name;
		this.values = values.toArray(new String[0]);;
	}

	public String getValue() {
		if (this.values == null) {
			return null;
		}

		if (this.values.length > 0) {
			return this.values[0];
		}

		return null;
	}

	public void setValue(String value) {
		if (this.values == null) {
			this.values = new String[0];
		}

		if (this.values.length != 1) {
			this.values = new String[1];
		}
		this.values[0] = value;
	}

	public Date getDate() {
		if (this.values == null) {
			return null;
		}

		if (this.values.length > 0 && values[0] != null) {
			try {
				return StaticUtils.decodeGeneralizedTime((String) values[0]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public void setDate(Date date) {
		if (this.values == null) {
			this.values = new String[0];
		}

		if (this.values.length != 1) {
			this.values = new String[1];
		}
		this.values[0] = StaticUtils.encodeGeneralizedTime(date);
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public void setValues(Collection<String> values) {
		this.values = values.toArray(new String[0]);
	}

	// To avoid extra code in CR interceptor script
	public void setValues(Set<String> values) {
		this.values = values.toArray(new String[0]);
	}



	public boolean isNew() {
		return newAttribute;
	}

	public void setNew(boolean newAttribute) {
		this.newAttribute = newAttribute;
	}


	public String getDisplayValue() {
		if (values == null) {
			return "";
		}

		if (values.length == 1) {
			return values[0];
		}

		StringBuilder sb = new StringBuilder(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(", ").append(values[i]);
		}

		return sb.toString();
	}

	public boolean isAdminCanAccess() {
		return (this.metadata != null) && this.metadata.isAdminCanAccess();
	}

	public boolean isAdminCanView() {
		return (this.metadata != null) && this.metadata.isAdminCanView();
	}

	public boolean isAdminCanEdit() {
		return (this.metadata != null) && this.metadata.isAdminCanEdit();
	}

	public boolean isUserCanAccess() {
		return (this.metadata != null) && this.metadata.isUserCanAccess();
	}

	public boolean isUserCanView() {
		return (this.metadata != null) && this.metadata.isUserCanView();
	}

	public boolean isUserCanEdit() {
		return (this.metadata != null) && this.metadata.isUserCanEdit();
	}

	// public boolean equals(Object attribute) {
	// return (attribute instanceof GluuCustomAttribute) &&
	// (((GluuCustomAttribute) attribute).getName().equals(getName()));
	// }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GluuCustomAttribute that = (GluuCustomAttribute) o;

		return !(name != null ? !name.equalsIgnoreCase(that.name) : that.name != null);

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return String.format("Attribute [name=%s, values=%s, metadata=%s]", name, Arrays.toString(values), metadata);
	}

	public int compareTo(GluuCustomAttribute o) {
		return name.compareTo(o.name);
	}

}