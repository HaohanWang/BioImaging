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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
}
