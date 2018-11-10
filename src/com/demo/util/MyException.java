package com.demo.util;


	
	@SuppressWarnings("serial")
	public class MyException extends RuntimeException {
		public String forCustomer;
		public String forDev;
		public Exception e;

		public MyException() {
			super();
		}

		public MyException(String message) {
			super(message);
		}

		public MyException(String forCustomer, String forDev, Exception e) {
			super(e.getMessage());
			this.forCustomer = forCustomer;
			this.forDev = forDev;
			this.e = e;
		}

	}
