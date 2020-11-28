package mujava;

import java.util.concurrent.ConcurrentHashMap;

public class MutantData {
	
	public ConcurrentHashMap<String,String> mutantSource = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String,byte[]> mutantClass = new ConcurrentHashMap<>();
	
	
	

}
