package com.jewel.spring.tag.holder;
public class BeanNameHolder {
	
	public static String  beanNameResolve(String interfaceName){
		String name;
		try {
			name = Class.forName(interfaceName).getSimpleName();
			byte[] items = name.getBytes();  
			items[0] = (byte)((char)items[0] - ( 'A' - 'a'));  
			String newStr = new String(items);  
			return newStr;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static void main(String[] args){
		System.out.println(beanNameResolve("com.jewel.test.Demo"));
	}
}
