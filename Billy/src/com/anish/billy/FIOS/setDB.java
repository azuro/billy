package com.anish.billy.FIOS;

import java.io.File;

import com.anish.billy.Global;

public class setDB {
	public static void set(File f){
		Global.setDb(f.getAbsolutePath(), true);
	}
}
