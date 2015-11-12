package org.mozilla.javascript;

public class JavaArrayFactory {

	public String[] createStringArray() {
		return new String[]{"aaa", "bbb", "ccc",  "ddd"};
	}

	public char[] createCharArray() {
		return new char[]{'a', 'b', 'c',  'd'};
	}

	public int[] createIntArray() {
		return new int[]{1, 2, 3,  4};
	}

	public Object[] createObjectArray() {
		return new Object[]{new SimpleObject(1), new SimpleObject(2), new SimpleObject(3), new SimpleObject(4)};
	}
	
	
	
	static class SimpleObject {
		private int i;
		
		SimpleObject(int i) {
			this.i = i;
		}

		public String toString() {
			return "Object "+ i; 
		}
	}
	
}
