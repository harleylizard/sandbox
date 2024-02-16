package com.harleylizard.sandbox.entity;

import org.joml.Vector3f;

public sealed interface Entity permits Player {

    Vector3f getPosition();
}
