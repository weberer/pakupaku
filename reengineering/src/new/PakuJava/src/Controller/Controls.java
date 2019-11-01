package Controller;

import Model.Direction;

public enum Controls {
    arrow_up("arrow_up"),
    arrow_down("arrow_down"),
    arrow_left("arrow_left"),
    arrow_right("arrow_right"),
    W("W"),
    A("A"),
    S("S"),
    D("D"),
    escape("escape"),
    O("O"),
    enter("enter"),
    none("none");

    private String name;

    Controls(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Controls getControl(String input) {
        if(input == null)
            return none;
        switch(input){
            case "ArrowUp": return arrow_up;
            case "ArrowDown": return arrow_down;
            case "ArrowLeft": return arrow_left;
            case "ArrowRight": return arrow_right;
            case "KeyW": return W;
            case "KeyA": return A;
            case "KeyS": return S;
            case "KeyD": return D;
            case "Escape": return escape;
            case "KeyO": return O;
            case "Enter": return enter;
            case "NumpadEnter": return enter;
            case "none": return none;
        }
        return null;
    }

    public static Direction castToDir(Controls control)
    {
        if(control == null)
            return Direction.stay;
        if(control.equals(Controls.arrow_up) || control.equals(Controls.W))
        {
            return Direction.up;
        }
        else if(control.equals(Controls.arrow_down) || control.equals(Controls.S))
        {
            return Direction.down;
        }
        else if(control.equals(Controls.arrow_left) || control.equals(Controls.A))
        {
            return Direction.left;
        }
        else if(control.equals(Controls.arrow_right) || control.equals(Controls.D))
        {
            return Direction.right;
        }
        else
            return Direction.stay;
    }



}
