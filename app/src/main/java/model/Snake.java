package model;

import java.util.ArrayList;

import execption.ExecptionMoveInvalid;

public class Snake {

    private Coordinate head;
    private ArrayList<Coordinate> tail;
    private Plateau plateau;

    public Snake(Coordinate head,Plateau plateau) {
        this.head = head;
        this.tail = new ArrayList<Coordinate>();

        Coordinate tail1 = head.CoordinateOf(Direction.randomDirection());
        tail.add(tail1);

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    public Coordinate getHead() {
        return head;
    }

    public Coordinate[] getTail() {
        Coordinate[] res = new Coordinate[tail.size()];
        for(int i = 0; i < tail.size(); i++) {
            res[i] = new Coordinate(tail.get(i).getX(), tail.get(i).getY());
        }
        return res;
    }

    public void move() throws ExecptionMoveInvalid{
        Direction direction = Direction.randomDirection();
        Coordinate newHead = head.CoordinateOf(direction);
        if (plateau.isFree(newHead)) {
            head = newHead;
            tail.add(0, head);
            if(plateau.isEatingFood(head)){
                plateau.update(this,null);
            }
            else{
                Coordinate last = tail.get(tail.size() - 1);
                tail.remove(tail.size() - 1);
                plateau.update(this, last);
            }
        }
        else{
            throw new ExecptionMoveInvalid("Move invalid");
        }
        
    }
}
