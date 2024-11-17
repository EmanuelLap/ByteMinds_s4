package com.byteminds.utils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;



public class ExceptionsTools {

	public static Throwable getCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;

		while (null != (cause = result.getCause()) && (result != cause)) {
			result = cause;
			if (result instanceof ConstraintViolationException|| 
		            result instanceof SQLIntegrityConstraintViolationException) {
				return result;
			}
		}
		return result;
	}

public static String formatedMsg(Throwable ex) {
		
		return "["+ex.getClass().getSimpleName()+"] "+ex.getLocalizedMessage();
	}
}
