# FTC Driver Station Menu
Note: All the following code must be
placed in your loop() function.

First, in your dependencies in
teamcode/build.gradle, add this:
```java
    implementation 'com.github.Aedric-FTC.FtcRobotController:TeamCode:Menu-SNAPSHOT'
```
## Menu Button
```java
menu.setMenuMode();
```
When the "start" button on your gamepad
is pressed, the menu will open.

It is recommended to use an "if" 
statement so that the main code will
not run while in the menu to allow the
menu navigation buttons to be used
without conflicting.

## Setting the Counter
```java
menu.setMenuCounter(itemCount);
```
This sets which menu item is selected.
itemCount is the amount of items in the
menu.

## Creating a Menu Item
```java
menu.setMenuItem(menuNumber, itemName, 
                 originalInput, increment,
                 min, max);
```
Creates a double item in the menu.
menuNumber is the place on the menu where
the item will appear. itemName is the name
of the item that you want to display.
originalInput is the original value of
item. increment is the increment by which
the value will increase and decrease. min
is the minimum value. max is the maximum
value.
