package com.harleylizard.sandbox.graphics;

import com.harleylizard.sandbox.Resources;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.io.IOException;

import static org.lwjgl.opengl.GL45.*;

public final class ProgramPipeline {
    private final int pipeline;

    private ProgramPipeline(int pipeline) {
        this.pipeline = pipeline;
    }

    public void bind() {
        glBindProgramPipeline(pipeline);
    }

    public static void unbind() {
        glBindProgramPipeline(0);
    }

    public static final class Builder {
        private final Object2IntMap<Shader> programs = new Object2IntArrayMap<>();
        private final Object2IntMap<String> buffers = new Object2IntArrayMap<>();

        private final int pipeline = glCreateProgramPipelines();

        public Builder attach(Shader shader, String path) {
            try {
                var program = glCreateShaderProgramv(shader.getType(), Resources.readString(path));
                glUseProgramStages(pipeline, shader.getBit(), program);
                programs.put(shader, program);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public ProgramPipeline build() {
            glValidateProgramPipeline(pipeline);
            return new ProgramPipeline(pipeline);
        }
    }
}
