# 🤖 Robot Hardware Port Map

This document serves as the visual master configuration for our robot. When adding or changing physical cables, update this chart and verify that the names match the configuration on the Driver Station phone/Control Hub precisely.

```mermaid
graph LR
    %% Styles and Colors %%
    classDef hub fill:#2A2A2A,stroke:#FFCC00,stroke-width:3px,color:#FFF;
    classDef motor fill:#1F77B4,stroke:#333,stroke-width:2px,color:#FFF;
    classDef servo fill:#2CA02C,stroke:#333,stroke-width:2px,color:#FFF;
    classDef sensor fill:#9467BD,stroke:#333,stroke-width:2px,color:#FFF;

    %% Control Hub Layout %%
    subgraph ControlHub [REV Control Hub]
        direction TB
        
        subgraph Motors [DC Motor Ports]
            M0[Port 0]
            M1[Port 1]
            M2[Port 2]
            M3[Port 3]
        end

        subgraph Servos [Servo Ports]
            S0[Port 0]
            S1[Port 1]
            S2[Port 2]
        end

        subgraph I2C [I2C Buses]
            I2C0[Bus 0]
            I2C1[Bus 1]
        end

        subgraph Digital [Digital I/O]
            D0[Port 0]
        end
    end

    %% Physical Connections %%
    M0 -->|Cable| FL["Drive Front Left (leftFront)"]
    M1 -->|Cable| FR["Drive Front Right (rightFront)"]
    M2 -->|Cable| BL["Drive Back Left (leftBack)"]
    M3 -->|Cable| BR["Drive Back Right (rightBack)"]

    S0 -->|Cable| Claw["Claw Servo (claw)"]
    S1 -->|Cable| Wrist["Wrist Servo (wrist)"]

    I2C1 -->|Cable| Distance["Color/Distance Sensor (sensorDistance)"]
    D0   -->|Cable| Limit["Arm Limit Switch (limitSwitch)"]

    %% Assigning Classes for Visual Distinction %%
    class ControlHub hub;
    class FL,FR,BL,BR motor;
    class Claw,Wrist servo;
    class Distance,Limit sensor;
```

## 📋 Hardware Details Table

| Hub Unit | Port Type | Port # | Device Name in Code | Physical Hardware Component | Notes / Target Subsystem |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Control Hub | Motor | 0 | `"leftFront"` | GoBILDA 5203 (19.2:1) | Mecanum Drivetrain |
| Control Hub | Motor | 1 | `"rightFront"` | GoBILDA 5203 (19.2:1) | Mecanum Drivetrain |
| Control Hub | Motor | 2 | `"leftBack"` | GoBILDA 5203 (19.2:1) | Mecanum Drivetrain |
| Control Hub | Motor | 3 | `"rightBack"` | GoBILDA 5203 (19.2:1) | Mecanum Drivetrain |
| Control Hub | Servo | 0 | `"claw"` | REV Smart Servo | Intake Mechanism |
| Control Hub | Servo | 1 | `"wrist"` | GoBILDA Torque Servo | Intake Articulation |
| Control Hub | I2C | 1 | `"sensorDistance"`| REV Color Sensor V3 | Autonomous Navigation |
| Control Hub | Digital | 0 | `"limitSwitch"` | REV Touch Sensor | Lift Safety Boundary |
