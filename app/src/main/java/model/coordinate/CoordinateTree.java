package model.coordinate;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

import interfaces.Orientation;

/**
 * @deprecated Old version for the implementation of the foods in the board 
 */
public class CoordinateTree<Type extends Number & Comparable<Type>,O extends Orientation<O>, Value> {

    private TreeMap<Coordinate<Type, O>, Value> map = new TreeMap<>(new CoordinateComparator());

    public boolean add(Coordinate<Type, O> coordinate, Value value) {
        return map.put(coordinate, value) == null;
    }

    public boolean remove(Coordinate<Type, O> coordinate) {
        return map.remove(coordinate) != null;
    }

    public boolean contains(Coordinate<Type, O> coordinate) {
        return map.containsKey(coordinate);
    }

    public Coordinate<Type, O> getClosest(Coordinate<Type, O> coordinate) {
        Coordinate<Type, O> closestCoordinate = null;
        double minDistance = Double.MAX_VALUE;
    
        for (Coordinate<Type, O> c : map.keySet()) {
            double distance = coordinate.distanceTo(c);
    
            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = c;
            }
        }
    
        return closestCoordinate;
    }

    public Value get(Coordinate<Type, O> coordinate) {
        return map.get(coordinate);
    }

    public Set<Coordinate<Type, O>> keySet() {
        return map.keySet();
    }

    public Collection<Value> getAllFood() {
        return map.values();
    }

    private class CoordinateComparator implements Comparator<Coordinate<Type, O>> {
        @Override
        public int compare(Coordinate<Type, O> c1, Coordinate<Type, O> c2) {
            double distance1 = Math.sqrt(Math.pow(c1.getX().doubleValue(), 2) +
                                         Math.pow(c1.getY().doubleValue(), 2));
            double distance2 = Math.sqrt(Math.pow(c2.getX().doubleValue(), 2) +
                                         Math.pow(c2.getY().doubleValue(), 2));

            return Double.compare(distance1, distance2);
        }
    }

    public int size() {
        return map.size();
    }
}