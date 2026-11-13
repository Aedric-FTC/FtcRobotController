package org.firstinspires.ftc.teamcode.aedricLib;

public class SubMenu {
    //region Variables
    public Menu subMenu;
    Menu superMenu;
    public MenuItem subItem;
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
        subItem = new MenuItem(superMenu, subMenu.subMenuName);
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
    }
    public void create()
    {
        subMenu.create();
        subItem.itemNumber = this.superMenu.numberOfMenuItems;
        if (superMenu.inMenu)
        {
            if (subItem.itemNumber == this.superMenu.menuCounter)
            {
                this.superMenu.opMode.telemetry.addLine(">  " + subItem.itemName);
                openSubMenu.run();
            } else
            {
                this.superMenu.opMode.telemetry.addLine(subItem.itemName);
            }
        }
        else if (subMenu.inMenu)
        {
            closeSubMenu.run();
        }
    }
}