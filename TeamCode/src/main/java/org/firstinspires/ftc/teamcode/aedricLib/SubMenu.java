package org.firstinspires.ftc.teamcode.aedricLib;

public class SubMenu {
    //region Variables
    public Menu subMenu;
    Menu superMenu;
    MenuItem subMenuItem;
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
        subMenu.addLine(subMenu.subMenuName);
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
        this.superMenu.subMenuRegistry.put(subMenuName, subMenu);
        subMenuItem = subMenu.getItem(subMenuName);
    }
    public void create()
    {
        //subMenu.create();
        subMenuItem.displayLine();
        //subMenu.getItem(this.subMenu.subMenuName).itemNumber = this.superMenu.numberOfMenuItems;
        if (superMenu.inMenu)
        {
            if (subMenuItem.itemNumber == this.superMenu.menuCounter)
            {
                if (superMenu.opMode.gamepad1.a) openSubMenu.run();
            }
        }
        else if (subMenu.inMenu)
        {
            if (superMenu.opMode.gamepad1.b) closeSubMenu.run();
        }
    }
}