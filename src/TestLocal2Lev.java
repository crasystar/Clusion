//***********************************************************************************************//
// This file is to test the 2Lev construction by Cash et al. NDSS'14. 
//**********************************************************************************************


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class TestLocal2Lev {

	public static void main(String[] args) throws Exception{

		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter your password :");

		String pass	=	keyRead.readLine();

		List<byte[]> listSK	=	IEX2Lev.keyGen(256, pass, "salt/salt", 100);

		long startTime =System.nanoTime();


		BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt", true));


		System.out.println("Enter the relative path name of the folder that contains the files to make searchable");

		String pathName	=	keyRead.readLine();

		ArrayList<File> listOfFile=new ArrayList<File>();
		TextProc.listf(pathName, listOfFile); 

		TextProc.TextProc(false, pathName);

		//The two parameters depend on the size of the dataset. Change accordingly to have better search performance
		int bigBlock	=	1000;
		int smallBlock	=	100;
		int dataSize	=	10000;

		//Construction of the global multi-map
		System.out.println("\nBeginning of Global MM creation \n");

		MMGlobal	twolev	=	MMGlobal.constructEMMPar(listSK.get(0), TextExtractPar.lp1, bigBlock, smallBlock, dataSize);


		while (true){

			System.out.println("Enter the keyword to search for");
			String keyword	=	keyRead.readLine();
			byte[][] token = MMGlobal.genToken(listSK.get(0), keyword);
			System.out.println(twolev.testSI(token, twolev.getDictionary(), twolev.getArray()));
		}

	}
}
