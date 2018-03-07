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
package fr.ocelet.runtime.relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import fr.ocelet.runtime.entity.AbstractEntity;
import fr.ocelet.runtime.geom.OceletGeomFactory;
import fr.ocelet.runtime.geom.SpatialManager;
import fr.ocelet.runtime.geom.ocltypes.Line;
import fr.ocelet.runtime.geom.ocltypes.MultiLine;
import fr.ocelet.runtime.geom.ocltypes.MultiPoint;
import fr.ocelet.runtime.geom.ocltypes.MultiPolygon;
import fr.ocelet.runtime.geom.ocltypes.Point;
import fr.ocelet.runtime.geom.ocltypes.Polygon;
import fr.ocelet.runtime.ocltypes.KeyMap;
import fr.ocelet.runtime.ocltypes.List;
import fr.ocelet.runtime.raster.CellAggregOperator;
import fr.ocelet.runtime.raster.Grid;
import fr.ocelet.runtime.raster.IX;
import fr.ocelet.runtime.raster.IY;
import fr.ocelet.runtime.raster.Node;

// Referenced classes of package fr.ocelet.runtime.relation:
//            OcltEdge, OcltRole, AggregOperator
/**
 * @author Mathieu Castets - Initial contribution
 */
public abstract class GeomCellEdge2<R1 extends OcltRole, R2 extends OcltRole> extends OcltEdge {

	private HashMap<String, CellAggregOperator> aggregMap = new HashMap<String, CellAggregOperator>();

	// private Node node = new Node<R2>();
	private List<R2> geomEntities;
	private Grid grid;
	// private IX<R2> currentX;
	// private IY<R2> currentY;
	private R2 currentEntity;
	private Node node = new Node();
	private IX currentX;
	private IY currentY;
	// private int currentxKey;
	// private int currentyKey;
	// private R2 currentRoleKey;
	private Iterator<Integer> xIterator;
	private Iterator<Integer> yIterator;
	private Iterator<R2> roleIterator;
	private String cellShapeType = "QUADRILATERAL";
	private HashMap<R2, ArrayList<Integer[]>> added = new HashMap<R2, ArrayList<Integer[]>>();

	// private CellIterator<R1> cellIterator = new CellIterator<R1>();
	private int edgeNumber = 0;
	private int roleIndex = 0;

	public String getCellType() {
		return grid.getCellShapeType();
	}

	public void clearAggregMap() {
		aggregMap.clear();

	}

	public void addOperator(String name, CellAggregOperator operator) {
		aggregMap.put(name, operator);
	}

	public void setCellOperator(String name, AggregOperator operator, KeyMap<String, String> typeProps) {
		CellAggregOperator cao = new CellAggregOperator();
		if (typeProps.get(name).equals("Double")) {
			setAggregOpDouble(name, operator);
		} else if (typeProps.get(name).equals("Integer")) {
			setAggregOpInteger(name, operator);

		} else if (typeProps.get(name).equals("Float")) {
			setAggregOpFloat(name, operator);

		} else if (typeProps.get(name).equals("Byte")) {
			setAggregOpByte(name, operator);

		} else if (typeProps.get(name).equals("Boolean")) {
			setAggregOpBoolean(name, operator);

		}
	}

	public void setAggregOpDouble(String name, AggregOperator<Double, List<Double>> agg) {
		CellAggregOperator cao = new CellAggregOperator();
		cao.setOperatorDouble(agg);
		aggregMap.put(name, cao);

	}

	public void setAggregOpInteger(String name, AggregOperator<Integer, List<Integer>> agg) {
		CellAggregOperator cao = new CellAggregOperator();
		cao.setOperatorInteger(agg);
		aggregMap.put(name, cao);

	}

	public void setAggregOpFloat(String name, AggregOperator<Float, List<Float>> agg) {
		CellAggregOperator cao = new CellAggregOperator();
		cao.setOperatorFloat(agg);
		aggregMap.put(name, cao);
	}

	public void setAggregOpByte(String name, AggregOperator<Byte, List<Byte>> agg) {
		CellAggregOperator cao = new CellAggregOperator();
		cao.setOperatorByte(agg);
		aggregMap.put(name, cao);
	}

	public void setAggregOpBoolean(String name, AggregOperator<Boolean, List<Boolean>> agg) {
		CellAggregOperator cao = new CellAggregOperator();
		cao.setOperatorBoolean(agg);
		aggregMap.put(name, cao);
	}

	public GeomCellEdge2(Grid grid, List<R2> geomEntities) {

		this.geomEntities = geomEntities;
		this.grid = grid;
		fill(this.geomEntities);
	}

	public void add(AbstractEntity e, int x, int y) {

		if (x < 0 || y < 0) {
		} else {
			node.add(x, y, e);
		}

	}

	public void fill(List<R2> list) {

		if (grid.getCellShapeType().equals("QUADRILATERAL")) {
			quadrilateralExtract(list);
		}
		if (grid.getCellShapeType().equals("HEXAGONAL")) {
			hexagonalExtract(list);
		}

		if (grid.getCellShapeType().equals("TRIANGULAR")) {
			triangularExtract(list);
		}

		currentX = node.get();
		currentY = currentX.get();
		currentEntity = (R2) currentY.get();

	}

	private void setTriangularCells(R2 r2, Polygon polygon) {
		int bounds[] = grid.intBounds(polygon);
		int index = 0;
		for (int i = bounds[0]; i < bounds[2]; i++) {
			for (int j = bounds[1]; j < bounds[3]; j++) {
				Coordinate c = grid.gridCoordinate(i, j);
				Point point = Point.xy(Double.valueOf(c.x), Double.valueOf(c.y));
				if (polygon.touches(point) || point.within(polygon)) {
					add((AbstractEntity) r2, i, j);
					index++;
				}
			}
		}
		if (index == 0) {

			int scaledCentroid[] = grid.gridCoordinate(polygon.getCentroid().getX(), polygon.getCentroid().getY());
			add((AbstractEntity) r2, scaledCentroid[0], scaledCentroid[1]);
		}
	}

	private void setTriangularCells(R2 r2, Line line) {
		Coordinate coordinates[] = line.getCoordinates();
		for (int i = 0; i < coordinates.length - 1; i++) {
			int c1[] = grid.gridCoordinate(coordinates[i].x, coordinates[i].y);
			int c2[] = grid.gridCoordinate(coordinates[i + 1].x, coordinates[i + 1].y);
			int x = c1[0];
			int y = c1[1];
			int x2 = c2[0];
			int y2 = c2[1];
			bresenham(x, y, x2, y2, r2);
		}

	}

	private void setTriangularCells(R2 r2, Point point) {
		Coordinate coordinates[] = point.getCoordinates();

		int c1[] = grid.gridCoordinate(coordinates[0].x, coordinates[0].y);
		add((AbstractEntity) r2, c1[0], c1[1]);

	}

	private void setHexagonalCells(R2 r2, Polygon polygon) {

		int bounds[] = grid.intBounds(polygon);
		int index = 0;
		for (int i = bounds[0]; i <= bounds[2]; i++) {

			for (int j = bounds[1]; j <= bounds[3]; j++) {

				Coordinate c = grid.gridCoordinate(i, j);

				Point point = Point.xy(Double.valueOf(c.x), Double.valueOf(c.y));

				if (polygon.distance(point) < grid.getXRes() / 2) {
					add((AbstractEntity) r2, i, j);
					index++;
				}
			}

		}

		if (index == 0) {

			int scaledCentroid[] = grid.gridCoordinate(polygon.getCentroid().getX(), polygon.getCentroid().getY());
			add((AbstractEntity) r2, scaledCentroid[0], scaledCentroid[1]);
		}

	}

	private void setHexagonalCells(R2 r2, Line line) {
		Coordinate coordinates[] = line.getCoordinates();
		for (int i = 0; i < coordinates.length - 1; i++) {
			int c1[] = grid.gridCoordinate(coordinates[i].x, coordinates[i].y);
			int c2[] = grid.gridCoordinate(coordinates[i + 1].x, coordinates[i + 1].y);
			int x = c1[0];
			int y = c1[1];
			int x2 = c2[0];
			int y2 = c2[1];
			bresenham(x, y, x2, y2, r2);
		}

	}

	private void setHexagonalCells(R2 r2, Point point) {

		Coordinate coordinates[] = point.getCoordinates();

		int c1[] = grid.gridCoordinate(coordinates[0].x, coordinates[0].y);
		add((AbstractEntity) r2, c1[0], c1[1]);

	}

	private HashMap<Integer, Line> makeLines() {
		HashMap<Integer, Line> lines = new HashMap<Integer, Line>();

		int width = grid.getWidth();
		int height = grid.getHeight();
		double res = grid.getXRes();
		Double[] bounds = grid.getWorldBounds();

		double minX = bounds[0] - (5 * res);

		double maxX = bounds[2] + (5 * res);

		double minY = bounds[1] - (5 * res);

		double maxY = bounds[3] + (5 * res);

		for (int i = -3; i < height + 3; i++) {

			Point p1 = Point.xy(minX, maxY);
			Point p2 = Point.xy(maxX, maxY);

			maxY = maxY - res;
			// List<Point> pList = new List<Point>();
			// pList.add(p1);
			// pList.add(p2);
			// System.out.println(p2+" "+p1);
			Line l = Line.points(p1, p2);
			lines.put(i - 2, l);
		}

		return lines;
	}

	private void setQuadrilateralCells(R2 r2, Polygon polygon, HashMap<Integer, Line> lines) {

		int bounds[] = grid.intBounds(polygon);
		int index = 0;
		for (int j = bounds[1] - 1; j <= bounds[3] + 1; j++) {
			Line l = lines.get(j);

			if (l.intersects(polygon)) {

				try {
					MultiLine ml = l.multiLineIntersection(polygon);
					for (Line l2 : ml.asListOfLines()) {
						Coordinate[] c = l2.getCoordinates();
						Coordinate c1 = c[0];
						Coordinate c2 = c[c.length - 1];

						int[] ix = grid.gridCoordinate(c1.x, c1.y);
						int[] ix2 = grid.gridCoordinate(c2.x, c2.y);

						int x1 = ix[0];
						int x2 = ix2[0];
						int temp1 = ix[0];
						int temp2 = ix2[0];

						if (x1 > x2) {
							temp2 = ix[0];
							temp1 = ix2[0];
						}

						for (int x = temp1; x < temp2; x++) {
							add((AbstractEntity) r2, x, j - 1);
							// System.out.print(" "+x+" "+j+" |");
							index++;
						}
					}
				} catch (Exception e) {
					System.out.println("error");
				}
			}
		}
		if (index == 0) {
			int scaledCentroid[] = grid.gridCoordinate(polygon.getCentroid().getX(), polygon.getCentroid().getY());
			add((AbstractEntity) r2, scaledCentroid[0], scaledCentroid[1]);
		}
	}

	private void setQuadrilateralCells(R2 r2, Polygon polygon, HashMap<Integer, Line> lines,
			HashMap<Integer, Integer> abs, HashMap<Integer, Integer> ordo) {

		int bounds[] = grid.intBounds(polygon);
		int index = 0;

		for (int j = bounds[1] - 1; j <= bounds[3] + 1; j++) {
			Line l = lines.get(j);

			if (l.intersects(polygon)) {

				try {
					MultiLine ml = l.multiLineIntersection(polygon);

					for (Line l2 : ml.asListOfLines()) {
						Coordinate[] c = l2.getCoordinates();
						Coordinate c1 = c[0];
						Coordinate c2 = c[c.length - 1];

						int[] ix = grid.gridCoordinate(c1.x, c1.y);
						int[] ix2 = grid.gridCoordinate(c2.x, c2.y);

						int x1 = ix[0];
						int x2 = ix2[0];
						int temp1 = ix[0];
						int temp2 = ix2[0];

						if (x1 > x2) {
							temp2 = ix[0];
							temp1 = ix2[0];
						}

						for (int x = temp1; x < temp2; x++) {
							// add(r2,x , j - 1);
							add((AbstractEntity) r2, abs.get(x), ordo.get(j - 1));
							// System.out.print(" "+x+" "+j+" |");
							index++;
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("error");
				}

			}

		}

		if (index == 0) {
			int scaledCentroid[] = grid.gridCoordinate(polygon.getCentroid().getX(), polygon.getCentroid().getY());
			add((AbstractEntity) r2, scaledCentroid[0], scaledCentroid[1]);
		}
	}

	private void setQuadrilateralCells(R2 r2, Polygon polygon) {

		int bounds[] = grid.intBounds(polygon);
		int index = 0;
		for (int i = bounds[0]; i <= bounds[2]; i++) {
			for (int j = bounds[1]; j <= bounds[3]; j++) {
				Coordinate c = grid.gridCoordinate(i, j);
				Point point = Point.xy(Double.valueOf(c.x), Double.valueOf(c.y));

				if (polygon.touches(point) || point.within(polygon)) {
					add((AbstractEntity) r2, i, j);
					index++;
				}
			}

		}

		if (index == 0) {
			int scaledCentroid[] = grid.gridCoordinate(polygon.getCentroid().getX(), polygon.getCentroid().getY());
			add((AbstractEntity) r2, scaledCentroid[0], scaledCentroid[1]);
		}
	}

	private void setQuadrilateralCells(R2 r2, Line line) {
		Coordinate coordinates[] = line.getCoordinates();
		for (int i = 0; i < coordinates.length - 1; i++) {
			int c1[] = grid.gridCoordinate(coordinates[i].x, coordinates[i].y);
			int c2[] = grid.gridCoordinate(coordinates[i + 1].x, coordinates[i + 1].y);
			int x = c1[0];
			int y = c1[1];
			int x2 = c2[0];
			int y2 = c2[1];
			bresenham(x, y, x2, y2, r2);
		}

	}

	private void setQuadrilateralCells(R2 r2, Point point) {
		Coordinate coordinates[] = point.getCoordinates();

		int c1[] = grid.gridCoordinate(coordinates[0].x, coordinates[0].y);
		add((AbstractEntity) r2, c1[0], c1[1]);

	}

	public void bresenham(int x1, int y1, int x2, int y2, R2 role) {
		if (x1 == x2 && y1 == y2) {
			add((AbstractEntity) role, x1 - grid.getMinX(), y1 - grid.getMinY());
		} else {
			int dx = Math.abs(x2 - x1);
			int dy = Math.abs(y2 - y1);
			int rozdil = dx - dy;
			int posun_x;
			if (x1 < x2)
				posun_x = 1;
			else
				posun_x = -1;
			int posun_y;
			if (y1 < y2)
				posun_y = 1;
			else
				posun_y = -1;
			while (x1 != x2 || y1 != y2) {
				int p = 2 * rozdil;
				if (p > -dy) {
					rozdil -= dy;
					x1 += posun_x;
				}
				if (p < dx) {
					rozdil += dx;
					y1 += posun_y;
				}
				add((AbstractEntity) role, x1, y1);
			}
		}
	}

	public int[] getBounds(Coordinate coordinates[]) {
		int bounds[] = new int[4];
		int minX = 0x7fffffff;
		int minY = 0x7fffffff;
		int maxX = 0;
		int maxY = 0;
		Coordinate acoordinate[];
		int j = (acoordinate = coordinates).length;
		for (int i = 0; i < j; i++) {
			Coordinate c = acoordinate[i];
			if (c.x > (double) maxX)
				maxX = (int) c.x;
			if (c.y > (double) maxY)
				maxY = (int) c.y;
			if (c.x < (double) minX)
				minX = (int) c.x;
			if (c.y < (double) minY)
				minY = (int) c.y;
		}

		bounds[0] = minX - 1;
		bounds[1] = minY - 1;
		bounds[2] = maxX + 1;
		bounds[3] = maxY + 1;
		return bounds;
	}

	private Point getPoint(Coordinate coordinate) {
		Coordinate c[] = new Coordinate[1];
		c[0] = coordinate;
		com.vividsolutions.jts.geom.CoordinateSequence cs = new CoordinateArraySequence(c);
		return new Point(cs, new GeometryFactory());
	}

	public void cellSynchronisation() {

		for (Iterator iterator = grid.getTempName().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();

			List<Double> values = grid.getGeomTempValues(name);
			if (!values.isEmpty()) {

				if (aggregMap.keySet().contains(name)) {
					CellAggregOperator cao = (CellAggregOperator) aggregMap.get(name);

					Double d = cao.apply(values, Double.valueOf(0.0D));

					grid.setCellValue(name, currentX.getX(), currentY.getY(), d);

				} else {

					if (values.size() > 1) {
						grid.setCellValue(name, currentX.getX(), currentY.getY(),
								(Double) values.get((int) (Math.random() * values.size())));
					} else {
						grid.setCellValue(name, currentX.getX(), currentY.getY(), (Double) values.get(0));
					}

				}
			}

		}
		grid.clearGeomTempVal();

	}

	public boolean xHasNext() {
		return node.hasNext();
	}

	public boolean yHasNext() {
		return currentX.hasNext();
	}

	public boolean roleHasNext() {
		return currentY.hasNext();
	}

	public void xNext() {

		node.next();
		currentX = node.get();
		currentY = currentX.get();
		currentEntity = (R2) currentY.get();
	}

	public void yNext() {
		currentX.next();
		currentY = currentX.get();
		currentEntity = (R2) currentY.get();
	}

	public void roleNext() {

		currentY.next();
		currentEntity = (R2) currentY.get();
	}

	public boolean hasNext() {

		if (!xHasNext() && !yHasNext() && !roleHasNext()) {

			cellSynchronisation();
			currentY.reset();
			currentX.reset();
			node.reset();
			grid.clearGeomTempVal();
			return false;
		}
		return true;

	}

	public void next() {

		if (roleHasNext()) {

			roleNext();
		} else {

			cellSynchronisation();
			currentY.reset();
			if (yHasNext()) {
				yNext();

			} else {

				currentX.reset();
				xNext();
			}
		}

		update();
	}

	public List<R2> getGeomEntities() {
		return geomEntities;
	}

	public abstract void update();

	public Integer getX() {
		return currentX.getX();
	}

	public Integer getY() {
		return currentY.getY();
	}

	public R2 getGeomEntity() {
		return (R2) node.get().get().get();
	}

	public void setMode(int mode) {
		grid.setMode(mode);
	}

	public void quadrilateralExtract(List<R2> list) {

		HashMap<Integer, Line> lines = makeLines();

		HashMap<Integer, Integer> abscisse = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> ordo = new HashMap<Integer, Integer>();

		for (int i = -2; i < grid.getWidth() + 2; i++) {
			abscisse.put(i, new Integer(i));
		}
		for (int i = -2; i < grid.getHeight() + 2; i++) {
			ordo.put(i, new Integer(i));
		}
		for (R2 r2 : list) {

			OcltRole e = (OcltRole) r2;
			if (e.getSpatialType() instanceof Polygon) {
				// setQuadrilateralCells(r2, (Polygon)e.getSpatialType());
				// setQuadrilateralCells(r2, (Polygon)e.getSpatialType(), lines);
				setQuadrilateralCells(r2, (Polygon) e.getSpatialType(), lines, abscisse, ordo);
			}
			if (e.getSpatialType() instanceof MultiPolygon) {

				MultiPolygon mp = (MultiPolygon) e.getSpatialType();
				for (int i = 0; i < mp.getNumGeometries(); i++) {
					// setQuadrilateralCells(r2, (Polygon)mp.getGeometryN(i));
					// setQuadrilateralCells(r2, (Polygon)mp.getGeometryN(i), lines);
					setQuadrilateralCells(r2, (Polygon) mp.getGeometryN(i), lines, abscisse, ordo);
				}
			}
			if (e.getSpatialType() instanceof Line)
				setQuadrilateralCells(r2, (Line) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiLine) {
				MultiLine ml = (MultiLine) e.getSpatialType();
				for (int i = 0; i < ml.getNumGeometries(); i++)
					setQuadrilateralCells(r2, (Line) ml.getGeometryN(i));

			}

			if (e.getSpatialType() instanceof Point)
				setQuadrilateralCells(r2, (Point) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiPoint) {
				MultiPoint mp = (MultiPoint) e.getSpatialType();
				for (int i = 0; i < mp.getNumGeometries(); i++)
					setQuadrilateralCells(r2, (Point) mp.getGeometryN(i));

			}

		}
		abscisse.clear();
		ordo.clear();

	}

	public void hexagonalExtract(List<R2> list) {
		for (R2 r2 : list) {

			OcltRole e = (OcltRole) r2;
			if (e.getSpatialType() instanceof Polygon)
				setHexagonalCells(r2, (Polygon) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiPolygon) {

				MultiPolygon mp = (MultiPolygon) e.getSpatialType();
				for (int i = 0; i < mp.getNumGeometries(); i++)
					setHexagonalCells(r2, (Polygon) mp.getGeometryN(i));

			}
			if (e.getSpatialType() instanceof Line)
				setQuadrilateralCells(r2, (Line) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiLine) {
				MultiLine ml = (MultiLine) e.getSpatialType();
				for (int i = 0; i < ml.getNumGeometries(); i++)
					setHexagonalCells(r2, (Line) ml.getGeometryN(i));

			}
			if (e.getSpatialType() instanceof Point)
				setHexagonalCells(r2, (Point) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiPoint) {
				MultiPoint mp = (MultiPoint) e.getSpatialType();
				for (int i = 0; i < mp.getNumGeometries(); i++)
					setHexagonalCells(r2, (Point) mp.getGeometryN(i));

			}
		}

	}

	public void triangularExtract(List<R2> list) {
		for (R2 r2 : list) {

			OcltRole e = (OcltRole) r2;
			if (e.getSpatialType() instanceof Polygon)
				setTriangularCells(r2, (Polygon) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiPolygon) {

				MultiPolygon mp = (MultiPolygon) e.getSpatialType();
				for (int i = 0; i < mp.getNumGeometries(); i++)
					setTriangularCells(r2, (Polygon) mp.getGeometryN(i));

			}
			if (e.getSpatialType() instanceof Line)
				setTriangularCells(r2, (Line) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiLine) {
				MultiLine ml = (MultiLine) e.getSpatialType();
				for (int i = 0; i < ml.getNumGeometries(); i++)
					setTriangularCells(r2, (Line) ml.getGeometryN(i));

			}
			if (e.getSpatialType() instanceof Point)
				setTriangularCells(r2, (Point) e.getSpatialType());
			if (e.getSpatialType() instanceof MultiPoint) {
				MultiPoint mp = (MultiPoint) e.getSpatialType();
				for (int i = 0; i < mp.getNumGeometries(); i++)
					setTriangularCells(r2, (Point) mp.getGeometryN(i));

			}
		}

	}

	/*
	 * public void connect(R1 cell, R2 role){
	 * 
	 * Cell c = (Cell)cell.getSpatialType(); add(role, c.getX(), c.getY());
	 * if(added.keySet().contains(role)){ added.get(role).add(new
	 * Integer[]{c.getX(), c.getY()}); }else{ ArrayList<Integer[]> coords = new
	 * ArrayList<Integer[]>(); coords.add(new Integer[]{c.getX(), c.getY()});
	 * added.put(role, coords); } xIterator = matrice.keySet().iterator();
	 * currentxKey = xIterator.next().intValue(); yIterator =
	 * matrice.get(currentxKey).keySet().iterator(); currentyKey =
	 * yIterator.next().intValue(); roleIterator =
	 * matrice.get(currentxKey).get(currentyKey).iterator();
	 * 
	 * } public void disconnectAll(){ matrice = new HashMap<Integer,
	 * HashMap<Integer,ArrayList<R2>>>(); } public void disconnect(R1 cell, R2
	 * role){
	 * 
	 * Cell c = (Cell)cell.getSpatialType();
	 * 
	 * if(matrice.containsKey(c.getX())){
	 * 
	 * if(matrice.get(c.getX()).containsKey(c.getY())){
	 * 
	 * if(matrice.get(c.getX()).get(c.getY()).contains(role)){
	 * 
	 * matrice.get(c.getX()).get(c.getY()).remove(role);
	 * 
	 * if(matrice.get(c.getX()).get(c.getY()).isEmpty()){
	 * 
	 * matrice.get(c.getX()).remove(c.getY());
	 * 
	 * if(matrice.get(c.getX()).isEmpty()){
	 * 
	 * matrice.remove(c.getX()); } } xIterator = matrice.keySet().iterator();
	 * currentxKey = xIterator.next().intValue(); yIterator =
	 * matrice.get(currentxKey).keySet().iterator(); currentyKey =
	 * yIterator.next().intValue(); roleIterator =
	 * matrice.get(currentxKey).get(currentyKey).iterator(); }
	 * 
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * }
	 */

	/*
	 * public void connect(R2 r2, Geometry zone){
	 * 
	 * if(grid.getCellShapeType().equals("QUADRILATERAL")){
	 * 
	 * OcltRole e = (OcltRole)r2; if(e.getSpatialType() instanceof Polygon)
	 * setQuadrilateralCells(r2, (Polygon)e.getSpatialType()); if(e.getSpatialType()
	 * instanceof MultiPolygon) {
	 * 
	 * MultiPolygon mp = (MultiPolygon)e.getSpatialType(); for(int i = 0; i <
	 * mp.getNumGeometries(); i++) setQuadrilateralCells(r2,
	 * (Polygon)mp.getGeometryN(i));
	 * 
	 * } if(e.getSpatialType() instanceof Line) setQuadrilateralCells(r2,
	 * (Line)e.getSpatialType()); if(e.getSpatialType() instanceof MultiLine) {
	 * MultiLine ml = (MultiLine)e.getSpatialType(); for(int i = 0; i <
	 * ml.getNumGeometries(); i++) setQuadrilateralCells(r2,
	 * (Line)ml.getGeometryN(i));
	 * 
	 * }
	 * 
	 * if(e.getSpatialType() instanceof Point) setQuadrilateralCells(r2,
	 * (Point)e.getSpatialType()); if(e.getSpatialType() instanceof MultiPoint) {
	 * MultiPoint mp = (MultiPoint)e.getSpatialType(); for(int i = 0; i <
	 * mp.getNumGeometries(); i++) setQuadrilateralCells(r2,
	 * (Point)mp.getGeometryN(i));
	 * 
	 * } } if(grid.getCellShapeType().equals("HEXAGONAL")){ OcltRole e =
	 * (OcltRole)r2; if(e.getSpatialType() instanceof Polygon) setHexagonalCells(r2,
	 * (Polygon)e.getSpatialType()); if(e.getSpatialType() instanceof MultiPolygon)
	 * {
	 * 
	 * MultiPolygon mp = (MultiPolygon)e.getSpatialType(); for(int i = 0; i <
	 * mp.getNumGeometries(); i++) setHexagonalCells(r2,
	 * (Polygon)mp.getGeometryN(i));
	 * 
	 * } if(e.getSpatialType() instanceof Line) setQuadrilateralCells(r2,
	 * (Line)e.getSpatialType()); if(e.getSpatialType() instanceof MultiLine) {
	 * MultiLine ml = (MultiLine)e.getSpatialType(); for(int i = 0; i <
	 * ml.getNumGeometries(); i++) setHexagonalCells(r2, (Line)ml.getGeometryN(i));
	 * 
	 * } if(e.getSpatialType() instanceof Point) setHexagonalCells(r2,
	 * (Point)e.getSpatialType()); if(e.getSpatialType() instanceof MultiPoint) {
	 * MultiPoint mp = (MultiPoint)e.getSpatialType(); for(int i = 0; i <
	 * mp.getNumGeometries(); i++) setHexagonalCells(r2, (Point)mp.getGeometryN(i));
	 * 
	 * } }
	 * 
	 * if(grid.getCellShapeType().equals("TRIANGULAR")){ OcltRole e = (OcltRole)r2;
	 * if(e.getSpatialType() instanceof Polygon) setTriangularCells(r2,
	 * (Polygon)e.getSpatialType()); if(e.getSpatialType() instanceof MultiPolygon)
	 * {
	 * 
	 * MultiPolygon mp = (MultiPolygon)e.getSpatialType(); for(int i = 0; i <
	 * mp.getNumGeometries(); i++) setTriangularCells(r2,
	 * (Polygon)mp.getGeometryN(i));
	 * 
	 * } if(e.getSpatialType() instanceof Line) setTriangularCells(r2,
	 * (Line)e.getSpatialType()); if(e.getSpatialType() instanceof MultiLine) {
	 * MultiLine ml = (MultiLine)e.getSpatialType(); for(int i = 0; i <
	 * ml.getNumGeometries(); i++) setTriangularCells(r2, (Line)ml.getGeometryN(i));
	 * 
	 * } if(e.getSpatialType() instanceof Point) setTriangularCells(r2,
	 * (Point)e.getSpatialType()); if(e.getSpatialType() instanceof MultiPoint) {
	 * MultiPoint mp = (MultiPoint)e.getSpatialType(); for(int i = 0; i <
	 * mp.getNumGeometries(); i++) setTriangularCells(r2,
	 * (Point)mp.getGeometryN(i));
	 * 
	 * } }
	 * 
	 * 
	 * xIterator = matrice.keySet().iterator(); currentxKey =
	 * xIterator.next().intValue(); yIterator =
	 * matrice.get(currentxKey).keySet().iterator(); currentyKey =
	 * yIterator.next().intValue(); roleIterator =
	 * matrice.get(currentxKey).get(currentyKey).iterator(); }
	 */

	/*
	 * public void disconnect(R2 r2, Geometry zone){
	 * 
	 * 
	 * if(zone instanceof Polygon){
	 * 
	 * disconnectPolygon((Polygon)zone, r2); } if(zone instanceof MultiPolygon) {
	 * 
	 * MultiPolygon mp = (MultiPolygon)zone; for(int k = 0; k <
	 * mp.getNumGeometries(); k++){
	 * 
	 * disconnectPolygon((Polygon)mp.getGeometryN(k), r2); }
	 * 
	 * 
	 * } if(zone instanceof Line){
	 * 
	 * disconnectLine((Line)zone, r2); }
	 * 
	 * if(zone instanceof MultiLine) { MultiLine ml = (MultiLine)zone; for(int i =
	 * 0; i < ml.getNumGeometries(); i++){ disconnectLine((Line)ml.getGeometryN(i),
	 * r2);
	 * 
	 * }
	 * 
	 * 
	 * } if(zone instanceof Point){
	 * 
	 * 
	 * int[] ic = grid.gridCoordinate(zone.getCentroid().getX(),
	 * zone.getCentroid().getY()); disconnect(ic[0], ic[1], r2);
	 * 
	 * }
	 * 
	 * if(zone instanceof MultiPoint) { MultiPoint mp = (MultiPoint)zone; for(int i
	 * = 0; i < mp.getNumGeometries(); i++){
	 * 
	 * int[] ic = grid.gridCoordinate(mp.getGeometryN(i).getCentroid().getX(),
	 * mp.getGeometryN(i).getCentroid().getY()); disconnect(ic[0], ic[1], r2); }
	 * 
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * public void disconnectPolygon(Polygon polygon, R2 role){
	 * 
	 * int bounds[] = grid.intBounds(polygon); int index = 0; for(int i = bounds[0];
	 * i < bounds[2]; i++) { for(int j = bounds[1]; j < bounds[3]; j++) { Coordinate
	 * c = grid.gridCoordinate(i, j); Point point = Point.xy(Double.valueOf(c.x),
	 * Double.valueOf(c.y)); if(polygon.touches(point) || point.within(polygon)) {
	 * disconnect( i , j, role); index ++;
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * if(index == 0) { int scaledCentroid[] =
	 * grid.gridCoordinate(polygon.getCentroid().getX(),
	 * polygon.getCentroid().getY()); disconnect(scaledCentroid[0],
	 * scaledCentroid[1], role); }
	 * 
	 * 
	 * }
	 */

	public void disconnectLine(Line line, R2 role) {

		Coordinate coordinates[] = line.getCoordinates();
		for (int i = 0; i < coordinates.length - 1; i++) {
			int c1[] = grid.gridCoordinate(coordinates[i].x, coordinates[i].y);
			int c2[] = grid.gridCoordinate(coordinates[i + 1].x, coordinates[i + 1].y);
			int x = c1[0];
			int y = c1[1];
			int x2 = c2[0];
			int y2 = c2[1];
			disconnectBresenham(x, y, x2, y2, role);
		}

	}

	public void disconnectBresenham(int x1, int y1, int x2, int y2, R2 role) {
		if (x1 == x2 && y1 == y2) {
			add((AbstractEntity) role, x1 - grid.getMinX(), y1 - grid.getMinY());
		} else {
			int dx = Math.abs(x2 - x1);
			int dy = Math.abs(y2 - y1);
			int rozdil = dx - dy;
			int posun_x;
			if (x1 < x2)
				posun_x = 1;
			else
				posun_x = -1;
			int posun_y;
			if (y1 < y2)
				posun_y = 1;
			else
				posun_y = -1;
			while (x1 != x2 || y1 != y2) {
				int p = 2 * rozdil;
				if (p > -dy) {
					rozdil -= dy;
					x1 += posun_x;
				}
				if (p < dx) {
					rozdil += dx;
					y1 += posun_y;
				}
				// disconnect( x1, y1, role);
			}
		}
	}

	/*
	 * public void disconnect(int x, int y, R2 role){ if(matrice.containsKey(x)){
	 * if(matrice.get(x).containsKey(y)){ if(matrice.get(x).get(y).contains(role)){
	 * matrice.get(x).get(y).remove(role); } } } }
	 */
	public Grid getGrid() {
		return grid;
	}

	private Geometry getCellShape(Point p) {
		if (grid.getCellShapeType().equals("HEXAGONAL")) {

		}
		if (grid.getCellShapeType().equals("QUADRILATERAL")) {

			double dx = p.getX();
			double dy = p.getY();

			Coordinate[] coords = new Coordinate[5];
			coords[0] = new Coordinate(dx + grid.getXRes() / 2, dy + grid.getYRes() / 2);
			coords[1] = new Coordinate(dx + grid.getXRes() / 2, dy - grid.getYRes() / 2);
			coords[2] = new Coordinate(dx - grid.getXRes() / 2, dy - grid.getYRes() / 2);
			coords[3] = new Coordinate(dx - grid.getXRes() / 2, dy + grid.getYRes() / 2);
			coords[4] = new Coordinate(dx + grid.getXRes() / 2, dy + grid.getYRes() / 2);
			return toPolygon(coords);
		}

		return null;
	}

	private Polygon toPolygon(Coordinate[] c) {

		CoordinateSequence cs = new CoordinateArraySequence(c);

		LinearRing lr = new LinearRing(cs, new OceletGeomFactory());

		Polygon poly = new Polygon(lr, null, new OceletGeomFactory());

		return poly;

	}

	public void morph(Double buffer) {

		HashMap<R2, ArrayList<Geometry>> geoms = new HashMap<R2, ArrayList<Geometry>>();

		for (R2 r : added.keySet()) {

			for (Integer[] coords : added.get(r)) {

				Coordinate wCoord = grid.gridCoordinate(coords[0], coords[1]);

				Point p = Point.xy(wCoord.x, wCoord.y);
				if (geoms.containsKey(r)) {
					geoms.get(r).add(getCellShape(p));
				} else {

					ArrayList<Geometry> gList = new ArrayList<Geometry>();
					gList.add(getCellShape(p));
					geoms.put(r, gList);
				}
			}
		}

		if (!geoms.isEmpty())
			convert2(geoms, buffer);

		added.clear();
	}

	public void convert2(HashMap<R2, ArrayList<Geometry>> geoms, Double distance) {

		for (R2 role : geoms.keySet()) {

			Geometry initG = ((Geometry) role.getSpatialType()).buffer(0.0);
			Geometry[] gs = new Geometry[geoms.get(role).size()];

			for (int i = 0; i < geoms.get(role).size(); i++) {

				gs[i] = geoms.get(role).get(i);

			}

			GeometryCollection gc = new GeometryCollection(gs, SpatialManager.geometryFactory());

			Geometry g3 = gc.buffer(0.0);

			Geometry[] gs2 = new Geometry[g3.getNumGeometries() + 1];
			gs2[0] = initG;

			for (int i = 0; i < g3.getNumGeometries(); i++) {

				gs2[i + 1] = g3.getGeometryN(i);

			}

			GeometryCollection gc2 = new GeometryCollection(gs2, SpatialManager.geometryFactory());
			Geometry g = gc2.buffer(0.0);

			String name = role.getPropName(role.getSpatialType());

			for (R2 role2 : geoms.keySet()) {

				if (!role.equals(role2)) {

					Geometry g2 = (Geometry) role2.getSpatialType();

					if (g.intersects(g2)) {

						g2 = g2.difference(g);

						g = g.difference(g2);

						if (g2 instanceof MultiPolygon && role2.getSpatialType() instanceof MultiPolygon) {
							role2.setProperty(name, g2);
						}

						if (g2 instanceof Polygon && role2.getSpatialType() instanceof MultiPolygon) {

							Polygon[] ps = new Polygon[1];
							ps[0] = (Polygon) g2;
							MultiPolygon mp = new MultiPolygon(ps, SpatialManager.geometryFactory());

							role2.setProperty(name, mp);
						}

					}
				}
			}

			if (g instanceof MultiPolygon && role.getSpatialType() instanceof MultiPolygon) {

				role.setProperty(name, g);
			}

			if (g instanceof Polygon && role.getSpatialType() instanceof MultiPolygon) {
				Polygon[] ps = new Polygon[1];
				ps[0] = (Polygon) g;
				MultiPolygon mp = new MultiPolygon(ps, SpatialManager.geometryFactory());

				role.setProperty(name, mp);
			}

		}
	}
	/*
	 * public Iterator<R1> getCells(){ return cellIterator; }
	 */

}
