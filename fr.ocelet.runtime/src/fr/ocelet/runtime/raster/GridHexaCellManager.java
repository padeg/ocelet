/*
*  Ocelet spatial modelling language.   www.ocelet.org
*  Copyright Cirad 2010-2018
*
*  This software is a domain specific programming language dedicated to writing
*  spatially explicit models and performing spatial dynamics simulations.
*
*  This software is governed by the CeCILL license under French law and
*  abiding by the rules of distribution of free software.  You can  use,
*  modify and/ or redistribute the software under the terms of the CeCILL
*  license as circulated by CEA, CNRS and INRIA at the following URL
*  "http://www.cecill.info".
*  As a counterpart to the access to the source code and  rights to copy,
*  modify and redistribute granted by the license, users are provided only
*  with a limited warranty  and the software's author,  the holder of the
*  economic rights,  and the successive licensors  have only limited
*  liability.
*  The fact that you are presently reading this means that you have had
*  knowledge of the CeCILL license and that you accept its terms.
*/
package fr.ocelet.runtime.raster;

import fr.ocelet.runtime.ocltypes.List;
import fr.ocelet.runtime.relation.CellValues;

/**
 * @author Mathieu Castets - Initial contribution
 */
public class GridHexaCellManager extends GridCellManager {

	

	  private CellValues firstLine[];
	  private CellValues midLine[];
	    private CellValues nextLine[];
	  
	  
	
  public GridHexaCellManager(Grid grid){
  
  	super(grid);
   
     
  }
  @Override
  public void reset(){
  
	  validateAll();
     
      y = 0;
      for(int i = 0; i < grid.getWidth(); i++){
      	
      	for(String name : properties){
      		midLine[i].clear(name);
      		nextLine[i].clear(name);
      		firstLine[i].clear(name);
      	}
      }
   

  }

  @Override
  public void init(){
  
      firstLine = new CellValues[grid.getWidth()];
      midLine = new CellValues[grid.getWidth()];
      nextLine = new CellValues[grid.getWidth()];
      for(int i = 0; i < grid.getWidth(); i++){
      
    	  midLine[i] = new CellValues();
          firstLine[i] = new CellValues();
          nextLine[i] = new CellValues();
          for(String name : properties){
          	nextLine[i].set(name);
          	midLine[i].set(name);
          	firstLine[i].set(name);
          }
         

      }

  }

  @Override
  public void increment(){
	  
	  //System.out.println(" increment "+y);
	  if(y > 0)
      validate();
	  
      CellValues temp[] = firstLine;
      firstLine = midLine;
      midLine = nextLine;
      nextLine = temp;
     /* for(int i = 0; i < grid.getWidth(); i++){
      
      	for(String name : properties){
      		nextLine[i].clear(name);
      	}       
      }   */
      y++;
  }
  
  

  @Override
  public CellValues get(int x, int y){
	  
      if(y + 1== this.y){
    	//  System.out.println("FIRST ");
          return firstLine[x];
      }
      if(y == this.y){
    	//  System.out.println("MID ");
          return midLine[x];
      }
      if(y - 1 == this.y){
    	//  System.out.println("NEXT ");
          return nextLine[x];
      }
      
          return null;
  }


  @Override
  public void add(int x, int y, String name, Double value){
	  
	   /* if(y + 1== this.y){
	      }
	      if(y == this.y){
	      }
	      if(y - 1 == this.y){
	         
	      }*/
      get(x, y).add(name, value);
     
  }

  @Override
  public CellValues[] getValues(){
  
      return nextLine;
  }


 public void validateAll(){
	 
//	 System.out.println("VALIDATE ALL "+this.y);
	   for(int i = 0; i < firstLine.length; i++){
		      
	        //  CellValues cv = firstLine[i];
	          CellValues cv2 = firstLine[i];
	          CellValues cv3 = midLine[i];
	          
	          for(String name : properties){
	              
	              if(aggregMap.containsKey(name)){
	              
	            	   CellAggregOperator cao = aggregMap.get(name);
	               /*   if(!cv.getValues(name).isEmpty()){	                	 
	              
	                      grid.setCellValue(name, i, y - 1, aggregMap.get(name).apply(cv.getValues(name), 0.0));	                    
	                  }*/

	                  if(!cv2.getValues(name).isEmpty()){	  
	                /*	  System.out.println(i+" "+(y - 1));
	              System.out.println(" CV2 "+cv2);*/
	                	  
	                	  
	                	  
	                	  if(cao.preval() == false){
	                		 grid.setCellValue(name, i, y - 1, cao.apply(cv2.getValues(name), grid.getDoubleValue(name, i, y - 1)));	                    
  
	                	  }else{
	                		  
	                		  cv2.getValues(name).add(grid.getDoubleValue(name, i, y - 1));
	                		  grid.setCellValue(name, i, y - 1, cao.apply(cv2.getValues(name), grid.getDoubleValue(name, i, y - 1)));	                    

	                	  }
	                	  
	                	  
	                    //  grid.setCellValue(name, i, y - 1, aggregMap.get(name).apply(cv2.getValues(name), 0.0));	                    
	                  }

	                  if(!cv3.getValues(name).isEmpty()){
	                	/*  System.out.println(i+" "+(y ));
	                	  System.out.println(" CV2 "+cv3);*/
	                	  if(cao.preval() == false){
	                		 grid.setCellValue(name, i, y, cao.apply(cv3.getValues(name), grid.getDoubleValue(name, i, y)));	                    
  
	                	  }else{
	                		  cv2.getValues(name).add(grid.getDoubleValue(name, i, y));
	                		  grid.setCellValue(name, i, y, cao.apply(cv3.getValues(name), grid.getDoubleValue(name, i, y)));	                    

	                	  }
	                     // grid.setCellValue(name, i, y, aggregMap.get(name).apply(cv3.getValues(name), 0.0));	                    
	                  }
	                  
	              } else {
	            	 /* if(!cv.getValues(name).isEmpty()){
	                  grid.setCellValue(name, i, y- 1, (Double)cv.getValues(name).get((int)(Math.random() * (double)cv.getValues(name).size())));
	                  
	              }*/
	            	  if(!cv2.getValues(name).isEmpty()){
		                  grid.setCellValue(name, i, y - 1, cv2.getValues(name).get((int)(Math.random() * cv2.getValues(name).size())));
		                  
		              }
	            	  if(!cv3.getValues(name).isEmpty()){
		                  grid.setCellValue(name, i, y , cv3.getValues(name).get((int)(Math.random() * cv3.getValues(name).size())));
		                  
	              }
	          }
	              cv2.clear(name);
	              cv3.clear(name);
          }
	 }
 }
  
@Override
  public void validate(){
  
	//System.out.println("VALIDATE "+(y-1));
      for(int i = 0; i < firstLine.length; i++){
      //System.out.println(i+" "+(y - 1));
          CellValues cv = firstLine[i];
          
          for(String name : properties){
              
              if(aggregMap.containsKey(name)){
            	 
            	  CellAggregOperator cao = aggregMap.get(name);
                  if(!cv.getValues(name).isEmpty()){
                	  
                 Double d; 
                 List<Double> values = cv.getValues(name);
                 Double value = grid.getDoubleValue(name, i, y - 1);
               	 if(cao.preval() == false){
               		 
               		d = cao.apply(values, value); 
               	 }else{
               		 values.add(value);
               		 d = cao.apply(values, value); 

               	 }
                	 //System.out.println(" CV1 "+cv);
                      grid.setCellValue(name, i, y - 1, d);
                     
                  }
              } else if(!cv.getValues(name).isEmpty()){
              
                  grid.setCellValue(name, i, y - 1, cv.getValues(name).get((int)(Math.random() * cv.getValues(name).size())));
              }
              cv.clear(name);
          }
          

      }

  }

}
