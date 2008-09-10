package org.amnh.bif.rasterProvider;
/*
** File: AsciiGridEngine.java
** Author: Peter J. Ersts (ersts@amnh.org)
** Creation Date: 2008-07-30
**
** Copyright (c) 2008 American Museum of Natural History. All rights reserved.
** 
** This library is free software; you can redistribute it and/or
** modify it under the terms of the GNU Library General Public
** License as published by the Free Software Foundation; either
** version 2 of the License, or (at your option) any later version.
** 
** This library is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Library General Public License for more details.
** 
** You should have received a copy of the GNU Library General Public
** License along with this library; if not, write to the
** Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
** MA 02110-1301, USA.
**
**/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

public class AsciiGridEngine {
	private int cvNumberOfRows;
	private int cvNumberOfColumns;
	private double cvXLLCenter;
	private double cvXLLCorner;
	private double cvYLLCenter;
	private double cvYLLCorner;
	private double cvCellSize;
	private double cvNoDataValue;
	private boolean cvValidGridHeader;
	private boolean cvCornerPoint;
	private boolean cvCenterPoint;
	private StreamTokenizer cvGridTokenizer;
		
	public AsciiGridEngine(String theFilename) 
	{
		/*
	 	* Zero out all class variables
	 	*/
		cvNumberOfRows = Integer.MIN_VALUE;
		cvNumberOfColumns = Integer.MIN_VALUE;
		cvXLLCenter = Double.MIN_VALUE;
		cvXLLCorner = Double.MIN_VALUE;
		cvYLLCenter = Double.MIN_VALUE;
		cvYLLCorner = Double.MIN_VALUE;
		cvCellSize = Double.MIN_VALUE;
		cvNoDataValue = Double.MIN_VALUE;
		cvValidGridHeader = false;
		cvCornerPoint = false;
		cvCenterPoint = false;
		
		loadGridParameters(theFilename);
		/*
		 * Check that all parameters were found. We could just bomb out here, but added the 
		 * validGridHeader for possible future expansion. 
		 */
		if(hasRequiredParameters())
		{
			cvValidGridHeader = true;
		}
		else 
		{
			System.err.println("[AsciiGridEngine:MISSING PARAMETERS] Not all required grid parameters were found or contained valid values.");
		}
	}
	
	/*
	 * Check to make sure token is a number, later this should throw an exception rather than quitting.
	 */
	private void expectNumber(int theType, String theField) 
	{
		if(theType != StreamTokenizer.TT_NUMBER) 
		{
			System.err.println("[expectNumber:FORMAT ERROR] The value of "+ theField +" is expected to be an number.");	
			System.exit(0);
		}
	}
	
	private boolean hasRequiredParameters() 
	{
		if(cvNumberOfRows <= 0)
			return false;
		else if(cvNumberOfColumns <= 0)
			return false;
		else if(cvXLLCenter == Integer.MIN_VALUE && cvXLLCorner == Integer.MIN_VALUE)
			return false;
		else if(cvYLLCenter == Integer.MIN_VALUE && cvYLLCorner == Integer.MIN_VALUE)
			return false;
		else if(cvCellSize <= 0)
			return false;
		else if(cvCornerPoint && cvCenterPoint)
			return false;
		else 
			return true;
	}
	
	/*
	 * Check to make sure token is an integer, later this should throw an exception rather than quitting.
	 */
	private void isInteger(String theField, double theNumber) 
	{
		int temp = (int) theNumber;
		if(theNumber - (double)temp != 0) 
		{
			System.err.println("[isInteger:FORMAT ERROR] The value of "+ theField +" is expected to be an integer.");	
			System.exit(0);
		}
	}
	
	/*
	 * Load the actual grid data into the provided two dimensional array, later this could return true/false rather than quitting
	 */
	public void loadGridData(double[][] theDestinationGrid)
	{
		if(!cvValidGridHeader) {
			System.err.println("[loadGridData:WARNING] Valid grid paramaters have not yet been loaded.");
			System.exit(0);
		}
		/*
		 * Check the dimensions of the grid
		 */
		if(theDestinationGrid.length != cvNumberOfRows)
		{
			System.err.println("[loadGridData:READ ERROR] The destination array does not have enough rows.");
			System.exit(0);
		}
		
		if(theDestinationGrid[0].length != cvNumberOfColumns)
		{
			System.err.println("[loadGridData:READ ERROR] The destination array does not have enough columns.");
			System.exit(0);
		}
		
		try
		{
			/*
			 * Check to make sure we are not at the end of the file, this means the grid has already been loaded
			 */
			cvGridTokenizer.nextToken();
			if(cvGridTokenizer.ttype == StreamTokenizer.TT_EOF) {
				System.err.println("[loadGridData:EOF] The input stream has already reached the end of the file.");
				System.exit(0);
			}
			else
				cvGridTokenizer.pushBack();
			
			/*
			 * Read all of the grid data
			 */
			for (int y = 0; y < cvNumberOfRows; y++)
			{
				for (int x = 0; x < cvNumberOfColumns; x++) 
				{
					cvGridTokenizer.nextToken();
					if(cvGridTokenizer.ttype == StreamTokenizer.TT_WORD) 
					{
						System.out.println("[loadGridData:READ ERROR] Non numeric characters found in grid data.");
						System.exit(0);
					}
					else if(cvGridTokenizer.ttype == StreamTokenizer.TT_WORD) 
					{
							System.out.println("[loadGridData:READ ERROR] Unexpected EOF reached.");
							System.exit(0);
					}
					if(cvGridTokenizer.nval == cvNoDataValue)
					{
						theDestinationGrid[y][x] = 0.0;
					}
					else
					{
						theDestinationGrid[y][x] = cvGridTokenizer.nval;
					}
				}
			}
		}
		catch (IOException e) 
		{
			System.err.println("[loadGridData:READ ERROR] An exception was thrown while trying to read the grid");
			System.exit(0);
		}
	}
	
	/*
	 * Load the actual grid data into the provided two dimensional array, later this could return true/false rather than quitting
	 */
	public void loadGridData(byte[][] theDestinationGrid)
	{
		if(!cvValidGridHeader) {
			System.err.println("[loadGridData:WARNING] Valid grid paramaters have not yet been loaded.");
			System.exit(0);
		}
		/*
		 * Check the dimensions of the grid
		 */
		if(theDestinationGrid.length != cvNumberOfRows)
		{
			System.err.println("[loadGridData:READ ERROR] The destination array does not have enough rows.");
			System.exit(0);
		}
		
		if(theDestinationGrid[0].length != cvNumberOfColumns)
		{
			System.err.println("[loadGridData:READ ERROR] The destination array does not have enough columns.");
			System.exit(0);
		}
		
		try
		{
			/*
			 * Check to make sure we are not at the end of the file, this means the grid has already been loaded
			 */
			cvGridTokenizer.nextToken();
			if(cvGridTokenizer.ttype == StreamTokenizer.TT_EOF) {
				System.err.println("[loadGridData:EOF] The input stream has already reached the end of the file.");
				System.exit(0);
			}
			else
				cvGridTokenizer.pushBack();
			
			/*
			 * Read all of the grid data
			 */
			for (int y = 0; y < cvNumberOfRows; y++)
			{
				for (int x = 0; x < cvNumberOfColumns; x++) 
				{
					cvGridTokenizer.nextToken();
					if(cvGridTokenizer.ttype == StreamTokenizer.TT_WORD) 
					{
						System.out.println("[loadGridData:READ ERROR] Non numeric characters found in grid data.");
						System.exit(0);
					}
					else if(cvGridTokenizer.ttype == StreamTokenizer.TT_WORD) 
					{
							System.out.println("[loadGridData:READ ERROR] Unexpected EOF reached.");
							System.exit(0);
					}
					if(cvGridTokenizer.nval == cvNoDataValue)
					{
						theDestinationGrid[y][x] = 0;
					}
					else
					{
						theDestinationGrid[y][x] = (byte)cvGridTokenizer.nval;
					}
				}
			}
		}
		catch (IOException e) 
		{
			System.err.println("[loadGridData:READ ERROR] An exception was thrown while trying to read the grid");
			System.exit(0);
		}
	}
	
	private boolean loadGridParameters(String theFilename) 
	{
		try 
		{
			/*
			 * Set up tokenizer
			 */
			cvGridTokenizer = new StreamTokenizer(new BufferedReader(new FileReader(theFilename)));
			cvGridTokenizer.wordChars(95,95); //By default '_' is not a word character, we need it for nodata_value
			cvGridTokenizer.parseNumbers();
	
			/*
			 * Get the first token in the stream parse for the ESRI ASCII Grid parameters. Order
			 * does not matter to this program. The patter expected is [String] [Number], if a number is
			 * encountered 'first', then it is suspected that we have reach the data area and we 
			 * break out of the parameter mining loop.
			 * 
			 * Later this could be put into a read header function
			 */
			cvGridTokenizer.nextToken();
			while(cvGridTokenizer.ttype != StreamTokenizer.TT_NUMBER) {
				if(cvGridTokenizer.sval.equalsIgnoreCase("ncols")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "ncols");
					isInteger("ncols", cvGridTokenizer.nval);
					cvNumberOfColumns = (int) cvGridTokenizer.nval;
				}
				else if(cvGridTokenizer.sval.equalsIgnoreCase("nrows")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "ncols");
					isInteger("ncols", cvGridTokenizer.nval);
					cvNumberOfRows = (int) cvGridTokenizer.nval;
				}
				else if(cvGridTokenizer.sval.equalsIgnoreCase("xllcorner")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "xllcorner");
					cvXLLCorner = cvGridTokenizer.nval;
					cvCornerPoint = true;
				}
				else if(cvGridTokenizer.sval.equalsIgnoreCase("xllcenter")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "xllcenter");
					cvXLLCenter = cvGridTokenizer.nval;
					cvCenterPoint = true;
				}
				else if(cvGridTokenizer.sval.equalsIgnoreCase("yllcorner")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "yllcorner");
					cvYLLCorner = cvGridTokenizer.nval;
					cvCornerPoint = true;
				}
				else if(cvGridTokenizer.sval.equalsIgnoreCase("yllcenter")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "yllcenter");
					cvYLLCenter = cvGridTokenizer.nval;
					cvCenterPoint = true;
				}
				else if(cvGridTokenizer.sval.equalsIgnoreCase("cellsize")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "cellsize");
					cvCellSize = cvGridTokenizer.nval;
				}				
				else if(cvGridTokenizer.sval.equalsIgnoreCase("nodata_value")) {
					cvGridTokenizer.nextToken();
					expectNumber(cvGridTokenizer.ttype, "nodata_value");
					cvNoDataValue = cvGridTokenizer.nval;
				}				
				else {
					System.err.println("[loadGridParameters:ERROR] Unexpected parameter "+cvGridTokenizer.sval+" encountered while initalizeing the grid.");
					return false;
				}
				cvGridTokenizer.nextToken();
			}
			/*
			 * This function is only interested in the parameters at this time so lets put back the 'grid data' 
			 */
			cvGridTokenizer.pushBack();
		}
		catch (IOException e) 
		{
			System.err.println("[loadGridParameters:READ ERROR] An exception was thrown while trying to read "+ theFilename);
			return false;
		}
		
		return true;
	}
	
	/*
	 * This is run externally to prepare a binary file
	 */
	public static void main(String[] args) 
	{
		/*
		 * Manually transform image into individual ascii grids
		 * example: gdal_translate -b 1 -of AAIGrid p013r43_5t860429_subset.tif band1.asc 
		 * example: gdal_translate -b 2 -of AAIGrid p013r43_5t860429_subset.tif band2.asc 
		 */
		
		/*
		 * Since this is a helper function just set the size by hand, could read it from the grid
		 */
		EightBitProvider EBP = new EightBitProvider(7,256,256);
		
		/*
		 * Manually load all of the bands that we exported.
		 */
		for(int lvIterator = 0; lvIterator < 7; lvIterator++)
		{
			AsciiGridEngine lvAGE = new AsciiGridEngine("/home/pete/devel/temp/band"+ (lvIterator+1) +".asc");
			lvAGE.loadGridData(EBP.cvData[lvIterator]);
		}
		EBP.write("/home/pete/devel/temp/7band_256x256_example.dat");
	}

}
