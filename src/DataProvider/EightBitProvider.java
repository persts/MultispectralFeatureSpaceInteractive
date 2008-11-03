/*
** File: EightBitProvider.java
** Author(s): Peter J. Ersts (ersts at amnh.org)
** Creation Date: 2008-08-22
** Copyright (c) 2008, American Museum of Natural History. All rights reserved.
** 
** This library/program is free software; you can redistribute it 
** and/or modify it under the terms of the GNU Library General Public
** License as published by the Free Software Foundation; either
** version 2 of the License, or (at your option) any later version.
** 
** This library/program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Library General Public License for more details.
**
** This work was made possible through the support from the 
** Center For Biodiversity and Conservation and The Spanish Ministry of 
** Science and Innovation's INTEGRANTS program.
**
**/
package DataProvider;
import java.io.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EightBitProvider {

	public byte[][][] cvData;
	
	public EightBitProvider()
	{
		cvData = null;
	}
	
	public EightBitProvider(int theNumberOfBand, int theNumberOfRows, int theNumberOfColumns)
	{
		cvData = new byte[theNumberOfBand][theNumberOfRows][theNumberOfColumns];
	}
	
	public int getInt(int theBand, int theRow, int theColumn)
	{
		if(cvData == null || theBand > cvData.length || theRow > cvData[0].length || theColumn > cvData[0][0].length)
			return -1;
		return (int)cvData[theBand][theRow][theColumn] & 0xFF;
	}
	
	/*
	 * Read from file static format
	 * int int int byte btye btye
	 * bands rows cols band1_grid_data band1_grid_data....bandn_grid_data bandn_grid_data...
	 */
	public boolean read(String theInputFile)
	{
		InputStream lvInputStream = null;
		try
		{
			lvInputStream = this.getClass().getClassLoader().getResourceAsStream(theInputFile);
			if(lvInputStream == null)
			{
			  return false;
			}
			DataInputStream lvInputDataStream = new DataInputStream(new DataInputStream(lvInputStream));
			//DataInputStream lvInputStream = new DataInputStream(new FileInputStream(theInputFile));
			int lvNumberOfBands = lvInputDataStream.readInt();
			int lvNumberOfRows = lvInputDataStream.readInt();
			int lvNumberOfColumns = lvInputDataStream.readInt();

		    if(lvNumberOfBands <= 0 || lvNumberOfRows <= 0 || lvNumberOfColumns <= 0)
		    {
		    	System.err.println("[read:IO ERROR] Band, row, and column parameters are invalid");
				return false;
		    }
			cvData = new byte[lvNumberOfBands][lvNumberOfRows][lvNumberOfColumns];

			for(int lvBandRunner = 0; lvBandRunner < cvData.length; lvBandRunner++)
			{
				for(int lvRowRunner = 0; lvRowRunner < cvData[0].length; lvRowRunner++)
				{
					for(int lvColumnRunner = 0; lvColumnRunner < cvData[0][0].length; lvColumnRunner++)
					{
						cvData[lvBandRunner][lvRowRunner][lvColumnRunner] = lvInputDataStream.readByte();
					}
				}
			}

			lvInputStream.close();
		}
		catch (IOException e) 
		{
			cvData = null;
			System.err.println("[read:IO ERROR] An exception was thrown while trying to read from "+ theInputFile);
			return false;
		}

		return true;
	}
	
	/*
	 * Write to an output file static format
	 * int int int byte btye btye
	 * bands rows cols band1_grid_data band1_grid_data....bandn_grid_data bandn_grid_data...
	 */
	public boolean write(String theOutputFile)
	{
		try
		{
			DataOutputStream lvOutputStream = new DataOutputStream(new FileOutputStream(theOutputFile));
			lvOutputStream.writeInt(cvData.length);
			lvOutputStream.writeInt(cvData[0].length);
			lvOutputStream.writeInt(cvData[0][0].length);
			for(int lvBandRunner = 0; lvBandRunner < cvData.length; lvBandRunner++)
			{
				for(int lvRowRunner = 0; lvRowRunner < cvData[0].length; lvRowRunner++)
				{
					for(int lvColumnRunner = 0; lvColumnRunner < cvData[0][0].length; lvColumnRunner++)
					{
						lvOutputStream.writeByte(cvData[lvBandRunner][lvRowRunner][lvColumnRunner]);
					}
				}
			}
			
			lvOutputStream.close();
		}
		catch (IOException e) 
		{
			System.err.println("[write:IO ERROR] An exception was thrown while trying to write to "+ theOutputFile);
			return false;
		}

		return true;
	}
	
	
	public static void main(String[] args) 
	{
		/*
		 * Test Function to see if the data are being loaded correctly
		 */
		EightBitProvider EBP = new EightBitProvider();
		EBP.read("/home/pete/devel/temp/7band_256x256_example.dat");
		System.out.println("Bands: "+ EBP.cvData.length);
		System.out.println("Rows: "+ EBP.cvData[0].length);
		System.out.println("Columns: "+ EBP.cvData[0][0].length);
		System.out.println("Band 1 Data:");
		
		/*
		 * This output should match the band1.asc data
		 */
		for(int lvRowRunner = 0; lvRowRunner < EBP.cvData[0].length; lvRowRunner++) 
		{
			for(int lvColumnRunner = 0; lvColumnRunner < EBP.cvData[0][0].length; lvColumnRunner++)
			{
				System.out.print(EBP.getInt(0,lvRowRunner,lvColumnRunner) + " ");
			}
			System.out.println();
		}
		
	}
}
