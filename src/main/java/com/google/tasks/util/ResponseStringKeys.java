package com.google.tasks.util;

public class ResponseStringKeys {
	
	public static final String NOT_FOUND = "Not found";
	public static final String MODEL_FOUND = "The model is existed";
	public static final String CREATED = "Created successfully";
	public static final String UPDATED = "Updated successfully";
	public static final String OK = "Successfully";
	public static final String DELETED = "Deleted successfully";
	// For Registration Operations
	public static final String STEP_ONE_CREATION = "Step one is done";
	public static final String  REQUEST_CREATED = "Request created";
	// Validation
	public static final String NOT_VALID_EXCEPTION = "This attribute is not valid";
	public static final String EMPTY_LIST = "Empty list";
	public static final String SORTED_LIST = "Stored list";
	public static final String UN_SORTED_LIST = "Unstored list";
	// Empty
	public static final String EMPTY = "Empty attribute";
	// product status
	public static final String PRODUCT_STATUS = "Product status and subStatus updated successfully !";
	public static final String QUANTITY_STATUS = "Quantity status and subStatus updated successfully !";
	public static final String MAX_SIZE_EXCEPTION = "Max size exception";
	public static final String SQL_EXC = "SQL exception";
	public static final String DATA_INTEGERITY_VIOLATION = "Data integrity violation exception";
	public static final String EXC = "Exception";
	public static final String ERROR = "error";
	public static final String EVENT_PUBLISHED = "Event published successfully";
	public static final String BINDING_EXCEPTION = "Binding Exception";
	
	private ResponseStringKeys() {
		throw new IllegalStateException("Utility class");
	}
}
