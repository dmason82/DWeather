package com.mason.doug.weather;

public class Utilities {
	static int fToC(int temp)
	{
		return(int)((5.0f/9.0f)*(temp-32));
	}
	static int cToF(int temp)
	{
		return (int)(((9.0f/5.0f)*(temp))+32);
	}
}
