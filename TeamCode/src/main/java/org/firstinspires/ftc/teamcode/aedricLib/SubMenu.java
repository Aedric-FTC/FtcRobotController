package org.firstinspires.ftc.teamcode.aedricLib;

public class SubMenu {
    //region Variables
    public Menu subMenu;
    Menu superMenu;
    ButtonOperation openSubMenu;
    ButtonOperation closeSubMenu;
    //endregion
    public SubMenu(Menu enclosingMenu, String subMenuName)
    {
        this.superMenu = enclosingMenu;
        subMenu = new Menu(this.superMenu.opMode, subMenuName);
        subMenu.subMenuName = subMenuName;
        superMenu.children.add(subMenu);
        subMenu.runClose = false;
        this.superMenu.addLine(subMenu.subMenuName);
        openSubMenu = new ButtonOperation(this.superMenu.opMode.gamepad1, GamepadButton.A, () -> {
            subMenu.outputToggle = true;
            subMenu.inMenu = true;
            subMenu.runOnMenuEnter();
            subMenu.menuEnter();
        });
        closeSubMenu = new ButtonOperation(this.superMenu.opMode.gamepad1, GamepadButton.B, () -> {
            superMenu.outputToggle = true;
            superMenu.inMenu = true;
            superMenu.runOnMenuEnter();
            superMenu.menuEnter();
        });
        this.superMenu.subMenuRegistry.put(subMenu.subMenuName, subMenu);
    }
    public void create()
    {
        //subMenu.create();
        superMenu.getItem(subMenu.subMenuName).displayLine();
        //subMenu.getItem(this.subMenu.subMenuName).itemNumber = this.superMenu.numberOfMenuItems;
        if (superMenu.inMenu)
        {
            if (subMenu.getItem(subMenu.subMenuName).itemNumber == this.superMenu.menuCounter)
            {
                openSubMenu.run();
            }
        }
        else if (subMenu.inMenu)
        {
            closeSubMenu.run();
        }
    }
}