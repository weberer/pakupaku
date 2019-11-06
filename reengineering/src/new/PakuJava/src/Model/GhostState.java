package Model;

public enum GhostState
{
    scatter, //ghost's initial state after exiting jail; ghosts move toward the corners
    chase, //Ghosts try to pursue and eat paku
    flee, //Paku has eateb super dot, ghosts in "scared" blue state
    eaten; //ghost has just been eaten, heads to jail as a pair of eyeballs

    public static String castState(GhostState state){
        if(state.equals(scatter) || state.equals(chase)){
            return "normal";
        }
        else if(state.equals(flee)){
            return "scared";
        }
        else if(state.equals(eaten)){
            return "eaten";
        }
        return "";
    }
}
