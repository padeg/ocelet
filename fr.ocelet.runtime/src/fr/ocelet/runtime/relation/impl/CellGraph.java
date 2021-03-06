/*
*  Ocelet spatial modelling language.   www.ocelet.org
*  Copyright Cirad 2010-2016
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
package fr.ocelet.runtime.relation.impl;

import fr.ocelet.runtime.entity.AbstractEntity;
import fr.ocelet.runtime.geom.SpatialManager;
import fr.ocelet.runtime.geom.ocltypes.Cell;
import fr.ocelet.runtime.geom.ocltypes.Line;
import fr.ocelet.runtime.geom.ocltypes.MultiLine;
import fr.ocelet.runtime.geom.ocltypes.MultiPoint;
import fr.ocelet.runtime.geom.ocltypes.Polygon;
import fr.ocelet.runtime.ocltypes.KeyMap;
import fr.ocelet.runtime.ocltypes.List;
import fr.ocelet.runtime.raster.CellAggregOperator;
import fr.ocelet.runtime.raster.Grid;
import fr.ocelet.runtime.raster.GridCellFactory;
import fr.ocelet.runtime.raster.GridCellManager;
import fr.ocelet.runtime.raster.GridGenerator;
import fr.ocelet.runtime.relation.*;

import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.operation.polygonize.Polygonizer;

// Referenced classes of package fr.ocelet.runtime.relation.impl:
//            CompleteIteratorCell

public abstract class CellGraph<E extends OcltEdge, Ro extends OcltRole> implements AutoGraphInterface<E, Ro>{


	protected Grid grid;
	protected CompleteIteratorCell<E, Ro> cellIterator;
	protected Iterator<E> filteredIterator;
	protected CursorEdge cursorEdge;
	
	

	public CellGraph(Grid grid){    
		this.grid = grid;
		grid.setGridCellManager(GridCellFactory.create(grid));
	}

	public CellGraph(){

	}

	public void setGrid(List<Ro> r1List)
	{
		AbstractEntity r1 = (AbstractEntity)r1List.get(0);
    	
    	Cell c1 = (Cell)r1.getSpatialType();
        this.grid = c1.getGrid();

		grid.setGridCellManager(GridCellFactory.create(grid));
	}

	public void setCompleteIteratorCell(E e)
	{
		this.cursorEdge = (CursorEdge)e;
		cellIterator = new CompleteIteratorCell<E, Ro>(e, this);
		setCellShapeType(grid.getCellShapeType());
	}

	public void initInteraction() {
		
	}
	public void endInteraction() {
		
	}
	public Iterator<E> iterator()
	{
		if (filteredIterator != null)
			return filteredIterator;
		//cleanGraph();
		return cellIterator;
	}
	
	public void addFilter(EdgeFilter<Ro, Ro> ef) {
		
		if ((filteredIterator == null)
				|| !(filteredIterator instanceof EdgeFilteringIterator))
			filteredIterator = new EdgeFilteringIteratorImpl(iterator());
		((EdgeFilteringIterator)filteredIterator).setSourceIterator(cellIterator);
		((EdgeFilteringIterator) filteredIterator).addFilter(ef);
		
	}

	public int size()
	{
		return grid.getWidth() * grid.getHeight();
	}

	public String toString()
	{
		return (new StringBuilder("Interaction graph (")).append(getClass().getSimpleName()).append(") contains ").append(size()).append(" edges.").toString();
	}

	public int getWidth(){
		return grid.getWidth();
	}

	public int getHeight(){
		return grid.getHeight();
	}
	public void setAggregOpDouble(String name, AggregOperator<Double, List<Double>> agg, boolean val){
		cellIterator.setAggregOpDouble(name, agg, val);

	}
	public void setAggregOpInteger(String name, AggregOperator<Integer, List<Integer>> agg, boolean val){
		cellIterator.setAggregOpInteger(name, agg, val);

	}

	public void setAggregOpFloat(String name, AggregOperator<Float, List<Float>> agg, boolean val){
		cellIterator.setAggregOpFloat(name, agg, val);
	}

	public void setAggregOpByte(String name, AggregOperator<Byte, List<Byte>> agg, boolean val){
		cellIterator.setAggregOpByte(name, agg, val);
	}

	public void setAggregOpBoolean(String name, AggregOperator<Boolean, List<Boolean>> agg, boolean val){
		cellIterator.setAggregOpBoolean(name, agg, val);
	}
	/* protected void setAggregOp(String name, AggregOperator agg, boolean val){
        cellIterator.setAggregOp(name, agg, val);
    }*/

	protected void setCellOperator(CellAggregOperator operator){
		grid.setAggregOp(operator);
	}

	protected void cleanOperator(){
		grid.cleanOperator();
		grid.clearGeomTempVal2();
	}

	protected void setMode(int mode){
		grid.setMode(mode);
	}


	public E connect(Ro arg0, Ro arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	public E createEdge(Ro arg0, Ro arg1) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void beginTransaction() {

	}

	@Override
	public void endTransaction() {

	}

	@Override
	public void abortTransaction() {

	}

	public void connect() {

	}

	public void disconnect() {

	}

	@Override
	public void disconnect(E edge) {

	}

	@Override
	public void disconnect(Iterable<E> edges) {

	}

	protected void cleanGraph() {

	}

	public CellGraph<E, Ro> getComplete() {
		CompleteIteratorCell<E, Ro> cit = new CompleteIteratorCell<E, Ro>((E)this.cursorEdge,this);
		if ((filteredIterator != null)
				&& (filteredIterator instanceof EdgeFilteringIterator))
			((EdgeFilteringIterator) filteredIterator).setSourceIterator(cit);
		else
			filteredIterator = cit;
		return this;
	}	

	protected RoleSet<Ro> getLeftSet() {
		// TODO Auto-generated method stub
		return null;
	}

	protected RoleSet<Ro> getRightSet() {
		// TODO Auto-generated method stub
		return null;
	}

	protected E getEdge(){
		return cellIterator.getEdge();
	}

	protected void setCellShapeType(String type){
		grid.setCellShapeType(type);
		cellIterator.setCellShapeType(type);


	}

	protected Grid createHexagon(String name, List<String> props, Double[] bounds, double size){
		grid = GridGenerator.hexagonalGrid(name, props, size, bounds);
		
		return grid;

	}

	protected Grid createHexagon(String name, List<String> props, double minX, double minY, double maxX, double maxY, double size){
		grid = GridGenerator.hexagonalGrid(name, props, size, minX, minY, maxX, maxY);
		
		return grid;
	}
	protected Grid createHexagon(String name, List<String> props, Geometry geometry, double size){
		Double[] bounds = getBounds(geometry);
		grid = GridGenerator.hexagonalGrid(name, props, size, bounds);
		
		return grid;
		
	}
	protected Grid createSquare(String name, List<String> props,  Geometry geometry, double xRes, double yRes){
		Double[] bounds = getBounds(geometry);
		grid = GridGenerator.squareGrid(name, props, xRes, yRes, bounds);
		
		return grid;
	}
	protected Grid createSquare(String name, List<String> props,  Double[] bounds, double xRes, double yRes){
		grid = GridGenerator.squareGrid(name, props, xRes, yRes, bounds);
		
		return grid;
	}

	protected Grid createSquare(String name, List<String> props, double minX, double minY, double maxX, double maxY, double xRes, double yRes){	
		grid = GridGenerator.squareGrid(name, props, xRes, yRes, minX, minY, maxX, maxY);
		
		return grid;
	}

	protected Grid createTriangle(String name, List<String> props,Geometry  geometry, double size){
		Double[] bounds = getBounds(geometry);
		grid =  GridGenerator.triangularGrid(name, props, size, bounds);
		
		return grid;

	}
	protected Grid createTriangle(String name, List<String> props,  Double[] bounds, double size){
		grid = GridGenerator.triangularGrid(name, props, size, bounds);
		
		return grid;

	}

	protected Grid createTriangle(String name, List<String> props, double minX, double minY, double maxX, double maxY, double size){
		return null;
	}

	
	private Double[] getBounds(Geometry geometry) {
    	
		  
		  Double[] bounds = new Double[4];
	    	double minX = Double.POSITIVE_INFINITY;
	    	double minY = Double.POSITIVE_INFINITY;
	    	double maxX = Double.NEGATIVE_INFINITY;
	    	double maxY = Double.NEGATIVE_INFINITY;
	    	
	    	
	    		
	    		for(Coordinate c : geometry.getCoordinates()) {
	    			if(c.x > maxX) {
	    				maxX = c.x;
	    			}
	    			if(c.y > maxY) {
	    				maxY = c.y;
	    			}
	    			
	    			if(c.x < minX) {
	    				minX = c.x;
	    			}
	    			if(c.y < minY) {
	    				minY = c.y;
	    			}
	    			
	    		}
	    	
	    	bounds[0] = minX;
	    	bounds[1] = minY;
	    	bounds[2] = maxX;
	    	bounds[3] = maxY;

	    	return bounds;
	    	
	    }
	    
	
	public Grid getGrid(){
		return grid;
	}

	public class RoIterator<Ro> implements Iterator<Ro>{


		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Ro next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

	/*protected void update(Grid upGrid){		

		HashMap<String , Integer> bands = grid.getBands();

		for(int i = 0; i < grid.getWidth() - 1 ; i++){

			for(int j = 0; j < grid.getHeight() - 1; j ++){

				for(String name : bands.keySet()){
					grid.setCellValue(name, i + grid.getMinX(), j + grid.getMinY(), upGrid.getValue(name, i, j));
				}
			}
		}
	}*/


	private Double[] getMinMaxValueOf(int band){

		Double max = 0.0;
		Double min = Double.POSITIVE_INFINITY;
		for(int i = 0 ; i < grid.getWidth(); i ++){
			for(int j = 0; j < grid.getHeight(); j ++){
				Double gridVal = grid.getValue(band, i, j);
				if(gridVal > max)
					max = gridVal;
				if(gridVal < min)
					min = gridVal;
			}
		}
		return new Double[]{min, max};
	}
	public KeyMap<List<Double>, List<Geometry>> isoLines(Integer range, String propertyName){

		KeyMap<List<Double>, List<Geometry>> map = new KeyMap<List<Double>, List<Geometry>>();	    	
		HashMap<Double[], Integer> indexed = new HashMap<Double[], Integer>();	    	
		int[][] structure = new int[grid.getWidth()][grid.getHeight()];
		int index = 0;
		Double[] bounds = getMinMaxValueOf(grid.getBand(propertyName));
		int min = bounds[0].intValue();
		int max = bounds[1].intValue();

		int nbr = (max - min)/ range;
		for(int i = min; i < max; i = i + range){

			indexed.put(new Double[]{(double)i , (double)i + range}, index);

			index ++;
		}	       	

		for(Double[] d : indexed.keySet()){
			List<Double> dlist = new List<Double>();
			dlist.add(d[0]);
			dlist.add(d[1]);
			System.out.println(dlist);

			ArrayList<Line> geoms = new ArrayList<Line>();
			structure =	fillStructure(structure, d, propertyName);
			int[][] cells = toCells(structure);

			for(int x = 0; x < grid.getWidth() - 1; x ++){

				for(int y = 0 ; y < grid.getHeight() - 1; y ++){
					Geometry iso = isoLine(cells[x][y], x, y);

					if(iso != null){
						if(iso instanceof MultiLine){
							for(int i = 0; i < iso.getNumGeometries(); i ++){
								geoms.add((Line)iso.getGeometryN(i));
							}
						}else{
							geoms.add((Line)iso);
						}
					}
				}
			}	    	    	    	  

			MultiLine ml = new MultiLine(geoms.toArray(new Line[geoms.size()]), SpatialManager.geometryFactory()); 
			//  System.out.println(ml.union());
			Polygonizer polygonizer=new Polygonizer();
			polygonizer.add(ml);


			GeometryCollection gc = new GeometryCollection(geoms.toArray(new Geometry[geoms.size()]), SpatialManager.geometryFactory());
			System.out.println(gc);
			Geometry g = gc.buffer(0.0);
			System.out.println(g); 
			//System.out.println(g);
			List<Geometry> polys = new List<Geometry>();

			HashMap<Polygon, Polygon> pMap = new HashMap<Polygon, Polygon>();

			for(Object o : polygonizer.getPolygons()){
				System.out.println(o);
				if(o instanceof Geometry){
					Polygon p = (Polygon)o;
					LinearRing lr = new LinearRing(new CoordinateArraySequence(p.getExteriorRing().getCoordinates()), SpatialManager.geometryFactory());
					Polygon np = new Polygon(lr, null, SpatialManager.geometryFactory());
					pMap.put(p, np);
					polys.add((Geometry)o);
				}
			}

			List<Geometry> temp = new List<Geometry>();
			for(Geometry g1 : polys){
				temp.add(g1);
			}

			for(Geometry p1 : polys){

				for(Geometry p2 : polys){

					if(!p1.equals(p2)){

						if(pMap.get(p1).contains(p2)){
							temp.remove(p2);
						}
						if(pMap.get(p2).contains(p1)){
							temp.remove(p1);
						}	    	    	
					}
				}
			}
			map.put(dlist, temp);	   	    	    	    
		}	    		    	    		  	    	
		return map;	    	
	}

	public KeyMap<List<Double>, List<Geometry>> isoLines(List<Double> range, String propertyName){

		KeyMap<List<Double>, List<Geometry>> map = new KeyMap<List<Double>, List<Geometry>>();	    	
		HashMap<Double[], Integer> indexed = new HashMap<Double[], Integer>();	    	
		int[][] structure = new int[grid.getWidth()][grid.getHeight()];
		int index = 0;
		for(int i = 0; i < range.size() - 2; i = i + 2){

			indexed.put(new Double[]{range.get(i), range.get(i + 1)}, index);

			index ++;
		}	       	

		for(Double[] d : indexed.keySet()){


			List<Double> dlist = new List<Double>();
			dlist.add(d[0]);
			dlist.add(d[1]);
			System.out.println(dlist);
			ArrayList<Line> geoms = new ArrayList<Line>();
			structure =	fillStructure(structure, d, propertyName);
			int[][] cells = toCells(structure);

			for(int x = 0; x < grid.getWidth() ; x ++){

				for(int y = 0 ; y < grid.getHeight(); y ++){
					Geometry iso = isoLine(cells[x][y], x, y);

					if(iso != null){
						if(iso instanceof MultiLine){
							for(int i = 0; i < iso.getNumGeometries(); i ++){
								geoms.add((Line)iso.getGeometryN(i));
							}
						}else{
							geoms.add((Line)iso);
						}
						System.out.println(iso);
					}
				}
			}	    	    	    	  

			MultiLine ml = new MultiLine(geoms.toArray(new Line[geoms.size()]), SpatialManager.geometryFactory()); 
			//  System.out.println(ml.union());
			Polygonizer polygonizer=new Polygonizer();
			polygonizer.add(ml);


			GeometryCollection gc = new GeometryCollection(geoms.toArray(new Geometry[geoms.size()]), SpatialManager.geometryFactory());
			Geometry g = gc.buffer(0.0);

			//System.out.println(g);
			List<Geometry> polys = new List<Geometry>();

			HashMap<Polygon, Polygon> pMap = new HashMap<Polygon, Polygon>();

			for(Object o : polygonizer.getPolygons()){
				System.out.println(o);
				if(o instanceof Geometry){
					Polygon p = (Polygon)o;
					LinearRing lr = new LinearRing(new CoordinateArraySequence(p.getExteriorRing().getCoordinates()), SpatialManager.geometryFactory());
					Polygon np = new Polygon(lr, null, SpatialManager.geometryFactory());
					pMap.put(p, np);
					polys.add((Geometry)o);
				}
			}

			List<Geometry> temp = new List<Geometry>();
			for(Geometry g1 : polys){
				temp.add(g1);
			}

			for(Geometry p1 : polys){

				for(Geometry p2 : polys){

					if(!p1.equals(p2)){

						if(pMap.get(p1).contains(p2)){
							temp.remove(p2);
						}
						if(pMap.get(p2).contains(p1)){
							temp.remove(p1);
						}	    	    	
					}
				}
			}
			map.put(dlist, temp);	   	    	    	    
		}	    		    	    		  	    	
		return map;	    	
	}

	private int[][] fillStructure(int[][] structure,Double[] d, String propertyName){
		for(int i = 0; i < grid.getWidth(); i ++){
			for(int j = 0; j < grid.getHeight(); j ++){

				Double val = grid.getValue(grid.getBand(propertyName), i, j);
				if(val >= d[0] && val < d[1]){

					structure[i][j] = 1;
				}else{
					structure[i][j] = 0;
				}

			}		 
		}
		return structure;
	}

	private int[][]  toCells(int[][] structure){

		int[][] cells = new int[grid.getWidth() - 1][grid.getHeight() - 1];    	

		for(int x = 0; x < grid.getWidth() - 1; x ++){	    	
			System.out.println();
			for(int y = 0; y < grid.getHeight() - 1; y ++){


				int finalVal = 0;	   		 
				int val1 = structure[x][y];
				int val2 = structure[x + 1][y];
				int val3 = structure[x + 1][y + 1];
				int val4 = structure[x][y  + 1];

				if(val1 ==1){
					finalVal += 8;
				}
				if(val2 ==1){
					finalVal += 4;
				}
				if(val3 ==1){
					finalVal += 2;
				}
				if(val4 ==1){
					finalVal += 1;
				}
				cells[x][y] = finalVal;

				System.out.print(finalVal+"\t");
			}
		}

		return cells;
	}


	public Geometry isoLine(int type, int x, int y){


		if(type == 1){ 			
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), downCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;			 
		}
		if(type == 2){				
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{downCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 3){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 4){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{upCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 5){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), upCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());			 
			Coordinate[] coordinates2 = new Coordinate[]{downCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs2 = new CoordinateArraySequence(coordinates2);		
			Line line2 = new Line(cs2, SpatialManager.geometryFactory());			 
			MultiLine ml = new MultiLine(new Line[]{line,  line2},SpatialManager.geometryFactory());
			return ml;
		}
		if(type == 6){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{upCoordinate(c), downCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 7){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), upCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 8){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), upCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 9){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{upCoordinate(c), downCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 10){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), downCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());			 
			Coordinate[] coordinates2 = new Coordinate[]{upCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs2 = new CoordinateArraySequence(coordinates2);		
			Line line2 = new Line(cs2, SpatialManager.geometryFactory());			 
			MultiLine ml = new MultiLine(new Line[]{line,  line2},SpatialManager.geometryFactory());
			return ml;
		}
		if(type == 11){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{upCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 12){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 13){
			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{downCoordinate(c), rightCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		if(type == 14){

			Coordinate c = grid.gridCoordinate(x, y);
			Coordinate[] coordinates = new Coordinate[]{leftCoordinate(c), downCoordinate(c)};
			CoordinateSequence cs = new CoordinateArraySequence(coordinates);		
			Line line = new Line(cs, SpatialManager.geometryFactory());
			return line;
		}
		return null;

	}

	private Coordinate upCoordinate(Coordinate c){
		return new Coordinate(c.x + (grid.getXRes() / 2) - (2 * grid.getXRes()), c.y + (2 * grid.getYRes()));
	}

	private Coordinate downCoordinate(Coordinate c){
		return new Coordinate(c.x + (grid.getXRes() / 2)- (2 * grid.getXRes()), c.y - grid.getYRes() + (2 * grid.getYRes()));
	}

	private Coordinate rightCoordinate(Coordinate c){
		return new Coordinate(c.x + (grid.getXRes())- (2 * grid.getXRes()), c.y - (grid.getYRes() / 2)+ (2 * grid.getYRes()));
	}

	private Coordinate leftCoordinate(Coordinate c){
		return new Coordinate(c.x- (2 * grid.getXRes()), c.y - (grid.getYRes() / 2)+ (2 * grid.getYRes()));

	}

	public void clearData(){
		grid = null;
	}

	public void extendedMoore(int n){
		cellIterator.extendedMoore(n);
	}
	public void extendedCircularMoore(int n){
		cellIterator.extendedCircularMoore(n);
	}

	public int getSize(){
		return grid.getWidth() * grid.getHeight();
	}
	
	

}
