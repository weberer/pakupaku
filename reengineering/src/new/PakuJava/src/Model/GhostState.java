package Model;

public enum GhostState
{
    scatter,
    chase,
    flee,
    eaten;


    public static String castState(GhostState state){
        if(state.equals(scatter) || state.equals(chase)){
            return "normal";
        }
        else if(state.equals(flee)){
            return "scared";
        }
        else if(state.equals(eaten)){
            return "eyes";
        }
        return "";
    }
}
