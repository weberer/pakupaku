package Model;

public enum Direction
{
    up,
    down,
    left,
    right,
    stay;

    @Override
    public String toString() {
        switch(this)
        {
            case up :
                return "up";

            case down :
                return "down";


            case left :
                return "left";


            case right :
                return "right";


            case stay :
                return "stay";


        }
        return "";
    }
}
