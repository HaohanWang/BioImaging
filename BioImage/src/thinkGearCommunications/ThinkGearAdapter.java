package thinkGearCommunications;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import objectModel.SignalNode;

public class ThinkGearAdapter {
	private static ThinkGearAdapter instance;
	private Class<?> thinkGear;
	private Class<?>[] nullClass;
	private Object[] nullObject;
	private Constructor<?> constructor;
	private Object tgInstance;
	private Method getNodeMethod;
	
	private ThinkGearAdapter(){
		try {
			thinkGear = Class.forName("ThinkGear");
			nullClass = new Class<?>[]{};
			nullObject = new Object[] {};
			constructor = thinkGear.getConstructor(nullClass);
			tgInstance = constructor.newInstance(nullObject);
			getNodeMethod = thinkGear.getMethod("getSignalNode", nullClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static ThinkGearAdapter getInstance(){
		if (instance == null)
			instance = new ThinkGearAdapter();
		return instance;
	}
	
	
	public SignalNode getSignalNode(){
		SignalNode node = null;
		try {
			node = (SignalNode) getNodeMethod.invoke(tgInstance);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return node;
	}
	
	public void closeConnection(){
		
	}
	
	public static void main(String[] args) {
		ThinkGearAdapter tg = ThinkGearAdapter.getInstance();
		while (true) {
			System.out.println(tg.getSignalNode().getConcentration());
		}
	}
}
