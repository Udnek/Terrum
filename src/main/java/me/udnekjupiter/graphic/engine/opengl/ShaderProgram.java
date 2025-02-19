package me.udnekjupiter.graphic.engine.opengl;

import me.udnekjupiter.util.Initializable;
import org.jetbrains.annotations.NotNull;

import static org.lwjgl.opengl.GL32.*;

public class ShaderProgram implements Initializable {

    protected int vertex;
    protected int fragment;
    protected Integer program = null;

    @Override
    public void initialize(){

        program = glCreateProgram();

        vertex = createShader("""
                    #version 150 core
                    
                    in vec3 position;
                    in vec3 color;
                    in vec2 texcoord;
                    
                    out vec3 vertexColor;
                    out vec2 textureCoord;
                    
                    uniform mat4 model;
                    uniform mat4 view;
                    uniform mat4 projection;
                    
                    void main() {
                        vertexColor = color;
                        textureCoord = texcoord;
                        mat4 mvp = projection * view * model;
                        gl_Position = mvp * vec4(position, 1.0);
                    }
                    """,
                GL_VERTEX_SHADER);

        fragment = createShader("""
                    #version 150 core
                    
                    in vec3 vertexColor;
                    in vec2 textureCoord;
                    
                    out vec4 fragColor;
                    
                    uniform sampler2D texImage;
                    
                    void main() {
                        vec4 textureColor = texture(texImage, textureCoord);
                        fragColor = vec4(vertexColor, 1.0) * textureColor;
                    }
                    """,
                GL_FRAGMENT_SHADER);


        glBindFragDataLocation(program, 0, "fragColor");
        glLinkProgram(program);
        int status = glGetProgrami(program, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(program));
        }
        glUseProgram(program);

        System.out.println("SHADERS INITIALIZED");
    }

    public void clear(){
        glDeleteShader(vertex);
        glDeleteShader(fragment);
        glDeleteProgram(program);
    }


    protected int createShader(@NotNull String shaderData, int shaderType){
        int id = glCreateShader(shaderType);
        glShaderSource(id, shaderData);
        glCompileShader(id);
        glAttachShader(program, id);
        return id;
    }

    public int getProgram() {
        if (program == null) throw new RuntimeException("Shader has not been initialized");
        return program;
    }
}
